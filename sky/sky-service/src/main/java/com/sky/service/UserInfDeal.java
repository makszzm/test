package com.sky.service;

import java.util.List;

import com.sky.po.User;
import com.sky.po.UserExample;

public interface UserInfDeal {

	    int deleteByPrimaryKey(String id);

	    int insert(User record);

	    int insertSelective(User record);

	    User selectByPrimaryKey(String id);

	    int updateByPrimaryKeySelective(User record);

	    int updateByPrimaryKey(User record);
	    
	    List<User> selectByExample(UserExample example);
}
