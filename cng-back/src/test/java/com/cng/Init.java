package com.cng;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cng.domain.Member;
import com.cng.domain.Role;
import com.cng.persistence.MemberRepository;

@SpringBootTest
public class Init {

	@Autowired
	MemberRepository memRepo;

	PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	public void doWork() {
		memRepo.save(Member.builder().username("member").password(encoder.encode("abcd")).role(Role.ROLE_MEMBER)
				.enabled(true).build());
		memRepo.save(Member.builder().username("manager").password(encoder.encode("abcd")).role(Role.ROLE_MANAGER)
				.enabled(true).build());
		memRepo.save(Member.builder().username("admin").password(encoder.encode("abcd")).role(Role.ROLE_ADMIN)
				.enabled(true).build());
	}
}
