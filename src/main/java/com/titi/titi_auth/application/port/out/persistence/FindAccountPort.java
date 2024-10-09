package com.titi.titi_auth.application.port.out.persistence;

import java.util.Optional;

import com.titi.titi_auth.domain.Account;

public interface FindAccountPort {

	Optional<Account> invoke(Account account);

}
