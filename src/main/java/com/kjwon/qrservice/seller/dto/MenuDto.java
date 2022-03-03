package com.kjwon.qrservice.seller.dto;

import com.kjwon.qrservice.seller.entity.Category;
import com.kjwon.qrservice.seller.entity.StoreMenu;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Setter
@Getter
public class MenuDto {
    String category;
    String menuName;
    Long price;
    boolean isDiscount;
    Long discountPrice;
    boolean isLabel;
    String labelName;
    String fileLocalUrl;

    public static MenuDto of(StoreMenu menu) {
        return MenuDto.builder()
                .category(menu.getCategory())
                .menuName(menu.getMenuName())
                .price(menu.getPrice())
                .isDiscount(menu.isDiscount())
                .discountPrice(menu.getDiscountPrice())
                .isLabel(menu.isLabel())
                .labelName(menu.getLabelName())
                .fileLocalUrl(menu.getFileLocalUrl())
                .build();
    }

    public static Set<MenuDto> of(Set<StoreMenu> menus){
        if (menus == null) {
            return null;
        }

        Set<MenuDto> menuList = new HashSet<>();
        for(StoreMenu x : menus) {
            menuList.add(MenuDto.of(x));
        }
        return menuList;
    }
}
