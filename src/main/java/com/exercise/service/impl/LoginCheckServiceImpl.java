package com.exercise.service.impl;

import com.exercise.bean.UserInfo;
import com.exercise.service.LoginCheckService;
import com.exercise.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginCheckServiceImpl implements LoginCheckService {

    private UserInfoService userInfoService;

    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }

    @Override
    @Transactional
    public UserInfo check(UserInfo account) {
        UserInfo user=userInfoService.findUserById(account.getId());
        if(user==null||!user.getPassword().equals(account.getPassword())){
            return null;
        }
        return user;
    }
}
