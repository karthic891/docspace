/**
 * 
 */
package com.docspace.domain;

import java.util.Date;

/**
 * @author karthic
 *
 */
public interface IMember {
	
	public String getUserID();
	
	public void setUserID(String userID);
	
	public String getUserName();
	
	public void setUserName(String userName);
	
	public String getPassword();
	
	public void setPassword(String password);
	
	public String getFirstName();
	
	public void setFirstName(String firstName);
	
	public String getLastName();
	
	public void setLastName(String lastName);
	
	public String getDisplayName();
	
	public void setDisplayName(String displayName);
	
	public String getEmailId();
	
	public void setEmailId(String emailId);
	
	public Date getDateOfBirth();
	
	public void setDateOfBirth(Date dateOfBirth);
	
	public String getGender();
	
	public void setGender(String gender);
	
	public String getCountry();
	
	public void setCountry(String country);
	
}
