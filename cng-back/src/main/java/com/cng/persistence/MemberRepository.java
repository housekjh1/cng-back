package com.cng.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cng.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {

}
