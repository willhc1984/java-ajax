package com.javaajax.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
	public ResponseEntity<?> salvarPromocao(@Valid Promocao promocao, BindingResult result) {
		log.info("Promoção {}", promocao.toString());
		
		if(result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for(FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			System.out.println(errors);
			return ResponseEntity.unprocessableEntity().body(errors);
		}
		
		promocao.setDataCadastro(LocalDate.now());
		promocaoRepository.save(promocao);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/list")
	public String listarOfertas(ModelMap model) {
		Sort sort = Sort.by(Sort.Direction.DESC, "dataCadastro");
		PageRequest pageRequest = PageRequest.of(0, 8, sort);
		model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));
		return "promo-list";
	}

}
