/**
 * 
 */
package com.els.employee.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author h.boufaied
 *
 */
@Getter
@Setter
public class UploadFileResponse {

    private String fileName;
    private String fileType;
    private long initialEmployeeNumber;
    private long finalEmployeeNumber;
    
	/**
	 * @param fileName
	 * @param fileType
	 * @param initialEmployeeNumber
	 * @param finalEmployeeNumber
	 */
	public UploadFileResponse(String fileName, String fileType, long initialEmployeeNumber, long finalEmployeeNumber) {
		super();
		this.fileName = fileName;
		this.fileType = fileType;
		this.initialEmployeeNumber = initialEmployeeNumber;
		this.finalEmployeeNumber = finalEmployeeNumber;
	}
    
}