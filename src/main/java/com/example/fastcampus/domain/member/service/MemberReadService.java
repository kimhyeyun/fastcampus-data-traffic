package com.example.fastcampus.domain.member.service;

import org.springframework.stereotype.Service;

import com.example.fastcampus.domain.member.dto.MemberDto;
import com.example.fastcampus.domain.member.entity.Member;
import com.example.fastcampus.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberReadService {

	private final MemberRepository memberRepository;

	public MemberDto getMember(Long id) {
		var member =  memberRepository.findById(id).orElseThrow();
		return MemberDto.toDto(member);
	}

}
