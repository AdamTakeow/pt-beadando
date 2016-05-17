package hu.bertalanadam.prt.beadando.szolgaltatas.test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

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
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;
import hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.FelhasznaloSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class FelhasznaloSzolgaltatasTest {

	@Mock
	private FelhasznaloTarolo felhasznaloTarolo;
	
	@Spy
	@InjectMocks
	private FelhasznaloSzolgaltatasImpl felhasznaloSzolgaltatas;
	
	private static Felhasznalo tesztFelhasznalo;
	
	@BeforeClass
	public static void setUpClass(){
		// elkészítjük az "adatbázist"
		
		// felhasználó létrehozás
		Felhasznalo ujFelhasznalo = new Felhasznalo();
		ujFelhasznalo.setId(1L);
		ujFelhasznalo.setFelhasznalonev("TesztFelhasznalo");
		ujFelhasznalo.setJelszo("tesztJelszo");
		ujFelhasznalo.setKategoriak(new ArrayList<>());
		ujFelhasznalo.setTranzakciok(new ArrayList<>());
		ujFelhasznalo.setKezdoIdopont(LocalDate.now().minusYears(1L));
		ujFelhasznalo.setVegIdopont(LocalDate.now());
		
		tesztFelhasznalo = ujFelhasznalo;
		
		// kategóriák létrehozása
		Kategoria ujKategoria = new Kategoria();
		ujKategoria.setFelhasznalok(new ArrayList<>(Arrays.asList(tesztFelhasznalo)));
		ujKategoria.setId(2L);
		ujKategoria.setNev("TesztKategoria1");
				
		Kategoria ujKategoria2 = new Kategoria();
		ujKategoria2.setFelhasznalok(new ArrayList<>(Arrays.asList(tesztFelhasznalo)));
		ujKategoria2.setId(3L);
		ujKategoria2.setNev("TesztKategoria2");
			
		// tranzakciók létrehozása
		Tranzakcio ujTranzakcio = new Tranzakcio();
		ujTranzakcio.setDatum(LocalDate.now().minusDays(1L));
		ujTranzakcio.setFelhasznalo(tesztFelhasznalo);
		ujTranzakcio.setId(4L);
		ujTranzakcio.setKategoria(ujKategoria2);
		ujTranzakcio.setLeiras("Tranzakcio4 leirasa");
		ujTranzakcio.setOsszeg(-800L);

		Tranzakcio ujTranzakcio2 = new Tranzakcio();
		ujTranzakcio2.setDatum(LocalDate.now().minusDays(2L));
		ujTranzakcio2.setFelhasznalo(tesztFelhasznalo);
		ujTranzakcio2.setId(5L);
		ujTranzakcio2.setKategoria(ujKategoria);
		ujTranzakcio2.setLeiras("Tranzakcio1 leirasa");
		ujTranzakcio2.setOsszeg(500L);

		Tranzakcio ujTranzakcio3 = new Tranzakcio();
		ujTranzakcio3.setDatum(LocalDate.now().minusDays(5L));
		ujTranzakcio3.setFelhasznalo(tesztFelhasznalo);
		ujTranzakcio3.setId(6L);
		ujTranzakcio3.setKategoria(ujKategoria2);
		ujTranzakcio3.setLeiras("Tranzakcio3 leirasa");
		ujTranzakcio3.setOsszeg(1000L);
				
		Tranzakcio ujTranzakcio4 = new Tranzakcio();
		ujTranzakcio4.setDatum(LocalDate.now().minusMonths(3L));
		ujTranzakcio4.setFelhasznalo(tesztFelhasznalo);
		ujTranzakcio4.setId(7L);
		ujTranzakcio4.setKategoria(ujKategoria);
		ujTranzakcio4.setLeiras("Tranzakcio2 leirasa");
		ujTranzakcio4.setOsszeg(-300L);
				
		tesztFelhasznalo.getKategoriak().addAll( Arrays.asList(ujKategoria, ujKategoria2));
			
		tesztFelhasznalo.getTranzakciok().addAll( Arrays.asList( ujTranzakcio, ujTranzakcio2, ujTranzakcio3, ujTranzakcio4));
	}
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		
		Mockito.when( felhasznaloTarolo.findByFelhasznalonev(Mockito.any(String.class)))
		.thenAnswer( new Answer<Felhasznalo>() {
			@Override
			public Felhasznalo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				// jelenleg csak egy felhasználó van az "adatbázisban"
				if( ((String)args[0]).equals("TesztFelhasznalo") ){
					return tesztFelhasznalo;
				} else {
					return null;
				}
			}
		});
		
		Mockito.when( felhasznaloTarolo.save(Mockito.any(Felhasznalo.class)))
		.thenAnswer( new Answer<Felhasznalo>() {
			@Override
			public Felhasznalo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Felhasznalo)args[0]).getId() == null ){
					((Felhasznalo)args[0]).setId(87L);
				}
				return ((Felhasznalo)args[0]);
			}
		});
		
	}
	
	@Test
	public void _1letrehozFelhasznalotTeszt() {
		
		FelhasznaloVo ujFelhasznalo = new FelhasznaloVo();
		ujFelhasznalo.setFelhasznalonev("Uj felhasznalo");
		ujFelhasznalo.setJelszo("Uj jelszo");
		
		FelhasznaloVo mentett = 
				felhasznaloSzolgaltatas.letrehozFelhasznalot(ujFelhasznalo);
		
		Assert.assertEquals("Uj felhasznalo", mentett.getFelhasznalonev());
		Assert.assertNotNull( mentett.getId() );
		Assert.assertNotNull( mentett.getEgyenleg() );
		Assert.assertNotNull( mentett.getKategoriak() );
		Assert.assertNotNull( mentett.getTranzakciok() );
		Assert.assertNotNull( mentett.getKezdoIdopont() );
		Assert.assertNotNull( mentett.getVegIdopont() );
		Assert.assertNotNull( mentett.getKiadasraSzantPenz() );
		
	}
	
	@Test
	public void _2keresFelhasznalotTeszt() {
		FelhasznaloVo vanIlyen = felhasznaloSzolgaltatas.keresFelhasznalot("TesztFelhasznalo");
		Assert.assertNotNull(vanIlyen);
		
		FelhasznaloVo nincsIlyen = felhasznaloSzolgaltatas.keresFelhasznalot("NincsIlyen");
		Assert.assertNull(nincsIlyen);
	}
	
	@Test
	public void _3frissitFelhasznalotTeszt(){
		FelhasznaloVo ujFelhasznalo = new FelhasznaloVo();
		ujFelhasznalo.setFelhasznalonev("Uj felhasznalo");
		ujFelhasznalo.setJelszo("Uj jelszo");
		
		FelhasznaloVo mentett = 
				felhasznaloSzolgaltatas.letrehozFelhasznalot(ujFelhasznalo);
		
		mentett.setFelhasznalonev("UjFelhasznaloNev");
		
		// újraszámolja az egyenleget
		FelhasznaloVo frissitett = felhasznaloSzolgaltatas.frissitFelhasznalot(mentett);
		
		Assert.assertEquals("UjFelhasznaloNev", frissitett.getFelhasznalonev());
	}

	@Test
	public void _4felhasznaloOsszesBeveteleTeszt(){
		// egy nappal korábbanról tranzakciókat máig
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusDays(1L));
		tesztFelhasznalo.setVegIdopont(LocalDate.now());
		
		Long osszes = felhasznaloSzolgaltatas.felhasznaloOsszesBevetele(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertEquals(new Long(0L), osszes);
		
		// három nappal korábbanról máig
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusDays(3L));
		
		osszes = felhasznaloSzolgaltatas.felhasznaloOsszesBevetele(FelhasznaloMapper.toVo(tesztFelhasznalo));
		Assert.assertEquals(new Long(500L), osszes);
	}
	
	@Test
	public void _5felhasznaloOsszesKiadasaTeszt(){
		// egy nappal korábbanról tranzakciókat máig
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now());
		tesztFelhasznalo.setVegIdopont(LocalDate.now());
		
		Long osszes = felhasznaloSzolgaltatas.felhasznaloOsszesKiadasa(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertEquals(new Long(0L), osszes);
		
		// három hónappal korábbanról máig
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusMonths(3L));
		
		osszes = felhasznaloSzolgaltatas.felhasznaloOsszesKiadasa(FelhasznaloMapper.toVo(tesztFelhasznalo));
		Assert.assertEquals(new Long(1100L), osszes);
	}
	
	@Test
	public void _6bevDiagramAdatokSzamitasaFelhasznalohozTeszt(){
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusYears(1L));
		tesztFelhasznalo.setVegIdopont(LocalDate.now());
		
		Map<String, Long> map = felhasznaloSzolgaltatas
				.bevDiagramAdatokSzamitasaFelhasznalohoz(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertTrue(map.containsKey("TesztKategoria1"));
		Assert.assertEquals(new Long(500L), map.get("TesztKategoria1"));
		
		Assert.assertTrue(map.containsKey("TesztKategoria2"));
		Assert.assertEquals(new Long(1000L), map.get("TesztKategoria2"));
		
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusDays(1L));
		
		map = felhasznaloSzolgaltatas
			.bevDiagramAdatokSzamitasaFelhasznalohoz(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertFalse(map.containsKey("TesztKategoria1"));
		
		Assert.assertFalse(map.containsKey("TesztKategoria2"));
	}
	
	@Test
	public void _7kiadDiagramAdatokSzamitasaFelhasznalohozTeszt(){
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusYears(1L));
		tesztFelhasznalo.setVegIdopont(LocalDate.now());
		
		Map<String, Long> map = felhasznaloSzolgaltatas
				.kiadDiagramAdatokSzamitasaFelhasznalohoz(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertTrue(map.containsKey("TesztKategoria1"));
		Assert.assertEquals(new Long(300L), map.get("TesztKategoria1"));
		
		Assert.assertTrue(map.containsKey("TesztKategoria2"));
		Assert.assertEquals(new Long(800L), map.get("TesztKategoria2"));
		
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusDays(1L));
		
		map = felhasznaloSzolgaltatas
				.kiadDiagramAdatokSzamitasaFelhasznalohoz(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertFalse(map.containsKey("TesztKategoria1"));
		
		Assert.assertTrue(map.containsKey("TesztKategoria2"));
		Assert.assertEquals(new Long(800L), map.get("TesztKategoria2"));
	}
	
	@Test
	public void _8szamolMennyitKolthetMegAFelhasznaloTeszt(){
		tesztFelhasznalo.setEgyenleg(0L);
		tesztFelhasznalo.setKiadasraSzantPenz(0L);
		
		long eredmeny = felhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(FelhasznaloMapper.toVo(tesztFelhasznalo));
		Assert.assertEquals(0L, eredmeny);
		
		tesztFelhasznalo.setEgyenleg(800L);
		tesztFelhasznalo.setKiadasraSzantPenz(0L);
		
		eredmeny = felhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(FelhasznaloMapper.toVo(tesztFelhasznalo));
		Assert.assertEquals(800L, eredmeny);
		
		tesztFelhasznalo.setEgyenleg(300L);
		tesztFelhasznalo.setKiadasraSzantPenz(800L);
		
		eredmeny = felhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(FelhasznaloMapper.toVo(tesztFelhasznalo));
		Assert.assertEquals(300L, eredmeny);
		
		tesztFelhasznalo.setEgyenleg(1000L);
		// eddig 800 kiadása volt már
		tesztFelhasznalo.setKiadasraSzantPenz(900L);
		// 100Ft-t költhet még
		eredmeny = felhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertEquals(100L, eredmeny);
		
		tesztFelhasznalo.setKiadasraSzantPenz(800L);
		// elköltötte a kiadásra szántat
		eredmeny = felhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(FelhasznaloMapper.toVo(tesztFelhasznalo));
		
		Assert.assertEquals(0L, eredmeny);
	}
}
