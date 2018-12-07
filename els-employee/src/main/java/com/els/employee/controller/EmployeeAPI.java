package com.els.employee.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author h.boufaied
 *
 */
@Api(value = "/api/v1/employee", description = "Employee Management API")
@RequestMapping("/api/v1/employee")
public interface EmployeeAPI {

	@ApiOperation(value = "Save Employee on Databases", notes = "The endpoint will remove the duplicated data from employee file and save it into databases", response = Void.class, tags = {
			"Employee Management" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Saved date ", response = Void.class),
			@ApiResponse(code = 401, message = "Authorization information is missing or invalid") })
	@RequestMapping(value = "/upload", produces = { "application/json" }, consumes = {
			"multipart/form-data" }, method = RequestMethod.POST)
	ResponseEntity<?> uploadFile(
			@ApiParam(value = "", required = true) @RequestParam(value = "file") MultipartFile file);

	@ApiOperation(value = "Upload multiple json files and save Employee on Databases", notes = "The endpoint will remove the duplicated data from employee file and save it into databases", response = Void.class, tags = {
			"Employee Management" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Saved date ", response = Void.class),
			@ApiResponse(code = 401, message = "Authorization information is missing or invalid") })
	@RequestMapping(value = "/uploadMultipleFiles", produces = { "application/json" }, consumes = {
			"multipart/form-data" }, method = RequestMethod.POST)
	ResponseEntity<?> saveEmployee(
			@ApiParam(value = "", required = true) @RequestParam("files") MultipartFile[] files);
}