/**
 * 
 */
package com.docspace.facade;

import java.io.FileInputStream;
import java.util.List;

import com.docspace.domain.IDocument;
import com.docspace.service.DocumentService;
import com.docspace.utilities.exception.DocSpaceException;

/**
 * @author karthic
 *
 */
public class DocumentFacadeImpl implements DocumentFacade {
	
	private DocumentService documentService;
	
	public DocumentFacadeImpl(DocumentService documentService) {
		if(documentService == null) {
			throw new IllegalArgumentException("documentService is null. Check spring config");
		}
		this.documentService = documentService;
	}
	
	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.DocumentFacade#addFolder(IDocument)
	 */
	public void addFolder(IDocument document) throws DocSpaceException {
		documentService.addFolder(document);
	}
	
	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.DocumentFacade#saveFile(FileInputStream)
	 */
	public void saveFile(IDocument document, FileInputStream fileInputStream) throws DocSpaceException {
		documentService.saveFile(document, fileInputStream);
	}
	
	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.DocumentFacade#loadDocuments(Integer)
	 */
	public List<IDocument> loadDocuments(Integer parentId) throws DocSpaceException {
		return documentService.loadDocuments(parentId);
	}
	
	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.DocumentFacade#deleteDocuments(List)
	 */
	public void deleteDocuments(List<Integer> documentId) throws DocSpaceException {
		documentService.deleteDocuments(documentId);
	}
	
	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.DocumentFacade#retrieveDocument(Integer)
	 */
	public IDocument retrieveDocument(Integer documentId) throws DocSpaceException {
		return documentService.retrieveDocument(documentId);
	}
	
	/**
	 * @throws DocSpaceException 
	 * @see com.docspace.facade.DocumentFacade#lockDocuments(List)
	 */
	public void lockDocuments(List<Integer> documentList) throws DocSpaceException {
		documentService.lockDocuments(documentList);
	}
	
	/**
	 * @see com.docspace.facade.DocumentFacade#unLockDocuments(List)
	 * @param documentList
	 * @throws DocSpaceException 
	 */
	public void unLockDocuments(List<Integer> documentList) throws DocSpaceException {
		documentService.unLockDocuments(documentList);
	}

}
