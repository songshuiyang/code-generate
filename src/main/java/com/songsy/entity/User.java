//package com.songsy.entity;
//import com.songsy.common.BaseEntity;
//import javax.persistence.*;
//import java.util.Date;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
///**
//* 用戶表 实体类
//* @author songsy
//* @date 2018-04-14 22:35:09
//*/
//@Table(name="sys_user")
//@Getter
//@Setter
//@ToString
//public class User extends BaseEntity {
//
//    /**
//    * 用户名
//    * jdbcType: VARCHAR
//    * javaType: String
//    */
//    private String username;
//
//    /**
//    * 密码
//    * jdbcType: VARCHAR
//    * javaType: String
//    */
//    private String password;
//
//    /**
//    * 昵称
//    * jdbcType: VARCHAR
//    * javaType: String
//    */
//    private String nickname;
//
//    /**
//    * 性别(0:男 1:女)
//    * jdbcType: INT
//    * javaType: Integer
//    */
//    private Integer sex;
//
//    /**
//    * 手机
//    * jdbcType: VARCHAR
//    * javaType: String
//    */
//    private String phone;
//
//    /**
//    *
//    * jdbcType: VARCHAR
//    * javaType: String
//    */
//    private String email;
//
//    /**
//    * 地址
//    * jdbcType: VARCHAR
//    * javaType: String
//    */
//    private String address;
//
//    /**
//    * 加密盐值
//    * jdbcType: VARCHAR
//    * javaType: String
//    */
//    private String salt;
//
//
//
//    public String getUsername() {
//        return this.username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//
//    public String getPassword() {
//        return this.password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//
//    public String getNickname() {
//        return this.nickname;
//    }
//
//    public void setNickname(String nickname) {
//        this.nickname = nickname;
//    }
//
//
//    public Integer getSex() {
//        return this.sex;
//    }
//
//    public void setSex(String sex) {
//        this.sex = sex;
//    }
//
//
//    public String getPhone() {
//        return this.phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//
//    public String getEmail() {
//        return this.email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//
//    public String getAddress() {
//        return this.address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//
//    public String getSalt() {
//        return this.salt;
//    }
//
//    public void setSalt(String salt) {
//        this.salt = salt;
//    }
//
//
//}