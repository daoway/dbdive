package com.blogspot.ostas.apps.dbdive.jpa.domain.currency;

import com.blogspot.ostas.apps.dbdive.jpa.domain.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyPair extends Identifiable {

	@ManyToOne
	private Currency baseCurrency;

	@ManyToOne
	private Currency quoteCurrency;

	@Override
	public String toString() {
		return baseCurrency + "/" + quoteCurrency;
	}

}
