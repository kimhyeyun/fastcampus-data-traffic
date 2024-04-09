package com.example.fastcampus.domain.member.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.fastcampus.domain.member.dto.RegisterMemberCommand;
import com.example.fastcampus.domain.member.entity.Member;
import com.example.fastcampus.domain.member.service.MemberWriteService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MemberController {

	private final MemberWriteService memberWriteService;

	@PostMapping("/members")
	public Member register(@RequestBody RegisterMemberCommand command) {
		return memberWriteService.create(command);
	}

}
