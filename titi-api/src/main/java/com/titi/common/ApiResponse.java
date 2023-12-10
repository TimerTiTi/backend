package com.titi.common;

import com.titi.common.ApiConstants.Common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = Common.RESPONSE_DESCRIPTION)
@Getter
public abstract class ApiResponse {

	@ApiModelProperty(position = 1, value = Common.RESPONSE_PROPERTY_DESCRIPTION1, example = Common.RESPONSE_PROPERTY_EXAMPLE1)
	private final int status;
	@ApiModelProperty(position = 2, value = Common.RESPONSE_PROPERTY_DESCRIPTION2, example = Common.RESPONSE_PROPERTY_EXAMPLE2)
	private final String code;
	@ApiModelProperty(position = 3, value = Common.RESPONSE_PROPERTY_DESCRIPTION3, example = Common.RESPONSE_PROPERTY_EXAMPLE3)
	private final String message;

	public ApiResponse(ResponseCodes.SuccessCode successCode) {
		this.status = successCode.getStatus();
		this.code = successCode.getCode();
		this.message = successCode.getMessage();
	}

}
