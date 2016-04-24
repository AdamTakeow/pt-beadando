package hu.bertalanadam.prt.beadando.mapper;

import java.util.ArrayList;
import java.util.List;

import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;

/**
 * Átalakítja a {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} adatbázis
 * réteg-beli objektumot a szolgáltatás rétegben használt ennek megfelelő 
 * {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektummá, vagy éppen fordítva,
 * ha arra van szükség.
 * Az átalakítást a Dozer Bean Mapper segítségével hajtjuk végre.
 * */
public class FelhasznaloMapper {
	
	/**
	 * A mapper objektum ami a leképezést végzi.
	 * */
	private static Mapper mapper = new DozerBeanMapper();
	
	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo}
	 * objektumot a magasabb rétegben használt {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo}
	 * objektummá.
	 * @param felhasznaloDto Az átalakítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo}.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektum.
	 */
	public static FelhasznaloVo toVo(Felhasznalo felhasznaloDto) {
		if (felhasznaloDto == null) {
			return null;
		}
		return mapper.map(felhasznaloDto, FelhasznaloVo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektumot az alacsonyabb
	 * rétegben használt {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} objektummá.
	 * @param felhasznaloVo Az átalakítando {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektum.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} objektum.
	 */
	public static Felhasznalo toDto(FelhasznaloVo felhasznaloVo) {
		if (felhasznaloVo == null) {
			return null;
		}
		return mapper.map(felhasznaloVo, Felhasznalo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} objektumokat tartalmazó
	 * Listát a {@link #toVo(Felhasznalo) toVo} segítségével {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektumokat
	 * tartalmazó listává.
	 * @param felhasznaloDtos A {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} objektumokat tartalmazó 
	 * {@link java.util.List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektumokat tartalmazó {@link java.util.List} típusú lista.
	 */
	public static List<FelhasznaloVo> toVo(List<Felhasznalo> felhasznaloDtos) {
		List<FelhasznaloVo> felhasznaloVos = new ArrayList<>();
		for (Felhasznalo felhasznaloDto : felhasznaloDtos) {
			felhasznaloVos.add(toVo(felhasznaloDto));
		}
		return felhasznaloVos;
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektumokat tartalmazó
	 * Listát a {@link #toDto(FelhasznaloVo) toDto} segítségével {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} objektumokat
	 * tartalmazó listává.
	 * @param felhasznaloDtos A {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektumokat tartalmazó 
	 * {@link java.util.List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} objektumokat tartalmazó {@link java.util.List} típusú lista.
	 */
	public static List<Felhasznalo> toDto(List<FelhasznaloVo> felhasznaloVos) {
		List<Felhasznalo> felhasznaloDtos = new ArrayList<>();
		for (FelhasznaloVo felhasznaloVo : felhasznaloVos) {
			felhasznaloDtos.add(toDto(felhasznaloVo));
		}
		return felhasznaloDtos;
	}

}
