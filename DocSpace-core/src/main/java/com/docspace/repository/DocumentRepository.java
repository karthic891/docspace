/**
 * 
 */
package com.docspace.repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import com.docspace.domain.Document;
import com.docspace.domain.IDocument;
import com.docspace.domain.IMember;
import com.docspace.domain.Member;
import com.docspace.utilities.exception.DocSpaceException;

/**
 * @author karthic
 *
 */
public class DocumentRepository {
	
	private final static Logger logger = Logger.getLogger(DocumentRepository.class.getName());
	final String DOCUMENT_ID = "DOCUMENT_ID";
	final String FILE_NAME = "FILE_NAME";
	final String OWNER_ID = "OWNER_ID";
	final String CREATION_DATE = "CREATION_DATE";
	final String LAST_ACCESSED_DATE = "LAST_ACCESSED_DATE";
	final String FILE_TYPE = "FILE_TYPE";
	final String PARENT_ID = "PARENT_ID";
	final String LOCK_STATUS = "LOCK_STATUS";
	final String FILE_CONTENT = "FILE_CONTENT";
	private DBConnector dbConnector;
	private MemberRepository memberRepository;
	
	public DocumentRepository(DBConnector dbConnector, MemberRepository memberRepository) {
		if(dbConnector == null || memberRepository == null) {
			logger.severe("dbConnector/memberRepository is null. Check spring config.");
			throw new IllegalArgumentException("dbConnector/MemberRepository is null. Check spring config.");
		}
		this.dbConnector = dbConnector;
		this.memberRepository = memberRepository;
	}
	
	/**
	 * Adds new document to the DB specified by document
	 * @param document
	 * @throws DocSpaceException 
	 */
	public void addFolder(IDocument document) throws DocSpaceException {
		logger.info("Adding a new folder to DB");
		Connection connection = dbConnector.getConnection();
		final StringBuilder sql = new StringBuilder("insert into DOCUMENT_INFO (FILE_NAME, OWNER_ID, CREATION_DATE, LAST_ACCESSED_DATE, FILE_TYPE, PARENT_ID) values (?, ?, ?, ?, ?, ?)");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql.toString());
			statement.setString(1, document.getName());
			statement.setInt(2, new Integer(document.getOwner().getUserID()));
			statement.setDate(3, new Date(new java.util.Date().getTime()));
			statement.setDate(4, null);
			statement.setNull(5, Types.VARCHAR);
			statement.setInt(6, document.getParentID());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.severe("Error adding new folder to DB. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		}  finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("Folder addition successful.");
	}
	
	/**
	 * Save the uploaded file contents specified by the input stream
	 * @param document
	 * @param fileInputStream
	 * @throws DocSpaceException 
	 */
	public void saveFile(IDocument document, FileInputStream fileInputStream) throws DocSpaceException {
		logger.info("Saving a file to DB");
		Connection connection = dbConnector.getConnection();
		final StringBuilder sql = new StringBuilder("insert into DOCUMENT_INFO (FILE_NAME, OWNER_ID, CREATION_DATE, LAST_ACCESSED_DATE, FILE_TYPE, PARENT_ID, FILE_CONTENT) values (?, ?, ?, ?, ?, ?, ?)");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql.toString());
			statement.setString(1, document.getName());
			statement.setInt(2, new Integer(document.getOwner().getUserID()));
			statement.setDate(3, new Date(new java.util.Date().getTime()));
			statement.setDate(4, null);
			statement.setString(5, document.getType());
			statement.setInt(6, document.getParentID());
			statement.setBlob(7, fileInputStream);
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.severe("Error saving uploaded file to DB. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		}  finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("File saved successfully.");
	}
	
