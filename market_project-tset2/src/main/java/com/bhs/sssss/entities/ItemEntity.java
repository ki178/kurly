package com.bhs.sssss.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode(of = {"index", "itemId"})
public class ItemEntity {
    private int index;
    private String itemId;
    private String itemTitle;
    private String subTitle;
    private String itemImage;
    private String sticker;
    private String discountRate;
    private String price;
    private String salesPrice;
    private String itemCategory;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;

}
