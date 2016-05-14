package hu.bertalanadam.prt.beadando.szolgaltatas.test;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.szolgaltatas.FelhasznaloSzolgaltatas;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.FelhasznaloSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("/spring-szolg.xml")
@Transactional
@Rollback(true)
public class FelhasznaloSzolgaltatasTest {

	private static FelhasznaloSzolgaltatas mockedFelhasznaloSzolgaltatas =
			mock(FelhasznaloSzolgaltatasImpl.class);
//	@Autowired
//	private FelhasznaloSzolgaltatas mockedFelhasznaloSzolgaltatas;
	
	private static FelhasznaloVo tesztFelhasznalo;
	
	@BeforeClass
	public static void setUpClass(){
		
		when(mockedFelhasznaloSzolgaltatas.keresFelhasznalot(any(String.class)))
		.thenAnswer( new Answer<FelhasznaloVo>() {
			@Override
			public FelhasznaloVo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((String)args[0]).equals("TesztFelhasznalo") ){
					return tesztFelhasznalo;
				} else {
					return null;
				}
			}
		});
		
		when(mockedFelhasznaloSzolgaltatas.letrehozFelhasznalot(any(FelhasznaloVo.class)))
		.thenAnswer( new Answer<FelhasznaloVo>() {
			@Override
			public FelhasznaloVo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				FelhasznaloVo ansFelh = (FelhasznaloVo)args[0];
				ansFelh.setId(1L);
				ansFelh.setEgyenleg(0L);
				ansFelh.setKiadasraSzantPenz(0L);
				
				LocalDate innentol = LocalDate.of(1970, 1, 1);
				LocalDate idaig = LocalDate.now();
				ansFelh.setKezdoIdopont(innentol);
				ansFelh.setVegIdopont(idaig);
				ansFelh.setTranzakciok(new ArrayList<>());
				ansFelh.setKategoriak(new ArrayList<>());
				tesztFelhasznalo = ansFelh;
				
				return tesztFelhasznalo;
			}
		});
		
		when(mockedFelhasznaloSzolgaltatas.frissitFelhasznalot(any(FelhasznaloVo.class)))
		.thenAnswer( new Answer<FelhasznaloVo>() {
			@Override
			public FelhasznaloVo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				return (FelhasznaloVo)args[0];
			}
		});
		
		when(mockedFelhasznaloSzolgaltatas.felhasznaloOsszesBevetele(any(FelhasznaloVo.class)))
		.thenCallRealMethod();
		
		when(mockedFelhasznaloSzolgaltatas.felhasznaloOsszesKiadasa(any(FelhasznaloVo.class)))
		.thenCallRealMethod();
		
		when(mockedFelhasznaloSzolgaltatas.bevDiagramAdatokSzamitasaFelhasznalohoz(any(FelhasznaloVo.class)))
		.thenCallRealMethod();
		
		when(mockedFelhasznaloSzolgaltatas.kiadDiagramAdatokSzamitasaFelhasznalohoz(any(FelhasznaloVo.class)))
		.thenCallRealMethod();
		
		when(mockedFelhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(any(FelhasznaloVo.class)))
		.thenCallRealMethod();
	}
	
	@Before
	public void setUp(){
		// felhasználó létrehozás
		FelhasznaloVo ujFelhasznalo = new FelhasznaloVo();
		ujFelhasznalo.setFelhasznalonev("TesztFelhasznalo");
		ujFelhasznalo.setJelszo("tesztJelszo");
		ujFelhasznalo.setKategoriak(new ArrayList<>());
		ujFelhasznalo.setTranzakciok(new ArrayList<>());
		
		// kategóriák létrehozása
		KategoriaVo ujKategoria = new KategoriaVo();
		ujKategoria.setFelhasznalok(new ArrayList<>(Arrays.asList(ujFelhasznalo)));
		ujKategoria.setId(2L);
		ujKategoria.setNev("TesztKategoria1");
		
		KategoriaVo ujKategoria2 = new KategoriaVo();
		ujKategoria2.setFelhasznalok(new ArrayList<>(Arrays.asList(ujFelhasznalo)));
		ujKategoria2.setId(3L);
		ujKategoria2.setNev("TesztKategoria2");
		
		// tranzakciók létrehozása
		TranzakcioVo ujTranzakcio = new TranzakcioVo();
		ujTranzakcio.setDatum(LocalDate.now().minusDays(2L));
		ujTranzakcio.setFelhasznalo(ujFelhasznalo);
		ujTranzakcio.setId(4L);
		ujTranzakcio.setKategoria(ujKategoria);
		ujTranzakcio.setLeiras("Tranzakcio1 leirasa");
		ujTranzakcio.setOsszeg(500L);
		
		TranzakcioVo ujTranzakcio2 = new TranzakcioVo();
		ujTranzakcio2.setDatum(LocalDate.now().minusMonths(3L));
		ujTranzakcio2.setFelhasznalo(ujFelhasznalo);
		ujTranzakcio2.setId(4L);
		ujTranzakcio2.setKategoria(ujKategoria);
		ujTranzakcio2.setLeiras("Tranzakcio2 leirasa");
		ujTranzakcio2.setOsszeg(-300L);
		
		TranzakcioVo ujTranzakcio3 = new TranzakcioVo();
		ujTranzakcio3.setDatum(LocalDate.now().minusDays(5L));
		ujTranzakcio3.setFelhasznalo(ujFelhasznalo);
		ujTranzakcio3.setId(4L);
		ujTranzakcio3.setKategoria(ujKategoria2);
		ujTranzakcio3.setLeiras("Tranzakcio3 leirasa");
		ujTranzakcio3.setOsszeg(1000L);
		
		TranzakcioVo ujTranzakcio4 = new TranzakcioVo();
		ujTranzakcio4.setDatum(LocalDate.now().minusDays(1L));
		ujTranzakcio4.setFelhasznalo(ujFelhasznalo);
		ujTranzakcio4.setId(4L);
		ujTranzakcio4.setKategoria(ujKategoria2);
		ujTranzakcio4.setLeiras("Tranzakcio4 leirasa");
		ujTranzakcio4.setOsszeg(-800L);
		
		ujFelhasznalo.getKategoriak().add(ujKategoria);
		ujFelhasznalo.getKategoriak().add(ujKategoria2);
		
		ujFelhasznalo.getTranzakciok().add(ujTranzakcio);
		ujFelhasznalo.getTranzakciok().add(ujTranzakcio2);
		ujFelhasznalo.getTranzakciok().add(ujTranzakcio3);
		ujFelhasznalo.getTranzakciok().add(ujTranzakcio4);
		
		tesztFelhasznalo = ujFelhasznalo;
	}
	
	@Test
	public void keresFelhasznalotTeszt() {
		FelhasznaloVo vanIlyen = mockedFelhasznaloSzolgaltatas.keresFelhasznalot("TesztFelhasznalo");
		Assert.assertNotNull(vanIlyen);
		
		FelhasznaloVo nincsIlyen = mockedFelhasznaloSzolgaltatas.keresFelhasznalot("NincsIlyen");
		Assert.assertNull(nincsIlyen);
	}
	
	@Test
	public void letrehozFelhasznalotTeszt() {
		
		FelhasznaloVo mentett = 
				mockedFelhasznaloSzolgaltatas.letrehozFelhasznalot(tesztFelhasznalo);
		
		Assert.assertEquals("TesztFelhasznalo", mentett.getFelhasznalonev());
		Assert.assertTrue( mentett.getId() != null );
		
	}
	
	@Test
	public void frissitFelhasznalotTeszt(){
		tesztFelhasznalo.setEgyenleg(100L);
		tesztFelhasznalo.setFelhasznalonev("UjFelhasznaloNev");
		
		FelhasznaloVo frissitett = mockedFelhasznaloSzolgaltatas.frissitFelhasznalot(tesztFelhasznalo);
		
		Assert.assertEquals("UjFelhasznaloNev", frissitett.getFelhasznalonev());
		Assert.assertEquals( new Long(100L), frissitett.getEgyenleg());
	}

	@Test
	public void felhasznaloOsszesBeveteleTeszt(){
		// egy nappal korábbanról tranzakciókat máig
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusDays(1L));
		tesztFelhasznalo.setVegIdopont(LocalDate.now());
		
		Long osszes = mockedFelhasznaloSzolgaltatas.felhasznaloOsszesBevetele(tesztFelhasznalo);
		
		Assert.assertEquals(new Long(0L), osszes);
		
		// három nappal korábbanról máig
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusDays(3L));
		
		osszes = mockedFelhasznaloSzolgaltatas.felhasznaloOsszesBevetele(tesztFelhasznalo);
		Assert.assertEquals(new Long(500L), osszes);
	}
	
	@Test
	public void felhasznaloOsszesKiadasaTeszt(){
		// egy nappal korábbanról tranzakciókat máig
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now());
		tesztFelhasznalo.setVegIdopont(LocalDate.now());
		
		Long osszes = mockedFelhasznaloSzolgaltatas.felhasznaloOsszesKiadasa(tesztFelhasznalo);
		
		Assert.assertEquals(new Long(0L), osszes);
		
		// három hónappal korábbanról máig
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusMonths(3L));
		
		osszes = mockedFelhasznaloSzolgaltatas.felhasznaloOsszesKiadasa(tesztFelhasznalo);
		Assert.assertEquals(new Long(1100L), osszes);
	}
	
	@Test
	public void bevDiagramAdatokSzamitasaFelhasznalohozTeszt(){
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusYears(1L));
		tesztFelhasznalo.setVegIdopont(LocalDate.now());
		
		Map<String, Long> map = mockedFelhasznaloSzolgaltatas.bevDiagramAdatokSzamitasaFelhasznalohoz(tesztFelhasznalo);
		
		Assert.assertTrue(map.containsKey("TesztKategoria1"));
		Assert.assertEquals(new Long(500L), map.get("TesztKategoria1"));
		
		Assert.assertTrue(map.containsKey("TesztKategoria2"));
		Assert.assertEquals(new Long(1000L), map.get("TesztKategoria2"));
		
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusDays(1L));
		
		map = mockedFelhasznaloSzolgaltatas.bevDiagramAdatokSzamitasaFelhasznalohoz(tesztFelhasznalo);
		
		Assert.assertFalse(map.containsKey("TesztKategoria1"));
		
		Assert.assertFalse(map.containsKey("TesztKategoria2"));
	}
	
	@Test
	public void kiadDiagramAdatokSzamitasaFelhasznalohozTeszt(){
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusYears(1L));
		tesztFelhasznalo.setVegIdopont(LocalDate.now());
		
		Map<String, Long> map = mockedFelhasznaloSzolgaltatas.kiadDiagramAdatokSzamitasaFelhasznalohoz(tesztFelhasznalo);
		
		Assert.assertTrue(map.containsKey("TesztKategoria1"));
		Assert.assertEquals(new Long(300L), map.get("TesztKategoria1"));
		
		Assert.assertTrue(map.containsKey("TesztKategoria2"));
		Assert.assertEquals(new Long(800L), map.get("TesztKategoria2"));
		
		tesztFelhasznalo.setKezdoIdopont(LocalDate.now().minusDays(1L));
		
		map = mockedFelhasznaloSzolgaltatas.kiadDiagramAdatokSzamitasaFelhasznalohoz(tesztFelhasznalo);
		
		Assert.assertFalse(map.containsKey("TesztKategoria1"));
		
		Assert.assertTrue(map.containsKey("TesztKategoria2"));
		Assert.assertEquals(new Long(800L), map.get("TesztKategoria2"));
	}
	
	@Test
	public void szamolMennyitKolthetMegAFelhasznaloTeszt(){
		tesztFelhasznalo.setEgyenleg(0L);
		tesztFelhasznalo.setKiadasraSzantPenz(0L);
		
		long eredmeny = mockedFelhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(tesztFelhasznalo);
		Assert.assertEquals(0L, eredmeny);
		
		tesztFelhasznalo.setEgyenleg(800L);
		tesztFelhasznalo.setKiadasraSzantPenz(0L);
		
		eredmeny = mockedFelhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(tesztFelhasznalo);
		Assert.assertEquals(800L, eredmeny);
		
		tesztFelhasznalo.setEgyenleg(300L);
		tesztFelhasznalo.setKiadasraSzantPenz(800L);
		
		eredmeny = mockedFelhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(tesztFelhasznalo);
		Assert.assertEquals(300L, eredmeny);
		
		tesztFelhasznalo.setEgyenleg(1000L);
		// eddig 800 kiadása volt már
		tesztFelhasznalo.setKiadasraSzantPenz(900L);
		// 100Ft-t költhet még
		eredmeny = mockedFelhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(tesztFelhasznalo);
		
		Assert.assertEquals(100L, eredmeny);
		
		tesztFelhasznalo.setKiadasraSzantPenz(800L);
		// elköltötte a kiadásra szántat
		eredmeny = mockedFelhasznaloSzolgaltatas.szamolMennyitKolthetMegAFelhasznalo(tesztFelhasznalo);
		
		Assert.assertEquals(0L, eredmeny);
	}
}
