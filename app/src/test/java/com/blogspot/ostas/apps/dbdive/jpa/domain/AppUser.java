package com.blogspot.ostas.apps.dbdive.jpa.domain;

import com.blogspot.ostas.apps.dbdive.jpa.domain.orders.ExchangeOrder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class AppUser extends Identifiable {

	private String login;

	private String password;

	@OneToOne
	private Wallet wallet = new Wallet();

	@OneToMany
	private List<ExchangeOrder> exchangeOrders;

	public Wallet getWallet() {
		return wallet;
	}

	public void setWallet(Wallet wallet) {
		this.wallet = wallet;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public List<ExchangeOrder> getExchangeOrders() {
		return exchangeOrders;
	}

	public void setExchangeOrders(List<ExchangeOrder> exchangeOrders) {
		this.exchangeOrders = exchangeOrders;
	}

}
