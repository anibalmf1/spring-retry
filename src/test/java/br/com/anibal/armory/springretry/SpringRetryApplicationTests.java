package br.com.anibal.armory.springretry;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringRetryApplicationTests {

	@LocalServerPort
	private String serverPort;

	@Test
	public void testRetryWithAnnotation() {
		RestTemplate restTemplate = new RestTemplate();

		String criminal = restTemplate.getForObject("http://localhost:" + serverPort + "/simpleRetry", String.class);

		assertNotNull(criminal);
	}

	@Test
	public void testRetryWithTemplate() {
		RestTemplate restTemplate = new RestTemplate();

		String captured = restTemplate.getForObject("http://localhost:" + serverPort + "/templateRetry/2", String.class);
		assertEquals("Captured", captured);

		captured = restTemplate.getForObject("http://localhost:" + serverPort + "/templateRetry/5", String.class);
		assertEquals("Captured", captured);

		String escaped = restTemplate.getForObject("http://localhost:" + serverPort + "/templateRetry/10", String.class);
		assertEquals("Escaped", escaped);
	}

}
