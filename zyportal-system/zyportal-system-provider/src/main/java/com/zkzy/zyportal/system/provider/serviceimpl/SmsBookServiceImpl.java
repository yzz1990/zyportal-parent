package com.zkzy.zyportal.system.provider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zkzy.portal.common.redis.RedisRepository;
import com.zkzy.portal.common.utils.RandomHelper;
import com.zkzy.zyportal.system.api.constant.CodeObject;
import com.zkzy.zyportal.system.api.constant.ReturnCode;
import com.zkzy.zyportal.system.api.entity.SmsBookB;
import com.zkzy.zyportal.system.api.entity.SmsManB;
import com.zkzy.zyportal.system.api.service.ISmsBookService;
import com.zkzy.zyportal.system.api.viewModel.ZtreeSimpleView;
import com.zkzy.zyportal.system.provider.mapper.SmsBookBMapper;
import com.zkzy.zyportal.system.provider.mapper.SmsManBMapper;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 Created by yupc on 2017/4/8.
 */
@Service("smsBookServiceImpl")
public class SmsBookServiceImpl implements ISmsBookService{
    /**
     * 通讯录Mapper
     */
    @Autowired
    private SmsBookBMapper smsBookBMapper;
    @Autowired
    private SmsManBMapper smsManBMapper;

    @Autowired
    private RedisRepository redisRepository;

    /**
     * 获取通讯录树结构
     *
     */
    @Override
    public List<ZtreeSimpleView> getBookZTree(){
        List<ZtreeSimpleView> ztreeSimpleViewList=new ArrayList<ZtreeSimpleView>();
        //所有的通讯人员
        List<SmsBookB> list= smsBookBMapper.selectAll("");
        for(SmsBookB book:list){
            String bookId=book.getId()==null?null:book.getId().toString();
            if(bookId!=null){
                ZtreeSimpleView ztreeSimpleView=new ZtreeSimpleView();
                ztreeSimpleView.setId(bookId);
                ztreeSimpleView.setName(book.getName()==null?null:book.getName());
                ztreeSimpleView.setpId(book.getPid()==null?null:book.getPid().toString());
                ztreeSimpleView.setChecked(false);
                ztreeSimpleView.setOpen(true);
                ztreeSimpleViewList.add(ztreeSimpleView);
            }
        }
        return ztreeSimpleViewList;
    }

    /**
     * 新建通讯录
     *
     * @param book 通讯录信息
     */
    @Override
    public CodeObject addBook(SmsBookB book){
        try {
            if (book.getId()==null || book.getId().trim().length()==0){
                book.setId(RandomHelper.uuid());
                book.setCreatetime(DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                smsBookBMapper.insert(book);
            }else{
                return ReturnCode.AREA_FAILED;//新增失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.AREA_FAILED;//新增失败
        }finally {

        }
        return ReturnCode.AREA_SUCCESS;//新增成功
    }

    /**
     * 修改通讯录
     *
     * @param book 通讯录信息
     */
    @Override
    public CodeObject updateBook(SmsBookB book){
        try {
            if (book.getId()!=null && book.getId().trim().length()>0){
                smsBookBMapper.updateByPrimaryKey(book);
            }else{
                return ReturnCode.UPDATE_FAILED;//更新失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.UPDATE_FAILED;//更新失败
        }finally {

        }
        return ReturnCode.UPDATE_SUCCESS;//更新成功
    }

    /**
     * 删除通讯录
     *
     * @param book 通讯录信息
     */
    @Override
    public CodeObject deleteBook(SmsBookB book){
        try {
            if (book.getId()!=null && book.getId().trim().length()>0){
                List<SmsBookB> listBook = smsBookBMapper.selectAll(" and pid = '"+book.getId()+"'");
                List<SmsManB> listMan = smsManBMapper.selectAll(" and bookid like '%"+book.getId()+"%'");
                if(listBook.size()>0||listMan.size()>0){
                    return  ReturnCode.DELETEBOOK_HAVEMANS;
                }else {
                    smsBookBMapper.deleteByPrimaryKey(book.getId());
                }
            }else{
                return ReturnCode.DEL_FAILED;//删除失败
            }
        }catch (Exception e){
            e.printStackTrace();
            return ReturnCode.DEL_FAILED;//删除失败
        }finally {

        }
        return ReturnCode.DEL_SUCCESS;//删除成功
    }

    @Override
    public PageInfo bookList(String param, Integer pageNumber, Integer pageSize) {
        if(param==null){
            param="";
        }
        PageInfo pageInfo=null;
        try {
            PageHelper.startPage(pageNumber,pageSize);//分页
            List<SmsBookB> list= smsBookBMapper.selectAll(param);
            pageInfo=new PageInfo(list);
            return pageInfo;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
