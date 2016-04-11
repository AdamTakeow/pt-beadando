package hu.bertalanadam.prt.beadando.db.tarolo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hu.bertalanadam.prt.beadando.db.entitas.Tranzakcio;

@Repository
@Transactional(propagation = Propagation.SUPPORTS)
public interface TranzakcioTarolo extends JpaRepository<Tranzakcio, Long> {

}
