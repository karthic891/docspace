/**
 * 
 */
package com.docspace.domain;

import java.util.Date;

/**
 * @author karthic
 *
 */
public class Document implements IDocument {
	
	private Integer id;
	private String name;
	private String type;
	private Date creationDate;
	private String lastAccessDate;
	private IMember owner;
	private boolean file;
	private Integer parentID;
	private byte[] fileContent;
	private boolean selected;
	private boolean locked;
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}
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
	 * @return the type
	 */
	public String getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}
	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the lastAccessDate
	 */
	public String getLastAccessDate() {
		return lastAccessDate;
	}
	/**
	 * @param lastAccessDate the lastAccessDate to set
	 */
	public void setLastAccessDate(String lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}
	/**
	 * @return the owner
	 */
	public IMember getOwner() {
		return owner;
	}
	/**
	 * @param owner the owner to set
	 */
	public void setOwner(IMember owner) {
		this.owner = owner;
	}
	/**
	 * @return the file
	 */
	public boolean isFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(boolean file) {
		this.file = file;
	}
	/**
	 * @return the parentID
	 */
	public Integer getParentID() {
		return parentID;
	}
	/**
	 * @param parentID the parentID to set
	 */
	public void setParentID(Integer parentID) {
		this.parentID = parentID;
	}
	/**
	 * @return the fileContent
	 */
	public byte[] getFileContent() {
		return fileContent;
	}
	/**
	 * @param fileContent the fileContent to set
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}
	/**
	 * @return the selected
	 */
	public boolean isSelected() {
		return selected;
	}
	/**
	 * @param selected the selected to set
	 */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	/**
	 * @return the locked
	 */
	public boolean isLocked() {
		return locked;
	}
	/**
	 * @param locked the locked to set
	 */
	public void setLocked(boolean locked) {
		this.locked = locked;
	}
	
}
