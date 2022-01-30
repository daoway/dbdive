package com.blogspot.ostas.apps.dbdive.jpa.domain.currency;

import com.blogspot.ostas.apps.dbdive.jpa.domain.Identifiable;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class Currency extends Identifiable {

	private String ticker;

	private boolean isFiat;

	private String description;

}
