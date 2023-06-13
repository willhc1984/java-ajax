package com.javaajax.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.javaajax.domain.Promocao;
import com.javaajax.dto.PromocaoDto;
import com.javaajax.repository.CategoriaRepository;
import com.javaajax.repository.PromocaoRepository;
import com.javaajax.service.PromocaoDataTablesService;

import ch.qos.logback.classic.Logger;

@Controller
@RequestMapping("/promocao")
public class PromocaoController {
	
	private static Logger log = (Logger) LoggerFactory.getLogger(PromocaoController.class);
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	@Autowired
	private PromocaoRepository promocaoRepository;
	
	@GetMapping("/tabela")
	public String showTabela() {
		return "promo-datatables";
	}
	
	@GetMapping("/datatables/server")
	public ResponseEntity<?> datatables(HttpServletRequest request){
		Map<String, Object> data = new PromocaoDataTablesService().execute(promocaoRepository, request);
		System.out.println(data);
		return ResponseEntity.ok(data);
	}
	
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
	
	@GetMapping("/list/ajax")
	public String listarCard(@RequestParam(name = "page", defaultValue = "1") int page, 
							@RequestParam(name = "site", defaultValue = "") String site,
							ModelMap model) {
		
		Sort sort = Sort.by(Sort.Direction.DESC, "dataCadastro");
		PageRequest pageRequest = PageRequest.of(page, 8, sort);
		
		if(site.isEmpty()) {
			model.addAttribute("promocoes", promocaoRepository.findAll(pageRequest));
		}else {
			model.addAttribute("promocoes", promocaoRepository.findBySite(site, pageRequest));
		}
		
		return "promo-card";
	}
	
	@PostMapping("/like/{id}")
	public ResponseEntity<?> adicionarLikes(@PathVariable("id") Long id){
		promocaoRepository.updateSomarLikes(id);
		int likes = promocaoRepository.findLikesById(id);
		
		return ResponseEntity.ok(likes);
	}
	
	@GetMapping("/site")
	public ResponseEntity<?> autocompleteByTermo(@RequestParam("termo") String termo){
		List<String> sites = promocaoRepository.findSitesByTermo(termo);
		return ResponseEntity.ok(sites);
	}
	
	@GetMapping("/site/list")
	public String listarPorSite(@RequestParam("site") String site, ModelMap model) {
		Sort sort = Sort.by(Sort.Direction.DESC, "dataCadastro");
		PageRequest pageRequest = PageRequest.of(0, 8, sort);
		model.addAttribute("promocoes", promocaoRepository.findBySite(site, pageRequest));
		return "promo-card";
	}
	
	@GetMapping("/delete/{id}")
	public ResponseEntity<?> excluirPromocao(@PathVariable("id") Long id){
		promocaoRepository.deleteById(id);
		return ResponseEntity.ok().build();
	}
	
	@GetMapping("/edit/{id}")
	public ResponseEntity<?> preEditarPromocao(@PathVariable("id") Long id){
		Promocao promo = promocaoRepository.findById(id).get();
		return ResponseEntity.ok(promo);
	}
	
	@PostMapping("/edit")
	public ResponseEntity<?> editarPromocao(@Valid PromocaoDto dto, BindingResult result){
		
		if(result.hasErrors()) {
			Map<String, String> errors = new HashMap<>();
			for(FieldError error : result.getFieldErrors()) {
				errors.put(error.getField(), error.getDefaultMessage());
			}
			System.out.println(errors);
			return ResponseEntity.unprocessableEntity().body(errors);
		}
		
		Promocao promo = promocaoRepository.findById(dto.getId()).get();
		promo.setCategoria(dto.getCategoria());
		promo.setDescricao(dto.getDescricao());
		promo.setLinkImagem(dto.getLinkImagem());
		promo.setPreco(dto.getPreco());
		promo.setTitulo(dto.getTitulo());
		
		promocaoRepository.save(promo);
		
		return ResponseEntity.ok().build();
	}
	
}
