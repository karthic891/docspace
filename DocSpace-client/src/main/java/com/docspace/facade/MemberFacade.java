/**
 * 
 */
package com.docspace.facade;

import com.docspace.domain.IMember;
import com.docspace.utilities.exception.DocSpaceException;

/**
 * @author karthic
 *
 */
public interface MemberFacade {
	public IMember createMember();
	
	/**
	 * Authenticates the member by verifying the login credentials
	 * @param member
	 * @return A string denoting the status of authentication
	 */
	public String authenticateMember(IMember member) throws DocSpaceException;
	
	/**
	 * Registers a new user by saving the user details into the database
	 * @param newUser
	 * @return A string denoting the status of registration 
	 */
	public String registerUser(IMember newUser) throws DocSpaceException;
	
	/**
	 * Retrieves the user information from the database given the user name
	 * @return {@link IMember}
	 */
	public IMember retrieveUser(String userName) throws DocSpaceException;
	
}
