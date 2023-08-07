package com.titi.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

@ApiModel(description = ApiConstants.Common.Model.DESCRIPTION)
@Getter
public abstract class ApiResponse {

	@ApiModelProperty(position = 1, value = ApiConstants.Common.Property.VALUE1, example = ApiConstants.Common.Property.EXAMPLE1)
	private final int status;
	@ApiModelProperty(position = 2, value = ApiConstants.Common.Property.VALUE2, example = ApiConstants.Common.Property.EXAMPLE2)
	private final String code;
	@ApiModelProperty(position = 3, value = ApiConstants.Common.Property.VALUE3, example = ApiConstants.Common.Property.EXAMPLE3)
	private final String message;

	public ApiResponse(ApiCodes.Result resultCode) {
		this.status = resultCode.getStatus();
		this.code = resultCode.getCode();
		this.message = resultCode.getMessage();
	}

	public ApiResponse(ApiCodes.Error errorCode) {
		this.status = errorCode.getStatus();
		this.code = errorCode.getCode();
		this.message = errorCode.getMessage();
	}

}
