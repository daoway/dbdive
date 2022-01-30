package com.blogspot.ostas.apps.dbdive.jpa.domain.currency;

import com.blogspot.ostas.apps.dbdive.jpa.domain.Identifiable;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
@RequiredArgsConstructor
public class CurrencyPair extends Identifiable {

	@ManyToOne
	private final Currency baseCurrency;

	@ManyToOne
	private final Currency quoteCurrency;

	@Override
	public String toString() {
		return baseCurrency + "/" + quoteCurrency;
	}

}
