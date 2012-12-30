/**
 * 
 */
package com.docspace.home;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;
import javax.servlet.ServletContext;

import com.docspace.domain.Document;
import com.docspace.domain.IDocument;
import com.docspace.facade.DocumentFacade;
import com.docspace.helper.ManagedBeanUtility;
import com.docspace.utilities.Utils;
import com.docspace.utilities.exception.DocSpaceException;
import com.docspace.utilities.servicelocator.ServiceLocator;
import com.icesoft.faces.component.inputfile.FileInfo;
import com.icesoft.faces.component.inputfile.InputFile;

/**
 * @author karthic
 *
 */
public class DocumentHandlerBean {

	private final Logger logger = Logger.getLogger(DocumentHandlerBean.class.getName());
	private List<IDocument> files;
	private List<IDocument> homeDocuments;
	private List<Integer> documentsToBeProcessed;
	private String createUploadPopup;
	private String folderName;
	private String errorMessage;
	private Integer parentFolder;
	private FileInfo file;
	private int selectedCount;
	private boolean nameSortedFlag;
	private boolean dateSortedFlag;
	
	public DocumentHandlerBean() {
		files = new ArrayList<IDocument>();
		documentsToBeProcessed = new ArrayList<Integer>();
		homeDocuments = new ArrayList<IDocument>();
		parentFolder = 0;
	}

	/**
	 * @return the files
	 */
	public List<IDocument> getFiles() {
		return files;
	}

	/**
	 * @param files the files to set
	 */
	public void setFiles(List<IDocument> files) {
		this.files = files;
	}
	
	/**
	 * @return the homeDocuments
	 */
	public List<IDocument> getHomeDocuments() {
		return homeDocuments;
	}

	/**
	 * @param homeDocuments the homeDocuments to set
	 */
	public void setHomeDocuments(List<IDocument> homeDocuments) {
		this.homeDocuments = homeDocuments;
	}

	/**
	 * @return the documentsToBeProcessed
	 */
	public List<Integer> getDocumentsToBeProcessed() {
		return documentsToBeProcessed;
	}

	/**
	 * @param documentsToBeDeleted the documentsToBeDeleted to set
	 */
	public void setDocumentsToBeProcessed(List<Integer> documentsToBeProcessed) {
		this.documentsToBeProcessed = documentsToBeProcessed;
	}

	/**
	 * @return the createUploadPopup
	 */
	public String getCreateUploadPopup() {
		return createUploadPopup;
	}

	/**
	 * @param createUploadPopup the createUploadPopup to set
	 */
	public void setCreateUploadPopup(String createUploadPopup) {
		this.createUploadPopup = createUploadPopup;
	}
	

	/**
	 * @return the folderName
	 */
	public String getFolderName() {
		return folderName;
	}

