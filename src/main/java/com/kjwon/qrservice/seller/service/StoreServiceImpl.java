package com.kjwon.qrservice.seller.service;

import com.kjwon.qrservice.customer.entity.Member;
import com.kjwon.qrservice.customer.repository.MemberRepository;
import com.kjwon.qrservice.seller.dto.CategoryDto;
import com.kjwon.qrservice.seller.dto.MenuDto;
import com.kjwon.qrservice.seller.entity.Category;
import com.kjwon.qrservice.seller.entity.StoreMenu;
import com.kjwon.qrservice.seller.repository.CategoryRepository;
import com.kjwon.qrservice.seller.repository.StoreMenuRepository;
import lombok.RequiredArgsConstructor;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.List;

@RequiredArgsConstructor
@Service
public class StoreServiceImpl implements StoreService{
    private final StoreMenuRepository storeMenuRepository;
    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;

    private  static final Logger log = Logger.getLogger(StoreServiceImpl.class);

    @Override
    public void addMenu(MenuDto menuDto, String userName, MultipartFile file) {

        /*copy file*/
        String basePath = "C:/dev/qrService/src/main/resources/static";
        String localDirPath = "/img/seller/thumbnail/";
        String saveFilename = localDirPath + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + "_" + file.getOriginalFilename();

        try{
            File newFile = new File(basePath + saveFilename);
            FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(newFile));
        }catch(IOException e){
            log.info("############################ - 1");
            log.info(e.getMessage());
        }
        /**/
        Optional<Member> memberOptional = memberRepository.findById(userName);

        if(memberOptional.isEmpty())
            return;

        StoreMenu storeMenu = StoreMenu.builder()
                .category(menuDto.getCategory())
                .price(menuDto.getPrice())
                .discountPrice(menuDto.getDiscountPrice())
                .isDiscount(menuDto.isDiscount())
                .isLabel(menuDto.isLabel())
                .labelName(menuDto.getLabelName())
                .menuName(menuDto.getMenuName())
                .regDt(LocalDateTime.now())
                .fileLocalUrl(saveFilename)
                .build();

        storeMenuRepository.save(storeMenu);

        Member member = memberOptional.get();
        Set<Category> categories = member.getCategories();

        for(Category c : categories){
            if(c.getName().equals(menuDto.getCategory()))
            {
                Set<StoreMenu> storeMenus = c.getStoreMenus();
                storeMenus.add(storeMenu);
                categoryRepository.save(c);
            }
        }
    }

    @Override
    public List<List<MenuDto>> getMenuList(String name) {
        Optional<Member> memberOptional = memberRepository.findById(name);

        if(memberOptional.isEmpty())
            return Collections.emptyList();

        Member member = memberOptional.get();

        List<List<MenuDto>> menuDtoss = new ArrayList<>();
        Set<Category> categories = member.getCategories();

        int index = 0;
        for(Category c : categories){
            menuDtoss.add(new ArrayList<>());

            for(StoreMenu storeMenu : c.getStoreMenus())
            {
                menuDtoss.get(index).add(MenuDto.of(storeMenu));
            }
            index++;
        }
        return menuDtoss;
    }

    @Override
    public void addCategory(CategoryDto categoryDto, String userName) {

        long beforeTime = System.currentTimeMillis(); //코드 실행 전에 시간 받아오기

        /*1.*/
        Optional<Member> memberOptional = memberRepository.findById(userName);

        if(memberOptional.isEmpty())
            return;

        Member member = memberOptional.get();
        Set<Category> categories = member.getCategories();

        /*2.*/
        for(Category s : categories){
            if(s.getName().equals(categoryDto.getCategoryName()))
                return;
        }

        /*3.*/
         Category category = Category.builder()
            .name(categoryDto.getCategoryName())
            .storeMenus(new HashSet<>())
            .build();


        categoryRepository.save(category);

        /*4.*/
        categories.add(category);
        member.setCategories(categories);

        memberRepository.save(member);

        /* End */
        long afterTime = System.currentTimeMillis(); // 코드 실행 후에 시간 받아오기
        long secDiffTime = (afterTime - beforeTime)/1000; //두 시간에 차 계산

        log.debug("getList speed : "+ secDiffTime + " ms");

    }

    @Override
    public Set<CategoryDto> getCategories(String name) {

        Optional<Member> memberOptional = memberRepository.findById(name);

        if(memberOptional.isEmpty())
            return Collections.emptySet();

        return CategoryDto.of(memberOptional.get().getCategories());
    }

    @Override
    public List<MenuDto> selectCategory(List<List<MenuDto>> menuDtoss, String category) {

        for(List<MenuDto> MenuDtos : menuDtoss){
            if(MenuDtos.get(0).getCategory().equals(category))
                return MenuDtos;
        }
        return null;
    }
}
