/**
 * Ebben a csomagban vannak azok az Entitások amelyek reprezentálják az 
 * adatbázis elemeket.
 * Tartalmaz egy {@link hu.bertalanadam.prt.beadando.db.entitas.FoEntitas} osztályt amely egy közös ősként funkcionál
 * a többi entitás számára. Ezt a megkülönböztetést {@link javax.persistence.MappedSuperclass} annotáció jelzi.
 * Az egyes osztályok az adatbázisban tárolt táblákat reprezentálják, az osztályok adattagjai pedig
 * a megfelelő táblában lévő oszlopokat. A táblák közti kapcsolatot a Hibernate annotációk segítségével
 * definiálom.
 */
package hu.bertalanadam.prt.beadando.db.entitas;