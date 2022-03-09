package com.blogspot.ostas.apps.dbdive.jpa.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Data
@Table(name = "APP_AUTH", uniqueConstraints = @UniqueConstraint(name = "UK_auth_uname", columnNames = { "uname" }))
public class AppAuth extends Identifiable {

	@Column(name = "uname", nullable = false)
	private String userName;

	@Column(name = "pwd", nullable = false)
	private String password;

}
