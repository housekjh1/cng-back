package com.cng.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

		Optional<Member> opt = memRepo.findById(username);

		if (!opt.isPresent()) {
			memRepo.save(Member.builder().username(username).realname(realname).password(encoder.encode(password))
					.role(Role.ROLE_MEMBER).build());
			return "ok";
		} else if (opt.get().getUsername().equals(username)) {
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
}
