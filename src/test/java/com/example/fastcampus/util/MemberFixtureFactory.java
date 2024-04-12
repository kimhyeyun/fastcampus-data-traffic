package com.example.fastcampus.util;

import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;

import com.example.fastcampus.domain.member.entity.Member;

public class MemberFixtureFactory {

	public static Member create(Long seed) {
		var param = new EasyRandomParameters();
		param.setSeed(seed);

		return new EasyRandom(param).nextObject(Member.class);
	}
}
