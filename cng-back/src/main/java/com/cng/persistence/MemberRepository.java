package com.cng.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.cng.domain.Member;

import jakarta.transaction.Transactional;

public interface MemberRepository extends JpaRepository<Member, String> {

	@Modifying
    @Transactional
    @Query("UPDATE Member m SET m.password = :password WHERE m.username = :username")
    int updatePassword(@Param("username") String username, @Param("password") String password);
}
