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
import hu.bertalanadam.prt.beadando.szolgaltatas.impl.TranzakcioSzolgaltatasImpl;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.IsmetlodoVo;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

@RunWith(MockitoJUnitRunner.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class IsmetlodoSzolgaltatasTest {
	
	@Mock
	private IsmetlodoTarolo ismetlodoTarolo;
	
	@Mock
	private KategoriaTarolo kategoriaTarolo;
	
	@Mock
	private TranzakcioTarolo tranzakcioTarolo;
	
	@Mock
	private FelhasznaloTarolo felhasznaloTarolo;
	
	@Spy
	@InjectMocks
	private IsmetlodoSzolgaltatasImpl ismetlodoSzolgaltatas;
	
	@Spy
	@InjectMocks
	private KategoriaSzolgaltatasImpl kategoriaSzolgaltatas;
	
	@Spy
	@InjectMocks
	private TranzakcioSzolgaltatasImpl tranzakcioSzolgaltatas;
	
	@Spy
	@InjectMocks
	private FelhasznaloSzolgaltatasImpl felhasznaloSzolgaltatas;
	
	private static Felhasznalo tesztFelhasznalo;
	private static Ismetlodo tesztIsmetlodo;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		// létrehozom az "adatbázist"

		// egy felhasználó
		Felhasznalo ujFelhasznalo = new Felhasznalo();
		ujFelhasznalo.setId(1L);
		ujFelhasznalo.setFelhasznalonev("TesztFelhasznalo");
		ujFelhasznalo.setJelszo("tesztJelszo");
		ujFelhasznalo.setKategoriak(new ArrayList<>());
		ujFelhasznalo.setTranzakciok(new ArrayList<>());
		ujFelhasznalo.setKezdoIdopont(LocalDate.now().minusYears(2L));
		ujFelhasznalo.setVegIdopont(LocalDate.now());
		
		tesztFelhasznalo = ujFelhasznalo;
				
		// egy kategória
		Kategoria ujKategoria = new Kategoria();
		ujKategoria.setFelhasznalok(new ArrayList<>(Arrays.asList(tesztFelhasznalo)));
		ujKategoria.setId(2L);
		ujKategoria.setNev("TesztKategoria1");
				
		// egy ismétlődő
		Ismetlodo ujIsmetlodo = new Ismetlodo();
		ujIsmetlodo.setId(3L);
		ujIsmetlodo.setIdo(1L);
		ujIsmetlodo.setUtolsoBeszuras(LocalDate.now().minusDays(1L));
		
		tesztIsmetlodo = ujIsmetlodo;
				
		// a felhasználónak egy ismétlődős tranzakció
		Tranzakcio ujTranzakcio = new Tranzakcio();
		ujTranzakcio.setDatum(LocalDate.now().minusDays(2L));
		ujTranzakcio.setFelhasznalo(tesztFelhasznalo);
		ujTranzakcio.setId(4L);
		ujTranzakcio.setKategoria(ujKategoria);
		ujTranzakcio.setLeiras("Tranzakcio1 leirasa");
		ujTranzakcio.setOsszeg(500L);
		// ismétlődik
		ujTranzakcio.setIsmetlodo(tesztIsmetlodo);
				
		tesztFelhasznalo.getTranzakciok().add(ujTranzakcio);
	}
	
	@Before
	public void initMocks(){
		MockitoAnnotations.initMocks(this);
		
		Mockito.when( ismetlodoTarolo.save(Mockito.any(Ismetlodo.class)))
		.thenAnswer(new Answer<Ismetlodo>() {
			@Override
			public Ismetlodo answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Ismetlodo)args[0]).getId() == null ){
					((Ismetlodo)args[0]).setId(5L);					
				}
				return (Ismetlodo)args[0];
			}
		});
		
		Mockito.when( tranzakcioTarolo.save(Mockito.any(Tranzakcio.class)))
		.thenAnswer(new Answer<Tranzakcio>() {
			@Override
			public Tranzakcio answer(InvocationOnMock invocation) throws Throwable {
				Object[] args = invocation.getArguments();
				if( ((Tranzakcio)args[0]).getId() == null ){
					((Tranzakcio)args[0]).setId(5L);					
				}
				return (Tranzakcio)args[0];
			}
		});
		
	}

	@Test
	public void _1letrehozIsmetlodotTeszt() {
		
		IsmetlodoVo uj = new IsmetlodoVo();
		uj.setIdo(5L);
		uj.setUtolsoBeszuras(LocalDate.now().minusDays(3L));
		
		IsmetlodoVo mentett = ismetlodoSzolgaltatas.letrehozIsmetlodot(uj);
		
		Assert.assertNotNull(mentett);
		Assert.assertNotNull(mentett.getId());
	}
	
	@Test
	public void _2frissitIsmetlodotTeszt() {
		
		IsmetlodoVo uj = new IsmetlodoVo();
		uj.setIdo(5L);
		uj.setUtolsoBeszuras(LocalDate.now());
		
		IsmetlodoVo mentett = ismetlodoSzolgaltatas.letrehozIsmetlodot(uj);
		
		mentett.setIdo(1L);
		mentett.setUtolsoBeszuras(LocalDate.now());
		
		IsmetlodoVo frissitett = ismetlodoSzolgaltatas.frissitIsmetlodot(mentett);
		
		Assert.assertEquals(new Long(1L), frissitett.getIdo());
		Assert.assertEquals(LocalDate.now(), frissitett.getUtolsoBeszuras());
	}
	
	@Test
	public void _3ismetlodoEllenorzesTeszt(){
		// átmappelem az objektumokat
		FelhasznaloVo felh = FelhasznaloMapper.toVo(tesztFelhasznalo);
		List<TranzakcioVo> felh_trzi = TranzakcioMapper.toVo( tesztFelhasznalo.getTranzakciok() );
		IsmetlodoVo ism = felh_trzi.get(0).getIsmetlodo();
		
		// nincs ismétlődő és nem csinál semmit
		
		// beállítom hogy az egyetlen ismétlődő utolsó beszúrása ma volt
		ism.setUtolsoBeszuras(LocalDate.now());
		ismetlodoSzolgaltatas.ismetlodoEllenorzes( felh, felh_trzi );
		
		Assert.assertEquals(LocalDate.now(), ism.getUtolsoBeszuras());
		// nem szúródik be új tranzackció
		Assert.assertEquals(1, felh.getTranzakciok().size()); 
		
		// van ismétlődő és be kell szúrni a tranzakciót

		// beállítom hogy az egyetlen ismétlődő utolsó beszúrása tegnap volt, tehát ma is be kell szúrni
		ism.setUtolsoBeszuras(LocalDate.now().minusDays(1L));
		
		ismetlodoSzolgaltatas.ismetlodoEllenorzes( felh, felh_trzi );
		
		Assert.assertEquals(LocalDate.now(), ism.getUtolsoBeszuras() );
		Assert.assertEquals(2, felh.getTranzakciok().size());
		
	}
}
