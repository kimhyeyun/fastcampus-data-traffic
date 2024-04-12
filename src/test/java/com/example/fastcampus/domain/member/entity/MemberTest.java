package com.example.fastcampus.domain.member.entity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.IntStream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.example.fastcampus.util.MemberFixtureFactory;

class MemberTest {

	@Test
	@DisplayName("닉네임 변경 - 성공")
	void testChangeNickname_success() {
		var member = MemberFixtureFactory.create(1L);
		var changeNickname = "change";

		member.changeNickname(changeNickname);

		assertEquals(member.getNickname(), changeNickname);
	}

	@Test
	@DisplayName("닉네임 변경 - 실패, 길이 초과")
	void testChangeNickname_fail() {
		var member = MemberFixtureFactory.create(1L);
		var changeNickname = "changeNickname";

		assertThatThrownBy(() -> member.changeNickname(changeNickname))
			.isInstanceOf(IllegalArgumentException.class)
			.hasMessage("최대 길이를 초과했습니다.");
	}
}