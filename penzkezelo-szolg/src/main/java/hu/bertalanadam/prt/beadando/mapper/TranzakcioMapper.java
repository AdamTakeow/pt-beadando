package hu.bertalanadam.prt.beadando.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

/**
 * Átalakítja a {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio} adatbázis
 * réteg-beli objektumot a szolgáltatás rétegben használt ennek megfelelő 
 * {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo TranzakcioVo} objektummá, 
 * vagy éppen fordítva, ha arra van szükség.
 * Az átalakítást az Apache License v2.0 alatt terjesztett Model Mapper 
 * segítségével hajtjuk végre.
 * */
public class TranzakcioMapper {
	
	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(FelhasznaloMapper.class);

	/**
	 * A mapper objektum ami a leképezést végzi.
	 * */
	private static ModelMapper mapper = new ModelMapper();
	
	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio}
	 * objektumot a magasabb rétegben használt {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo TranzakcioVo}
	 * objektummá.
	 * @param tranzakcioDto Az átalakítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio}.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo TranzakcioVo} objektum.
	 */
	public static TranzakcioVo toVo(Tranzakcio tranzakcioDto) {
		if (tranzakcioDto == null) {
			logolo.warn("TranzakcióDto null!");
			return null;
		}
		return mapper.map(tranzakcioDto, TranzakcioVo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo TranzakcioVo} objektumot az alacsonyabb
	 * rétegben használt {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio} objektummá.
	 * @param tranzakcioVo Az átalakítandó {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo TranzakcioVo} objektum.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio} objektum.
	 */
	public static Tranzakcio toDto(TranzakcioVo tranzakcioVo) {
		if (tranzakcioVo == null) {
			logolo.warn("TranzakcióVo null!");
			return null;
		}
		return mapper.map(tranzakcioVo, Tranzakcio.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio} objektumokat tartalmazó
	 * Listát a {@link #toVo(Tranzakcio) toVo} segítségével {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo TranzakcioVo} objektumokat
	 * tartalmazó listává.
	 * @param tranzakcioDtos A {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio} objektumokat tartalmazó 
	 * {@link java.util.List List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo TranzakcioVo} objektumokat tartalmazó
	 * {@link java.util.List List} típusú lista.
	 */
	public static List<TranzakcioVo> toVo(List<Tranzakcio> tranzakcioDtos) {
		List<TranzakcioVo> tranzakcioVos = new ArrayList<>();
		for (Tranzakcio tranzakcioDto : tranzakcioDtos) {
			tranzakcioVos.add(toVo(tranzakcioDto));
		}
		return tranzakcioVos;
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo TranzakcioVo} objektumokat tartalmazó
	 * Listát a {@link #toDto(TranzakcioVo) toDto} segítségével {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio} objektumokat
	 * tartalmazó listává.
	 * @param tranzakcioVos A {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo TranzakcioVo} objektumokat tartalmazó 
	 * {@link java.util.List List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio Tranzakcio} objektumokat tartalmazó
	 * {@link java.util.List List} típusú lista.
	 */
	public static List<Tranzakcio> toDto(List<TranzakcioVo> tranzakcioVos) {
		List<Tranzakcio> tranzakcioDtos = new ArrayList<>();
		for (TranzakcioVo tranzakcioVo : tranzakcioVos) {
			tranzakcioDtos.add(toDto(tranzakcioVo));
		}
		return tranzakcioDtos;
	}
}
