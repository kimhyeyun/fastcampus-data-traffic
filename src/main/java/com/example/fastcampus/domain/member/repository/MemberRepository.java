package com.example.fastcampus.domain.member.repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.fastcampus.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private static final String CATALOG_NAME = "fast_sns";
	private static final String TABLE_NAME = "Member";


	private final NamedParameterJdbcTemplate jdbcTemplate;

	public Optional<Member> findById(Long id) {
		/*
		 * select *
		 * from Member
		 * where id := id
		 * */

		var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE_NAME);
		var params = new MapSqlParameterSource()
			.addValue("id", id);

		RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member
			.builder()
			.id(resultSet.getLong("id"))
			.email(resultSet.getString("email"))
			.nickname(resultSet.getString("nickname"))
			.birth(resultSet.getObject("birth", LocalDate.class))
			.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
			.build();

		var member = jdbcTemplate.queryForObject(sql, params, rowMapper);

		return Optional.ofNullable(member);
	}

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
			.withCatalogName(CATALOG_NAME)
			.withTableName(TABLE_NAME)
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
