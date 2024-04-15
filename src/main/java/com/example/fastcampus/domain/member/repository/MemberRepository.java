package com.example.fastcampus.domain.member.repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.webjars.NotFoundException;

import com.example.fastcampus.domain.member.entity.Member;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private static final String CATALOG_NAME = "fast_sns";
	private static final String TABLE_NAME = "Member";


	private final NamedParameterJdbcTemplate jdbcTemplate;

	private static final RowMapper<Member> rowMapper = (ResultSet resultSet, int rowNum) -> Member
		.builder()
		.id(resultSet.getLong("id"))
		.email(resultSet.getString("email"))
		.nickname(resultSet.getString("nickname"))
		.birth(resultSet.getObject("birth", LocalDate.class))
		.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
		.build();

	public Optional<Member> findById(Long id) {
		/*
		 * select *
		 * from Member
		 * where id := id
		 * */

		var sql = String.format("SELECT * FROM %s WHERE id = :id", TABLE_NAME);
		var params = new MapSqlParameterSource()
			.addValue("id", id);

		var member = jdbcTemplate.queryForObject(sql, params, rowMapper);

		return Optional.ofNullable(member);
	}

	public Member save(Member member) {
		if (member.getId() == null) {
			return insert(member);
		}
		return update(member);
	}

	public List<Member> findAllByIdIn(List<Long> ids) {
		if (ids.isEmpty())
			throw new NotFoundException("팔로우하고 있는 회원이 없습니다.");

		var sql = String.format("SELECT * FROM %s WHERE id in (:ids)", TABLE_NAME);
		var params = new MapSqlParameterSource().addValue("ids", ids);

		return jdbcTemplate.query(sql, params, rowMapper);
	}

	private Member update(Member member) {
		var sql = String.format("UPDATE %s SET nickname = :nickname WHERE id = :id", TABLE_NAME);
		SqlParameterSource params = new BeanPropertySqlParameterSource(member);
		jdbcTemplate.update(sql, params);

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
