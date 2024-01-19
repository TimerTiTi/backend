package com.titi.springdoc;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

@Configuration
public class SpringDocConfig {

	@Bean
	@Profile(value = {"local", "dev"})
	public OpenAPI openAPI() {
		return new OpenAPI()
			.info(this.getApiInfo())
			.components(this.getSecuritySchemeComponents())
			.security(this.getSecurityRequirements());
	}

	private List<SecurityRequirement> getSecurityRequirements() {
		return List.of(new SecurityRequirement().addList("bearerAuth"));
	}

	private Components getSecuritySchemeComponents() {
		final SecurityScheme securityScheme = new SecurityScheme()
			.type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
			.in(SecurityScheme.In.HEADER).name("Authorization");
		return new Components().addSecuritySchemes("bearerAuth", securityScheme);
	}

	private Info getApiInfo() {
		return new Info()
			.title("TiTi API Document")
			.version(APIVersions.VERSION_1_0_0.getVersion())
			.contact(this.getContact());
	}

	private Contact getContact() {
		return new Contact().name("Sean").email("ksp970306@gmail.com");
	}

}
