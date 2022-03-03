package com.kjwon.qrservice.seller.service;

import com.kjwon.qrservice.seller.dto.CategoryDto;
import com.kjwon.qrservice.seller.dto.MenuDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface StoreService {
    void addMenu(MenuDto menuDto, String userName, MultipartFile file);

    List<List<MenuDto>> getMenuList(String name);

    void addCategory(CategoryDto categoryDto, String userName);

    Set<CategoryDto> getCategories(String name);

    List<MenuDto> selectCategory(List<List<MenuDto>> menuDtoss, String category);
}
