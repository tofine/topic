package com.exercise.controller;

import com.exercise.bean.UserInfo;
import com.exercise.service.LoginCheckService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    private LoginCheckService loginCheckService;

    @PostMapping("/login")
    public UserInfo login(@RequestBody UserInfo account){
         return loginCheckService.check(account);
    }

    @Autowired
    private void setLoginCheckService(LoginCheckService loginCheckService){
        this.loginCheckService=loginCheckService;
    }

}
