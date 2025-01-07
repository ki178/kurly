package com.bhs.sssss.mappers;

import com.bhs.sssss.entities.ItemEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ItemMapper {

    ItemEntity[] selectItems();

    ItemEntity[] selectItemsByNew(@Param("limitCount") int limitCount,
                                  @Param("offsetCount") int offsetCount);

    ItemEntity[] selectItemsBySticker(@Param("limitCount") int limitCount,
                                      @Param("offsetCount") int offsetCount);

    ItemEntity[] selectItemsByDiscount(@Param("limitCount") int limitCount,
                                       @Param("offsetCount") int offsetCount);

    ItemEntity[] selectItemsByKeyword(@Param("keyword") String keyword,
                                      @Param("limitCount") int limitCount,
                                      @Param("offsetCount") int offsetCount);

    int selectItemCount();

    int selectItemCount1();

    int selectItemCount2();

    int selectItemCountByKeyword(@Param("keyword") String keyword);
}
