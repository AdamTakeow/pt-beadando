package hu.bertalanadam.prt.beadando.mapper;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;

import hu.bertalanadam.prt.beadando.db.entitas.Kategoria;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;

/**
 *
 */
public class KategoriaMapper {

	/**
	 * A mapper objektum ami a leképezést végzi.
	 * */
	private static ModelMapper mapper = new ModelMapper();
	
	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria}
	 * objektumot a magasabb rétegben használt {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo}
	 * objektummá.
	 * @param kategoriaDto Az átalakítandó {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria}.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo} objektum.
	 */
	public static KategoriaVo toVo(Kategoria kategoriaDto) {
		if (kategoriaDto == null) {
			return null;
		}
		return mapper.map(kategoriaDto, KategoriaVo.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo} objektumot az alacsonyabb
	 * rétegben használt {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria} objektummá.
	 * @param kategoriaVo Az átalakítando {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo} objektum.
	 * @return Az átalakított immár {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria} objektum.
	 */
	public static Kategoria toDto(KategoriaVo kategoriaVo) {
		if (kategoriaVo == null) {
			return null;
		}
		return mapper.map(kategoriaVo, Kategoria.class);
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria} objektumokat tartalmazó
	 * Listát a {@link #toVo(Kategoria) toVo} segítségével {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo} objektumokat
	 * tartalmazó listává.
	 * @param kategoriaDtos A {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria} objektumokat tartalmazó 
	 * {@link java.util.List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo} objektumokat tartalmazó {@link java.util.List} típusú lista.
	 */
	public static List<KategoriaVo> toVo(List<Kategoria> kategoriaDtos) {
		List<KategoriaVo> kategoriaVos = new ArrayList<>();
		for (Kategoria kategoriaDto : kategoriaDtos) {
			kategoriaVos.add(toVo(kategoriaDto));
		}
		return kategoriaVos;
	}

	/**
	 * Átalakít egy {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo} objektumokat tartalmazó
	 * Listát a {@link #toDto(KategoriaVo) toDto} segítségével {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria} objektumokat
	 * tartalmazó listává.
	 * @param kategoriaVos A {@link hu.bertalanadam.prt.beadando.vo.KategoriaVo} objektumokat tartalmazó 
	 * {@link java.util.List} típusú lista.
	 * @return Az átalakított {@link hu.bertalanadam.prt.beadando.db.entitas.Kategoria} objektumokat tartalmazó {@link java.util.List} típusú lista.
	 */
	public static List<Kategoria> toDto(List<KategoriaVo> kategoriaVos) {
		List<Kategoria> kategoriaDtos = new ArrayList<>();
		for (KategoriaVo kategoriaVo : kategoriaVos) {
			kategoriaDtos.add(toDto(kategoriaVo));
		}
		return kategoriaDtos;
	}
}
