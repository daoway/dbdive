package com.blogspot.ostas.apps.dbdive.jpa.domain;

import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class AppUser extends Identifiable {

	private String login;

	private String password;

}
