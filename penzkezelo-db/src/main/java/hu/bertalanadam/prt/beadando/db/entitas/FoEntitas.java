package hu.bertalanadam.prt.beadando.db.entitas;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.SequenceGenerator;

/**
 * Közös ősként szolgál a többi entitás számára.
 * Az ebben a csomagban lévő tovább entitások mindegyike ebből az entitásból származik, ezáltal
 * mindegyik rendelkezni fog azokkal az adattagokkal amik itt szerepelnek.
 * A közös ős jelölését a {@link javax.persistence.MappedSuperclass} annotáció jelöli.
 * */
@MappedSuperclass
public class FoEntitas implements Serializable{

	/**
	 * Alapértelmezett szerializációs azonosító.
	 * */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Egyedi {@link java.lang.Long} típusú azonosító.
	 * Az adatbázisban a megfelelő típusként fog megjelenni a {@link javax.persistence.GeneratedValue}
	 * annotációnak köszönhetően.
	 * */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	
	/**
	 * Visszaadja az id-t.
	 * @return Az adott entitás id-ja.
	 */
	public Long getId() {
		return id;
	}

	
	/**
	 * Beállítja az id-t.
	 * @param id A beállítandó id.
	 */
	public void setId(Long id) {
		this.id = id;
	}
}
