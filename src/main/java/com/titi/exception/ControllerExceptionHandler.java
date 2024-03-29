package com.titi.exception;

import static com.titi.titi_common_lib.constant.Constants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

import com.titi.titi_common_lib.dto.ErrorResponse;
import com.titi.titi_common_lib.dto.ErrorResponse.FieldError;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

	/**
	 * <b>@Validated</b>에 의해 발생하는 <b>Bean Validation</b> 예외 처리<br>
	 *
	 * @see <a href="https://medium.com/javarevisited/are-you-using-valid-and-validated-annotations-wrong-b4a35ac1bca4">@Valid vs @Validated</a>
	 */
	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException e) {
		final ErrorResponse response = ErrorResponse.of(TiTiErrorCodes.INPUT_VALUE_INVALID, this.getErrors(e.getConstraintViolations()));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * <b>@Valid</b>에 의해 발생하는 <b>Bean Validation</b> 예외 처리
	 *
	 * @see <a href="https://medium.com/javarevisited/are-you-using-valid-and-validated-annotations-wrong-b4a35ac1bca4">@Valid vs @Validated</a>
	 */
	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		final ErrorResponse response = ErrorResponse.of(TiTiErrorCodes.INPUT_VALUE_INVALID, this.getErrors(e.getBindingResult()));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * <b>@ModelAttribute</b> 바인딩 실패 예외 처리
	 *
	 * @see <a href="https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args">@ModelAttribute</a>
	 */
	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleBindException(BindException e) {
		final ErrorResponse response = ErrorResponse.of(TiTiErrorCodes.INPUT_VALUE_INVALID, this.getErrors(e.getBindingResult()));
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * <b>@RequestParam</b> or <b>@RequestPart</b> 필수 파라미터가 결여된 경우 예외 처리
	 */
	@ExceptionHandler({MissingServletRequestParameterException.class, MissingServletRequestPartException.class})
	protected ResponseEntity<ErrorResponse> handleMissingServletRequestParameterException(Exception e) {
		final List<FieldError> errors = FieldError.of(this.getRequestParam(e), EMPTY, TiTiErrorCodes.REQUEST_PARAMETER_MISSING.getMessage());
		final ErrorResponse response = ErrorResponse.of(TiTiErrorCodes.INPUT_VALUE_INVALID, errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Controller method argument <b>타입</b>이 일치하지 않을 경우 예외 처리
	 */
	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
		final String value = e.getValue() == null ? EMPTY : e.getValue().toString();
		final List<FieldError> errors = FieldError.of(e.getName(), value, e.getErrorCode());
		final ErrorResponse response = ErrorResponse.of(TiTiErrorCodes.INPUT_TYPE_INVALID, errors);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * <b>@RequestBody</b> 형식과 불일치하거나, <b>JSON</b> 형식에 맞지 않을 경우 예외 처리
	 */
	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
		final ErrorResponse response = ErrorResponse.of(TiTiErrorCodes.HTTP_MESSAGE_NOT_READABLE);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * 지원하지 않는 <b>HTTP method</b> 호출할 경우 예외 처리
	 */
	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		final ErrorResponse response = ErrorResponse.of(TiTiErrorCodes.METHOD_NOT_ALLOWED);
		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

	/**
	 * <b>Business logic</b>에서 발생하는 모든 예외 처리
	 */
	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleTiTiException(TiTiException e) {
		final ErrorResponse response = ErrorResponse.of(e.getCode(), e.getMessage(), e.getErrors());
		return new ResponseEntity<>(response, HttpStatusCode.valueOf(e.getStatus()));
	}

	/**
	 * 위에 해당하지 않는 모든 예외 처리
	 */
	@ExceptionHandler
	protected ResponseEntity<ErrorResponse> handleException(Exception e) {
		log.error("Internal Server Error. ", e);
		final ErrorResponse response = ErrorResponse.of(TiTiErrorCodes.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private List<FieldError> getErrors(final BindingResult bindingResult) {
		final List<org.springframework.validation.FieldError> fieldErrors = bindingResult.getFieldErrors();
		return fieldErrors.stream()
			.map(error -> new FieldError(error.getField(), error.getRejectedValue() == null ? EMPTY : error.getRejectedValue().toString(), error.getDefaultMessage()))
			.collect(Collectors.toList());
	}

	private List<FieldError> getErrors(final Set<ConstraintViolation<?>> constraintViolations) {
		final List<ConstraintViolation<?>> lists = new ArrayList<>(constraintViolations);
		return lists.stream()
			.map(error -> {
				final String invalidValue = error.getInvalidValue() == null ? EMPTY : error.getInvalidValue().toString();
				final String propertyPath = error.getPropertyPath().toString();
				final int lastDotIndex = propertyPath.lastIndexOf(DOT);
				final String propertyName = lastDotIndex == -1 ? propertyPath : propertyPath.substring(lastDotIndex + 1);
				return new FieldError(propertyName, invalidValue, error.getMessage());
			})
			.collect(Collectors.toList());
	}

	private String getRequestParam(Exception e) {
		if (e instanceof MissingServletRequestParameterException) {
			return ((MissingServletRequestParameterException)e).getParameterName();
		} else {
			return ((MissingServletRequestPartException)e).getRequestPartName();
		}
	}

}
