package br.com.curadoria.core.services;

import br.com.curadoria.adapter.http.dto.AppleReceiptRequest;
import br.com.generic.security.filter.TokenValidationFilter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.security.PublicKey;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
@NoArgsConstructor
public class AppleService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AppleService.class);

	@Value("${url.plano_assinatura_apple}")
	private String url;

	@Value("${apple.shared_secret}")
	private String sharedSecret;

	private final RestTemplate restTemplate = new RestTemplate();
	private final ObjectMapper mapper = new ObjectMapper();

	public Long validaReciboAppleantigo(String appleUserId, String receipt) throws JsonProcessingException {
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
		String teste = new String(Base64.getDecoder().decode(receipt));
		// 🔹 3 - Se status diferente de 0, inválido
		if (json.get("status").asInt() != 0) {
			LOGGER.error("receipt: "+ receipt + " status: "+json.get("status").asInt());
			return 0L;
		}

		// 🔹 4 - Verifica expiração da assinatura
		JsonNode latestReceipt = json.get("latest_receipt_info");

		if (latestReceipt != null && latestReceipt.isArray() && latestReceipt.size() > 0) {

			JsonNode lastTransaction = latestReceipt.get(latestReceipt.size() - 1);
			long expiresDateMs = lastTransaction.get("expires_date_ms").asLong();

			return expiresDateMs;
		}
		LOGGER.error("receipt: "+ receipt);
		return 0L;
	}

	public Long validaReciboApple(String appleUserId, String jwsReceipt) {

		try {
			long x = jwsReceipt.chars().filter(c -> c == '.').count();
			JWSObject jwsObject = JWSObject.parse(jwsReceipt);

			// header
			List<String> certChain = (List<String>) jwsObject.getHeader().toJSONObject().get("x5c");
			String certBase64 = certChain.get(0);
			byte[] certBytes = Base64.getDecoder().decode(certBase64);

			CertificateFactory factory = CertificateFactory.getInstance("X.509");
			X509Certificate certificate = (X509Certificate) factory.generateCertificate(
					new java.io.ByteArrayInputStream(certBytes)
			);
			PublicKey publicKey = certificate.getPublicKey();

			// verifica assinatura
			ECDSAVerifier verifier = new ECDSAVerifier((java.security.interfaces.ECPublicKey) publicKey);

			if (!jwsObject.verify(verifier)) {
				LOGGER.error("Assinatura JWS inválida");
				return 0L;
			}

			// payload
			String payload = jwsObject.getPayload().toString();
			JsonNode json = mapper.readTree(payload);
			LOGGER.info("Apple payload: {}", json);

			if (json.has("expiresDate"))
				return json.get("expiresDate").asLong();

			if (json.has("expiresDateMs"))
				return json.get("expiresDateMs").asLong();

			LOGGER.error("jwsReceipt: "+ jwsReceipt + " expiresDateMs: "+json.get("expiresDateMs").asInt());
			return 0L;

		} catch (Exception e) {
			LOGGER.error("Erro validando JWS Apple jwsReceipt: "+ jwsReceipt, e);
			return 0L;
		}
	}
}
