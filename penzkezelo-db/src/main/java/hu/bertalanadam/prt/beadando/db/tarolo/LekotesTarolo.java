package hu.bertalanadam.prt.beadando.db.tarolo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import hu.bertalanadam.prt.beadando.db.entitas.Lekotes;

/**
 * A lekötésekhez tartozó DAO aminek segítségével műveleteket hajthatunk végre a lekötéseken.
 * Ez az osztály egy DAO amit a {@link org.springframework.stereotype.Repository Repository} annotációval jelzünk.
 * Ez az osztály a {@link org.springframework.data.jpa.repository.JpaRepository JpaRepository} leszármazottja,
 * ezáltal az alapvető CRUD műveletek előre definiáltak.
 * */
@Repository
public interface LekotesTarolo extends JpaRepository<Lekotes, Long> {

}
