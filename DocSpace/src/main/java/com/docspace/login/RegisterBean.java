/**
 * 
 */
package com.docspace.login;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import com.docspace.domain.IMember;
import com.docspace.facade.MemberFacade;
import com.docspace.helper.ManagedBeanUtility;
import com.docspace.helper.MemberHelper;
import com.docspace.utilities.Utils;
import com.docspace.utilities.exception.DocSpaceException;
import com.docspace.utilities.servicelocator.ServiceLocator;
import com.icesoft.faces.context.effects.Appear;
import com.icesoft.faces.context.effects.Effect;
import com.icesoft.faces.context.effects.EffectQueue;
import com.icesoft.faces.context.effects.Fade;

/**
 * @author karthic
 *
 */
public class RegisterBean {
	
	private static Logger logger = Logger.getLogger(RegisterBean.class.getName());
	private String userName;
	private String firstName;
	private String lastName;
	private String emailId;
	private String password;
	private String repeatPwd;
	private String gender;
	private String day;
	private String month;
	private String year;
	private List<SelectItem> genderItems;
	private List<SelectItem> days;
	private List<SelectItem> months;
	private List<SelectItem> years;
	private String errorMessage;
	
	private Effect effect = new EffectQueue("myEffect");
	
	public RegisterBean() {
		genderItems = new ArrayList<SelectItem>();
		genderItems.add(new SelectItem("", "Select One"));
		genderItems.add(new SelectItem("M", "Male"));
		genderItems.add(new SelectItem("F", "Female"));
		months = new ArrayList<SelectItem>();
		months.add(new SelectItem("", "Month:"));
		for(String monthName : Utils.MONTHS_OF_THE_YEAR) {
			months.add(new SelectItem(monthName, monthName));
		}
		days = new ArrayList<SelectItem>();
		days.add(new SelectItem("", "Day:"));
		for(int day=1; day<=31; day++) {
			days.add(new SelectItem(day, Integer.toString(day)));
		}
		years = new ArrayList<SelectItem>();
		years.add(new SelectItem("", "Year:"));
		for(int year=1; year<=31; year++) {
			years.add(new SelectItem(year, Integer.toString(year)));
		}
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
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
	 * @return the repeatPwd
	 */
	public String getRepeatPwd() {
		return repeatPwd;
	}
	
	/**
	 * @param repeatPwd the repeatPwd to set
	 */
	public void setRepeatPwd(String repeatPwd) {
		this.repeatPwd = repeatPwd;
	}
	
	
	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}

	/**
	 * @return the month
	 */
	public String getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(String month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(String year) {
		this.year = year;
	}

	/**
	 * @return the genderItems
	 */
	public List<SelectItem> getGenderItems() {
		return genderItems;
	}
	
	/**
	 * @return the days
	 */
	public List<SelectItem> getDays() {
		return days;
	}

	/**
	 * @param days the days to set
	 */
	public void setDays(List<SelectItem> days) {
		this.days = days;
	}

	/**
	 * @return the months
	 */
	public List<SelectItem> getMonths() {
		return months;
	}

	/**
	 * @param months the months to set
	 */
	public void setMonths(List<SelectItem> months) {
		this.months = months;
	}

	/**
	 * @return the years
	 */
	public List<SelectItem> getYears() {
		return years;
	}

	/**
	 * @param years the years to set
	 */
	public void setYears(List<SelectItem> years) {
		this.years = years;
	}

	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		((EffectQueue) effect).add(new Fade(0, 0.25f));
		((EffectQueue) effect).add(new Appear(0.26f, 0.5f));
		((EffectQueue) effect).setDuration(0.5f);
		effect.setFired(false);
		return errorMessage;
	}

	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	
	/**
	 * @return the effect
	 */
	public Effect getEffect() {
		return effect;
	}

	/**
	 * @param effect the effect to set
	 */
	public void setEffect(Effect effect) {
		this.effect = effect;
	}

	/**
	 * This method saves the new user information into the data base
	 * @return Navigation string - configured in faces-config.xml
	 */
	public String registerUser() {
		logger.info("Registering a new user...");
		LoginBean loginBean = (LoginBean) ManagedBeanUtility.getManagedBean("LoginBean");
		loginBean.setErrorMessage(null);
		errorMessage = null;
		IMember newUser = MemberHelper.createMember();
		newUser.setUserName(userName);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmailId(emailId);
		newUser.setGender(gender);
		newUser.setPassword(password);
		if(validateRegisterInfo(newUser)) {
			MemberFacade memberFacade = (MemberFacade) ServiceLocator.getInstance().getBean("memberFacade");
			try {
				if(memberFacade.registerUser(newUser).equalsIgnoreCase("SUCCESS")) {
					logger.info("New user registration successful. Navigating to home page...");
					loginBean.setUserName(newUser.getUserName());
					return (String) ManagedBeanUtility.invokeMethodBinding("#{HomeBean.enterHome}");
				} else {
					errorMessage = "There is an existing account associated with this Email Id";
				}
			} catch (DocSpaceException e) {
				FacesMessage message = new FacesMessage(e.getMessage());
				FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
				return "error";
			}
		}
		return null;
	}
	
	private boolean validateRegisterInfo(IMember newUser) {
		logger.info("Validating new user information.");
		boolean error = false;
		if(!Utils.isNullOrEmpty(newUser.getEmailId())) {
			if(! Pattern.matches("^[a-zA-z][-.a-zA-Z0-9].*@[a-zA-Z]+.[a-zA-Z]+", newUser.getEmailId())) {
				errorMessage = "Email ID not in proper format\n";
				error = true;
			}
		}
		test: {
			if (Utils.isNullOrEmpty(newUser.getFirstName())
					|| Utils.isNullOrEmpty(newUser.getLastName())
					|| Utils.isNullOrEmpty(newUser.getEmailId())
					|| Utils.isNullOrEmpty(newUser.getPassword())
					|| Utils.isNullOrEmpty(newUser.getGender())) {
				errorMessage = "You must fill in all the fields.";
				error = true;
				break test;
			}
			if(! newUser.getPassword().equals(repeatPwd)) {
				errorMessage = "The passwords do not match.";
				error = true;
				break test;
			}
		}
		if(error) {
			return false;
		}
		return true;
	}

}
