package com.songsy.service;

import com.songsy.entity.User;

/**
* 用戶表 service接口
* @author songsy
* @date 2018-04-15 16:56:23
*/
public interface UserService extends extends BaseService<User,Integer>{
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}