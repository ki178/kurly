package com.bhs.sssss.mappers;


import com.bhs.sssss.entities.LoginAttemptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LoginAttemptMapper {
    int insertLoginAttempt(LoginAttemptEntity loginAttempt);

    int selectLoginAttemptsCount();

    int selectLoginAttemptsCountBySearch(@Param("keyword") String keyword);

    LoginAttemptEntity[] selectLoginAttempts(@Param("limitCount") int limitCount,
                                             @Param("offsetCount") int offsetCount);

    LoginAttemptEntity[] selectLoginAttemptsBySearch(@Param("keyword") String keyword,
                                                     @Param("limitCount") int limitCount,
                                                     @Param("offsetCount") int offsetCount);
}