package com.practo.urlshortener.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practo.urlshortener.Utility;
import com.practo.urlshortener.entities.Users;
import com.practo.urlshortener.models.UserModel;
import com.practo.urlshortener.services.UserService;

import inti.ws.spring.exception.client.ConflictException;
import inti.ws.spring.exception.client.UnauthorizedException;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void login(@RequestBody UserModel user, HttpSession session, HttpServletResponse response)
			throws UnauthorizedException {

		if (user == null || user.getEmailID() == null || user.getEmailID().trim().isEmpty())
			throw new IllegalArgumentException("User email/password are null");
		user.setEmailID(user.getEmailID().trim());
		Users userEntity = userService.isVaildUser(user);
		if (userEntity == null)
			throw new UnauthorizedException("User email/password are not valid");
		session.setAttribute(Utility.UserID_Session, userEntity.getId());

	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	@ResponseStatus(HttpStatus.CREATED)
	public void registration(@RequestBody UserModel user, HttpSession session) throws ConflictException {
		if (user == null | user.getName() == null | user.getName().isEmpty()
				| !Utility.isValidEMailID(user.getEmailID()) | user.getPassword() == null
				| user.getPassword().isEmpty())
			throw new IllegalArgumentException("User details are empty");
		user.setName(user.getName().trim());
		user.setEmailID(user.getEmailID().trim());
		Users userEntity = userService.getUserByEmail(user.getEmailID());
		if (userEntity != null)
			throw new ConflictException("User EmailID already in use");
		int userID = userService.registerUser(user);
		if (userID == -1)
			throw new IllegalArgumentException("Can not register user with given details");
		session.setAttribute(Utility.UserID_Session, userID);
	}

}
