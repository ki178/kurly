package com.bhs.sssss.mappers;

import com.bhs.sssss.entities.CartEntity;
import com.bhs.sssss.entities.PayLoadEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface PayMapper {

    List<CartEntity> selectAllCarts();

    List<PayLoadEntity> selectAllPayLoads();

    void insertItemLoad(PayLoadEntity payLoadEntity);

    CartEntity selectCartById(int itemId);

    List<Integer> getPayIndexByCartIndex(@Param("payItemId") int payItemId);

}