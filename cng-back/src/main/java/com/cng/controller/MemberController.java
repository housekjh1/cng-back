package com.cng.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cng.domain.MemberDTO;
import com.cng.domain.Response;
import com.cng.service.MemberService;

@RestController
public class MemberController {
	
	@Autowired
	private MemberService memS;
	
	@PostMapping("/join")
	public String join(@RequestBody MemberDTO memberDTO) {
		return memS.join(memberDTO.getUsername(), memberDTO.getRealname(), memberDTO.getPassword());
	}
	
	@PostMapping("/getUser")
	public Response getUser(@RequestParam String hasToken) {
		return memS.getUser(hasToken);
	}
}
