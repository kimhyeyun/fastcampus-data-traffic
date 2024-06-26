package com.example.fastcampus.domain.follow.repository;

import java.sql.ResultSet;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.fastcampus.domain.follow.entity.Follow;
import com.example.fastcampus.domain.member.entity.MemberNicknameHistory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class FollowRepository {
	private static final String CATALOG_NAME = "fast_sns";
	private static final String TABLE_NAME = "Follow";


	private final NamedParameterJdbcTemplate jdbcTemplate;

	private static final RowMapper<Follow> rowMapper = (ResultSet resultSet, int rowNum) -> Follow
		.builder()
		.id(resultSet.getLong("id"))
		.fromMemberId(resultSet.getLong("fromMemberId"))
		.toMemberId(resultSet.getLong("toMemberId"))
		.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
		.build();

	public Follow save(Follow follow) {
		if (follow.getId() == null) {
			return insert(follow);
		}
		throw new UnsupportedOperationException("Follow는 갱신을 지원하지 않습니다.");
	}

	private Follow insert(Follow follow) {
		SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
			.withCatalogName(CATALOG_NAME)
			.withTableName(TABLE_NAME)
			.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(follow);
		var id = jdbcInsert.executeAndReturnKey(params).longValue();

		return Follow.builder()
			.id(id)
			.fromMemberId(follow.getFromMemberId())
			.toMemberId(follow.getToMemberId())
			.createdAt(follow.getCreatedAt())
			.build();
	}

	public List<Follow> findAllByFromMemberId(Long fromMemberId) {
		var sql = String.format("SELECT * FROM %s WHERE fromMemberId = :fromMemberId", TABLE_NAME);
		var params = new MapSqlParameterSource().addValue("fromMemberId", fromMemberId);

		return jdbcTemplate.query(sql, params, rowMapper);
	}

}
