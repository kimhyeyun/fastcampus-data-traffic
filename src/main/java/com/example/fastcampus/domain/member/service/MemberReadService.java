package com.example.fastcampus.domain.member.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.fastcampus.domain.member.dto.MemberDto;
import com.example.fastcampus.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampus.domain.member.entity.Member;
import com.example.fastcampus.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampus.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampus.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberReadService {

	private final MemberRepository memberRepository;
	private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

	public MemberDto getMember(Long id) {
		var member =  memberRepository.findById(id).orElseThrow();
		return MemberDto.toDto(member);
	}

	public List<MemberNicknameHistoryDto> getNicknameHistories(Long memberId) {
		return memberNicknameHistoryRepository.findAllByMemberId(memberId)
			.stream().map(MemberNicknameHistoryDto::toDto).toList();
	}

	public List<MemberDto> getMembers(List<Long> ids) {
		var members = memberRepository.findAllByIdIn(ids);
		return members.stream().map(MemberDto::toDto).toList();
	}
}
