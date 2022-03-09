package com.blogspot.ostas.apps.dbdive.jpa;

import com.blogspot.ostas.apps.dbdive.OracleXeContainertTests;
import com.blogspot.ostas.apps.dbdive.jpa.domain.AppAuth;
import com.blogspot.ostas.apps.dbdive.jpa.domain.AppUser;
import com.blogspot.ostas.apps.dbdive.jpa.domain.Wallet;
import com.blogspot.ostas.apps.dbdive.jpa.domain.currency.Currency;
import com.blogspot.ostas.apps.dbdive.jpa.domain.currency.CurrencyPair;
import com.blogspot.ostas.apps.dbdive.jpa.domain.enums.OrderSide;
import com.blogspot.ostas.apps.dbdive.jpa.domain.orders.LimitOrder;
import com.blogspot.ostas.apps.dbdive.jpa.domain.orders.MarketOrder;
import com.blogspot.ostas.apps.dbdive.jpa.repository.AppAuthRepository;
import com.blogspot.ostas.apps.dbdive.jpa.repository.AppUserRepository;
import com.blogspot.ostas.apps.dbdive.jpa.repository.CurrencyPairRepository;
import com.blogspot.ostas.apps.dbdive.jpa.repository.CurrencyRepository;
import com.blogspot.ostas.apps.dbdive.jpa.repository.OrderRepository;
import com.blogspot.ostas.apps.dbdive.jpa.repository.WalletRepository;
import com.blogspot.ostas.apps.dbdive.jpa.service.AppUserService;
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

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@TestPropertySource(properties = "spring.jpa.hibernate.ddl-auto=update")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SuppressWarnings("PMD.JUnitTestContainsTooManyAsserts")
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

	@Autowired
	private WalletRepository walletRepository;

	@Autowired
	private AppAuthRepository appAuthRepository;

	@Autowired
	private AppUserService appUserService;

	@Test
	public void ordersTest() {
		var pair = createPair();

		var trader1 = motherOfUsers("trader0", pair);
		var fromDb1 = appUserRepository.findById(trader1.getId()).get();
		assertThat(fromDb1).isEqualTo(trader1);

		var trader2 = motherOfUsers("trader1", pair);
		var fromDb2 = appUserRepository.findById(trader2.getId()).get();
		assertThat(fromDb2).isEqualTo(trader2);
	}

	public CurrencyPair createPair() {
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
		return pair;
	}

	private AppUser motherOfUsers(String traderAccountName, CurrencyPair pair) {
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

		var appAuth = new AppAuth();
		appAuth.setUserName(traderAccountName);
		appAuth.setPassword("trader");

		appAuthRepository.save(appAuth);

		trader.setAppAuth(appAuth);

		trader.getExchangeOrders().add(limitOrder);
		trader.getExchangeOrders().add(marketOrder);

		var wallet = new Wallet();
		var btc = currencyRepository.findByTicker("BTC");
		var usd = currencyRepository.findByTicker("USD");

		wallet.getMoneyMap().put(btc, BigDecimal.ONE);
		wallet.getMoneyMap().put(usd, BigDecimal.valueOf(10_000));

		walletRepository.save(wallet);

		trader.setWallet(wallet);

		appUserService.saveUser(trader);
		return trader;
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
