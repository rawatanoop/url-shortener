package com.practo.urlshortener.services;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.practo.urlshortener.Utility;
import com.practo.urlshortener.daos.ShortenerDao;
import com.practo.urlshortener.daos.UserDao;
import com.practo.urlshortener.entities.Users;
import com.practo.urlshortener.models.UserModel;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;

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
		
		return new Users(null,user.getName(),null,null,user.getEmailID());
	}

	public Users isVaildUser(UserModel userModel) {
		if (userModel!=null&&userModel.getPassword()!=null&&Utility.isValidEMailID(userModel.getEmailID())) {
			try {
				Users user = userDao.getUserByEmailId(userModel.getEmailID());
				if (user!=null&&Utility.decryptPassword(user.getPassword()).equals(userModel.getPassword()))
					return user;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

}
