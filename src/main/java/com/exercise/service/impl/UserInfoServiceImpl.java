package com.exercise.service.impl;

import com.exercise.bean.TableRender;
import com.exercise.bean.UserInfo;
import com.exercise.dao.UserInfoDao;
import com.exercise.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private UserInfoDao userInfoDao;

    @Override
    @Transactional
    public int addUser(UserInfo userInfo) {
        return userInfoDao.addUserInfo(userInfo);
    }

    @Override
    @Transactional
    public TableRender<UserInfo> getOnePage(int page, int limit) {
       TableRender<UserInfo> tableRender=new TableRender<>();
        List<UserInfo> data=userInfoDao.getPage((page-1)*limit,limit);
        int count=userInfoDao.getCount();
        tableRender.setCount(count);
        tableRender.setData(data);
        return tableRender;
    }

    @Override
    @Transactional
    public int deleteUser(int id) {
        return userInfoDao.deleteUserInfo(id);
    }

    @Override
    @Transactional
    public int updateUser(UserInfo userInfo) {
        return userInfoDao.changeUserInfo(userInfo);
    }

    @Override
    @Transactional
    public UserInfo findUserById(int id) {
        return userInfoDao.getUserInfo(id);
    }

    @Autowired
    @Transactional
    public void setUserInfoDao(UserInfoDao userInfoDao){
        this.userInfoDao=userInfoDao;
    }
}
