package com.sap.bkc.controllers;

import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.web3j.crypto.Credentials;

import com.sap.bkc.utils.CredentialsHelper;

@RestController
@RequestMapping("/property/Login")
public class LoginController {
	
	@GetMapping(value = "/{user}/{password}/")
	public void setSessionCredentials(HttpSession session, @PathVariable("user") String user,@PathVariable("password") String password) {
		
		Credentials  creds = CredentialsHelper.getCredentials(user, password);
		System.out.println("coming here");
		System.out.println(creds.getAddress());
		session.setAttribute("creds", creds);
		
	}
}
