package com.samuexx.cobrancaSS.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.samuexx.cobrancaSS.model.StatusTitulo;
import com.samuexx.cobrancaSS.model.Titulo;
import com.samuexx.cobrancaSS.repository.Titulos;
import com.samuexx.cobrancaSS.repository.filter.TituloFilter;
import com.samuexx.cobrancaSS.service.CadastroTituloService;

@Controller
@RequestMapping("/titulos")
public class TituloController {

	private static final String CADASTRO_VIEW = "CadastroTitulo";
	
	@Autowired
	private CadastroTituloService cadastroTituloService;

	@Autowired
	private Titulos titulos;
	
	@RequestMapping("/novo")
	public ModelAndView novo() {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW);
		mv.addObject(new Titulo());
		return mv;
	}

	@RequestMapping(method = RequestMethod.POST)
	public String salvar(@Validated Titulo titulo, Errors errors, RedirectAttributes attributes) {
		if(errors.hasErrors()){
			return CADASTRO_VIEW;
		}
		
		try{
			cadastroTituloService.salvar(titulo);
			attributes.addFlashAttribute("mensagem", "Titulo Salvo com sucesso!!!!");
				return "redirect:/titulos/novo";
		}catch(IllegalArgumentException e){
			errors.rejectValue("dataVencimento", null, e.getMessage());
				return CADASTRO_VIEW;
		}
	}

	@ModelAttribute("allStatusTitulos")
	public List<StatusTitulo> allStatusTitulo() {
		return Arrays.asList(StatusTitulo.values());
	}
	
	@RequestMapping("{codigo}")
	public ModelAndView edicao(@PathVariable("codigo") Titulo titulo) {
		ModelAndView mv = new ModelAndView(CADASTRO_VIEW); 
		mv.addObject(titulo);
		return mv;
	}
	
	
	@RequestMapping(value = "/{codigo}/receber", method = RequestMethod.PUT)
	public @ResponseBody String receber(@PathVariable Long codigo){
		
		return cadastroTituloService.receber(codigo);
	
	}
	
	
	@RequestMapping(value ="{codigo}",method = RequestMethod.DELETE)
	public String excluir(@PathVariable Long codigo,RedirectAttributes attributes){
		cadastroTituloService.excluir(codigo);
		
		attributes.addFlashAttribute("mensagem","Titulo excluído com sucesso");
		
		return "redirect:/titulos";	
	}
	
	@RequestMapping
	public ModelAndView pesquisa(@ModelAttribute("filtro") TituloFilter filtro){
		
		List<Titulo> todosTitulos = cadastroTituloService.filtrar(filtro);
		
		ModelAndView mv = new ModelAndView("PesquisaTitulos");
		mv.addObject("allTitulos",todosTitulos);
		return mv;
	}
	

	
}