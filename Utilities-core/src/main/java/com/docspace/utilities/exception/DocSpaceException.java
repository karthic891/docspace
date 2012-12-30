/**
 * 
 */
package com.docspace.utilities.exception;

/**
 * @author karthic
 *
 */
public class DocSpaceException extends Exception {
	
	/**
	 * Exception Message
	 */
	private String message;
	
	/**
	 * Creates new DocSpaceException with the message provided
	 * @param message
	 */
	public DocSpaceException(String message) {
		this.message = message;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
