package com.titi.titi_user.data.jpa.repository.querydsl;

import java.util.Optional;

import com.titi.titi_user.data.jpa.entity.MemberEntity;

public interface MemberEntityQuerydsl {

	Optional<MemberEntity> findByEntity(MemberEntity entity);

}
