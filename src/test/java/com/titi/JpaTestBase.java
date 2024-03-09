package com.titi;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.titi.infrastructure.persistence.jpa.config.JpaAuditingConfig;
import com.titi.infrastructure.persistence.jpa.config.QuerydslConfig;

@Import({JpaAuditingConfig.class, QuerydslConfig.class})
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public abstract class JpaTestBase {

}
