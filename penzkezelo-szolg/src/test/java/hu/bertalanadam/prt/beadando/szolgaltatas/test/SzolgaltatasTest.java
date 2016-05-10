package hu.bertalanadam.prt.beadando.szolgaltatas.test;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-szolg.xml")
@Transactional
@Rollback(true)
public class SzolgaltatasTest {

	@Autowired
	FelhasznaloTarolo felhasznaloTarolo;

//	@Ignore
	@Test
	public void tesztFelhasznaloBeszuras() throws Exception{
		
		Felhasznalo felh = new Felhasznalo();
		felh.setFelhasznalonev("tesztFelhasznalo");
		felh.setEgyenleg(0L);
		felh.setJelszo("jelszo");
		felh.setTranzakciok(new ArrayList<Tranzakcio>());

		felhasznaloTarolo.save(felh);
		
		Felhasznalo f = felhasznaloTarolo.findByFelhasznalonev("tesztFelhasznalo");
		if( f == null )
			Assert.fail();
		
		Assert.assertTrue( f.getJelszo() == "jelszo" );
		
	}

}
