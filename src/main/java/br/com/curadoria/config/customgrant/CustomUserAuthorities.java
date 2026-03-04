package br.com.curadoria.config.customgrant;

import java.util.Collection;
import java.util.Date;

import org.springframework.security.core.GrantedAuthority;

public class CustomUserAuthorities {

	private String username;
	private Collection<? extends GrantedAuthority> authorities;
	private Date subscriptionExpiresAt;

	public CustomUserAuthorities(String username, Collection<? extends GrantedAuthority> authorities, Date subscriptionExpiresAt) {
		this.username = username;
		this.authorities = authorities;
		this.subscriptionExpiresAt = subscriptionExpiresAt;
	}

	public String getUsername() {
		return username;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Date getSubscriptionExpiresAt() {
		return subscriptionExpiresAt;
	}
}