	/**
	 * @param folderName the folderName to set
	 */
	public void setFolderName(String folderName) {
		this.folderName = folderName;
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
	 * @return the parentFolder
	 */
	public Integer getParentFolder() {
		return parentFolder;
	}

	/**
	 * @param parentFolder the parentFolder to set
	 */
	public void setParentFolder(Integer parentFolder) {
		this.parentFolder = parentFolder;
	}

	/**
	 * @return the file
	 */
	public FileInfo getFile() {
		return file;
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(FileInfo file) {
		this.file = file;
	}
	
	/**
	 * @return the selectedCount
	 */
	public int getSelectedCount() {
		return selectedCount;
	}

	/**
	 * @return the nameSortedFlag
	 */
	public boolean getNameSortedFlag() {
		return nameSortedFlag;
	}

	/**
	 * @param nameSortedFlag the nameSortedFlag to set
	 */
	public void setNameSortedFlag(boolean nameSortedFlag) {
		this.nameSortedFlag = nameSortedFlag;
	}

	/**
	 * @return the dateSortedFlag
	 */
	public boolean isDateSortedFlag() {
		return dateSortedFlag;
	}

	/**
	 * @param dateSortedFlag the dateSortedFlag to set
	 */
	public void setDateSortedFlag(boolean dateSortedFlag) {
		this.dateSortedFlag = dateSortedFlag;
	}

	public String delete() {
		System.out.println("delete operation called");
		IDocument document = null;
		Iterator<IDocument> iter = files.iterator();
		while(iter.hasNext()) {
			document = iter.next();
			if(document.isSelected()) {
				documentsToBeProcessed.add(document.getId());
			}
		}
		DocumentFacade documentFacade = (DocumentFacade) ServiceLocator.getInstance().getBean("documentFacade");
		try {
			documentFacade.deleteDocuments(documentsToBeProcessed);
		} catch (DocSpaceException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
			return "error";
		}
		documentsToBeProcessed = new ArrayList<Integer>();
		selectedCount = 0;
		loadDocuments(parentFolder);
		return null;
	}
	
	/**
	 * Launch popup for adding a folder or uploading a file
	 * @return
	 */
	public String launchCreateUploadPopup() {
		createUploadPopup = ManagedBeanUtility.getRequestParameter("popupType");
		return null;
	}
	
	/**
	 * Close the modal popup
	 * @return Navigation string
	 */
	public String closePopup() {
		createUploadPopup = null;
		errorMessage = null;
		folderName = null;
		return null;
	}
	
	public void valuechange(ValueChangeEvent event) {
		System.out.println(event);
		if(Boolean.valueOf(event.getNewValue().toString()).booleanValue()) {
			
		}
	}
	
	/**
	 * Add a new folder
	 * @return Navigation string
	 */
	public String addFolder() {
		if(! Utils.isNullOrEmpty(folderName)) {
			IDocument document = new Document();
			document.setFile(false);
			document.setName(folderName);
			HomeBean homeBean = (HomeBean) ManagedBeanUtility.getManagedBean("HomeBean");
			document.setOwner(homeBean.getCurrentUser());
			document.setType("folder");
			document.setParentID(parentFolder);
			DocumentFacade documentFacade = (DocumentFacade) ServiceLocator.getInstance().getBean("documentFacade");
			try {
				documentFacade.addFolder(document);
			} catch (DocSpaceException e) {
				FacesMessage message = new FacesMessage(e.getMessage());
				FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
				return "error";
			}
			createUploadPopup = null;
			errorMessage = null;
			folderName = null;
			loadDocuments(parentFolder);
		} else {
			errorMessage = "Please enter a folder name.";
		}
		return null;
	}
	
	/**
	 * Listens and executes when a file is uploaded. Saves the uploaded file into the DB
	 * @param actionEvent
	 */
	public void uploadListener(ActionEvent actionEvent) {
        InputFile inputFile = (InputFile) actionEvent.getSource();
        file = inputFile.getFileInfo();
        if(file.getFile() == null) {
        	errorMessage = "Please select a file.";
        } else {
        	FacesContext facesContext = FacesContext.getCurrentInstance();
			ServletContext servletContext = (ServletContext) facesContext.getExternalContext().getContext();
        	String fileName = file.getFileName();
            IDocument document = new Document();
    		document.setFile(true);
    		document.setName(fileName);
    		HomeBean homeBean = (HomeBean) ManagedBeanUtility.getManagedBean("HomeBean");
    		document.setOwner(homeBean.getCurrentUser());
    		document.setType(servletContext.getMimeType(fileName));
    		document.setParentID(parentFolder);
            try {
    			FileInputStream fileInputStream = new FileInputStream(file.getFile());
    			DocumentFacade documentFacade = (DocumentFacade) ServiceLocator.getInstance().getBean("documentFacade");
    			try {
					documentFacade.saveFile(document, fileInputStream);
				} catch (DocSpaceException e) {
					FacesMessage message = new FacesMessage(e.getMessage());
					FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
				}
    		} catch (FileNotFoundException e) {
    			e.printStackTrace();
    		}
            createUploadPopup = null;
            errorMessage = null;
            loadDocuments(parentFolder);
        }
        
	}
	
	/**
	 * Load the home folders
	 * @return Navigation string
	 */
	public String loadHomeFolders() {
		return loadDocuments(0);
	}
	
	/**
	 * Load the documents from DB identified by superFolder Id
	 * @param parentId
	 */
	public String loadDocuments(Integer parentId) {
		DocumentFacade documentFacade = (DocumentFacade) ServiceLocator.getInstance().getBean("documentFacade");
		try {
			files = documentFacade.loadDocuments(parentId);
		} catch (DocSpaceException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
			return "error";
		}
		List<IDocument> filesList = extractFilesOrFolder(files, true);
		List<IDocument> foldersList = extractFilesOrFolder(files, false);
		/* Load left side navigation */
		if(parentId == 0) {
			homeDocuments.clear();
			homeDocuments.addAll(foldersList); 
		}
		Collections.sort(filesList, new FileNameSorter());
		Collections.sort(foldersList, new FileNameSorter());
		files.clear();
		files.addAll(foldersList);
		files.addAll(filesList);
		return null;
	}
	
	/**
	 * Handle the selected document based on the following conditions.
	 * If file -> Make it available for download.
	 * If folder -> Navigate inside the folder.
	 * @return Navigation string - configured in faces-comfig.xml
	 */
	public String handleDocument() {
		String documentId = ManagedBeanUtility.getRequestParameter("documentId");
		DocumentFacade documentFacade = (DocumentFacade) ServiceLocator.getInstance().getBean("documentFacade");
		IDocument document = null;
		try {
			document = documentFacade.retrieveDocument(Integer.parseInt(documentId));
		} catch (NumberFormatException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
			return "error";
		} catch (DocSpaceException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
			return "error";
		}
		if(document.isFile()) {
		    logger.severe("Would have been handled by download servlet");
		} else {
			parentFolder = Integer.parseInt(documentId);
			loadDocuments(parentFolder);
		}
		return null;
	}
	
	public void documentSelectionListener(ValueChangeEvent event) {
		if(Boolean.valueOf(event.getNewValue().toString()).booleanValue()) {
			selectedCount++;
			System.out.println("selected :: " + selectedCount);
		} else {
			selectedCount--;
			System.out.println("unSelected :: " + selectedCount);
		}
	}
	
	public String sort() {
		String sortType = ManagedBeanUtility.getRequestParameter("sortBy");
		if(sortType != null) {
			List<IDocument> filesList = extractFilesOrFolder(files, true);
			List<IDocument> foldersList = extractFilesOrFolder(files, false);
			if(sortType.equalsIgnoreCase("date")) {
				Collections.sort(files, new DateSorter());
				if(dateSortedFlag) {
					dateSortedFlag = false;
					sortByComparator(filesList, foldersList, new DateSorter());
				} else {
					dateSortedFlag = true;
					Collections.sort(filesList, new DateSorter());
					Collections.reverse(filesList);
					Collections.sort(foldersList, new DateSorter());
					Collections.reverse(foldersList);
					files.clear();
					files.addAll(foldersList);
					files.addAll(filesList);
				}
			} else if(sortType.equalsIgnoreCase("name")) {
				if(nameSortedFlag) {
					nameSortedFlag = false;
					sortByComparator(filesList, foldersList, new FileNameSorter());
				} else {
					nameSortedFlag = true;
					Collections.sort(filesList, new FileNameSorter());
					Collections.reverse(filesList);
					Collections.sort(foldersList, new FileNameSorter());
					Collections.reverse(foldersList);
					files.clear();
					files.addAll(foldersList);
					files.addAll(filesList);
				}
			}
		}
		return null;
	}
	
	private void sortByComparator(List<IDocument> filesList, List<IDocument> foldersList, Comparator<IDocument> comparator) {
		Collections.sort(filesList, comparator);
		Collections.sort(foldersList, comparator);
		files.clear();
		files.addAll(foldersList);
		files.addAll(filesList);
	}
	
	private List<IDocument> extractFilesOrFolder(List<IDocument> docsList, boolean fileFlag) {
		List<IDocument> dataList = new ArrayList<IDocument>();
		if(fileFlag) {
			for(IDocument doc : docsList) {
				if(doc.isFile()) {
					dataList.add(doc);
				}
			}
		} else {
			for(IDocument doc : docsList) {
				if(! doc.isFile()) {
					dataList.add(doc);
				}
			}
		}
		return dataList;
	}
	
	public String lockDocuments() {
		IDocument document = null;
		Iterator<IDocument> iter = files.iterator();
		while(iter.hasNext()) {
			document = iter.next();
			if(document.isSelected()) {
				documentsToBeProcessed.add(document.getId());
			}
		}
		DocumentFacade documentFacade = (DocumentFacade) ServiceLocator.getInstance().getBean("documentFacade");
		try {
			documentFacade.lockDocuments(documentsToBeProcessed);
		} catch (DocSpaceException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
			return "error";
		}
		documentsToBeProcessed = new ArrayList<Integer>();
		selectedCount = 0;
		loadDocuments(parentFolder);
		return null;
	}
	
	public String unlockDocuments() {
		IDocument document = null;
		Iterator<IDocument> iter = files.iterator();
		while(iter.hasNext()) {
			document = iter.next();
			if(document.isSelected()) {
				documentsToBeProcessed.add(document.getId());
			}
		}
		DocumentFacade documentFacade = (DocumentFacade) ServiceLocator.getInstance().getBean("documentFacade");
		try {
			documentFacade.unLockDocuments(documentsToBeProcessed);
		} catch (DocSpaceException e) {
			FacesMessage message = new FacesMessage(e.getMessage());
			FacesContext.getCurrentInstance().addMessage("Error Occured.", message);
			return "error";
		}
		documentsToBeProcessed = new ArrayList<Integer>();
		selectedCount = 0;
		loadDocuments(parentFolder);
		return null;
	}
}

class DateSorter implements Comparator<IDocument> {

	public int compare(IDocument doc1, IDocument doc2) {
		return doc1.getCreationDate().compareTo(doc2.getCreationDate());
	}
}

class FileNameSorter implements Comparator<IDocument> {

	public int compare(IDocument doc1, IDocument doc2) {
		return doc1.getName().compareTo(doc2.getName());
	}
}
