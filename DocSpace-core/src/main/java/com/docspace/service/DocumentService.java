/**
 * 
 */
package com.docspace.service;

import java.io.FileInputStream;
import java.util.List;

import com.docspace.domain.IDocument;
import com.docspace.repository.DocumentRepository;
import com.docspace.utilities.exception.DocSpaceException;

/**
 * @author karthic
 *
 */
public class DocumentService {
	
	private DocumentRepository documentRepository;
	
	public DocumentService(DocumentRepository documentRepository) {
		if(documentRepository == null) {
			throw new IllegalArgumentException("documentRepository is null. Check spring config");
		}
		this.documentRepository = documentRepository;  
	}
	
	/**
	 * Adds new document to the DB specified by document
	 * @param document
	 * @throws DocSpaceException 
	 */
	public void addFolder(IDocument document) throws DocSpaceException {
		documentRepository.addFolder(document);
	}
	
	/**
	 * Save the uploaded file contents specified by the input stream
	 * @param document
	 * @param fileInputStream
	 * @throws DocSpaceException 
	 */
	public void saveFile(IDocument document, FileInputStream fileInputStream) throws DocSpaceException {
		documentRepository.saveFile(document, fileInputStream);
	}
	
	/**
	 * Load the documents under the folder specified by parentId
	 * @param parentId
	 * @return {@link List}
	 * @throws DocSpaceException 
	 */
	public List<IDocument> loadDocuments(Integer parentId) throws DocSpaceException {
		return documentRepository.loadDocuments(parentId);
	}
	
	/**
	 * Delete the documents specified by the list of documentIds
	 * @param documentId
	 * @throws DocSpaceException 
	 */
	public void deleteDocuments(List<Integer> documentId) throws DocSpaceException {
		documentRepository.deleteDocuments(documentId);
	}
	
	/**
	 * Retrieve the document from DB identified by its id
	 * @param documentId
	 * @return {@link IDocument}
	 * @throws DocSpaceException 
	 */
	public IDocument retrieveDocument(Integer documentId) throws DocSpaceException {
		return documentRepository.retrieveDocument(documentId);
	}
	
	/**
	 * Lock the documents so that they cannot be accessed
	 * @param documentList
	 * @throws DocSpaceException 
	 */
	public void lockDocuments(List<Integer> documentList) throws DocSpaceException {
		documentRepository.lockDocuments(documentList);
	}
	
	/**
	 * Lock the documents so that they cannot be accessed
	 * @param documentList
	 * @throws DocSpaceException 
	 */
	public void unLockDocuments(List<Integer> documentList) throws DocSpaceException {
		documentRepository.unLockDocuments(documentList);
	}

}
