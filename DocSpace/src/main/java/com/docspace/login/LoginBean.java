package com.docspace.login;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.docspace.domain.IMember;
import com.docspace.facade.MemberFacade;
import com.docspace.helper.ManagedBeanUtility;
import com.docspace.helper.MemberHelper;
import com.docspace.helper.SessionController;
import com.docspace.utilities.exception.DocSpaceException;
import com.docspace.utilities.servicelocator.ServiceLocator;

/**
 * @author karthic
 * This class acts as the managed bean for login page
 *
 */
public class LoginBean {
	
	private String name;
	private String userName;
	private String password;
	private String errorMessage;

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * Authenticate the user by validating the user name and password
	 * @return Navigation string - configured in faces-comfig.xml
	 */
	public String authenticateUser() {
		IMember member = MemberHelper.createMember();
		member.setUserName(getUserName());
		member.setPassword(getPassword());
		MemberFacade memberFacade = (MemberFacade) ServiceLocator.getInstance().getBean("memberFacade");
		try {
			if(memberFacade.authenticateMember(member).equalsIgnoreCase("SUCCESS")) {
				setErrorMessage(null);
				return (String) ManagedBeanUtility.invokeMethodBinding("#{HomeBean.enterHome}");
			}
		} catch (DocSpaceException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
			return "error";
		}
		setErrorMessage("Invalid Username/Password");
		return "failure";
	}
	
	public String logOut() {
		SessionController.cleanSession();
		return "indexpage";
	}
	
	/**
	 * This method serves as an action method to redirect to the register page
	 * @return Navigation string - configured in faces-config.xml
	 */
	public String register() {
		return "register";
	}
	
	public String testMethod() {
		return "homepage";
	}
	
}
