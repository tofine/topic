package com.exercise.service;

import com.exercise.bean.TableRender;
import com.exercise.bean.UserInfo;

public interface UserInfoService {

    public int addUser(UserInfo userInfo);

    public TableRender<UserInfo> getOnePage(int page, int limit);

    public int deleteUser(int id);

    public int updateUser(UserInfo userInfo);

    public UserInfo findUserById(int id);

}
