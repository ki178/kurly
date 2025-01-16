package com.bhs.sssss.mappers;


import com.bhs.sssss.entities.LoginAttemptEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LoginAttemptMapper {
    int insertLoginAttempt(LoginAttemptEntity loginAttempt);
}