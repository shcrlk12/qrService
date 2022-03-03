package com.kjwon.qrservice.seller.entity;

import com.kjwon.qrservice.customer.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class StoreMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;
    private String menuName;
    private Long price;
    private boolean isDiscount;
    private Long discountPrice;
    private boolean isLabel;
    private String labelName;
    private String fileLocalUrl;

    private LocalDateTime regDt;

}
