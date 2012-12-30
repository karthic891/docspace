/**
 * 
 */
package com.docspace.helper;

import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;

import com.docspace.utilities.Utils;

/**
 * @author karthic
 * The ManagedBeanUtility class is an utility class that contains helper methods for the managed bean classes
 */
public class ManagedBeanUtility {
	
	private ManagedBeanUtility() {
	}
	
	/**
	 * Get the request parameter passed from source page.
	 * @param paramName
	 * @return {@link String} - The value of the passed parameter
	 */
	public static String getRequestParameter(String paramName) {
		if(Utils.isNullOrEmpty(paramName)) {
			throw new IllegalArgumentException(paramName+" is null or empty");
		}
		Object paramValue = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap().get(paramName);
		return (paramValue != null) ? paramValue.toString() : null;
	}
	
	/**
	 * Get reference to a managed bean from the session given its name 
	 * @param beanName
	 * @return Reference to the managed bean
	 */
	public static Object getManagedBean(String beanName) { 
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ValueBinding valueBinding = facesContext.getApplication().createValueBinding("#{"+beanName+"}");
		return valueBinding.getValue(facesContext);
	}
	
	/**
	 * Calls a method of a particular managed bean specified by the method expression
	 * Example: #{BeanName.methodName}
	 * @param methodExpression
	 * @return 
	 */
	public static Object invokeMethodBinding(String methodExpression) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		return facesContext.getApplication().createMethodBinding(methodExpression, null).invoke(facesContext, null);
	}

}
