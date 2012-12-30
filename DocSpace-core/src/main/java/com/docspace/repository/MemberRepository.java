/**
 * 
 */
package com.docspace.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

import com.docspace.domain.IMember;
import com.docspace.domain.Member;
import com.docspace.utilities.exception.DocSpaceException;

/**
 * @author karthic
 *
 */
public class MemberRepository {
	
	private final static Logger logger = Logger.getLogger(MemberRepository.class.getName());
	DBConnector dbConnector;
	final String userName = "USERNAME";
	final String password = "PASSWORD";
	final String dob = "DATE_OF_BIRTH";
	final String firstName = "FIRSTNAME";
	final String lastName = "LASTNAME";
	final String gender = "GENDER";
	final String emailId = "EMAIL_ID";
	
	public MemberRepository(DBConnector dbConnector) {
		if(dbConnector == null) {
			logger.severe("dbConnector is null. Check spring config.");
			throw new IllegalArgumentException("dbConnector is null. Check spring config.");
		}
		this.dbConnector = dbConnector;
	}
	
	public void print() {
		System.out.println("PRINTED!!");
	}
	
	public String authenticateMember(IMember member) throws DocSpaceException {
		Connection connection = dbConnector.getConnection();
		String query = "select password from userinfo where username = ?";
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			statement = connection.prepareStatement(query);
			statement.setString(1, member.getUserName());
			resultSet = statement.executeQuery();
			if(resultSet.next()) {
				String pwdFromDB = resultSet.getString(1);
				if(member.getPassword().equals(pwdFromDB)) {
					return "success";
				}
			}
			
		} catch (SQLException e) {
			logger.severe("User Authentication error. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		} finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return "failure";
	}
	
	/**
	 * 
	 * @param newUser
	 * @return
	 * @throws DocSpaceException 
	 */
	public String registerUser(IMember newUser) throws DocSpaceException {
		if(! userAlreadyExists(newUser)) {
			saveUser(newUser);
			return "SUCCESS";
		} else {
			return "FAILURE";
		}
	}
	
	/**
	 * Retrieves the user information from the database given the user name
	 * @return {@link IMember}
	 * @throws DocSpaceException 
	 */
	public IMember retrieveUser(String userName) throws DocSpaceException {
		Connection connection = dbConnector.getConnection();
		String query = "select * from userinfo where username = ?";
		try {
			PreparedStatement statement = connection.prepareStatement(query);
			statement.setString(1, userName);
			ResultSet resultSet = statement.executeQuery();
			resultSet.next();
			IMember member = new Member();
			member.setUserID(resultSet.getString("user_id"));
			member.setUserName(userName);
			member.setFirstName(resultSet.getString(firstName));
			member.setLastName(resultSet.getString(lastName));
			member.setGender(resultSet.getString(gender));
			member.setEmailId(resultSet.getString(emailId));
			return member;
		} catch (SQLException e) {
			logger.severe("User Retrieval error. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		}
	}
	
	/**
	 * Retrieves the user information from the database given the userId
	 * @param userId
	 * @return {@link IMember}
	 * @throws DocSpaceException 
	 */
	public IMember retrieveUserByUserId(Integer userId) throws DocSpaceException {
		logger.info("Retrieve user by userId");
		Connection connection = dbConnector.getConnection();
		String query = "select * from userinfo where user_id = ?";
		PreparedStatement statement;
		try {
			statement = connection.prepareStatement(query);
			statement.setInt(1, userId);
			ResultSet resultSet = statement.executeQuery();
			if(resultSet.next()) {
				return retrieveUser(resultSet.getString(userName));
			}
		} catch (SQLException e) {
			logger.severe("Retrieval by userId error. " + e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Checks if an user with the same email(user Id) exists in the system already
	 * @param newUser
	 * @return true - if an user already exists
	 */
	private boolean userAlreadyExists(IMember newUser) {
		Connection connection = dbConnector.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String sql = "select * from UserInfo where userName = ?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, newUser.getUserName());
			resultSet = statement.executeQuery();
			if(resultSet.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}
	
	
	/**
	 * Save the User Info into the DB
	 * @param user
	 * @throws DocSpaceException 
	 */
	private void saveUser(IMember user) throws DocSpaceException {
		logger.info("Saving User Info into DB");
		Connection connection = dbConnector.getConnection();
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		StringBuilder sql = new StringBuilder("insert into UserInfo (");
		sql.append(userName);
		sql.append(", ");
		sql.append(password);
		sql.append(", ");
		sql.append(firstName);
		sql.append(", ");
		sql.append(lastName);
		sql.append(", ");
		sql.append(gender);
		sql.append(", ");
		sql.append(emailId);
		sql.append(") values (?, ?, ?, ?, ?, ?);");
		try {
			statement = connection.prepareStatement(sql.toString());
			statement.setString(1, user.getUserName());
			statement.setString(2, user.getPassword());
			statement.setString(3, user.getFirstName());
			statement.setString(4, user.getLastName());
			statement.setString(5, user.getGender());
			statement.setString(6, user.getEmailId());
			statement.executeUpdate();
		} catch (SQLException e) {
			logger.severe("Error saving User Info into DB. " + e.getMessage());
			throw new DocSpaceException(e.getMessage());
		} finally {
			if(resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
