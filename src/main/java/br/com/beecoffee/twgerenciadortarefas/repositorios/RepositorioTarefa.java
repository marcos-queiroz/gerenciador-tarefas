package br.com.beecoffee.twgerenciadortarefas.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.beecoffee.twgerenciadortarefas.modelos.Tarefa;

@Repository
public interface RepositorioTarefa extends JpaRepository<Tarefa, Long> {
	
}
