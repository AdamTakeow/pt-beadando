package hu.bertalanadam.prt.beadando.szolgaltatas.test;

import java.time.LocalDate;
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
import hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo;
import hu.bertalanadam.prt.beadando.db.entitas.Kategoria;
import hu.bertalanadam.prt.beadando.db.entitas.Lekotes;
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.db.tarolo.FelhasznaloTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.IsmetlodoTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo;
import hu.bertalanadam.prt.beadando.db.tarolo.TranzakcioTarolo;
import hu.bertalanadam.prt.beadando.mapper.FelhasznaloMapper;
import hu.bertalanadam.prt.beadando.mapper.TranzakcioMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.FelhasznaloSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.IsmetlodoSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.KategoriaSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.LekotesSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.TranzakcioSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TranzakcioSzolgaltatasTest {

	private static Felhasznalo AFelhasznalo;
	private static Felhasznalo BFelhasznalo;
	private static Kategoria tesztKategoria;
	private static List<Tranzakcio> ATranzakcioi = new ArrayList<>();
	private static List<Tranzakcio> BTranzakcioi = new ArrayList<>();
	private static Ismetlodo AIsmetlodoje;
	private static Lekotes BLekotese;
	
	@Mock
	private TranzakcioTarolo tranzakcioTarolo;
	@Mock
	private KategoriaTarolo kategoriaTarolo;
	@Mock
	private FelhasznaloTarolo felhasznaloTarolo;
	@Mock
	private IsmetlodoTarolo ismetlodoTarolo;
	
	@Spy
	@InjectMocks
	private FelhasznaloSzolgaltatasImpl felhasznaloSzolgaltatas;
	
	@Spy
	@InjectMocks
	private IsmetlodoSzolgaltatasImpl ismetlodoSzolgaltatas;
	
	@Spy
	@InjectMocks
	private LekotesSzolgaltatasImpl lekotesSzolgaltatas;
	
	@Spy
	@InjectMocks
	private KategoriaSzolgaltatasImpl kategoriaSzolgaltatas;
	
	@Spy
	@InjectMocks
	private TranzakcioSzolgaltatasImpl tranzakcioSzolgaltatas;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// elkészítjük az "Adatbázist"
		
		// Az adatbázisban van két felhasználó
		Felhasznalo felh = new Felhasznalo();
		felh.setId(1L);
		felh.setFelhasznalonev("AAAA");
		felh.setTranzakciok(new ArrayList<>());
		felh.setKategoriak(new ArrayList<>());
		felh.setKezdoIdopont(LocalDate.now().minusYears(1L));
		felh.setVegIdopont(LocalDate.now());
		
		AFelhasznalo = felh;

		// B felhasználó
		Felhasznalo felh2 = new Felhasznalo();
		felh2.setId(2L);
		felh2.setFelhasznalonev("BBBB");
		felh2.setTranzakciok(new ArrayList<>());
		felh2.setKategoriak(new ArrayList<>());
		felh2.setKezdoIdopont(LocalDate.now().minusYears(1L));
		felh2.setVegIdopont(LocalDate.now());

		BFelhasznalo = felh2;
		
		// van még az adatbázisban egy kategória
		Kategoria ujKateg = new Kategoria();
		ujKateg.setNev("Kateg1");
		ujKateg.setId(3L);
		ujKateg.setFelhasznalok(new ArrayList<>());
		ujKateg.getFelhasznalok().add(AFelhasznalo);
		ujKateg.getFelhasznalok().add(BFelhasznalo);
		
		tesztKategoria = ujKateg;
		
		// az A felhasználó ismétlődője
		Ismetlodo ujIsmetlodo = new Ismetlodo();
		ujIsmetlodo.setId(4L);
		ujIsmetlodo.setIdo(1L);
		ujIsmetlodo.setUtolsoBeszuras(LocalDate.now());
		
		AIsmetlodoje = ujIsmetlodo;
		
		// az A felhasználó tranzakciói
		Tranzakcio tranzakcio1 = new Tranzakcio();
		tranzakcio1.setId(7L);
		tranzakcio1.setOsszeg(100L);
		tranzakcio1.setFelhasznalo(AFelhasznalo);
		tranzakcio1.setDatum(LocalDate.now().minusDays(5L));
		tranzakcio1.setKategoria(tesztKategoria);
		
		Tranzakcio tranzakcio2 = new Tranzakcio();
		tranzakcio2.setId(8L);
		tranzakcio2.setOsszeg(-500L);
		tranzakcio2.setFelhasznalo(felh);
		tranzakcio2.setDatum(LocalDate.now().minusDays(1L));
		tranzakcio2.setKategoria(ujKateg);
		
		Tranzakcio ujTranzakcio = new Tranzakcio();
		ujTranzakcio.setId(9L);
		ujTranzakcio.setDatum(LocalDate.now().minusDays(1L));
		ujTranzakcio.setFelhasznalo(AFelhasznalo);
		ujTranzakcio.setOsszeg(345L);
		// az ismétlődős trz
		ujTranzakcio.setIsmetlodo(AIsmetlodoje);
		ujTranzakcio.setKategoria(tesztKategoria);
		
		ATranzakcioi.addAll(Arrays.asList(tranzakcio1, tranzakcio2, ujTranzakcio));
		
		// A B felhasználó tranzakciói
		Tranzakcio tranzakcio3 = new Tranzakcio();
		tranzakcio3.setId(10L);
		tranzakcio3.setOsszeg(1100L);
		tranzakcio3.setFelhasznalo(BFelhasznalo);
		tranzakcio3.setDatum(LocalDate.now().minusDays(3L));
		
		Tranzakcio tranzakcio4 = new Tranzakcio();
		tranzakcio4.setId(11L);
		tranzakcio4.setOsszeg(-200L);
		tranzakcio4.setFelhasznalo(BFelhasznalo);
		tranzakcio4.setDatum(LocalDate.now().minusDays(7L));
		
		// A B felhasználó lekötése
		Lekotes lekotes = new Lekotes();
		lekotes.setId(13L);
		lekotes.setFutamido(1L);
		lekotes.setTeljesitett(false);
		lekotes.setKamat(10.0);
		lekotes.setOsszeg(10000L);
		lekotes.setVarhato(12345L);

		BLekotese = lekotes;

		Tranzakcio tranzakcio5 = new Tranzakcio();
		tranzakcio5.setId(12L);
		tranzakcio5.setOsszeg(10000L);
		tranzakcio5.setFelhasznalo(BFelhasznalo);
		tranzakcio5.setDatum(LocalDate.now().minusDays(1L));
		tranzakcio5.setLekotes(BLekotese);
		
		BTranzakcioi.addAll(Arrays.asList(tranzakcio3, tranzakcio4, tranzakcio5));
		
		AFelhasznalo.getTranzakciok().add(tranzakcio1);
		AFelhasznalo.getTranzakciok().add(tranzakcio2);
		AFelhasznalo.getTranzakciok().add(ujTranzakcio);
		
		BFelhasznalo.getTranzakciok().add(tranzakcio3);
		BFelhasznalo.getTranzakciok().add(tranzakcio4);
		BFelhasznalo.getTranzakciok().add(tranzakcio5);
		
		felh.getKategoriak().add(tesztKategoria);
		felh2.getKategoriak().add(tesztKategoria);
	}
	
	@Before
	 public void initMocks(){
		 MockitoAnnotations.initMocks(this);
		 
		 Mockito.when( tranzakcioTarolo.save(Mockito.any(Tranzakcio.class)))
		 .thenAnswer(new Answer<Tranzakcio>() {
			@Override
			public Tranzakcio answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Tranzakcio)args[0]).getId() == null ){
					((Tranzakcio)args[0]).setId(10L);					
				}
				return (Tranzakcio)args[0];
			}
		});
		 
		 // ha valaki ID alapján akar keresni az adatbázisban, akkor 
		 Mockito.when( tranzakcioTarolo.findOne(Mockito.any(Long.class)))
		 .thenAnswer(new Answer<Tranzakcio>() {
			@Override
			public Tranzakcio answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				switch( ((Long)args[0]).intValue() ){
				case 7 : return ATranzakcioi.get(0);
				case 8 : return ATranzakcioi.get(1);
				case 9 : return ATranzakcioi.get(2);
				case 10 : return BTranzakcioi.get(0);
				case 11 : return BTranzakcioi.get(1);
				case 12 : return BTranzakcioi.get(2);
				default : return null;
				}
			}
		});
		 
		 // ha felhasználó alapján akarnak tranzakciókat keresni az adatbázisban, akkor 
		 // visszaadjuk a felhasználóknak megfelelő tranzakciókat ha van ilyen felhasználó 
		 Mockito.when( tranzakcioTarolo.findByFelhasznalo(Mockito.any(Felhasznalo.class)))
		 .thenAnswer(new Answer<List<Tranzakcio>>() {
			@Override
			public List<Tranzakcio> answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Felhasznalo)args[0]).getId().equals(AFelhasznalo.getId()) ){
					return ATranzakcioi;
				} else if ( ((Felhasznalo)args[0]).getId().equals(BFelhasznalo.getId()) ){
					return BTranzakcioi;
				} else {
					return null;					
				}
			}
		});
		 
		 
		 Mockito.when( tranzakcioTarolo.findFirstByFelhasznaloOrderByDatumAsc(Mockito.any(Felhasznalo.class)) )
		 .thenAnswer( new Answer<Tranzakcio>() {
			@Override
			public Tranzakcio answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Felhasznalo)args[0]).getId().equals(AFelhasznalo.getId()) ){
					return ATranzakcioi.get(1);
				} else if ( ((Felhasznalo)args[0]).getId().equals(BFelhasznalo.getId()) ){
					return BTranzakcioi.get(2);
				} else {
					return null;					
				}
			}
		});
		 
		 Mockito.when( kategoriaTarolo.findByNev(Mockito.any(String.class)) )
		 .thenAnswer(new Answer<Kategoria>() {
			@Override
			public Kategoria answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((String)args[0]).equals("Kateg1") ){
					return tesztKategoria;
				} else {
					return null;
				}
			}
		});
		 
		 Mockito.when( felhasznaloTarolo.save(Mockito.any(Felhasznalo.class)) )
		 .thenAnswer(new Answer<Felhasznalo>() {
			@Override
			public Felhasznalo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Felhasznalo)args[0]).getId() == null ){
					((Felhasznalo)args[0]).setId(8L);
				}
				return (Felhasznalo)args[0];
			}
		});
		 
		 Mockito.when( ismetlodoTarolo.save(Mockito.any(Ismetlodo.class)))
		 .thenAnswer(new Answer<Ismetlodo>() {
			@Override
			public Ismetlodo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Ismetlodo)args[0]).getId() == null ){
					((Ismetlodo)args[0]).setId(32L);					
				}
				return (Ismetlodo)args[0];
			}
		});
		 
		 // letezo tranzakcio idk: 7,8,9 10,11
		
		 Mockito.doNothing().when( tranzakcioTarolo ).delete( Mockito.notNull(Long.class) );
		 Mockito.doThrow( new NullPointerException("Nincs mit torolni!")).when( tranzakcioTarolo ).delete( Mockito.isNull(Long.class) );
		 
	 }

	@Test
	public void _1letrehozTranzakciotTeszt(){
		Assert.assertNotNull(tranzakcioSzolgaltatas);
		
		TranzakcioVo ujTranzakcio = new TranzakcioVo();		
		ujTranzakcio.setOsszeg(500L);
		ujTranzakcio.setLeiras("Teszt lekotes");
		ujTranzakcio.setFelhasznalo(new FelhasznaloVo());
		ujTranzakcio.setKategoria(new KategoriaVo());
		
		TranzakcioVo mentett = tranzakcioSzolgaltatas.letrehozTranzakciot(ujTranzakcio);
		
		Assert.assertNotNull(mentett);
		Assert.assertNotNull(mentett.getId());
		Assert.assertEquals(new Long(10L), mentett.getId());
		Assert.assertNotNull(mentett.getDatum());
	}
	
	@Test
	public void _2frissitTranzakciotTeszt(){
		TranzakcioVo ujTranzakcio = new TranzakcioVo();		
		ujTranzakcio.setOsszeg(500L);
		ujTranzakcio.setLeiras("Teszt lekotes");
		ujTranzakcio.setFelhasznalo(new FelhasznaloVo());
		ujTranzakcio.setKategoria(new KategoriaVo());
		
		TranzakcioVo mentett = tranzakcioSzolgaltatas.letrehozTranzakciot(ujTranzakcio);
		
		mentett.setLeiras("Uj leiras");
		mentett.setOsszeg(800L);
		
		TranzakcioVo frissitett = tranzakcioSzolgaltatas.frissitTranzakciot(mentett);
		
		Assert.assertEquals("Uj leiras", frissitett.getLeiras());
		Assert.assertEquals(new Long(800L), frissitett.getOsszeg());
	}
	
	@Test
	public void _3keresTranzakciotTeszt(){
		
		TranzakcioVo van = tranzakcioSzolgaltatas.keresTranzakciot(7L);
		Assert.assertNotNull(van);
		
		TranzakcioVo nincs = tranzakcioSzolgaltatas.keresTranzakciot(1L);
		Assert.assertNull(nincs);
	}
	
	@Test
	public void _4felhasznaloOsszesTranzakciojaTeszt(){
		// átmappelem az objektumokat
		FelhasznaloVo felh = FelhasznaloMapper.toVo(AFelhasznalo);
		
		List<TranzakcioVo> felh_trzi = tranzakcioSzolgaltatas.felhasznaloOsszesTranzakcioja(felh);
		
		Assert.assertNotNull(felh_trzi);
		Assert.assertEquals(3, felh_trzi.size());
		
	}
	
	@Test(expected=NullPointerException.class)
	public void _7tranzakcioTorlesTeszt(){
		
		try {
			tranzakcioSzolgaltatas.tranzakcioTorles(TranzakcioMapper.toVo(ATranzakcioi.get(0)));
		} catch (NullPointerException npe) {
			Assert.fail();
		}
		
		TranzakcioVo tranz = new TranzakcioVo();
		
		tranzakcioSzolgaltatas.tranzakcioTorles(tranz);
	}
	
	@Test
	public void _5felhasznaloLegkorabbiTranzakciojaTeszt(){
		
		TranzakcioVo legkorabbi = tranzakcioSzolgaltatas.felhasznaloLegkorabbiTranzakcioja(FelhasznaloMapper.toVo(AFelhasznalo));
		
		Assert.assertNotNull(legkorabbi);
		Assert.assertNotNull(legkorabbi.getId());
		Assert.assertEquals(new Long(8L), legkorabbi.getId());
	}
	
	@Test
	public void _6felhasznaloLekotesiTranzakciojaTeszt(){
		
		TranzakcioVo lekottranz = tranzakcioSzolgaltatas.felhasznaloLekotesiTranzakcioja(FelhasznaloMapper.toVo(BFelhasznalo));
		
		Assert.assertNotNull(lekottranz);
		Assert.assertEquals(new Long(12L), lekottranz.getId());
		
		BLekotese.setTeljesitett(true);
		
		lekottranz = tranzakcioSzolgaltatas.felhasznaloLekotesiTranzakcioja(FelhasznaloMapper.toVo(BFelhasznalo));
		
		Assert.assertNull(lekottranz);
		
	}
}
