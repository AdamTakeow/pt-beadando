package hu.bertalanadam.prt.beadando.szolgaltatas.test;

import static org.junit.Assert.assertEquals;

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
@ContextConfiguration("/spring-szolg.xml")
@Transactional
@Rollback(true)
public class SzolgaltatasTeszt {

	@Autowired
	FelhasznaloTarolo felhasznaloTarolo;

	@Test
	public void tesztBeszurasAFelhasznaloba() throws Exception{

		Felhasznalo admin = felhasznaloTarolo.findByFelhasznalonev("admin");
		
		assertEquals( admin.getFelhasznalonev(), "admin");

	}

}
