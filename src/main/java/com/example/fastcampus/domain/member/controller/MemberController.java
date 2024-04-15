package com.example.fastcampus.domain.member.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastcampus.domain.member.dto.MemberDto;
import com.example.fastcampus.domain.member.dto.MemberNicknameHistoryDto;
import com.example.fastcampus.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampus.domain.member.entity.MemberNicknameHistory;
import com.example.fastcampus.domain.member.service.MemberReadService;
import com.example.fastcampus.domain.member.service.MemberWriteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberWriteService memberWriteService;
	private final MemberReadService memberReadService;

	@PostMapping("/members")
	public MemberDto register(@RequestBody RegisterMemberCommand command) {
		return memberWriteService.register(command);
	}

	@GetMapping("/members/{id}")
	public MemberDto getMember(@PathVariable Long id) {
		return memberReadService.getMember(id);
	}

	@PostMapping("/members/{id}/nickname")
	public MemberDto changeNickname(@PathVariable Long id, @RequestBody String nickname) {
		return memberWriteService.changeNickname(id, nickname);
	}

	@GetMapping("/members/{memberId}/nickname-histories")
	public List<MemberNicknameHistoryDto> getHistories(@PathVariable Long memberId) {
		return memberReadService.getNicknameHistories(memberId);
	}

}
