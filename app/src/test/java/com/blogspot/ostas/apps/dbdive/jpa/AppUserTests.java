package com.blogspot.ostas.apps.dbdive.jpa;

import com.blogspot.ostas.apps.dbdive.OracleXeContainertTests;
import com.blogspot.ostas.apps.dbdive.jpa.domain.AppUser;
import com.blogspot.ostas.apps.dbdive.jpa.domain.currency.Currency;
import com.blogspot.ostas.apps.dbdive.jpa.domain.currency.CurrencyPair;
import com.blogspot.ostas.apps.dbdive.jpa.domain.orders.LimitOrder;
import com.blogspot.ostas.apps.dbdive.jpa.domain.enums.OrderSide;
import com.blogspot.ostas.apps.dbdive.jpa.domain.orders.MarketOrder;
import com.blogspot.ostas.apps.dbdive.jpa.repository.AppUserRepository;
import com.blogspot.ostas.apps.dbdive.jpa.repository.CurrencyPairRepository;
import com.blogspot.ostas.apps.dbdive.jpa.repository.CurrencyRepository;
import com.blogspot.ostas.apps.dbdive.jpa.repository.OrderRepository;
import lombok.SneakyThrows;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.ext.oracle.Oracle10DataTypeFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=update")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AppUserTests implements OracleXeContainertTests {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private CurrencyRepository currencyRepository;

	@Autowired
	private CurrencyPairRepository currencyPairRepository;

	@Autowired
	private AppUserRepository appUserRepository;

	@Test
	public void ordersTest() {
		var btc = new Currency();
		btc.setTicker("BTC");
		btc.setFiat(false);
		btc.setDescription("Bitcoin");
		currencyRepository.save(btc);

		var usd = new Currency();
		usd.setTicker("USD");
		usd.setFiat(true);
		usd.setDescription("US Dollar");
		currencyRepository.save(usd);

		var pair = new CurrencyPair(btc, usd);
		currencyPairRepository.save(pair);

		var limitOrder = new LimitOrder();
		limitOrder.setPair(pair);
		limitOrder.setType(OrderSide.BID);
		limitOrder.setPrice(BigDecimal.valueOf(45000));
		limitOrder.setAmount(BigDecimal.valueOf(1000));
		limitOrder.setAmountCompleted(BigDecimal.valueOf(500));
		limitOrder.setCompleted(false);
		orderRepository.save(limitOrder);

		var marketOrder = new MarketOrder();
		marketOrder.setPair(pair);
		marketOrder.setType(OrderSide.ASK);
		marketOrder.setMoneyAmount(BigDecimal.valueOf(100_500));
		orderRepository.save(marketOrder);

		var trader = new AppUser();
		trader.setLogin("trader");
		trader.setPassword("dummy");
		trader.setExchangeOrders(List.of(limitOrder, marketOrder));

		appUserRepository.save(trader);

		var fromDb = appUserRepository.findById(trader.getId()).get();
		assertThat(fromDb).isEqualTo(trader);
	}

	@AfterAll
	@SneakyThrows
	public void afterAll() {
		var dbConnection = dataSource.getConnection();
		var connection = new DatabaseConnection(dbConnection, dbConnection.getSchema());// for
																						// oracle
		var beforeDataSet = connection.createDataSet();
		connection.getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new Oracle10DataTypeFactory());
		FlatXmlDataSet.write(beforeDataSet, new FileOutputStream("schema-ums.xml"));
	}

}
