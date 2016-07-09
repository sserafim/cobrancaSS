package com.samuexx.cobrancaSS.testesBoleto;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import org.jrimum.bopepo.BancosSuportados;
import org.jrimum.bopepo.Boleto;
import org.jrimum.bopepo.view.BoletoViewer;
import org.jrimum.domkee.comum.pessoa.endereco.CEP;
import org.jrimum.domkee.comum.pessoa.endereco.Endereco;
import org.jrimum.domkee.comum.pessoa.endereco.UnidadeFederativa;
import org.jrimum.domkee.financeiro.banco.febraban.Agencia;
import org.jrimum.domkee.financeiro.banco.febraban.Carteira;
import org.jrimum.domkee.financeiro.banco.febraban.Cedente;
import org.jrimum.domkee.financeiro.banco.febraban.ContaBancaria;
import org.jrimum.domkee.financeiro.banco.febraban.NumeroDaConta;
import org.jrimum.domkee.financeiro.banco.febraban.Sacado;
import org.jrimum.domkee.financeiro.banco.febraban.TipoDeTitulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo;
import org.jrimum.domkee.financeiro.banco.febraban.Titulo.Aceite;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.samuexx.cobrancaSS.CobrancaSsApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CobrancaSsApplication.class)
@WebAppConfiguration
public class PrimeiroBoleto {

	@Test
	public void geraBoleto() {

		Cedente cedente = new Cedente("SterSam System", "10.687.566/0001-97");

		Sacado sacado = new Sacado("Samuel Serafim");

		Endereco endereco = new Endereco();
		endereco.setUF(UnidadeFederativa.SP);
		endereco.setLocalidade("São Paulo");
		endereco.setCep(new CEP("06332-020"));
		endereco.setBairro("Vila Ester");
		endereco.setLocalidade("Rua São Mateus");
		endereco.setNumero("260");

		sacado.addEndereco(endereco);

		ContaBancaria contaBancaria = new ContaBancaria(
				BancosSuportados.BANCO_BRADESCO.create());
		contaBancaria.setAgencia(new Agencia(1010, "0"));
		contaBancaria.setNumeroDaConta(new NumeroDaConta(2020, "0"));
		contaBancaria.setCarteira(new Carteira(6));

		Titulo titulo = new Titulo(contaBancaria, sacado, cedente);
		titulo.setNumeroDoDocumento("101010");
		titulo.setNossoNumero("12345678901");
		titulo.setDigitoDoNossoNumero("P");

		titulo.setValor(BigDecimal.valueOf(100.23));
		titulo.setDataDoDocumento(new Date());

		Calendar calendar = Calendar.getInstance();
		calendar.set(2016, 9, 20);

		titulo.setDataDoVencimento(calendar.getTime());

		titulo.setTipoDeDocumento(TipoDeTitulo.DM_DUPLICATA_MERCANTIL);
		titulo.setAceite(Aceite.N);

		Boleto boleto = new Boleto(titulo);
		boleto.setLocalPagamento("Pagar preferencialmente no Bradesco");
		boleto.setInstrucaoAoSacado("Evite multas, pague suas contas em dia");
		boleto.setInstrucao1("Não receber após o vencimento");

		BoletoViewer boletoViewer = new BoletoViewer(boleto);

		File arquivoPdf = boletoViewer.getPdfAsFile("C:\\temp\\meu-primeiro-boleto.pdf");

		mostrarNaTela(arquivoPdf);
		System.out.println("Arquivo gerado com sucesso!!");

	}

	public void mostrarNaTela(File arquivo) {
		Desktop desktop = Desktop.getDesktop();

		try {
			desktop.open(arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
