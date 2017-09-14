package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.upload.DiskFileOperator;
import com.zkzy.portal.common.utils.DateHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.WeChatMessage;
import com.zkzy.zyportal.system.api.entity.WeChatTag;
import com.zkzy.zyportal.system.api.entity.WeChatUser;
import com.zkzy.zyportal.system.api.service.WeChatAccountDevelopInfoService;
import com.zkzy.zyportal.system.api.service.WeChatMessageService;
import com.zkzy.zyportal.system.api.service.WeChatTagService;
import com.zkzy.zyportal.system.api.service.WeChatUserService;
import com.zkzy.zyportal.system.api.util.MessageUtil;
import com.zkzy.zyportal.system.api.util.WeChatUtil;
import com.zkzy.zyportal.system.provider.mapper.WeChatMessageMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static com.zkzy.portal.common.utils.RandomHelper.uuid;

/**
 * Created by Jisy on 2017/7/21.
 */
@Service("weChatMessageService")
public class WeChatMessageServiceImpl implements WeChatMessageService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private WeChatMessageMapper weChatMessageMapper;
    @Autowired
    private WeChatTagService weChatTagService;
    @Autowired
    private WeChatUserService weChatUserService;
    @Autowired
    private WeChatAccountDevelopInfoService weChatAccountDevelopInfoService;

    @Override
    public PageInfo queryMessage(Integer pageNumber, Integer pageSize, String param) {
        PageHelper.startPage(pageNumber,pageSize);
        List<WeChatMessage> list = weChatMessageMapper.selectAll(param);
        //BLOB数据处理
        for(WeChatMessage weChatMessage : list){
            byte[] contents = weChatMessage.getContents();
            try {
                String content = new String(contents);
                weChatMessage.setContent(content);
            } catch ( Exception e) {
                e.printStackTrace();
            }
        }
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @Override
    public CodeObject addMessage(WeChatMessage weChatMessage) {
        //获取消息发送时间，取当前时间
        Date date = new Date();
        String datestr = DateHelper.formatDateTime(date);
        weChatMessage.setSendtime(datestr);
        String weChatId = "";
        if(weChatMessage.getWechatid()!=null&& weChatMessage.getWechatid().trim().length()>0){
            weChatId = weChatMessage.getWechatid();
        }
        //获取access_token
        String access_token = redisTemplate.opsForValue().get(weChatId);
        if(access_token == null){
            access_token = WeChatUtil.getAccess_token(weChatAccountDevelopInfoService,weChatId);
            redisTemplate.opsForValue().set(weChatId,access_token);
        }

        if(weChatMessage !=null){
            weChatMessage.setId(uuid());
            //数据处理操作（openids,tagid,msgtype）
            if(weChatMessage.getMsgtype()!=null){
                String msgtype = weChatMessage.getMsgtype();
                if("text".equals(msgtype)){
                    weChatMessage.setMsgtype("文本消息");
                }else if("image".equals(msgtype)){
                    weChatMessage.setMsgtype("图片消息");
                }else if("video".equals(msgtype)||"mpvideo".equals(msgtype)){
                    weChatMessage.setMsgtype("视频消息");
                }else if("voice".equals(msgtype)){
                    weChatMessage.setMsgtype("语音消息");
                    String fileUrl = weChatMessage.getFileUrl();
                    String fileName = fileUrl.substring(fileUrl.lastIndexOf("/")+1);
                    String amrPath = DiskFileOperator.workFolderName+"/"+fileName;
                    String mp3Path = amrPath.replace(".amr",".mp3");
                    MessageUtil.changeToMp3(amrPath,mp3Path);
                    //message.setFileUrl(fileUrl.replace(".amr",".mp3"));
                }else if("mpnews".equals(msgtype)){
                    weChatMessage.setMsgtype("图文消息");
                    //content处理
                    String content = weChatMessage.getContent();
                    byte[] contents = content.getBytes();
                    weChatMessage.setContents(contents);
                    weChatMessage.setContent(null);
                }else if("music".equals(msgtype)){
                    weChatMessage.setMsgtype("音乐消息");
                }else{
                    weChatMessage.setMsgtype("");
                }
            }
            if(weChatMessage.getTagid()!=null&& weChatMessage.getTagid().trim().length()>0){
                WeChatTag weChatTag = weChatTagService.selectByTagid(Integer.parseInt(weChatMessage.getTagid()));
                weChatMessage.setTagid(weChatTag.getTagname()+"标签组");
            }
            if(weChatMessage.getTouser()!=null&& weChatMessage.getTouser().trim().length()>0){
                String str = weChatMessage.getTouser();
                if(!"".equals(weChatMessage.getType()+"")&& weChatMessage.getType()!=-1){
                    if(weChatMessage.getType()==0){
                        WeChatUser weChatUser = weChatUserService.getUserInfoByOpenid(str,weChatId,access_token);
                        weChatMessage.setTouser(weChatUser.getNickname());
                    }
                    /*  //openids 群发
                    else if(message.getType()==1){
                        str = str.substring(1,str.length()-1);
                        String[] openids = str.split(",");
                        str = "";
                        for(String openid : openids){
                            User user = weChatUserService.getUserInfoByOpenid(str,access_token);
                            str += user.getNickname()+",";
                        }
                        message.setTouser(str.substring(0,str.length()-1));
                    }*/
                }
            }
            weChatMessageMapper.insertSelective(weChatMessage);
            return ReturnCode.MESSAGE_ADD_SUCCESS;
        }else{
            return ReturnCode.MESSAGE_ADD_FAILED;
        }
    }

    @Override
    public CodeObject delMessage(WeChatMessage weChatMessage) {
        if(weChatMessage !=null){
            weChatMessageMapper.deleteByPrimaryKey(weChatMessage.getId());
            return ReturnCode.MESSAGE_DELETE_SUCCESS;
        }else{
            return ReturnCode.MESSAGE_DELETE_FAILED;
        }
    }

}
