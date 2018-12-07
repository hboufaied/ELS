package com.els.employee.service;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toCollection;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.els.employee.domain.Employee;
import com.els.employee.model.ELSEmployeeException;
import com.els.employee.model.UploadFileResponse;
import com.els.employee.repository.EmployeeRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * @author h.boufaied
 * EmployeeService service to manage the CRUD actions on Employee domain
 */
@Service
@Slf4j
public class EmployeeService {

	private EmployeeRepository employeeRepository;

	
	public UploadFileResponse treatEmployee(MultipartFile multipartFile) throws ELSEmployeeException {
		
	    long initialEmployeeNumber = 0;
	    long finalEmployeeNumber = 0;
	    
		try {
			File file = convertMultiPartToFile(multipartFile);
			ObjectMapper objectMapper = new ObjectMapper();
			
			// Convert json file to list of employee
			List<Employee> listEmployee = objectMapper.readValue(file, new TypeReference<List<Employee>>(){});
			initialEmployeeNumber = listEmployee.size();
			listEmployee.forEach(employee -> {
				if (log.isDebugEnabled()) {
					log.debug("Employee data {}", employee.toString());
				}
			});
			
			// Remove duplicate data : passportNumber should not be duplicated
			List<Employee> uniqueList = listEmployee.stream()
                    .collect(collectingAndThen(toCollection(() -> new TreeSet<>(comparing(Employee::getPassportNumber))),
                                               ArrayList::new));
			finalEmployeeNumber = uniqueList.size();
			
			// Save all employee in database
			employeeRepository.save(uniqueList);
			log.debug("List of employee on database {}", employeeRepository.count());
			
		} catch (IOException e) {
			log.error("Error reading/parsing json file", e);
			throw new ELSEmployeeException("Error reading/parsing json file", e.getMessage() );
		}
		return new UploadFileResponse(multipartFile.getOriginalFilename(), multipartFile.getContentType(), initialEmployeeNumber, finalEmployeeNumber);
		

	}
	
	
	public EmployeeService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	public Iterable<Employee> list() {
		return employeeRepository.findAll();
	}

	public Employee save(Employee employee) {
		return employeeRepository.save(employee);
	}

	public void save(List<Employee> employees) {
		employeeRepository.save(employees);
	}
	
	/*
	 * Convert multipartFile received from the customer
	 */
	private File convertMultiPartToFile(MultipartFile file) throws IOException {
		
		File convFile = new File(file.getOriginalFilename());
		try (FileOutputStream fos = new FileOutputStream(convFile)) {
			fos.write(file.getBytes());
		}

		return convFile;
	}
}