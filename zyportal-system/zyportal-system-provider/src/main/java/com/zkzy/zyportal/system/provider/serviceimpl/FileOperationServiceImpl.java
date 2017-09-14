package com.zkzy.zyportal.system.provider.serviceimpl;

import com.sun.star.io.ConnectException;
import com.zkzy.portal.common.utils.FileUtil;
import com.zkzy.zyportal.system.api.entity.SystemNodefile;
import com.zkzy.zyportal.system.api.service.FileOperationService;
import com.zkzy.zyportal.system.provider.mapper.SystemNodefileMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

@Service("fileOperationServiceImpl")
@ConfigurationProperties(prefix="fileUpload.zyportalclient")
public class FileOperationServiceImpl implements FileOperationService {
    private static final Logger LOGGER = LoggerFactory.getLogger(FileOperationServiceImpl.class);
    private static final String NEEDCONVERTFORMAT="'xlsOffice','docOffice'";//需要转换的类型
    private static Vector<SystemNodefile> fileQue=new Vector<SystemNodefile>();
    private static final ExecutorService pool=Executors.newFixedThreadPool(5);;
    private static final String CONVERTING="converting";//正在转换中的状态

    private static String url;


    @Autowired
    private SystemNodefileMapper systemNodefileMapper;


    /**
     * office转pdf
     */
    @Override
    public void office2PDF() {
        findOffice2PDF();//获取当前待转换的文件队列
        LOGGER.info("开始转换offcie文件,待转换个数:"+fileQue.size());
        if(fileQue.size()>0){
            for(int i=0;i<fileQue.size();i++){
                if(!CONVERTING.equals(fileQue.get(i).getStatus())){//不是正在转换中的
                    fileQue.get(i).setStatus(CONVERTING);
                    executeOffice2PDF(fileQue.get(i));
                }else{//正在转换中,跳过

                }
            }
        }
//        LOGGER.info("啊啊啊啊啊啊啊啊");
    }

    /**
     * 查询需要office转pdf的文件,加入转换队列
     */
    @Override
    public void findOffice2PDF() {
        String param=" and status='false' and filetype in ("+NEEDCONVERTFORMAT+") ";
        List<SystemNodefile> fileList =systemNodefileMapper.selectAll(param);
        if(fileList!=null){
            for(SystemNodefile file:fileList){
                if(isContains(file)){//已经存在队列中

                }else{
                    addQue(file);
                }

//                LOGGER.info("队列中是否存在文件"+fileQue.contains(file));
//                if(fileQue.contains(file)){//已经存在队列中
//
//                }else{//添加到队列
//                    addQue(file);
//                }

            }
        }
    }


    /**
     * 执行office转pdf
     * @param file
     */
    public void executeOffice2PDF(SystemNodefile file) {
        if(pool!=null){
            try {
                pool.submit(new Runnable() {
                    @Override
                    public void run() {

                        int flag = FileUtil.office2PDF(url+file.getFileurl(),url+file.getPdfpath());
//                        System.out.println(url);
                        if(flag==0){//转换成功
                            file.setStatus("true");
                            systemNodefileMapper.updateByPrimaryKey(file);
                            removeQue(file);
                            LOGGER.info(file.getBasename()+"转pdf成功");
                        }else if(flag==-2){//服务未启动,先从队列移除,等待下次加入队列
                            removeQue(file);
                            LOGGER.warn("服务未启动");
                        }else if(flag==-1){//文件不存在,移除队列,不再转pdf
                            file.setStatus("Non-Existent");
                            systemNodefileMapper.updateByPrimaryKey(file);
                            removeQue(file);
                            LOGGER.warn(file.getBasename()+"文件不存在");
                        }else if(flag==-3){//失败,先从队列移除,等待下次加入队列
                            removeQue(file);
                            LOGGER.warn(file.getBasename()+"转pdf失败");
                        }else{//,先从队列移除,等待下次加入队列
                            removeQue(file);
                            LOGGER.warn(file.getBasename()+"转pdf其他异常");
                        }
                    }
                });
            } catch (Exception e) {//先从队列移除,等待下次加入队列
                removeQue(file);
                LOGGER.error(file.getBasename()+"转pdf失败");
                LOGGER.error(e.getMessage());
            }
        }

    }

    /**
     * 添加到队列
     */
    public static void addQue(SystemNodefile file) {
        if(fileQue!=null){
            fileQue.add(file);
        }
    }

    /**
     * 从队列中删除
     */
    public static void removeQue(SystemNodefile file) {
        if(fileQue!=null && fileQue.size()>0 && fileQue.contains(file)){
            fileQue.remove(file);
        }
    }

    /**
     * 是否存在队列中
     * @param file
     * @return
     */
    public static boolean isContains(SystemNodefile file){
        boolean b = false;
        if(fileQue!=null){
            if(fileQue.size()==0){
                b=false;
            }else{
                for(SystemNodefile q:fileQue){
                    if(q.getId().equals(file.getId())){//存在队列中
                        b=true;
                        break;
                    }
                }
            }
        }else{
            b= false;
        }
        return b;
    }

    public static synchronized void shutdown() {
        if(pool!=null){
            pool.shutdown();
        }
    }

    public static String getUrl() {
        return url;
    }
    public static void setUrl(String url) {
        FileOperationServiceImpl.url = url;
    }

}

