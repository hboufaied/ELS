/**
 * 
 */
package com.els.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.els.employee.domain.Employee;

/**
 * @author h.boufaied
 *
 */
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}