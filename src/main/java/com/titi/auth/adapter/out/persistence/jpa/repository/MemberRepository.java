package com.titi.auth.adapter.out.persistence.jpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.titi.auth.adapter.out.persistence.jpa.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
