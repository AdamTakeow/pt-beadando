package hu.bertalanadam.prt.beadando.db.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-db.xml")
@Transactional
@Rollback(false)
public class TestConnection {

	@Autowired
	FelhasznaloTarolo fhtarolo;

	@Test
	public void testInsert() {
		Felhasznalo random = new Felhasznalo();

		random.setId(1L);

		fhtarolo.save(random);
	}

}
