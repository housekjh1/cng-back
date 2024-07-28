package com.cng.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cng.domain.Member;
import com.cng.domain.Role;

import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, String> {

	@Modifying
	@Transactional
	@Query("UPDATE Member m SET m.password = :password WHERE m.username = :username")
	int updatePassword(@Param("username") String username, @Param("password") String password);

	Page<Member> findByUsernameContainingOrRealnameContaining(String username, String realname, Pageable pageable);

	@Query("SELECT COUNT(m) FROM Member m WHERE m.username LIKE %:value% OR m.realname LIKE %:value%")
	long countByUsernameContainingOrRealnameContaining(@Param("value") String value);

	Page<Member> findByEnabledFalse(Pageable pageable);

	Page<Member> findByPunishedTrue(Pageable pageable);

	@Modifying
	@Transactional
	@Query("UPDATE Member m SET m.role = :role, m.enabled = :enabled, m.punished = :punished WHERE m.username = :username")
	int updateUser(@Param("username") String username, @Param("role") Role role, @Param("enabled") boolean enabled,
			@Param("punished") boolean punished);
}
