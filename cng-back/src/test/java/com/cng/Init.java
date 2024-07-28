package com.cng;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.cng.domain.Member;
import com.cng.persistence.MemberRepository;

@SpringBootTest
public class Init {

	@Autowired
	MemberRepository memRepo;

	PasswordEncoder encoder = new BCryptPasswordEncoder();

	@Test
	public void doInit() {
		for (int i = 0; i < 100; i++) {
			memRepo.save(Member.builder().username("member" + i).realname("member" + i).password(encoder.encode("1234"))
					.enabled(false).punished(true).build());
		}
	}
}
