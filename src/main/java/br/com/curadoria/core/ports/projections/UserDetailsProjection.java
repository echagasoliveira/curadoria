package br.com.curadoria.core.ports.projections;

import java.util.Date;

public interface UserDetailsProjection {

	String getUsername();
	Date getDataExpiracaoAssinatura();
	String getPassword();
	Long getRoleId();
	String getAuthority();
}
