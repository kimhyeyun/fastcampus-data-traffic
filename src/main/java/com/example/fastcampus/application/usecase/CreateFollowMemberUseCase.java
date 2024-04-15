package com.example.fastcampus.application.usecase;

import org.springframework.stereotype.Service;

import com.example.fastcampus.domain.follow.service.FollowWriteService;
import com.example.fastcampus.domain.member.dto.MemberDto;
import com.example.fastcampus.domain.member.service.MemberReadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CreateFollowMemberUseCase {
	private final MemberReadService memberReadService;
	private final FollowWriteService followWriteService;

	public void execute(Long fromMemberId, Long toMemberId) {
		var fromMember = memberReadService.getMember(fromMemberId);
		var toMember = memberReadService.getMember(toMemberId);

		followWriteService.create(fromMember, toMember);
	}
}
