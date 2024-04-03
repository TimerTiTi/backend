package com.titi.titi_auth.adapter.in.internal.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.titi.titi_auth.adapter.in.internal.GenerateAccessTokenGateway;
import com.titi.titi_auth.application.port.in.GenerateAccessTokenUseCase;

@Mapper
public interface AuthInternalMapper {

	AuthInternalMapper INSTANCE = Mappers.getMapper(AuthInternalMapper.class);

	GenerateAccessTokenUseCase.Command toCommand(GenerateAccessTokenGateway.GenerateAccessTokenRequest request);

	GenerateAccessTokenGateway.GenerateAccessTokenResponse toResponse(GenerateAccessTokenUseCase.Result result);

}
