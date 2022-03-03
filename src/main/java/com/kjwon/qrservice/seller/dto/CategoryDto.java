package com.kjwon.qrservice.seller.dto;

import com.kjwon.qrservice.seller.entity.Category;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Setter
@Getter
public class CategoryDto {
    private String categoryName;

    public static CategoryDto of(Category category) {
        return CategoryDto.builder()
                .categoryName(category.getName())
                .build();
    }

    public static Set<CategoryDto> of(Set<Category> categories){
        if (categories == null) {
            return null;
        }

        Set<CategoryDto> courseList = new HashSet<>();
        for(Category x : categories) {
            courseList.add(CategoryDto.of(x));
        }
        return courseList;
    }
}
