package com.titi.titi_user.data.jpa.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.titi.titi_user.data.jpa.entity.MemberEntity;
import com.titi.titi_user.domain.member.Member;

@Mapper
public interface EntityMapper {

	EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

	MemberEntity toEntity(Member member);

	Member toDomain(MemberEntity memberEntity);
}
