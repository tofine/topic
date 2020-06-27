package com.exercise.dao;

import com.exercise.bean.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInfoDao {

    public int addUserInfo(@Param("user") UserInfo userInfo);

    public int getCount();//获取用户信息总数

    public List<UserInfo> getPage(@Param("begin")int begin,@Param("number") int number);

    public int deleteUserInfo(int id);

    public int changeUserInfo(UserInfo userInfo);

    public UserInfo getUserInfo(int id);

}
