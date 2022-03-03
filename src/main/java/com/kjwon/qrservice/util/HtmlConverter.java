package com.kjwon.qrservice.util;

import com.kjwon.qrservice.seller.dto.MenuDto;

import java.util.List;

public class HtmlConverter {
    public static String getCategories(List<List<MenuDto>> menuDtoss){
//        String result = "";
        StringBuilder sb = new StringBuilder();
        sb.append("<a href = '?'>전체 </>");
        if(menuDtoss.size() == 0)
            return null;

        for (List<MenuDto> menuDtos : menuDtoss) {
            if(menuDtos.size() != 0){
                sb.append("<a href = '?category=");
                sb.append(menuDtos.get(0).getCategory());
                sb.append("'>");
                sb.append(menuDtos.get(0).getCategory());
                sb.append("(");
                sb.append(menuDtos.size());
                sb.append(")");
                sb.append(" </a>");
            }
        }

        return sb.toString();

    }
}
