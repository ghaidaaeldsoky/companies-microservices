package com.companies.serivce_request;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

@SpringBootTest
class SerivceRequestApplicationTests {

	@MockitoBean
    JwtDecoder jwtDecoder;
	
	@Test
	void contextLoads() {
	}

}
