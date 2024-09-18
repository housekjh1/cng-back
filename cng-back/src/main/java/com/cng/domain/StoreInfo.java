package com.cng.domain;

import java.time.ZoneId;
import java.time.ZonedDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class StoreInfo {
	@Id
	private Long storeNo;
	private String storeName;
	private String storeAddress;
	private Double storeLat;
	private Double storeLong;
	private String storeTel;
	private String storeOwner;
	@Builder.Default
	private Long storeView = 0L;
	@Builder.Default
	private Long storeStarRate = 0L;
	@Builder.Default
	private Long storeStarVote = 0L;
	@Builder.Default
	private boolean enabled = false;
	@Builder.Default
	private boolean punished = false;
	@Column(name = "create_date", nullable = false, updatable = false)
	@Builder.Default
	private ZonedDateTime createDate = ZonedDateTime.now(ZoneId.of("Asia/Seoul"));
}
