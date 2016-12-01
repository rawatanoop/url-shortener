package com.practo.urlshortener.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.urlshortener.Utility;
import com.practo.urlshortener.daos.UserDao;
import com.practo.urlshortener.entities.Users;
import com.practo.urlshortener.models.UserModel;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

	/***
	 * Creates a user with unique email-id and store the password with
	 * encryption.
	 * 
	 * @param user
	 * @return
	 */
	public int registerUser(UserModel user) {
		if (!Utility.isValidEMailID(user.getEmailID()))
			return -1;
		try {
			String password = Utility.encriptPassword(user.getPassword());
			Users userEntity = getUserEnity(user);
			userEntity.setPassword(password);
			return userDao.createUser(userEntity);
		} catch (Exception e) {
			return -1;
		}

	}

	private Users getUserEnity(UserModel user) {

		return new Users(null, user.getName(), null, null, user.getEmailID());
	}

	/****
	 * Checks whether user is registered in database.
	 * 
	 * @param userModel
	 * @return
	 */
	public Users isVaildUser(UserModel userModel) {
		if (userModel != null && userModel.getPassword() != null && Utility.isValidEMailID(userModel.getEmailID())) {
			try {
				Users user = getUserByEmail(userModel.getEmailID());
				if (user != null && Utility.decryptPassword(user.getPassword()).equals(userModel.getPassword()))
					return user;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}
	
	public Users getUserByEmail(String email){
		 return userDao.getUserByEmailId(email);
	}

}
