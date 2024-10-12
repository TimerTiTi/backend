package com.titi.titi_auth.application.port.out.persistence;

import com.titi.titi_auth.domain.Account;

public interface SaveAccountPort {

	Account invoke(Account account);

}