	/**
	 * Load the documents under the folder specified by parentId
	 * @param parentId
	 * @return {@link List}
	 * @throws DocSpaceException 
	 */
	public List<IDocument> loadDocuments(Integer parentId) throws DocSpaceException {
		logger.info("Loading documents from DB.");
		Connection connection = dbConnector.getConnection();
		List<IDocument> documents = new ArrayList<IDocument>();
		IDocument document = null;
		String query = "select * from document_info where parent_Id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, parentId);
			ResultSet resultSet = statement.executeQuery();
			while(resultSet.next()) {
				document = new Document();
				document.setId(resultSet.getInt(DOCUMENT_ID));
				document.setName(resultSet.getString(FILE_NAME));
				document.setOwner(getOwner(resultSet.getInt(OWNER_ID)));
				document.setCreationDate(resultSet.getDate(CREATION_DATE));
				//document.setLastAccessDate(resultSet.getDate(lastAccessedDate).toString());
				document.setLocked(resultSet.getBoolean(LOCK_STATUS));
				String docType = resultSet.getString(FILE_TYPE);
				if(docType == null) {
					document.setFile(false);
				} else {
					document.setFile(true);
				}
				document.setType(docType);
				document.setParentID(resultSet.getInt(PARENT_ID));
				documents.add(document);
			}
		} catch (SQLException e) {
			logger.severe("Loading documents from DB error. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		}
		return documents;
	}
	
	/**
	 * Delete the documents specified by the list of documentIds
	 * @param documentId
	 * @throws DocSpaceException 
	 */
	public void deleteDocuments(List<Integer> documentId) throws DocSpaceException {
		logger.info("Deleting documents...");
		Connection connection = dbConnector.getConnection();
		final StringBuilder sql = new StringBuilder("delete from document_info where document_id = ?");
		PreparedStatement statement = null;
		try {
			statement = connection.prepareStatement(sql.toString());
			for(Integer docId : documentId) {
				statement.setInt(1, docId);
				statement.executeUpdate();
			} 
		} catch (SQLException e) {
			logger.severe("Error during documents deletion. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		}  finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		logger.info("Deletion of documents successful..");
	}
	
	/**
	 * Retrieve the document from DB identified by its id
	 * @param documentId
	 * @return {@link IDocument}
	 * @throws DocSpaceException 
	 */
	public IDocument retrieveDocument(Integer documentId) throws DocSpaceException {
		logger.info("Retrieving document from DB.");
		Connection connection = dbConnector.getConnection();
		IDocument document = null;
		String query = "select * from document_info where document_Id = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setInt(1, documentId);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				document = new Document();
				document.setId(resultSet.getInt(DOCUMENT_ID));
				document.setName(resultSet.getString(FILE_NAME));
				document.setOwner(getOwner(resultSet.getInt(OWNER_ID)));
				document.setCreationDate(resultSet.getDate(CREATION_DATE));
				//document.setLastAccessDate(resultSet.getDate(lastAccessedDate).toString());
				String docType = resultSet.getString(FILE_TYPE);
				if(docType == null) {
					document.setFile(false);
				} else {
					document.setFile(true);
				}
				document.setType(docType);
				document.setParentID(resultSet.getInt(PARENT_ID));
				if(document.isFile()) {
					logger.info("Reading file content..");
					Blob blobContent = resultSet.getBlob(FILE_CONTENT);
					InputStream inputStream = blobContent.getBinaryStream();
					byte[] fileContent = new byte[(int) blobContent.length()];
					inputStream.read(fileContent);
					document.setFileContent(fileContent);
				}
			}
		} catch (SQLException e) {
			logger.severe("Retrieval of documents from DB error. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		} catch (IOException e) {
			logger.severe("Retrieval of documents from DB error. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		}
		return document;
	}
	
	/**
	 * Get the owner of a document(member) identified by his/her id 
	 * @param userId
	 * @return {@link IMember}
	 * @throws DocSpaceException 
	 */
	private IMember getOwner(Integer userId) throws DocSpaceException {
		IMember owner = memberRepository.retrieveUserByUserId(userId);
		return (owner != null) ? owner : new Member();
	}
	
	/**
	 * Lock the documents so that they cannot be accessed
	 * @param documentList
	 * @throws DocSpaceException 
	 */
	public void lockDocuments(List<Integer> documentList) throws DocSpaceException {
		logger.info("Locking the documents..");
		Connection connection = dbConnector.getConnection();
		final StringBuilder sql = new StringBuilder("update DOCUMENT_INFO set LOCK_STATUS = ? WHERE DOCUMENT_ID = ?");
		PreparedStatement statement = null;
		int lockStatus = 1;
		try {
			statement = connection.prepareStatement(sql.toString());
			for(Integer documentId : documentList) {
				statement.setInt(1, lockStatus);
				statement.setInt(2, documentId);
				statement.executeUpdate();
			} 
		} catch (SQLException e) {
			logger.severe("Error locking documents.. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		}  finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		logger.info("Documents locked successfully.");
		}
	}
	
	/**
	 * Lock the documents so that they cannot be accessed
	 * @param documentList
	 * @throws DocSpaceException 
	 */
	public void unLockDocuments(List<Integer> documentList) throws DocSpaceException {
		logger.info("Unlocking the documents..");
		Connection connection = dbConnector.getConnection();
		final StringBuilder sql = new StringBuilder("update DOCUMENT_INFO set LOCK_STATUS = ? WHERE DOCUMENT_ID = ?");
		PreparedStatement statement = null;
		int lockStatus = 0;
		try {
			statement = connection.prepareStatement(sql.toString());
			for(Integer documentId : documentList) {
				statement.setInt(1, lockStatus);
				statement.setInt(2, documentId);
				statement.executeUpdate();
			} 
		} catch (SQLException e) {
			logger.severe("Error unlocking documents.. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		}  finally {
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		logger.info("Documents unlocked successfully.");
		}
	}
	
}
