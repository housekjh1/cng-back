package com.cng.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cng.domain.StoreInfo;

public interface StoreRepository extends JpaRepository<StoreInfo, Long> {

}
