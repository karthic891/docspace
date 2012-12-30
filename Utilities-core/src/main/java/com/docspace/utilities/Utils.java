/**
 * 
 */
package com.docspace.utilities;

/**
 * @author karthic
 *
 */
public class Utils {
	
	public static final String[] MONTHS_OF_THE_YEAR = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"}; 
	
	/**
	 * Empty private constructor to prevent instantiating of this class.
	 */
	private Utils() {
	}
	
	/**
	 * Check the passed string argument for null or empty condition
	 * @param testValue
	 * @return boolean
	 */
	public static boolean isNullOrEmpty(String testValue) {
		if(testValue != null && testValue.trim().length() > 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * Get the extension of a file given the file name
	 * @param fileName
	 * @return {@link String} - the file extension
	 */
	public static String getFileExtension(String fileName) {
		if(fileName == null) {
			return null;
		}
		int separatorIndex = fileName.lastIndexOf(".");
		return fileName.substring(separatorIndex);
	}

}
