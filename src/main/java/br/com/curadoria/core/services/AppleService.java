package br.com.curadoria.core.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AppleService {

	@Value("${url.plano_assinatura_apple}")
	private String url;

	@Value("${apple.shared_secret}")
	private String sharedSecret;

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper mapper = new ObjectMapper();

	public Boolean validaReciboApple(Integer appleUserId, String receipt) throws JsonProcessingException {
		String requestJson = """
            {
                "receipt-data": "%s",
                "password": "%s",
                "exclude-old-transactions": true
            }
            """.formatted(receipt, sharedSecret);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> entity = new HttpEntity<>(requestJson, headers);

		// 🔹 1 - Chama url da apple
		ResponseEntity<String> response = restTemplate.exchange(
				url,
				HttpMethod.POST,
				entity,
				String.class
		);

		JsonNode json = mapper.readTree(response.getBody());

		// 🔹 3 - Se status diferente de 0, inválido
		if (json.get("status").asInt() != 0) {
			return false;
		}

		// 🔹 4 - Verifica expiração da assinatura
		JsonNode latestReceipt = json.get("latest_receipt_info");

		if (latestReceipt != null && latestReceipt.isArray() && latestReceipt.size() > 0) {

			JsonNode lastTransaction = latestReceipt.get(latestReceipt.size() - 1);
			long expiresDateMs = lastTransaction.get("expires_date_ms").asLong();

			long now = System.currentTimeMillis();

			return expiresDateMs > now;
		}

		return false;
	}
}
