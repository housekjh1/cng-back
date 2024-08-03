package com.cng.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.cng.domain.Member;
import com.cng.domain.Response;
import com.cng.domain.Role;
import com.cng.persistence.MemberRepository;

@Service
public class MemberService {

	@Autowired
	private MemberRepository memRepo;

	private PasswordEncoder encoder = new BCryptPasswordEncoder();

	public String join(String username, String realname, String password) {
		System.out.println(username + ' ' + realname + ' ' + password);

		Optional<Member> opt = Optional.empty();

		try {
			opt = memRepo.findById(username);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (opt.isEmpty()) {
			memRepo.save(
					Member.builder().username(username).realname(realname).password(encoder.encode(password)).build());
			return "ok";
		} else if (opt.isPresent() && opt.get().getUsername().equalsIgnoreCase(username)) {
			return "usernameDuplication";
		} else {
			return "error";
		}
	}

	public Response getUser(String hasToken) {

		if (hasToken == null || !hasToken.startsWith("Bearer ")) {
			return Response.builder().key("error").value("tokenError").build();
		}
		String jwtToken = hasToken.replace("Bearer ", "");
		String username = null;
		Member tmp = null;
		try {
			username = JWT.require(Algorithm.HMAC256("com.cng.jwt")).build().verify(jwtToken).getClaim("username")
					.asString();
			tmp = memRepo.findById(username).get();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.builder().key("error").value("findError").build();
		}
		if (!tmp.isEnabled()) {
			return Response.builder().key("error").value("notEnabled").build();
		} else if (tmp.isPunished()) {
			return Response.builder().key("error").value("punished").build();
		}
		return Response.builder().key("success").value(tmp.getRealname()).data(tmp).build();
	}

	public Response changePW(String username, String password) {

		Integer result = null;
		try {
			result = memRepo.updatePassword(username, encoder.encode(password));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.builder().key("error").value("updateError").build();
		}
		if (result > 0) {
			return Response.builder().key("success").value("updated").build();
		} else {
			return Response.builder().key("error").value("notUpdated").build();
		}
	}

	public Page<Member> getUserList(String value, int page) {
		PageRequest pageRequest = PageRequest.of(page, 100);
		return memRepo.findByUsernameContainingOrRealnameContaining(value, value, pageRequest);
	}

	public long getUserCount(String value) {
		return memRepo.countByUsernameContainingOrRealnameContaining(value);
	}

	public Response userEdit(String username) {
		Member tmp = null;
		try {
			tmp = memRepo.findById(username).get();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.builder().key("error").value("findError").build();
		}
		return Response.builder().key("success").value("success").data(tmp).build();
	}

	public Page<Member> getDisabledMembers(int page) {
		PageRequest pageRequest = PageRequest.of(page, 100);
		return memRepo.findByEnabledFalse(pageRequest);
	}

	public Page<Member> getPunishedMembers(int page) {
		PageRequest pageRequest = PageRequest.of(page, 100);
		return memRepo.findByPunishedTrue(pageRequest);
	}

	public int updateUser(String username, Role role, boolean enabled, boolean punished) {
		return memRepo.updateUser(username, role, enabled, punished);
	}
}
