package com.javaajax.controller;

import java.time.LocalDate;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.javaajax.domain.Promocao;
import com.javaajax.repository.CategoriaRepository;
import com.javaajax.repository.PromocaoRepository;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {
	
	private static Logger log = (Logger) LoggerFactory.getLogger(PromocaoController.class);
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private PromocaoRepository promocaoRepository;
	
	@GetMapping("/add")
	public String abrirCadastro(ModelMap model) {
		model.addAttribute("categorias", categoriaRepository.findAll());
		return "promo-add";
	}
	
	@PostMapping("/save")
	public ResponseEntity<Promocao> salvarPromocao(Promocao promocao) {
		log.info("Promoção {}", promocao.toString());
		promocao.setDataCadastro(LocalDate.now());
		promocaoRepository.save(promocao);
		return ResponseEntity.ok().build();
	}

}
