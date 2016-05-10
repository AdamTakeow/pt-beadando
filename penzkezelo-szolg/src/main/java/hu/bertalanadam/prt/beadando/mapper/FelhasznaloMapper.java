package hu.bertalanadam.prt.beadando.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;

/**
 * Átalakítja a {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo} adatbázis
 * réteg-beli objektumot a szolgáltatás rétegben használt ennek megfelelő 
 * {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektummá, 
 * vagy éppen fordítva, ha arra van szükség.
 * Az átalakítást az Apache License v2.0 alatt terjesztett Model Mapper 
 * segítségével hajtjuk végre.
 * */
public class FelhasznaloMapper {
	
	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(FelhasznaloMapper.class);
	
	/**
	 * A mapper objektum ami a leképezést végzi.
	 * */
	private static ModelMapper mapper = new ModelMapper();
	
	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo}
	 * objektumot a magasabb rétegben használt
	 * {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektummá.
	 * @param felhasznaloDto Az átalakítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo}.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektum.
	 */
	public static FelhasznaloVo toVo(Felhasznalo felhasznaloDto) {
		if (felhasznaloDto == null) {
			logolo.warn("FelhasználóDto null!");
			return null;
		}
		return mapper.map(felhasznaloDto, FelhasznaloVo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektumot az alacsonyabb
	 * rétegben használt {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo} objektummá.
	 * @param felhasznaloVo Az átalakítando {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektum.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo} objektum.
	 */
	public static Felhasznalo toDto(FelhasznaloVo felhasznaloVo) {
		if (felhasznaloVo == null) {
			logolo.warn("FelhasználóVo null!");
			return null;
		}
		return mapper.map(felhasznaloVo, Felhasznalo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo} objektumokat tartalmazó
	 * Listát a {@link #toVo(Felhasznalo) toVo} segítségével
	 * {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektumokat tartalmazó listává.
	 * @param felhasznaloDtos A {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo} objektumokat tartalmazó 
	 * {@link java.util.List List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektumokat tartalmazó {@link java.util.List List} típusú lista.
	 */
	public static List<FelhasznaloVo> toVo(List<Felhasznalo> felhasznaloDtos) {
		List<FelhasznaloVo> felhasznaloVos = new ArrayList<>();
		for (Felhasznalo felhasznaloDto : felhasznaloDtos) {
			felhasznaloVos.add(toVo(felhasznaloDto));
		}
		return felhasznaloVos;
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektumokat tartalmazó
	 * Listát a {@link #toDto(FelhasznaloVo) toDto} segítségével
	 * {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo} objektumokat
	 * tartalmazó listává.
	 * @param felhasznaloVos A {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo FelhasznaloVo} objektumokat tartalmazó 
	 * {@link java.util.List List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo Felhasznalo} objektumokat tartalmazó {@link java.util.List List} típusú lista.
	 */
	public static List<Felhasznalo> toDto(List<FelhasznaloVo> felhasznaloVos) {
		List<Felhasznalo> felhasznaloDtos = new ArrayList<>();
		for (FelhasznaloVo felhasznaloVo : felhasznaloVos) {
			felhasznaloDtos.add(toDto(felhasznaloVo));
		}
		return felhasznaloDtos;
	}

}
