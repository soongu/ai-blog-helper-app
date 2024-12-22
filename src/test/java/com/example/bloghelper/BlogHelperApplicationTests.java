package com.example.bloghelper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootTest
class BlogHelperApplicationTests {

	@Test
	void contextLoads() {

		SecureRandom secureRandom = new SecureRandom();
		byte[] key = new byte[64];  // 64바이트 = 512비트
		secureRandom.nextBytes(key);
		String secretKey = Base64.getEncoder().encodeToString(key);
		System.out.println(secretKey);
	}

}
