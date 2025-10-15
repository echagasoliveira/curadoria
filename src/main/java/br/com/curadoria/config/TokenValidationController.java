package br.com.curadoria.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncodingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class TokenValidationController {

    @Autowired
    private JwtDecoder jwtDecoder;

    @PostMapping("/validate")
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authorizationHeader) {
        // Extrair o token do cabeçalho de autorização
        String token = extractTokenFromHeader(authorizationHeader);

        // Realizar a validação do token
        if (isValidToken(token)) {
            System.out.println("Token is valid");
            return ResponseEntity.ok("Token is valid");
        } else {
            System.out.println("Invalid token");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid token");
        }
    }

    // Implemente os métodos extractTokenFromHeader e isValidToken conforme
    // necessário

    private String extractTokenFromHeader(String authorizationHeader) {
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private boolean isValidToken(String token) {
        try {
            // validação da assinatura e da expiração do token de maneira automática.
            jwtDecoder.decode(token); // Isso irá validar o token automaticamente

            // Realize validações adicionais conforme necessário
            // Exemplo: Validar a assinatura do token, data de expiração, etc.

            // Se necessário, você também pode consultar o Spring Security para validar o
            // token
            // Exemplo:
            // jwtAuthenticationProvider.authenticate(authenticationToken);
            return true; // Token válido

        } catch (JwtEncodingException e) {
            // Token expirado
            return false;
        } catch (Exception e) {
            return false; // Token inválido
        }
    }
}
