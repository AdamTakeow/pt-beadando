package hu.bertalanadam.prt.beadando.szolgaltatas;

import java.util.List;

import hu.bertalanadam.prt.beadando.vo.KategoriaVo;

public interface KategoriaSzolgaltatas {
	
	void ujKategoriaLetrehozas( KategoriaVo ujKategoria );
	
	KategoriaVo getKategoriaByNev( String kategoriaNev );
	
	List<KategoriaVo> getAllKategoria();

}
