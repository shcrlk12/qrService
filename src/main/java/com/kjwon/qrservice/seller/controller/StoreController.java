package com.kjwon.qrservice.seller.controller;

import com.kjwon.qrservice.customer.entity.Member;
import com.kjwon.qrservice.seller.dto.CategoryDto;
import com.kjwon.qrservice.seller.dto.MenuDto;
import com.kjwon.qrservice.seller.entity.Category;
import com.kjwon.qrservice.seller.entity.StoreMenu;
import com.kjwon.qrservice.seller.repository.StoreMenuRepository;
import com.kjwon.qrservice.seller.service.StoreService;
import com.kjwon.qrservice.util.HtmlConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class StoreController {
    private final StoreService storeService;

    @GetMapping("/store/list")
    public String storeList(Model model, String category, Principal principal){

        List<List<MenuDto>> menuDtoss = storeService.getMenuList(principal.getName());
        String categoryList = HtmlConverter.getCategories(menuDtoss);

        model.addAttribute("categoryList", categoryList);
        if(category == null){

            model.addAttribute("category", "");
            model.addAttribute("menuDtoss", menuDtoss);

        }else{
            List<MenuDto> menuDtos = storeService.selectCategory(menuDtoss, category);

            model.addAttribute("category", category);
            model.addAttribute("menuDtoss", menuDtos);
        }
        return "/store/list";
    }

    @GetMapping("/store/menu-add")
    public String storeMenuAddPage(Model model, Principal principal){
        Set<CategoryDto> categories = storeService.getCategories(principal.getName());

        model.addAttribute("categories", categories);
        return "/store/menu-add";
    }

    @PostMapping("/store/menu-add")
    public String storeMenuAdd(Model model, MenuDto menuDto,
                              MultipartFile file, Principal principal, HttpServletRequest request){

        System.out.println(file);
        storeService.addMenu(menuDto, principal.getName(), file);

        return "redirect:/store/menu-add";
    }

    @GetMapping("/store/category-add")
    public String storeCategoryAddPage(Model model, Principal principal){

        Set<CategoryDto> categories = storeService.getCategories(principal.getName());
        model.addAttribute("categories", categories);

        return "/store/category-add";
    }


    @PostMapping("/store/category-add")
    public String storeCategoryAdd(CategoryDto categoryDto, Principal principal){
        storeService.addCategory(categoryDto, principal.getName());

        return "redirect:/store/category-add";
    }
}
