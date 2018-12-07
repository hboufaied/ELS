package com.els.employee.controller;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.els.employee.model.ELSEmployeeException;
import com.els.employee.model.UploadFileResponse;
import com.els.employee.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author h.boufaied
 *
 */
@RestController
@Slf4j
public class EmployeeController implements EmployeeAPI {

	private EmployeeService employeeService;

	public EmployeeController(EmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	@Override
	public ResponseEntity<?> uploadFile(@RequestParam(value = "file") MultipartFile file) {
		
		UploadFileResponse response;
		
		if (log.isDebugEnabled()) {
			log.debug("Treat the json file containing the list of employees, file name {}, file type {}",
					file.getName(), file.getContentType());
		}
		try {
			response = employeeService.treatEmployee(file);
		} catch (ELSEmployeeException e) {
			log.error("Error during mapping json file", e);
			return new ResponseEntity<>(e.toString(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<?> saveEmployee(@RequestParam(value = "files") MultipartFile[] files) {
		return (ResponseEntity<?>) Arrays.asList(files).stream().map(file -> uploadFile(file)).collect(Collectors.toList());
	}

}