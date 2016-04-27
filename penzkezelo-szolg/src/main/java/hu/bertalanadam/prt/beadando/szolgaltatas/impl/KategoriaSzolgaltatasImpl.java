package hu.bertalanadam.prt.beadando.szolgaltatas.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Kategoria;
import hu.bertalanadam.prt.beadando.db.tarolo.KategoriaTarolo;
import hu.bertalanadam.prt.beadando.mapper.KategoriaMapper;
import hu.bertalanadam.prt.beadando.szolgaltatas.KategoriaSzolgaltatas;
import hu.bertalanadam.prt.beadando.vo.KategoriaVo;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public class KategoriaSzolgaltatasImpl implements KategoriaSzolgaltatas {
	
	@Autowired
	KategoriaTarolo kategoriaTarolo;

	@Override
	public void ujKategoriaLetrehozas(KategoriaVo ujKategoria) {
		// TODO nem csak ennyi
		Kategoria uj = KategoriaMapper.toDto(ujKategoria);
		kategoriaTarolo.save(uj);

	}

	@Override
	public KategoriaVo getKategoriaByNev(String kategoriaNev) {
		Kategoria found = kategoriaTarolo.findByNev(kategoriaNev);
		return KategoriaMapper.toVo(found);
	}

	@Override
	public List<KategoriaVo> getAllKategoria() {
		List<Kategoria> kategoriak = kategoriaTarolo.findAll();
		return KategoriaMapper.toVo(kategoriak);
	}

}
