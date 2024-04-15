package com.example.fastcampus.domain.follow.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fastcampus.domain.follow.entity.Follow;
import com.example.fastcampus.domain.follow.repository.FollowRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class FollowReadService {

	private final FollowRepository followRepository;

	public List<Follow> getFollowings(Long memberId) {
		return followRepository.findAllByFromMemberId(memberId);
	}
}
