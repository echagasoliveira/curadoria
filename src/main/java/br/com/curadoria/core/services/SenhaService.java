package br.com.curadoria.core.services;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SenhaService {

	public String encriptarSenha(String senha) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		return passwordEncoder.encode(senha);
	}

	public boolean validarSenha(String senha, String senhaEncriptada) {
		return BCrypt.checkpw(senha, senhaEncriptada);
	}
}