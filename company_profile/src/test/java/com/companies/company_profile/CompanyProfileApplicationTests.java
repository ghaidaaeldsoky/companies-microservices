package com.companies.company_profile;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class CompanyProfileApplicationTests {

	@MockitoBean
    JwtDecoder jwtDecoder;

	@Test
	void contextLoads() {
	}

}
