/**
 * 
 */
package com.docspace.home;

import java.awt.event.ActionEvent;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import com.docspace.domain.IMember;
import com.docspace.facade.MemberFacade;
import com.docspace.helper.ManagedBeanUtility;
import com.docspace.login.LoginBean;
import com.docspace.utilities.exception.DocSpaceException;
import com.docspace.utilities.servicelocator.ServiceLocator;
import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;

/**
 * @author karthic
 * This class acts as the managed bean for the user home page
 *
 */
public class HomeBean {
	private static final Logger logger = Logger.getLogger(HomeBean.class.getName()); 
	private IMember currentUser;
	private FileInfo currentFile;
	
	private String testValue;// remove when everything's alright
	
	public HomeBean() {
		testValue = "testValue";
	}

	/**
	 * @return the currentUser
	 */
	public IMember getCurrentUser() {
		return currentUser;
	}

	/**
	 * @param currenPOtUser the currentUser to set
	 */
	public void setCurrentUser(IMember currentUser) {
		this.currentUser = currentUser;
	}

	/**
	 * @return the testValue
	 */
	public String getTestValue() {
		System.out.println("test Value :: "+testValue);
		return testValue;
	}

	/**
	 * @param testValue the testValue to set
	 */
	public void setTestValue(String testValue) {
		this.testValue = testValue;
	}

	/**
	 * @return the currentFile
	 */
	public FileInfo getCurrentFile() {
		return currentFile;
	}

	/**
	 * @param currentFile the currentFile to set
	 */
	public void setCurrentFile(FileInfo currentFile) {
		this.currentFile = currentFile;
	}

	/**
	 * Navigate the user to his/her homepage by initializing the user information
	 * @return Navigation string - configured in faces-config.xml
	 */
	public String enterHome() {
		logger.info("Entering Home...");
		//initializeHeader();
		//initNavigations();
		LoginBean loginBean = (LoginBean) ManagedBeanUtility.getManagedBean("LoginBean");
		try {
			initializeUser(loginBean.getUserName());
		} catch (DocSpaceException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
			return "error";
		}
		DocumentHandlerBean documentHandlerBean = (DocumentHandlerBean) ManagedBeanUtility.getManagedBean("DocumentHandlerBean");
		documentHandlerBean.loadHomeFolders();
		return "homepage";
	}
	
	
	public void changePic() {
		System.out.println("Hi all");
	}
	
	public void upload(ActionEvent actionEvent) {
		InputFile inputFile = (InputFile) actionEvent.getSource();
		currentFile = inputFile.getFileInfo();
	}
	

	/**
	 * Initialize the user information to be displayed once the user is authenticated into his home
	 * @param userName
	 * @throws DocSpaceException 
	 */
	private void initializeUser(String userName) throws DocSpaceException {
		logger.info("Entering initializeUser method");
		if(userName == null) {
			logger.severe("initializeUser : userName is null");
			throw new IllegalArgumentException("User Name is null");
		}
		MemberFacade memberFacade = (MemberFacade) ServiceLocator.getInstance().getBean("memberFacade");
		currentUser = memberFacade.retrieveUser(userName);
	}
	
	
//	private void initNavigations() {
//		HomepageNavigationBean homepageNavigationBean = (HomepageNavigationBean) ManagedBeanUtility.getManagedBean("HomepageNavigationBean");
//		homepageNavigationBean.initNavigations();
//	}
	
	
	
}
