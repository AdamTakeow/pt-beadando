package hu.bertalanadam.prt.beadando.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hu.bertalanadam.prt.beadando.db.entitas.Lekotes;
import hu.bertalanadam.prt.beadando.vo.LekotesVo;

/**
 * Átalakítja a {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes} adatbázis
 * réteg-beli objektumot a szolgáltatás rétegben használt ennek megfelelő 
 * {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo} objektummá, 
 * vagy éppen fordítva, ha arra van szükség.
 * Az átalakítást az Apache License v2.0 alatt terjesztett Model Mapper 
 * segítségével hajtjuk végre.
 * */
public class LekotesMapper {
	
	/**
	 * A logoláshoz szükséges {@link org.slf4j.Logger Logger}.
	 */
	private static Logger logolo = LoggerFactory.getLogger(FelhasznaloMapper.class);

	/**
	 * A mapper objektum ami a leképezést végzi.
	 * */
	private static ModelMapper mapper = new ModelMapper();
	
	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes}
	 * objektumot a magasabb rétegben használt {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo}
	 * objektummá.
	 * @param lekotesDto Az átalakítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes}.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo} objektum.
	 */
	public static LekotesVo toVo(Lekotes lekotesDto) {
		if (lekotesDto == null) {
			logolo.warn("LekötésDto null!");
			return null;
		}
		return mapper.map(lekotesDto, LekotesVo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo} objektumot az alacsonyabb
	 * rétegben használt {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes} objektummá.
	 * @param lekotesVo Az átalakítandó {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo} objektum.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes} objektum.
	 */
	public static Lekotes toDto(LekotesVo lekotesVo) {
		if (lekotesVo == null) {
			logolo.warn("LekötésVo null!");
			return null;
		}
		return mapper.map(lekotesVo, Lekotes.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes} objektumokat tartalmazó
	 * Listát a {@link #toVo(Lekotes) toVo} segítségével {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo} objektumokat
	 * tartalmazó listává.
	 * @param lekotesDtos A {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes} objektumokat tartalmazó 
	 * {@link java.util.List List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo} objektumokat tartalmazó {@link java.util.List List} típusú lista.
	 */
	public static List<LekotesVo> toVo(List<Lekotes> lekotesDtos) {
		List<LekotesVo> lekotesVos = new ArrayList<>();
		for (Lekotes lekotesDto : lekotesDtos) {
			lekotesVos.add(toVo(lekotesDto));
		}
		return lekotesVos;
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo} objektumokat tartalmazó
	 * Listát a {@link #toDto(LekotesVo) toDto} segítségével {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes} objektumokat
	 * tartalmazó listává.
	 * @param lekotesVos A {@link hu.bertalanadam.prt.beadando.vo.LekotesVo LekotesVo} objektumokat tartalmazó 
	 * {@link java.util.List List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.db.entitas.Lekotes Lekotes} objektumokat tartalmazó {@link java.util.List List} típusú lista.
	 */
	public static List<Lekotes> toDto(List<LekotesVo> lekotesVos) {
		List<Lekotes> lekotesDtos = new ArrayList<>();
		for (LekotesVo lekotesVo : lekotesVos) {
			lekotesDtos.add(toDto(lekotesVo));
		}
		return lekotesDtos;
	}
}
