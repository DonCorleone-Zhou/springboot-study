package com.kuang.Mapper;

import com.kuang.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper {

    //查询用户by name
    public User queryUserByName(String name);
    //增


    //改

    //查





}
