package br.com.beecoffee.twgerenciadortarefas.controllers;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.beecoffee.twgerenciadortarefas.modelos.Tarefa;
import br.com.beecoffee.twgerenciadortarefas.repositorios.RepositorioTarefa;

@Controller
@RequestMapping("/tarefas")
public class TarefasController {

	@Autowired
	private RepositorioTarefa repositorioTarefa; // injeta a interface

	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView mv = new ModelAndView(); // implementa o metodo
		mv.setViewName("tarefas/listar"); // define a view a ser utilizada
		mv.addObject("tarefas", repositorioTarefa.findAll()); // estancia o objeto

		return mv;
	}

	// exibe a view
	@GetMapping("/inserir")
	public ModelAndView inserir() {
		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/inserir");
		mv.addObject("tarefa", new Tarefa());

		return mv;
	}

	// captura o post e seta no BD
	@PostMapping("/inserir")
	public ModelAndView inserir(@Valid Tarefa tarefa, BindingResult result) {
		ModelAndView mv = new ModelAndView();

		// valida a data
		if (tarefa.getDataExpiracao() == null) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida", "A data de expiração é obrigatória");
		} else if (tarefa.getDataExpiracao().before(new Date())) {
			result.rejectValue("dataExpiracao", "tarefa.dataExpiracaoInvalida",
					"A data de expiração não pode ser anterior a data atual");
		}

		if (result.hasErrors()) {
			mv.setViewName("tarefas/inserir");
			mv.addObject(tarefa);
		} else {
			repositorioTarefa.save(tarefa); // seta o valor no bd
			mv.setViewName("tarefas/listar"); // define o template
			mv.addObject("tarefas", repositorioTarefa.findAll());
		}

		return mv;
	}
	
	// View para Edição da tarefa
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable("id") Long id) {
		Tarefa tarefa = repositorioTarefa.getOne(id); // get pelo ID

		ModelAndView mv = new ModelAndView();
		mv.setViewName("tarefas/inserir");
		mv.addObject("tarefa", tarefa); // adiciona o objeto a view

		return mv;
	}

}
