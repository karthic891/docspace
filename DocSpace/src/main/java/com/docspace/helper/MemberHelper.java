/**
 * 
 */
package com.docspace.helper;

import com.docspace.domain.IMember;
import com.docspace.facade.MemberFacade;
import com.docspace.utilities.servicelocator.ServiceLocator;


/**
 * @author karthic
 *
 */
public class MemberHelper {
	public static IMember createMember() {
		MemberFacade memberFacade = (MemberFacade) ServiceLocator.getInstance().getBean("memberFacade");
		return memberFacade.createMember();
	}

}
