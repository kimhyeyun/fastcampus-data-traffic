package com.example.fastcampus.domain.member.dto;

import java.time.LocalDateTime;

import com.example.fastcampus.domain.member.entity.MemberNicknameHistory;

public record MemberNicknameHistoryDto(
	Long id,
	Long memberId,
	String nickname,
	LocalDateTime createdAt
) {

	public static MemberNicknameHistoryDto toDto(MemberNicknameHistory memberNicknameHistory) {
		return new MemberNicknameHistoryDto(
			memberNicknameHistory.getId(),
			memberNicknameHistory.getMemberId(),
			memberNicknameHistory.getNickname(),
			memberNicknameHistory.getCreatedAt()
		);
	}

}
