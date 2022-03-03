package com.kjwon.qrservice.customer.entity;

import com.kjwon.qrservice.seller.entity.Category;
import com.kjwon.qrservice.seller.entity.StoreMenu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
//@Table(name = "member")
public class Member{

    @Id
    @Column(name = "user_id")
    private String userId;
    private String userName;
    private String phone;
    private String password;
    private LocalDateTime regDt;

//    @OneToMany(mappedBy = "member")
//    private List<StoreMenu> storeMenus;

    @OneToMany
    @JoinColumn(name = "member_id") //child 테이블에 있는 parent_id FK
    private Set<Category> categories = new HashSet<>();
}