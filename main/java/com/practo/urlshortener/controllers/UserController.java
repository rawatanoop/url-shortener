package com.practo.urlshortener.controllers;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.practo.urlshortener.Utility;
import com.practo.urlshortener.entities.Users;
import com.practo.urlshortener.models.UserModel;
import com.practo.urlshortener.services.UserService;

@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@RequestMapping(value = "/user/login", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> login(@RequestBody UserModel user, HttpSession session,
			HttpServletResponse response) {

		if (user == null || user.getEmailID() == null || user.getEmailID().trim().isEmpty())
			return new ResponseEntity<String>("User email/password are not valid", HttpStatus.UNAUTHORIZED);
		user.setEmailID(user.getEmailID().trim());
		Users userEntity = userService.isVaildUser(user);
		if (userEntity == null)
			return new ResponseEntity<String>("User email/password are not valid", HttpStatus.UNAUTHORIZED);
		session.setAttribute(Utility.UserID_Session, userEntity.getId());
		return new ResponseEntity<String>("Successfully logged-in", HttpStatus.OK);

	}

	@RequestMapping(value = "/user/logout", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> logout(HttpSession session) {
		session.invalidate();
		return new ResponseEntity<String>("Successfully logged-out", HttpStatus.OK);

	}

	@RequestMapping(value = "/user/register", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> registration(@RequestBody UserModel user, HttpSession session) {
		if (user == null | user.getName() == null | user.getName().isEmpty()
				| !Utility.isValidEMailID(user.getEmailID()) | user.getPassword() == null
				| user.getPassword().isEmpty())
			return new ResponseEntity<String>("User details are not valid", HttpStatus.BAD_REQUEST);
		user.setName(user.getName().trim());
		user.setEmailID(user.getEmailID().trim());
		int userID = userService.registerUser(user);
		if (userID == -1)
			return new ResponseEntity<String>("User EmailID already in use", HttpStatus.UNAUTHORIZED);
		session.setAttribute(Utility.UserID_Session, userID);
		return new ResponseEntity<String>("User successfully registered", HttpStatus.CREATED);

	}

}
