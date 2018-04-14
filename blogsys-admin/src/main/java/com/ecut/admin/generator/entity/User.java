package com.ecut.admin.generator.entity;
import javax.persistence.*;
import java.util.Date;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
* 用户表 实体类
* @author 
* @date 2017/05/03
*/
@Table(name="sys_user")
@Getter
@Setter
@ToString
public class User extends BaseEntity {

        /**
        * 用户名
        */
        private String username;
        /**
        * 密码
        */
        private String password;
        /**
        * 昵称
        */
        private String nickname;
        /**
        * 性别(0:男 1:女)
        */
        private Integer sex;
        /**
        * 手机
        */
        private String phone;
        /**
        * 
        */
        private String email;
        /**
        * 地址
        */
        private String address;
        /**
        * 加密盐值
        */
        private String salt;

        public String getUsername() {
            return this.username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return this.password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getNickname() {
            return this.nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getPhone() {
            return this.phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return this.email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getAddress() {
            return this.address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getSalt() {
            return this.salt;
        }

        public void setSalt(String salt) {
            this.salt = salt;
        }


}