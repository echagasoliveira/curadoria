package br.com.curadoria.core.ports.repositories;

import br.com.curadoria.core.entities.RegimeAlimentacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RegimeAlimentacaoRepository extends JpaRepository<RegimeAlimentacao, Integer> {
	List<RegimeAlimentacao> findAll();
}
