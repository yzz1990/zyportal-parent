package com.zkzy.zyportal.system.provider.serviceimpl;

import com.zkzy.portal.common.service.service.CrudService;
import com.zkzy.zyportal.system.api.entity.TripUser;
import com.zkzy.zyportal.system.api.service.ITripUserService;
import com.zkzy.zyportal.system.provider.mapper.TripUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 用户服务实现
 *
 * @author admin
 */
@Service
@Transactional(readOnly = true)
public class TripUserService extends CrudService<TripUserMapper, TripUser> implements ITripUserService {

    @Override
    public TripUser getByMobile(String mobile) {
        TripUser tu=getDao().getByMobile(mobile);
        return tu;
    }

    @Override
    @Transactional(readOnly = false)
    public void updateInfo(TripUser tripUser) {
        tripUser.preUpdate();
        getDao().updateInfo(tripUser);
    }

    @Override
    @Transactional(readOnly = false)
    public void registryUser(String mobile, String password) {
        // 用户已存在不做处理，防止客户端重复提交
        TripUser oldUser = getByMobile(mobile);
        if (oldUser != null) {
            return;
        }

        //插入用户信息
        TripUser user = new TripUser();
        user.preInsert();
        user.setMobile(mobile);
        user.setPassword(password);
        getDao().insert(user);
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePasswordByMobile(String mobile, String password) {
        getDao().updatePasswordByMobile(mobile, password);
    }

    @Override
    @Transactional(readOnly = false)
    public void updatePhotoByUserId(String userId, String photo) {
        getDao().updatePhotoByUser(userId, photo);
    }

}
