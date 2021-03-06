package com.snapdeal.gohack.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.gohack.model.AuthenticateResponse;
import com.snapdeal.gohack.model.HackUser;
import com.snapdeal.gohack.service.HackUserService;




@RestController
public class HackUserController {

	Logger logger= LoggerFactory.getLogger(HackUserController.class);
	
	@Autowired
	private HackUserService hackUserService;

	@RequestMapping(value="/user/signup" ,method=RequestMethod.POST,headers = "content-type=application/x-www-form-urlencoded;charset=UTF-8" ,
			produces={"application/json"}
			)
	public @ResponseBody String doUserRegistration(@ModelAttribute  HackUser user)
			throws Exception{
		String userRegistrationRepose=hackUserService.doUserRegistration(user);
		return userRegistrationRepose;

	}
	
	
	@RequestMapping(value="/user/{userEmail}/forgotPassword", method=RequestMethod.GET)
	public String forgortPassword(@PathVariable String userEmail){
		return hackUserService.forgotPassword(userEmail);
		
	}
	
	@Cacheable(value="userLoginAuthentication")
	@RequestMapping (value="/user/login",method=RequestMethod.POST)
	public AuthenticateResponse loginAuthenticate(@RequestBody HackUser user){
		return hackUserService.doUserAuthentication(user);
	}
	
}
