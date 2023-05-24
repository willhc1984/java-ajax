package com.javaajax.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javaajax.domain.Categoria;
import com.javaajax.repository.CategoriaRepository;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@GetMapping("/add")
	public String abrirCadastro(ModelMap model) {
		model.addAttribute("categorias", categoriaRepository.findAll());
		return "promo-add";
	}
	
	@PostMapping("/save")
	public String salvarPromocao(ModelMap model) {
		model.addAttribute("categorias", categoriaRepository.findAll());
		return "promo-add";
	}

}
