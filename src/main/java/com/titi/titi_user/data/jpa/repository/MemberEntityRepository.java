package com.titi.titi_user.data.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titi.titi_user.data.jpa.entity.MemberEntity;

public interface MemberEntityRepository extends JpaRepository<MemberEntity, Long> {

}
