package com.example.fastcampus.domain.member.dto;

import java.time.LocalDate;

import com.example.fastcampus.domain.member.entity.Member;

public record MemberDto(
	Long id,
	String email,
	String nickname,
	LocalDate birth
) {

	public static MemberDto toDto(Member member) {
		return new MemberDto(member.getId(), member.getEmail(), member.getNickname(), member.getBirth());
	}
}
