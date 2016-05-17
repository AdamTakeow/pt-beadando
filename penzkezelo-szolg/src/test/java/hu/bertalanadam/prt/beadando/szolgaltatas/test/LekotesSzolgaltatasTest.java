package hu.bertalanadam.prt.beadando.szolgaltatas.test;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
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
import hu.bertalanadam.prt.beadando.db.entitas.Lekotes;
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.LekotesTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo;
import hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper;
import hu.bertalanadam.prt.beadando.mapper.LekotesMapper;
import hu.bertalanadam.prt.beadando.mapper.TranzakcioMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.FelhasznaloSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.IsmetlodoSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.KategoriaSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.LekotesSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.TranzakcioSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.LekotesVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class LekotesSzolgaltatasTest {

	@Mock
	private LekotesTarolo lekotesTarolo;
	@Mock
	private TranzakcioTarolo tranzakcioTarolo;
	@Mock
	private KategoriaTarolo kategoriaTarolo;
	@Mock
	private FelhasznaloTarolo felhasznaloTarolo;

	@Spy
	@InjectMocks
	private LekotesSzolgaltatasImpl lekotesSzolgaltatas;
	
	@Spy
	@InjectMocks
	private KategoriaSzolgaltatasImpl kategoriaSzolgaltatas;
	
	@Spy
	@InjectMocks
	private IsmetlodoSzolgaltatasImpl ismetlodoSzolgaltatas;
	
	@Spy
	@InjectMocks
	private TranzakcioSzolgaltatasImpl tranzakcioSzolgaltatas;
	
	@Spy
	@InjectMocks
	private FelhasznaloSzolgaltatasImpl felhasznaloSzolgaltatas;
	
	private static Lekotes tesztLekotes;
	private static Felhasznalo tesztFelhasznalo;
	private static Kategoria tesztKategoria;
	private static List<Tranzakcio> tesztFelhasznaloTranzakcioi = new ArrayList<>();

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// létrehozzuk az "adatbázist"

		
		// Az adatbázisban egyetlen felhasználó van
		Felhasznalo felh = new Felhasznalo();
		felh.setId(1L);
		felh.setFelhasznalonev("AAAA");
		felh.setTranzakciok(new ArrayList<>());
		felh.setKategoriak(new ArrayList<>());
		felh.setKezdoIdopont(LocalDate.now().minusYears(1L));
		felh.setVegIdopont(LocalDate.now());
				
		tesztFelhasznalo = felh;
		
		// Az adatbázisban szerepel egy kategória
		Kategoria ujKateg = new Kategoria();
		ujKateg.setNev("Lekötés");
		ujKateg.setId(2L);
		ujKateg.setFelhasznalok(new ArrayList<>());
		ujKateg.getFelhasznalok().add(tesztFelhasznalo);
		
		tesztKategoria = ujKateg;
		
		// Készítünk egy teljesítetlen lekötést a felhasználónak
		Lekotes lek = new Lekotes();
		lek.setFutamido(1L);
		lek.setId(3L);
		lek.setKamat(10.0);
		lek.setOsszeg(10000L);
		lek.setTeljesitett(false);
		lek.setVarhato(12100L);
				
		tesztLekotes = lek;
		
		// A lekötéshez járó tranzakció
		Tranzakcio tranzakcio1 = new Tranzakcio();
		tranzakcio1.setId(4L);
		tranzakcio1.setOsszeg(10000L);
		tranzakcio1.setFelhasznalo(tesztFelhasznalo);
		tranzakcio1.setDatum(LocalDate.now().minusDays(5L));
		tranzakcio1.setKategoria(tesztKategoria);
		tranzakcio1.setLekotes(tesztLekotes);
		
		tesztFelhasznalo.getTranzakciok().add(tranzakcio1);
		
		tesztFelhasznaloTranzakcioi.add(tranzakcio1);
	}
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		
		Mockito.when( lekotesTarolo.save( Mockito.any(Lekotes.class)))
		.thenAnswer(new Answer<Lekotes>() {
			@Override
			public Lekotes answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Lekotes)args[0]).getId() == null ){
					((Lekotes)args[0]).setId(10L);
				}
				return (Lekotes)args[0];
			}
		});
		
		Mockito.when( tranzakcioTarolo.save( Mockito.any(Tranzakcio.class)))
		.thenAnswer(new Answer<Tranzakcio>() {
			@Override
			public Tranzakcio answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Tranzakcio)args[0]).getId() == null  ){
					((Tranzakcio)args[0]).setId(88L);
				}
				return (Tranzakcio)args[0];
			}
		});
		
		Mockito.when( felhasznaloTarolo.save( Mockito.any(Felhasznalo.class)))
		.thenAnswer(new Answer<Felhasznalo>() {
			@Override
			public Felhasznalo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Felhasznalo)args[0]).getId() == null  ){
					((Felhasznalo)args[0]).setId(88L);
				}
				return (Felhasznalo)args[0];
			}
		});
		
		Mockito.when( tranzakcioTarolo.findByFelhasznalo(Mockito.any(Felhasznalo.class)))
		.thenAnswer(new Answer<List<Tranzakcio>>() {
			@Override
			public List<Tranzakcio> answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Felhasznalo)args[0]).getId().equals(tesztFelhasznalo.getId()) ){
					return tesztFelhasznaloTranzakcioi;
				} else {
					return null;
				}
			}
		});
		
		Mockito.when( kategoriaTarolo.findByNev( "Lekötés" ) )
		.thenReturn(tesztKategoria);
	}
	
	@Test
	public void _1letrehozLekotestTeszt(){
		// létrehozunk egy lekötést azonosító nélkül, mert azt az adatbázis generálja
		LekotesVo ujLekotes = new LekotesVo();
		ujLekotes.setFutamido(2L);
		ujLekotes.setKamat(10.0);
		ujLekotes.setOsszeg(10000L);
		ujLekotes.setTeljesitett(false);
		
		LekotesVo mentett = lekotesSzolgaltatas.letrehozLekotest(ujLekotes);
		
		Assert.assertNotNull( mentett );
		Assert.assertNotNull( mentett.getId() );
		Assert.assertEquals( new Long(12100L), mentett.getVarhato());
	}
	
	@Test
	public void _2frissitLekotestTeszt(){
		LekotesVo ujLekotes = new LekotesVo();
		ujLekotes.setFutamido(2L);
		ujLekotes.setKamat(10.0);
		ujLekotes.setOsszeg(10000L);
		ujLekotes.setTeljesitett(false);
		
		LekotesVo mentett = lekotesSzolgaltatas.letrehozLekotest(ujLekotes);
		
		mentett.setFutamido(3L);
		mentett.setOsszeg(800L);
		
		LekotesVo frissitett = lekotesSzolgaltatas.frissitLekotest(mentett);
		
		Assert.assertEquals(new Long(3L), frissitett.getFutamido());
		Assert.assertEquals(new Long(800L), frissitett.getOsszeg());
	}
	
	@Test
	public void _3vanAktivLekoteseAFelhasznalonakTeszt(){
		// jelenleg egyetlen lekötése van amely aktív
		boolean vanelekotes = lekotesSzolgaltatas.vanAktivLekoteseAFelhasznalonak(
				FelhasznaloMapper.toVo(tesztFelhasznalo),
				TranzakcioMapper.toVo(tesztFelhasznalo.getTranzakciok())
		);
		
		Assert.assertTrue( vanelekotes );
		
		tesztLekotes.setTeljesitett(true);
		
		vanelekotes = lekotesSzolgaltatas.vanAktivLekoteseAFelhasznalonak(
				FelhasznaloMapper.toVo(tesztFelhasznalo),
				TranzakcioMapper.toVo(tesztFelhasznalo.getTranzakciok())
		);
		
		Assert.assertFalse( vanelekotes );
	}
	
	@Test
	public void _4lekotesEllenorzesTeszt(){
		// átmappelem az objektumokat
		FelhasznaloVo felh = FelhasznaloMapper.toVo(tesztFelhasznalo);
		List<TranzakcioVo> felh_trzi = TranzakcioMapper.toVo(tesztFelhasznaloTranzakcioi); 
		LekotesVo lek = felh_trzi.get(0).getLekotes();
		
		// nincs teljesítve a lekötés
		lek.setTeljesitett(false);
		// öt hónapja lett lekötve aminek 1 éves a futamideje
		felh_trzi.get(0).setDatum(LocalDate.now().minusMonths(5L));
		
		// tehát van egy aktív lekötés ami nem járt még le, ezért nem is kell teljesíteni
		
		lekotesSzolgaltatas.lekotesEllenorzes( felh, felh_trzi );
		// nem teljesíti mert még nem járt le
		Assert.assertFalse( lek.isTeljesitett() );
		
		// beállítjuk 1 évre a lekötés dátumát
		felh_trzi.get(0).setDatum(LocalDate.now().minusYears(1L));
		// most már lejárt, teljesítenie kell
		lekotesSzolgaltatas.lekotesEllenorzes( felh, felh_trzi );
		
		Assert.assertTrue( lek.isTeljesitett() );
	}
	
	@Test
	public void _5felhasznaloLekoteseTeszt(){
		
		tesztLekotes.setTeljesitett(false);
		
		LekotesVo fhlekotese = lekotesSzolgaltatas.felhasznaloLekotese(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertEquals(LekotesMapper.toVo(tesztLekotes), fhlekotese);
	}
	
	@Test
	public void _6mennyiIdoVanHatraTeszt(){
		
		tesztLekotes.setTeljesitett(false);
		
		tesztFelhasznaloTranzakcioi.get(0).setDatum(LocalDate.now().minusDays(5L));
		
		// egy éves futamidővel, 5 napja
		Period p = lekotesSzolgaltatas.mennyiIdoVanHatra(FelhasznaloMapper.toVo(tesztFelhasznalo), LekotesMapper.toVo(tesztLekotes));
		
		Assert.assertEquals(0, p.getYears());
		Assert.assertEquals(11, p.getMonths());
		Assert.assertEquals(25, p.getDays());
	}
}
