package com.titi.titi_auth.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titi.titi_auth.data.jpa.entity.AccountEntity;
import com.titi.titi_auth.data.jpa.repository.querydsl.AccountEntityQuerydsl;

public interface AccountEntityRepository extends JpaRepository<AccountEntity, Long>, AccountEntityQuerydsl {

}
