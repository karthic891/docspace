/**
 * 
 */
package com.docspace.domain;

import java.util.Date;

/**
 * @author karthic
 *
 */
public interface IDocument {
	
	public Integer getId();
	
	public void setId(Integer id);
	
	public String getName();
	
	public void setName(String name);
	
	public String getType();
	
	public void setType(String type);
	
	public Date getCreationDate();
	
	public void setCreationDate(Date creationDate);
	
	public String getLastAccessDate();
	
	public void setLastAccessDate(String lastAccessDate);
	
	public IMember getOwner();
	
	public void setOwner(IMember owner);
	
	public boolean isFile();
	
	public void setFile(boolean file);
	
	public Integer getParentID();
	
	public void setParentID(Integer parentID);
	
	public byte[] getFileContent();
	
	public void setFileContent(byte[] fileContent);
	
	public boolean isSelected();
	
	public void setSelected(boolean selected);
	
	public boolean isLocked();
	
	public void setLocked(boolean locked);
	
}
