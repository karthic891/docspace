/**
 * 
 */
package com.docspace.facade;

import com.docspace.domain.IMember;
import com.docspace.service.MemberService;
import com.docspace.utilities.exception.DocSpaceException;


/**
 * @author karthic
 *
 */
public class MemberFacadeImpl implements MemberFacade {
	private MemberService memberService;
	
	public MemberFacadeImpl(MemberService memberService) {
		if(memberService == null) {
			throw new IllegalArgumentException("memberService is null. Check spring config");
		}
		this.memberService = memberService;
	}

	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.MemberFacade#authenticateMember(IMember)
	 */
	public String authenticateMember(IMember member) throws DocSpaceException {
		return memberService.authenticateMember(member);
	}

	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.MemberFacade#registerUser(IMember)
	 */
	public String registerUser(IMember newUser) throws DocSpaceException {
		return memberService.registerUser(newUser);
	}

	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.MemberFacade#registerUser(IMember)
	 */
	public IMember retrieveUser(String userName) throws DocSpaceException {
		return memberService.retrieveUser(userName);
	}

	public IMember createMember() {
		return memberService.createMember();
	}

}
