package com.samuexx.cobrancaSS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.samuexx.cobrancaSS.model.StatusTitulo;
import com.samuexx.cobrancaSS.model.Titulo;
import com.samuexx.cobrancaSS.repository.Titulos;
import com.samuexx.cobrancaSS.repository.filter.TituloFilter;

@Service
public class CadastroTituloService {

	@Autowired
	private Titulos titulos;

	public void salvar(Titulo titulo) {
		
		try {
			titulos.save(titulo);
		} catch (DataIntegrityViolationException e) {
			throw new IllegalArgumentException("Formato de data invalido!");

		}
	}
	
	public void excluir(Long codigo){
		titulos.delete(codigo);
	}

	public String receber(Long codigo) {
		Titulo titulo = titulos.findOne(codigo);
		titulo.setStatus(StatusTitulo.RECEBIDO);
		titulos.save(titulo);
		
		return StatusTitulo.RECEBIDO.getDescricao();
	}
	
	public List<Titulo> filtrar(TituloFilter filtro){
		String descricao = filtro.getDescricao() == null ? "%" : filtro.getDescricao();		
		return titulos.findByDescricaoContainingOrderByCodigoDesc(descricao);
		
	}


}
