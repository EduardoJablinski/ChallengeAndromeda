package br.com.portoseguro.andromeda.dominio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import br.com.portoseguro.andromeda.infra.dao.ConnectionFactory;

public class Modal {

	private String usuario;
	private int numeroApolice;
	private String enderecoOcorrencia;
	int tipoOcorrencia;
	private String telefoneContato;
	private String descricao;
	int acessoVeiculo;
	int cargaVeiculo;
	private Connection conexao;
	
	
	public Modal(String usuario, int numeroApolice, String enderecoOcorrencia, int tipoOcorrencia,  String telefoneContato, String descricao, int acessoVeiculo, int cargaVeiculo) {
    	this.usuario = usuario;
    	this.numeroApolice = numeroApolice;
        this.enderecoOcorrencia = enderecoOcorrencia;
        this.tipoOcorrencia = tipoOcorrencia;
        this.telefoneContato = telefoneContato;
        this.descricao = descricao;
        this.acessoVeiculo = acessoVeiculo;
        this.cargaVeiculo = cargaVeiculo;
        conexao = new ConnectionFactory().getConnection();
	}
	


	public void tipoGuincho(Modal modal) {
	    String tipoAcesso;
	    String tipoCarga;
	    String ocorrenciaVeiculo;


	    
	    if (acessoVeiculo == 1) {
	        tipoAcesso = "Sim";
	    } else {
	    	tipoAcesso = "Não";
	    }
	    
	    if (cargaVeiculo == 1) {
	    	tipoCarga = "Veículo com carga pesada";
	    } else {
	    	tipoCarga = "Veículo sem carga pesada";
	    }
	    
	    if (tipoOcorrencia == 1) {
	    	ocorrenciaVeiculo = "Acidente de Trânsito";
	    } else {
	    	ocorrenciaVeiculo = "Falha Operacional";
	    }
	    
	    System.out.println(
	    	    "Tipo de Ocorrência: " + ocorrenciaVeiculo + "\n" +
	    	    "Tipo de Carga: " + tipoCarga + "\n" +
	    	    "Endereço da Ocorrência: " + enderecoOcorrencia + "\n" +
	    	    "Local de Difícil Acesso?: " + tipoAcesso + "\n" +
	    	    "Telefone de Contato: " + telefoneContato + "\n" +
	    	    "Descrição da Ocorrência: " + descricao
	    	);


	}

		
	
	
}