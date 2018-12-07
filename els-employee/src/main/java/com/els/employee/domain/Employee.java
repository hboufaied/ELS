/**
 * 
 */
package com.els.employee.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;

/**
 * @author h.boufaied
 *
 */
@Data
@AllArgsConstructor
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	// This field should not be duplicated in databases
	private String passportNumber;

	public Employee() {};
}
