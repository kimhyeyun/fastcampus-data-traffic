package com.example.fastcampus.application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastcampus.application.usecase.CreateFollowMemberUseCase;
import com.example.fastcampus.application.usecase.GetFollowingMemberUseCase;
import com.example.fastcampus.domain.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/follow")
public class FollowController {
	private final CreateFollowMemberUseCase createFollowMemberUseCase;
	private final GetFollowingMemberUseCase getFollowingMemberUseCase;

	@PostMapping("/{fromMemberId}/{toMemberId}")
	public List<MemberDto> create(@PathVariable Long fromMemberId, @PathVariable Long toMemberId) {
		createFollowMemberUseCase.execute(fromMemberId, toMemberId);
		return new ArrayList<>();
	}

	@GetMapping("/members/{memberId}")
	public List<MemberDto> getFollowingMembers(@PathVariable Long memberId) {
		return getFollowingMemberUseCase.execute(memberId);
	}
}
