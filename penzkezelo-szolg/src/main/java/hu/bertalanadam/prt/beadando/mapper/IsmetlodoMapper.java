package hu.bertalanadam.prt.beadando.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo;
import hu.bertalanadam.prt.beadando.vo.FelhasznaloVo;
import hu.bertalanadam.prt.beadando.vo.IsmetlodoVo;

public class IsmetlodoMapper {

	/**
	 * A mapper objektum ami a leképezést végzi.
	 * */
	private static ModelMapper mapper = new ModelMapper();
	
	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo Ismetlodo}
	 * objektumot a magasabb rétegben használt {@link hu.bertalanadam.prt.beadando.vo.IsmetlodoVo IsmetlodoVo}
	 * objektummá.
	 * @param ismetlodoDto Az átalakítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo Ismetlodo}.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.vo.IsmetlodoVo IsmetlodoVo} objektum.
	 */
	public static IsmetlodoVo toVo(Ismetlodo ismetlodoDto) {
		if (ismetlodoDto == null) {
			return null;
		}
		return mapper.map(ismetlodoDto, IsmetlodoVo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.IsmetlodoVo IsmetlodoVo} objektumot az alacsonyabb
	 * rétegben használt {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo Ismetlodo} objektummá.
	 * @param ismetlodoVo Az átalakítando {@link hu.bertalanadam.prt.beadando.vo.IsmetlodoVo IsmetlodoVo} objektum.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo Ismetlodo} objektum.
	 */
	public static Ismetlodo toDto(IsmetlodoVo ismetlodoVo) {
		if (ismetlodoVo == null) {
			return null;
		}
		return mapper.map(ismetlodoVo, Ismetlodo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo Ismetlodo} objektumokat tartalmazó
	 * Listát a {@link #toVo(Ismetlodo) toVo} segítségével {@link hu.bertalanadam.prt.beadando.vo.IsmetlodoVo IsmetlodoVo} objektumokat
	 * tartalmazó listává.
	 * @param ismetlodoDtos A {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo Ismetlodo} objektumokat tartalmazó 
	 * {@link java.util.List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.vo.IsmetlodoVo IsmetlodoVo} objektumokat tartalmazó {@link java.util.List} típusú lista.
	 */
	public static List<IsmetlodoVo> toVo(List<Ismetlodo> ismetlodoDtos) {
		List<IsmetlodoVo> ismetlodoVos = new ArrayList<>();
		for (Ismetlodo ismetlodoDto : ismetlodoDtos) {
			ismetlodoVos.add(toVo(ismetlodoDto));
		}
		return ismetlodoVos;
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.IsmetlodoVo IsmetlodoVo} objektumokat tartalmazó
	 * Listát a {@link #toDto(IsmetlodoVo) toDto} segítségével {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo} objektumokat
	 * tartalmazó listává.
	 * @param ismetlodoDtos A {@link hu.bertalanadam.prt.beadando.vo.IsmetlodoVo IsmetlodoVo} objektumokat tartalmazó 
	 * {@link java.util.List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.db.entitas.Ismetlodo Ismetlodo} objektumokat tartalmazó {@link java.util.List} típusú lista.
	 */
	public static List<Ismetlodo> toDto(List<IsmetlodoVo> ismetlodoVos) {
		List<Ismetlodo> ismetlodoDtos = new ArrayList<>();
		for (IsmetlodoVo ismetlodoVo : ismetlodoVos) {
			ismetlodoDtos.add(toDto(ismetlodoVo));
		}
		return ismetlodoDtos;
	}
}
