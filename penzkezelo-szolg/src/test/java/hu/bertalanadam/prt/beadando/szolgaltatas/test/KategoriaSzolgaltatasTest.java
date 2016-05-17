package hu.bertalanadam.prt.beadando.szolgaltatas.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Kategoria;
import hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo;
import hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper;
import hu.bertalanadam.prt.beadando.mapper.KategoriaMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.KategoriaSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KategoriaSzolgaltatasTest {
	
	@Mock
	private KategoriaTarolo kategoriaTarolo;
	
	@Spy
	@InjectMocks
	private KategoriaSzolgaltatasImpl kategoriaSzolgaltatas;
	
	private static Felhasznalo AFelhasznalo;
	private static Felhasznalo BFelhasznalo;
	private static Kategoria AKategoria;
	private static Kategoria BKategoria;
	private static Kategoria CKategoria;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// létrehozom az "adatbázist"

		// van az adatbázisban egy felhasználó
		Felhasznalo ujFelhasznalo = new Felhasznalo();
		ujFelhasznalo.setId(1L);
		ujFelhasznalo.setFelhasznalonev("TesztFelhasznalo");
		ujFelhasznalo.setKategoriak(new ArrayList<>());
		
		AFelhasznalo = ujFelhasznalo;
		
		// van még egy másik felhasználó is
		Felhasznalo ujFelhasznalo2 = new Felhasznalo();
		ujFelhasznalo2.setId(2L);
		ujFelhasznalo2.setFelhasznalonev("Random Felhasznalo");
		ujFelhasznalo2.setKategoriak(new ArrayList<>());
		
		BFelhasznalo = ujFelhasznalo2;
		
		// 3 minta kategória
		Kategoria kateg1 = new Kategoria();
		kateg1.setNev("vanIlyen1");
		kateg1.setId(3L);
		kateg1.setFelhasznalok(new ArrayList<>());
		kateg1.getFelhasznalok().add(AFelhasznalo);
		
		AKategoria = kateg1;
		
		Kategoria kateg2 = new Kategoria();
		kateg2.setNev("kateg2");
		kateg2.setId(4L);
		kateg2.setFelhasznalok(new ArrayList<>());
		kateg2.getFelhasznalok().add(BFelhasznalo);
		
		BKategoria = kateg2;
		
		Kategoria kateg3 = new Kategoria();
		kateg3.setNev("kateg3");
		kateg3.setId(5L);
		kateg3.setFelhasznalok(new ArrayList<>());
		kateg3.getFelhasznalok().add(AFelhasznalo);
		
		CKategoria = kateg3;
		
		AFelhasznalo.getKategoriak().add(kateg1);
		AFelhasznalo.getKategoriak().add(kateg3);
		
		BFelhasznalo.getKategoriak().add(kateg2);
	}
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		
		Mockito.when( kategoriaTarolo.save(Mockito.any(Kategoria.class)))
		.thenAnswer( new Answer<Kategoria>() {
			@Override
			public Kategoria answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Kategoria)args[0]).getId() == null ){
					((Kategoria)args[0]).setId(91L);					
				}
				return (Kategoria)args[0];
			}
		});
		
		Mockito.when( kategoriaTarolo.findByNev(Mockito.any(String.class)))
		.thenAnswer(new Answer<Kategoria>() {
			@Override
			public Kategoria answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				switch( (String)args[0] ){
					case "vanIlyen1" : return AKategoria;
					case "kateg2" : return BKategoria;
					case "kateg3" : return CKategoria;
					default : return null;
				}
			}
		});
		
		Mockito.when( kategoriaTarolo.findAll() )
		.thenAnswer(new Answer<List<Kategoria>>() {
			@Override
			public List<Kategoria> answer(InvocationOnMock invocation) throws Throwable {
				return Arrays.asList(AKategoria, BKategoria, CKategoria);
			}
		});
		
		Mockito.when( kategoriaTarolo.findByFelhasznaloIn( Mockito.any(Felhasznalo.class)))
		.thenAnswer(new Answer<List<Kategoria>>() {
			@Override
			public List<Kategoria> answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Felhasznalo)args[0]).getId().equals(AFelhasznalo.getId()) ) {
					return Arrays.asList(AKategoria, CKategoria);					
				} else if ( ((Felhasznalo)args[0]).getId().equals(BFelhasznalo.getId()) ){
					return Arrays.asList(BKategoria);
				} else {
					return null;
				}
			}
		});
	}
	
	@Test
	public void _1letrehozKategoriatTeszt(){
		KategoriaVo ujKategoria = new KategoriaVo();
		ujKategoria.setNev("Uj kategoria");
		ujKategoria.setFelhasznalok(new ArrayList<>());
		
		KategoriaVo mentett = kategoriaSzolgaltatas.letrehozKategoriat(ujKategoria);
		
		Assert.assertNotNull(mentett);
		Assert.assertNotNull(mentett.getId());
	}
	
	@Test
	public void _2frissitKategoriatTeszt(){
		KategoriaVo ujKategoria = new KategoriaVo();
		ujKategoria.setNev("Uj kategoria");
		ujKategoria.setFelhasznalok(new ArrayList<>());
		
		KategoriaVo mentett = kategoriaSzolgaltatas.letrehozKategoriat(ujKategoria);
		
		mentett.setNev("ModositottKategoriaNev");
		
		KategoriaVo frissitett = kategoriaSzolgaltatas.frissitKategoriat(mentett);
		
		Assert.assertEquals("ModositottKategoriaNev", frissitett.getNev());
	}
	
	@Test
	public void _3keresKategoriatTeszt(){
		
		KategoriaVo nemleszilyen = kategoriaSzolgaltatas.keresKategoriat("valamilyenKategoria");
		Assert.assertNull(nemleszilyen);
		
		KategoriaVo leszIlyen = kategoriaSzolgaltatas.keresKategoriat("kateg2");
		Assert.assertNotNull(leszIlyen);
	}
	
	@Test
	public void _4osszesKategoriaTeszt(){
		
		List<KategoriaVo> osszesKategoria = kategoriaSzolgaltatas.osszesKategoria();
		
		Assert.assertFalse( osszesKategoria.isEmpty() );
		Assert.assertEquals(3, osszesKategoria.size());
	}
	
	@Test
	public void _5felhasznaloOsszesKategoriajaTeszt(){
		
		List<KategoriaVo> fhOsszesKategoria = 
				kategoriaSzolgaltatas.felhasznaloOsszesKategoriaja(FelhasznaloMapper.toVo(AFelhasznalo));
		
		Assert.assertFalse( fhOsszesKategoria.isEmpty() );
		Assert.assertEquals(2, fhOsszesKategoria.size());
		
		fhOsszesKategoria = 
				kategoriaSzolgaltatas.felhasznaloOsszesKategoriaja(FelhasznaloMapper.toVo(BFelhasznalo));
		
		Assert.assertFalse( fhOsszesKategoria.isEmpty() );
		Assert.assertEquals(1, fhOsszesKategoria.size());
	}
	
	@Test
	public void _6vanIlyenKategoriajaAFelhasznalonakTeszt(){
		
		Assert.assertTrue( kategoriaSzolgaltatas.vanIlyenKategoriajaAFelhasznalonak(
				FelhasznaloMapper.toVo(AFelhasznalo), KategoriaMapper.toVo(AKategoria)));
		
		Assert.assertFalse( kategoriaSzolgaltatas.vanIlyenKategoriajaAFelhasznalonak(
				FelhasznaloMapper.toVo(BFelhasznalo), KategoriaMapper.toVo(CKategoria)));
	}
}
