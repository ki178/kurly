package com.bhs.sssss.services;

import com.bhs.sssss.entities.CategoryEntity;
import com.bhs.sssss.entities.ItemEntity;
import com.bhs.sssss.entities.SubCategoryEntity;
import com.bhs.sssss.mappers.CategoryMapper;
import com.bhs.sssss.mappers.ItemMapper;
import com.bhs.sssss.mappers.SubCategoryMapper;
import com.bhs.sssss.vos.PageVo;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.PrivateKey;

@Service
public class ItemService {
    private final CategoryMapper categoryMapper;
    private final SubCategoryMapper subCategoryMapper;
    private final ItemMapper itemMapper;

    @Autowired
    public ItemService(CategoryMapper categoryMapper, SubCategoryMapper subCategoryMapper, ItemMapper itemMapper) {
        this.categoryMapper = categoryMapper;
        this.subCategoryMapper = subCategoryMapper;
        this.itemMapper = itemMapper;
    }

    public CategoryEntity[] getCategories() {
        return this.categoryMapper.selectCategories();
    }

    public SubCategoryEntity[] getSubCategoriesById(String categoryId) {
        String parentId = categoryId;
        return this.subCategoryMapper.selectSubCategoriesByParentId(parentId);
    }

    public ItemEntity[] getItems() {
        return this.itemMapper.selectItems();
    }

    public Pair<PageVo, ItemEntity[]> getItemsByNew(int page){
        page = Math.max(1, page);
        int totalCount = this.itemMapper.selectItemCount();
        PageVo pageVo = new PageVo(page, totalCount);
        ItemEntity[] items = this.itemMapper.selectItemsByNew(
                pageVo.countPerPage,
                pageVo.offsetCount
        );
        return Pair.of(pageVo, items);
    }

    public Pair<PageVo, ItemEntity[]> getItemsBySticker(int page){
        page = Math.max(1, page);
        int totalCount = this.itemMapper.selectItemCount1();
        PageVo pageVo = new PageVo(page, totalCount);
        ItemEntity[] items = this.itemMapper.selectItemsBySticker(
                pageVo.countPerPage,
                pageVo.offsetCount
        );
        return Pair.of(pageVo, items);
    }

    public Pair<PageVo, ItemEntity[]> getItemsByDiscount(int page){
        page = Math.max(1, page);
        int totalCount = this.itemMapper.selectItemCount2();
        PageVo pageVo = new PageVo(page, totalCount);
        ItemEntity[] items = this.itemMapper.selectItemsByDiscount(
                pageVo.countPerPage,
                pageVo.offsetCount
        );
        return Pair.of(pageVo, items);
    }

    public Pair<PageVo, ItemEntity[]> getItemsByKeyword(int page, String keyword){
        page = Math.max(1, page);
        int totalCount = this.itemMapper.selectItemCountByKeyword(keyword);
        PageVo pageVo = new PageVo(page, totalCount);
        ItemEntity[] items = this.itemMapper.selectItemsByKeyword(
                keyword,
                pageVo.countPerPage,
                pageVo.offsetCount
        );
        return Pair.of(pageVo, items);
    }


}
