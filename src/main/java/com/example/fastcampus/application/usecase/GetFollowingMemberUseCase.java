package com.example.fastcampus.application.usecase;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fastcampus.domain.follow.entity.Follow;
import com.example.fastcampus.domain.follow.service.FollowReadService;
import com.example.fastcampus.domain.member.dto.MemberDto;
import com.example.fastcampus.domain.member.service.MemberReadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GetFollowingMemberUseCase {
	private final MemberReadService memberReadService;
	private final FollowReadService followReadService;

	public List<MemberDto> execute(Long fromMemberId) {
		var followings = followReadService.getFollowings(fromMemberId);
		var followingMemberIds = followings.stream().map(Follow::getToMemberId).toList();

		return memberReadService.getMembers(followingMemberIds);
	}
}
