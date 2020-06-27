package com.exercise.controller;

import com.exercise.bean.TableRender;
import com.exercise.bean.UserInfo;
import com.exercise.service.UserInfoService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserInfoController {

    private UserInfoService userInfoService;
    @Autowired
    public void setUserInfoService(UserInfoService userInfoService) {
        this.userInfoService = userInfoService;
    }
    /**
     * 注册新用户
     * @param userInfo
     * @return
     */
    @PostMapping("/user")
    public int addUser(@RequestBody UserInfo userInfo){
        return userInfoService.addUser(userInfo);
    }

    @PutMapping("/user")
    public int changeUser(@RequestBody UserInfo userInfo){
        return userInfoService.updateUser(userInfo);
    }

    @DeleteMapping("/user/{id}")
    public int deleteUser(@PathVariable("id") int id){
        return userInfoService.deleteUser(id);
    }

    @GetMapping("/user/{id}")
    public UserInfo getUser(@PathVariable("id") int id){
        return userInfoService.findUserById(id);
    }

    /**
     * 分页查询用户信息表
     * @param page 第几页
     * @param limit 多少条
     * @return
     */
    @GetMapping("/userList")
    public TableRender<UserInfo> onePageUser(@Param(value = "page") int page, @Param(value = "limit")int limit){
        return userInfoService.getOnePage(page,limit);
    }
}
