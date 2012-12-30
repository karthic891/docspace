/**
 * 
 */
package com.docspace.helper;

import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;

import javax.faces.context.FacesContext;

/**
 * The SessionController class aids in Session Management for the application 
 * @author karthic
 *
 */
public class SessionController {
	private static final Logger log = Logger.getLogger(SessionController.class.getName());
	
	/**
	 * Cleans the session by removing all the objects from session
	 */
	public static void cleanSession() {
		Map sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		Iterator<String> iterator = sessionMap.keySet().iterator();
		while(iterator.hasNext()) {
			String attributeName = iterator.next();
			removeAttributeFromSession(attributeName);
		}
	}
	
	/**
	 * Removes the attribute specified by attributeName from the session
	 * @param attributeName - the name of the attribute to be removed from session
	 */
	private static void removeAttributeFromSession(String attributeName) {
		log.info("Entering removeAttributeFromSession method");
		Map sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
		sessionMap.remove(attributeName);
		log.info(attributeName+" - removed from session");
	}

}
