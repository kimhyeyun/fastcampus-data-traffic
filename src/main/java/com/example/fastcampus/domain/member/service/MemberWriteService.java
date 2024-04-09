package com.example.fastcampus.domain.member.service;

import org.springframework.stereotype.Service;

import com.example.fastcampus.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampus.domain.member.entity.Member;
import com.example.fastcampus.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

	private final MemberRepository memberRepository;

	/*
	 * 회원정보(이메일, 닉네임, 생년월일) 등록
	 * 닉네임은 최대 10자
	 *
	 * @parameter	memberRegisterCommand
	 * */
	public Member create(RegisterMemberCommand command) {
		var member = Member.builder()
			.email(command.email())
			.nickname(command.nickname())
			.birth(command.birth())
			.build();

		return memberRepository.save(member);
	}
}
