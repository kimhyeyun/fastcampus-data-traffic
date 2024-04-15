package com.example.fastcampus.domain.member.repository;

import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.fastcampus.domain.member.entity.Member;
import com.example.fastcampus.domain.member.entity.MemberNicknameHistory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberNicknameHistoryRepository {


	private static final String CATALOG_NAME = "fast_sns";
	private static final String TABLE_NAME = "MemberNicknameHistory";


	private final NamedParameterJdbcTemplate jdbcTemplate;

	private static final RowMapper<MemberNicknameHistory> rowMapper = (ResultSet resultSet, int rowNum) -> MemberNicknameHistory
		.builder()
		.id(resultSet.getLong("id"))
		.memberId(resultSet.getLong("memberId"))
		.nickname(resultSet.getString("nickname"))
		.createdAt(resultSet.getObject("createdAt", LocalDateTime.class))
		.build();

	public List<MemberNicknameHistory> findAllByMemberId(Long memberId) {
		var sql = String.format("SELECT * FROM %s WHERE memberId = :memberId", TABLE_NAME);
		var param = new MapSqlParameterSource().addValue("memberId", memberId);

		return jdbcTemplate.query(sql, param, rowMapper);
	}

	public MemberNicknameHistory save(MemberNicknameHistory memberNicknameHistory) {
		if (memberNicknameHistory.getId() == null) {
			return insert(memberNicknameHistory);
		}
		throw new UnsupportedOperationException("MemberNicknameHistory는 갱신 지원하지 않습니다.");
	}

	private MemberNicknameHistory insert(MemberNicknameHistory memberNicknameHistory) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate.getJdbcTemplate())
			.withCatalogName(CATALOG_NAME)
			.withTableName(TABLE_NAME)
			.usingGeneratedKeyColumns("id");

		SqlParameterSource params = new BeanPropertySqlParameterSource(memberNicknameHistory);
		var id = simpleJdbcInsert.executeAndReturnKey(params).longValue();

		return MemberNicknameHistory.builder()
			.id(id)
			.memberId(memberNicknameHistory.getMemberId())
			.nickname(memberNicknameHistory.getNickname())
			.createdAt(memberNicknameHistory.getCreatedAt())
			.build();
	}

}
