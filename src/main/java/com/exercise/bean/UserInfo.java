package com.exercise.bean;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.io.IOException;

@JsonSerialize(using=UserSerializer.class)
public class UserInfo {

    private Integer id;
    private String password;
    private String name;
    private Integer gender;
    private String phone;
    private Integer role;

    public UserInfo() {
        password="123";
    }

    public UserInfo(Integer id, String password, String name, Integer gender, String phone, Integer role) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.role = role;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(Integer role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", gender=" + gender +
                ", phone='" + phone + '\'' +
                ", role=" + role +
                '}';
    }
}

class UserSerializer extends JsonSerializer<UserInfo> {

    @Override
    public void serialize(UserInfo userInfo, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

        String role="";
        int r=userInfo.getRole();
        if(r==1) role="责任编辑";
        if(r==2) role="主编";
        if(r==3) role="编辑室工作人员";


        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id",userInfo.getId());
        jsonGenerator.writeStringField("password",userInfo.getPassword());
        jsonGenerator.writeStringField("name",userInfo.getName());
        jsonGenerator.writeStringField("gender",userInfo.getGender()==1?"男":"女");
        jsonGenerator.writeStringField("phone",userInfo.getPhone());
        jsonGenerator.writeStringField("role",role);
        jsonGenerator.writeEndObject();
    }
}
