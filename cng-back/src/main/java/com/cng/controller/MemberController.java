package com.cng.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cng.domain.Member;
import com.cng.domain.MemberDTO;
import com.cng.domain.Response;
import com.cng.domain.Role;
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

	@PutMapping("/member/changePW")
	public Response changePW(@RequestParam String username, @RequestParam String password) {
		return memS.changePW(username, password);
	}

	@PostMapping("/admin/getUserList")
	public Map<String, Object> getUserList(@RequestParam String mode, @RequestParam String value,
			@RequestParam(defaultValue = "0") int page) {

		if (mode.equals("1")) {
			Page<Member> userPage = memS.getUserList(value, page);
			long totalCount = memS.getUserCount(value);

			Map<String, Object> response = new HashMap<>();
			response.put("totalCount", totalCount);
			response.put("users", userPage.getContent());
			response.put("currentPage", userPage.getNumber());
			response.put("totalPages", userPage.getTotalPages());

			return response;

		} else if (mode.equals("2")) {
			Page<Member> userPage = memS.getDisabledMembers(page);
			long totalCount = userPage.getTotalElements();

			Map<String, Object> response = new HashMap<>();
			response.put("totalCount", totalCount);
			response.put("users", userPage.getContent());
			response.put("currentPage", userPage.getNumber());
			response.put("totalPages", userPage.getTotalPages());

			return response;
		} else {
			Page<Member> userPage = memS.getPunishedMembers(page);
			long totalCount = userPage.getTotalElements();

			Map<String, Object> response = new HashMap<>();
			response.put("totalCount", totalCount);
			response.put("users", userPage.getContent());
			response.put("currentPage", userPage.getNumber());
			response.put("totalPages", userPage.getTotalPages());

			return response;
		}
	}

	@PostMapping("/admin/userEdit")
	public Response userEdit(@RequestParam String username) {
		return memS.userEdit(username);
	}

	@PutMapping("/admin/updateUser")
	public String updateUser(@RequestParam String username, @RequestParam Role role, @RequestParam boolean enabled,
			@RequestParam boolean punished) {
		int result = memS.updateUser(username, role, enabled, punished);
		return result > 0 ? "success" : "error";
	}
}
