package com.example.fastcampus.domain.member.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

import org.springframework.util.Assert;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Member {
	private final Long id;
	private final String email;
	private String nickname;
	private final LocalDate birth;
	private final LocalDateTime createdAt;

	private static final Long NAME_MAX_LEN = 10L;

	@Builder
	public Member(Long id, String email, String nickname, LocalDate birth, LocalDateTime createdAt) {
		this.id = id;
		this.email = Objects.requireNonNull(email);

		validateNickname(nickname);
		this.nickname = Objects.requireNonNull(nickname);

		this.birth = Objects.requireNonNull(birth);
		this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
	}

	private void validateNickname(String nickname) {
		Assert.isTrue(nickname.length() <= NAME_MAX_LEN, "최대 길이를 초과했습니다.");
	}

	public void changeNickname(String nickname) {
		Objects.requireNonNull(nickname);
		validateNickname(nickname);

		this.nickname = nickname;
	}
}
