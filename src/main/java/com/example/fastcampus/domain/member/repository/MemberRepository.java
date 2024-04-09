package com.example.fastcampus.domain.member.repository;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.fastcampus.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final NamedParameterJdbcTemplate jdbcTemplate;

	public Member save(Member member) {
		if (member.getId() == null) {
			return insert(member);
		}
		return update(member);
	}

	private Member update(Member member) {
		return member;
	}

	private Member insert(Member member) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
			.withCatalogName("fast_sns")
			.withTableName("Member")
			.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

		return Member.builder()
			.id(id)
			.email(member.getEmail())
			.nickname(member.getNickname())
			.birth(member.getBirth())
			.build();
	}
}
