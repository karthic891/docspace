/**
 * 
 */
package com.docspace.service;

import com.docspace.domain.IMember;
import com.docspace.domain.Member;
import com.docspace.repository.MemberRepository;
import com.docspace.utilities.exception.DocSpaceException;

/**
 * @author karthic
 *
 */
public class MemberService {
	private MemberRepository memberRepository;
	
	public MemberService(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;  
	}
	
	public IMember createMember() {
		return new Member();
	}
	
	/**
	 * Registers a new user by saving the user details into the database
	 * @param newUser
	 * @return A string denoting the status of registration 
	 * @throws DocSpaceException 
	 */
	public String authenticateMember(IMember member) throws DocSpaceException {
		return memberRepository.authenticateMember(member);
	}
	
	/**
	 * Registers a new user by saving the user details into the database
	 * @param newUser
	 * @return A string denoting the status of registration 
	 * @throws DocSpaceException 
	 */
	public String registerUser(IMember newUser) throws DocSpaceException {
		return memberRepository.registerUser(newUser);
	}
	
	/**
	 * Retrieves the user information from the database given the user name
	 * @return {@link IMember}
	 * @throws DocSpaceException 
	 */
	public IMember retrieveUser(String userName) throws DocSpaceException {
		return memberRepository.retrieveUser(userName);
	}
	
	
	public void print() {
		memberRepository.print();
	}
}
