package br.com.curadoria.core.ports.repositories;

import br.com.curadoria.core.entities.PalavraChave;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PalavraChaveRepository extends JpaRepository<PalavraChave, Integer> {
    List<PalavraChave> findAll();
}
