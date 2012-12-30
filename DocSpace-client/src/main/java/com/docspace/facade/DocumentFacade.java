/**
 * 
 */
package com.docspace.facade;

import java.io.FileInputStream;
import java.util.List;

import com.docspace.domain.IDocument;
import com.docspace.utilities.exception.DocSpaceException;

/**
 * @author karthic
 *
 */
public interface DocumentFacade {

	/**
	 * Adds a folder specified by document
	 * @param document
	 */
	public void addFolder(IDocument document) throws DocSpaceException;
	
	/**
	 * Save the uploaded file contents specified by the input stream
	 * @param document
	 * @param fileInputStream
	 */
	public void saveFile(IDocument document, FileInputStream fileInputStream) throws DocSpaceException;
	
	/**
	 * Load the documents under the folder specified by parentId
	 * @param parentId
	 * @return {@link List}
	 */
	public List<IDocument> loadDocuments(Integer parentId) throws DocSpaceException;
	
	/**
	 * Delete the documents specified by the list of documentIds
	 * @param documentId
	 */
	public void deleteDocuments(List<Integer> documentId) throws DocSpaceException;
	
	/**
	 * Retrieve the document from DB identified by its id
	 * @param documentId
	 * @return {@link IDocument}
	 */
	public IDocument retrieveDocument(Integer documentId) throws DocSpaceException;
	
	/**
	 * Lock the documents so that they cannot be accessed
	 * @param documentList
	 */
	public void lockDocuments(List<Integer> documentList) throws DocSpaceException;
	
	/**
	 * Lock the documents so that they cannot be accessed
	 * @param documentList
	 */
	public void unLockDocuments(List<Integer> documentList) throws DocSpaceException;

}
