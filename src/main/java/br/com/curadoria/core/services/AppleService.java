package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.AppleReceiptRequest;
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

	public Long validaReciboApple(String appleUserId, String receipt) throws JsonProcessingException {
		AppleReceiptRequest request = new AppleReceiptRequest();
		request.setReceiptData(receipt);
		request.setPassword(sharedSecret);
		request.setExcludeOldTransactions(true);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		String requestJson = mapper.writeValueAsString(request);

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
			return 0L;
		}

		// 🔹 4 - Verifica expiração da assinatura
		JsonNode latestReceipt = json.get("latest_receipt_info");

		if (latestReceipt != null && latestReceipt.isArray() && latestReceipt.size() > 0) {

			JsonNode lastTransaction = latestReceipt.get(latestReceipt.size() - 1);
			long expiresDateMs = lastTransaction.get("expires_date_ms").asLong();

			return expiresDateMs;
		}

		return 0L;
	}
}
