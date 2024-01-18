package com.titi.titi_common_lib.dto;

import com.titi.titi_common_lib.constant.ApiConstants;
import com.titi.titi_common_lib.constant.ResponseCodes;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = ApiConstants.Common.RESPONSE_DESCRIPTION)
@Getter
public abstract class ApiResponse {

	@ApiModelProperty(position = 1, value = ApiConstants.Common.RESPONSE_PROPERTY_DESCRIPTION1, example = ApiConstants.Common.RESPONSE_PROPERTY_EXAMPLE1)
	private final int status;
	@ApiModelProperty(position = 2, value = ApiConstants.Common.RESPONSE_PROPERTY_DESCRIPTION2, example = ApiConstants.Common.RESPONSE_PROPERTY_EXAMPLE2)
	private final String code;
	@ApiModelProperty(position = 3, value = ApiConstants.Common.RESPONSE_PROPERTY_DESCRIPTION3, example = ApiConstants.Common.RESPONSE_PROPERTY_EXAMPLE3)
	private final String message;

	public ApiResponse(ResponseCodes.ResultCode resultCode) {
		this.status = resultCode.getStatus();
		this.code = resultCode.getCode();
		this.message = resultCode.getMessage();
	}

}
