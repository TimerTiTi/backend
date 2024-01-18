package com.titi.titi_auth.adapter.out.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titi.titi_auth.adapter.out.persistence.jpa.entity.OAuth2Info;

public interface OAuth2InfoRepository extends JpaRepository<OAuth2Info, Long> {

}
