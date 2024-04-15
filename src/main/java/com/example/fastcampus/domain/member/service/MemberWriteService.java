package com.example.fastcampus.domain.member.service;

import org.springframework.stereotype.Service;

import com.example.fastcampus.domain.member.dto.MemberDto;
import com.example.fastcampus.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampus.domain.member.entity.Member;
import com.example.fastcampus.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampus.domain.member.repository.MemberNicknameHistoryRepository;
import com.example.fastcampus.domain.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberWriteService {

	private final MemberRepository memberRepository;
	private final MemberNicknameHistoryRepository memberNicknameHistoryRepository;

	/*
	 * 회원정보(이메일, 닉네임, 생년월일) 등록
	 * 닉네임은 최대 10자
	 *
	 * @parameter	memberRegisterCommand
	 * */
	public MemberDto register(RegisterMemberCommand command) {
		var member = Member.builder()
			.email(command.email())
			.nickname(command.nickname())
			.birth(command.birth())
			.build();

		member = memberRepository.save(member);
		return MemberDto.toDto(member);
	}

	public MemberDto changeNickname(Long memberId, String nickname) {
		var member = memberRepository.findById(memberId).orElseThrow();
		member.changeNickname(nickname);
		member = memberRepository.save(member);

		var history = MemberNicknameHistory.builder()
			.memberId(memberId)
			.nickname(member.getNickname())
			.build();
		memberNicknameHistoryRepository.save(history);

		return MemberDto.toDto(member);
	}
}
