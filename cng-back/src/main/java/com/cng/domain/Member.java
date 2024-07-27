package com.cng.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Member {
	@Id
	private String username;
	private String realname;
	private String password;
	@Enumerated(EnumType.STRING)
	private Role role;
	@Builder.Default
	private boolean enabled = false;
	@Builder.Default
	private boolean punished = false;
	@Column(name = "create_date", nullable = false, updatable = false)
	@Builder.Default
	private ZonedDateTime createDate = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
}
