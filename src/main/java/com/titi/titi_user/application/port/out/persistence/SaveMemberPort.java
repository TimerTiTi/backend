package com.titi.titi_user.application.port.out.persistence;

import com.titi.titi_user.domain.member.Member;

public interface SaveMemberPort {

	Member invoke(Member member);

}
