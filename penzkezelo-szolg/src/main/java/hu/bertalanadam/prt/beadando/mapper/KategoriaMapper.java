package hu.bertalanadam.prt.beadando.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.bertalanadam.prt.beadando.db.entitas.Kategoria;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;

/**
 * Átalakítja a {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria} adatbázis
 * réteg-beli objektumot a szolgáltatás rétegben használt ennek megfelelő 
 * {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo} objektummá, 
 * vagy éppen fordítva, ha arra van szükség.
 * Az átalakítást az Apache License v2.0 alatt terjesztett Model Mapper 
 * segítségével hajtjuk végre.
 * */
public class KategoriaMapper {

	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(FelhasznaloMapper.class);
	
	/**
	 * A mapper objektum ami a leképezést végzi.
	 * */
	private static ModelMapper mapper = new ModelMapper();
	
	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria}
	 * objektumot a magasabb rétegben használt {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo}
	 * objektummá.
	 * @param kategoriaDto Az átalakítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria}.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo} objektum.
	 */
	public static KategoriaVo toVo(Kategoria kategoriaDto) {
		if (kategoriaDto == null) {
			logolo.warn("KategóriaDto null!");
			return null;
		}
		return mapper.map(kategoriaDto, KategoriaVo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo} objektumot az alacsonyabb
	 * rétegben használt {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria} objektummá.
	 * @param kategoriaVo Az átalakítando {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo} objektum.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria} objektum.
	 */
	public static Kategoria toDto(KategoriaVo kategoriaVo) {
		if (kategoriaVo == null) {
			logolo.warn("KategóriaVo null!");
			return null;
		}
		return mapper.map(kategoriaVo, Kategoria.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria} objektumokat tartalmazó
	 * Listát a {@link #toVo(Kategoria) toVo} segítségével {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo} objektumokat
	 * tartalmazó listává.
	 * @param kategoriaDtos A {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria} objektumokat tartalmazó 
	 * {@link java.util.List List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo} 
	 * objektumokat tartalmazó {@link java.util.List List} típusú lista.
	 */
	public static List<KategoriaVo> toVo(List<Kategoria> kategoriaDtos) {
		List<KategoriaVo> kategoriaVos = new ArrayList<>();
		for (Kategoria kategoriaDto : kategoriaDtos) {
			kategoriaVos.add(toVo(kategoriaDto));
		}
		return kategoriaVos;
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo} objektumokat tartalmazó
	 * Listát a {@link #toDto(KategoriaVo) toDto} segítségével {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria} objektumokat
	 * tartalmazó listává.
	 * @param kategoriaVos A {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo KategoriaVo} objektumokat tartalmazó 
	 * {@link java.util.List List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria Kategoria} objektumokat tartalmazó {@link java.util.List List} típusú lista.
	 */
	public static List<Kategoria> toDto(List<KategoriaVo> kategoriaVos) {
		List<Kategoria> kategoriaDtos = new ArrayList<>();
		for (KategoriaVo kategoriaVo : kategoriaVos) {
			kategoriaDtos.add(toDto(kategoriaVo));
		}
		return kategoriaDtos;
	}
}
