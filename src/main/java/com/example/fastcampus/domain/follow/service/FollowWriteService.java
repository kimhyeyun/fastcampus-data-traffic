package com.example.fastcampus.domain.follow.service;

import static java.util.Objects.*;

import java.util.Objects;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.example.fastcampus.domain.follow.entity.Follow;
import com.example.fastcampus.domain.follow.repository.FollowRepository;
import com.example.fastcampus.domain.member.dto.MemberDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowWriteService {

	private final FollowRepository followRepository;

	public void create(MemberDto fromMember, MemberDto toMember) {
		Assert.isTrue(!fromMember.id().equals(toMember.id()), "From, To 회원이 동일합니다.");

		var follow = Follow.builder()
			.fromMemberId(fromMember.id())
			.toMemberId(toMember.id())
			.build();

		followRepository.save(follow);
	}
}
