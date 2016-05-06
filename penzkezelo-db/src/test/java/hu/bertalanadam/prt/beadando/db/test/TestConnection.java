package hu.bertalanadam.prt.beadando.db.test;


import java.util.ArrayList;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring-db.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Transactional
@Rollback(true)
public class TestConnection {
	
//	private static Logger logolo = LoggerFactory.getLogger(TestConnection.class);

	@Autowired
	private FelhasznaloTarolo fhtarolo;

	@Ignore
	@Test
	public void teszt1FelhasznaloBeszuras() throws Exception {
		
		Felhasznalo felh = new Felhasznalo();
		felh.setFelhasznalonev("tesztFelhasznalo");
		felh.setEgyenleg(0L);
		felh.setJelszo("password");
//		felh.setLebontas(1);
		felh.setTranzakciok(new ArrayList<Tranzakcio>());

		fhtarolo.save(felh);
		
		Felhasznalo f = fhtarolo.findByFelhasznalonev("tesztFelhasznalo");
		if( f == null )
			Assert.fail();
		
		Assert.assertTrue( f.getJelszo() == "password" );
		
	}

}
