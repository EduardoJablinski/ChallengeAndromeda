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
	
	
	
	public String getTipoModal(int numeroApolice) {
	    try {
	        String sqlSelect = "SELECT tipoVeiculo FROM veiculo WHERE numeroApolice = '" + numeroApolice + "';";
	        PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
	        ResultSet rs = comandoDeSelecao.executeQuery();

	        if (rs.first()) {
	            String tipoVeiculo = rs.getString("tipoVeiculo");
	            return tipoVeiculo;
	        } else {
	            System.out.println("\nDados incorretos. Tente novamente.\n");
	            return null;
	        }
	    } catch (SQLException e) {
	        throw new RuntimeException(e);
	    }
	}

	public void tipoGuincho(Modal modal) {
		String tipoModal = modal.getTipoModal(numeroApolice);
	    String tipoGuincho;
	    String tipoAcesso;
	    String tipoCarga;
	    String ocorrenciaVeiculo;

	    if ("1".equals(tipoModal) || "2".equals(tipoModal)) {
	        tipoGuincho = "Guincho Leve";
	    } else {
	        tipoGuincho = "Guinho Pesado";
	    }
	    
	    if (acessoVeiculo == 1) {
	        tipoAcesso = "Sim";
	    } else {
	    	tipoAcesso = "Não";
	    }
	    
	    if (cargaVeiculo == 1) {
	    	tipoCarga = ", veículo com carga pesada";
	    } else {
	    	tipoCarga = ", veículo sem carga pesada";
	    }
	    
	    if (tipoOcorrencia == 1) {
	    	ocorrenciaVeiculo = "Acidente de Trânsito";
	    } else {
	    	ocorrenciaVeiculo = "Falha Operacional";
	    }
	    
	    System.out.println(
	    	    "Tipo de Ocorrência: " + ocorrenciaVeiculo + "\n" +
	    	    "Tipo de Guincho: " + tipoGuincho + tipoCarga + "\n" +
	    	    "Endereço da Ocorrência: " + enderecoOcorrencia + "\n" +
	    	    "Local de Difícil Acesso?: " + tipoAcesso + "\n" +
	    	    "Telefone de Contato: " + telefoneContato + "\n" +
	    	    "Descrição da Ocorrência: " + descricao
	    	);


	}

		
	
	
}