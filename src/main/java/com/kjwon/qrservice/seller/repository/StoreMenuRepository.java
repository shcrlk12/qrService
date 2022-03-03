package com.kjwon.qrservice.seller.repository;

import com.kjwon.qrservice.seller.entity.StoreMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StoreMenuRepository extends JpaRepository<StoreMenu, Long> {
}
