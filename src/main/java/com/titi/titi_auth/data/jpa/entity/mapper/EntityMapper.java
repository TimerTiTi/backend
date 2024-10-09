package com.titi.titi_auth.data.jpa.entity.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.titi.titi_auth.data.jpa.entity.AccountEntity;
import com.titi.titi_auth.domain.Account;

@Mapper
public interface EntityMapper {

	EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);

	AccountEntity toEntity(Account account);

	Account toDomain(AccountEntity accountEntity);

}
