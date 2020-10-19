package com.app.edulearn.model;


import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "user_edu_learn")
public class AppUser {
    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false)
    private Long userId;

    @Column(name = "name", nullable = false)
    private String userName;

    @Column(name = "fullname")
    private String fullname;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "encrypt_password", nullable = false)
    private String encryptedPassword;

    @Column(name = "enabled", nullable = false)
    private boolean enabled;

	public AppUser(final String userName, final String encryptedPassword, final List<GrantedAuthority> grantList) {
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(final Long userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(final String fullname) {
		this.fullname = fullname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getEncryptedPassword() {
		return encryptedPassword;
	}

	public void setEncryptedPassword(final String encryptedPassword) {
		this.encryptedPassword = encryptedPassword;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(final boolean enabled) {
		this.enabled = enabled;
	}

	public AppUser(final Long userId, final String userName, final String fullname, final String email,
			final String encryptedPassword, final boolean enabled) {
		this.userId = userId;
		this.userName = userName;
		this.fullname = fullname;
		this.email = email;
		this.encryptedPassword = encryptedPassword;
		this.enabled = enabled;
	}

	public AppUser() {
	}
   


}
