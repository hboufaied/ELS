package com.els.employee.model;

import javax.validation.constraints.NotNull;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author h.boufaied
 *
 */
@SuppressWarnings("serial")
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ELSEmployeeException extends Exception {

	@JsonProperty("code")
	private Integer code = null;

	@JsonProperty("message")
	private String message = null;

	@JsonProperty("description")
	private String description = null;

	public ELSEmployeeException(String message) {
		this.message = message;
	}

	public ELSEmployeeException(Integer code) {
		this.code = code;
	}

	public ELSEmployeeException(String message, String description) {
		this.message = message;
		this.description = description;
	}

	public ELSEmployeeException(Integer code, String message, String description) {
		this.code = code;
		this.message = message;
		this.description = description;
	}

	/**
	 * Get code
	 * 
	 * @return code
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull
	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	/**
	 * Get message
	 * 
	 * @return message
	 **/
	@ApiModelProperty(required = true, value = "")
	@NotNull
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * Get fields
	 * 
	 * @return fields
	 **/
	@ApiModelProperty(value = "")
	public String getField() {
		return description;
	}

	public void setFields(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("class BulkMessagingException\":{");
		sb.append("\"code\": \"").append(toIndentedString(code)).append("\",");
		sb.append("\"message\": \"").append(toIndentedString(message)).append("\",");
		sb.append("\"description\": \"").append(toIndentedString(description)).append("\"}");
		return sb.toString();
	}

	/**
	 * Convert the given object to string with each line indented by 4 spaces
	 * (except the first line).
	 */
	private String toIndentedString(java.lang.Object o) {
		if (o == null) {
			return "null";
		}
		return o.toString().replace("\n", "\n    ");
	}
}