package com.blogspot.ostas.apps.dbdive.jpa.domain;

import com.blogspot.ostas.apps.dbdive.jpa.domain.orders.ExchangeOrder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.List;

@Entity
public class AppUser extends Identifiable {

	@OneToOne
	private AppAuth appAuth = new AppAuth();

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

	public List<ExchangeOrder> getExchangeOrders() {
		return exchangeOrders;
	}

	public void setExchangeOrders(List<ExchangeOrder> exchangeOrders) {
		this.exchangeOrders = exchangeOrders;
	}

	public AppAuth getAppAuth() {
		return appAuth;
	}

	public void setAppAuth(AppAuth appAuth) {
		this.appAuth = appAuth;
	}
}
