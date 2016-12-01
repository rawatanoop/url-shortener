package com.practo.urlshortener.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import inti.ws.spring.exception.client.ConflictException;
import inti.ws.spring.exception.client.UnauthorizedException;

@ControllerAdvice
@RestController
public class GlobalExceptionHandeler {

	@ExceptionHandler
	public void handleIllegalArgumentException(IllegalArgumentException e, HttpServletResponse response)
			throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler
	public void handelConflictException(ConflictException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.CONFLICT.value());
	}

	@ExceptionHandler
	public void handelUnautherizedException(UnauthorizedException e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.UNAUTHORIZED.value());
	}

	@ExceptionHandler
	public void handelException(Exception e, HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Some interner error occurred!");
	}
}
