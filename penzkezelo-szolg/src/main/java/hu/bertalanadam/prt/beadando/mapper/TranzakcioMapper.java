package hu.bertalanadam.prt.beadando.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo;
import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;
import hu.bertalanadam.prt.beadando.vo.TranzakcioVo;

public class TranzakcioMapper {

	/**
	 * A mapper objektum ami a leképezést végzi.
	 * */
	private static ModelMapper mapper = new ModelMapper();
	
	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio}
	 * objektumot a magasabb rétegben használt {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo}
	 * objektummá.
	 * @param tranzakcioDto Az átalakítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio}.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo} objektum.
	 */
	public static TranzakcioVo toVo(Tranzakcio tranzakcioDto) {
		if (tranzakcioDto == null) {
			return null;
		}
		return mapper.map(tranzakcioDto, TranzakcioVo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo} objektumot az alacsonyabb
	 * rétegben használt {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio} objektummá.
	 * @param tranzakcioVo Az átalakítando {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo} objektum.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio} objektum.
	 */
	public static Tranzakcio toDto(TranzakcioVo tranzakcioVo) {
		if (tranzakcioVo == null) {
			return null;
		}
		return mapper.map(tranzakcioVo, Tranzakcio.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} objektumokat tartalmazó
	 * Listát a {@link #toVo(Felhasznalo) toVo} segítségével {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektumokat
	 * tartalmazó listává.
	 * @param felhasznaloDtos A {@link hu.bertalanadam.prt.beadando.db.entitas.Felhasznalo} objektumokat tartalmazó 
	 * {@link java.util.List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.vo.FelhasznaloVo} objektumokat tartalmazó {@link java.util.List} típusú lista.
	 */
	public static List<TranzakcioVo> toVo(List<Tranzakcio> tranzakcioDtos) {
		List<TranzakcioVo> tranzakcioVos = new ArrayList<>();
		for (Tranzakcio tranzakcioDto : tranzakcioDtos) {
			tranzakcioVos.add(toVo(tranzakcioDto));
		}
		return tranzakcioVos;
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo} objektumokat tartalmazó
	 * Listát a {@link #toDto(TranzakcioVo) toDto} segítségével {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio} objektumokat
	 * tartalmazó listává.
	 * @param tranzakcioDtos A {@link hu.bertalanadam.prt.beadando.vo.TranzakcioVo} objektumokat tartalmazó 
	 * {@link java.util.List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio} objektumokat tartalmazó {@link java.util.List} típusú lista.
	 */
	public static List<Tranzakcio> toDto(List<TranzakcioVo> tranzakcioVos) {
		List<Tranzakcio> tranzakcioDtos = new ArrayList<>();
		for (TranzakcioVo tranzakcioVo : tranzakcioVos) {
			tranzakcioDtos.add(toDto(tranzakcioVo));
		}
		return tranzakcioDtos;
	}
}
