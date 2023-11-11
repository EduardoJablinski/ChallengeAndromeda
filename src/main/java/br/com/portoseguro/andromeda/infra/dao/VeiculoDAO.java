package br.com.portoseguro.andromeda.infra.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import br.com.portoseguro.andromeda.dominio.Veiculo;

public class VeiculoDAO {

	private static Connection conexao;
	
	public VeiculoDAO() {
		conexao = new ConnectionFactory().getConnection();
	}
	
	
	public void adicionar(Veiculo veiculo, String usuarioLogado) {
	    String sqlInsertVeiculo = "INSERT INTO TB_ACS_VEICULO (id_veiculo, ano_veiculo, peso_veiculo, tipo_combustivel, cor_veiculo) VALUES (?, ?, ?, ?, ?)";
	    String sqlInsertVeiculoApolice = "INSERT INTO TB_ACS_VEICULO_APOLICE (id_veiculoap, placa_veiculo, condicao_veiculo, tb_acs_veiculo_id_veiculo, tb_acs_apolice_id_apolice) VALUES (?, ?, ?, ?, ?)";
	    String sqlInsertApolice = "INSERT INTO TB_ACS_APOLICE (id_apolice, numero_apolice, tb_acs_user_id_user, tb_acs_modeloap_id_modeloap) VALUES (?, ?, ?, ?)";

	    try {
	        // Obtém uma instância do UsuarioDAO
	        UsuarioDAO usuarioDao = new UsuarioDAO();
	        long idVeiculo = obterProximoIdVeiculo();
	        long idApolice = obterProximoIdApolice();


	        conexao.setAutoCommit(false);

	        // Inserir na tabela TB_ACS_VEICULO
	        try (PreparedStatement comandoDeInsercaoVeiculo = conexao.prepareStatement(sqlInsertVeiculo)) {
	            comandoDeInsercaoVeiculo.setLong(1, idVeiculo);
	            comandoDeInsercaoVeiculo.setInt(2, veiculo.getAnoVeiculo());
	            comandoDeInsercaoVeiculo.setInt(3, veiculo.getPesoVeiculo());
	            comandoDeInsercaoVeiculo.setString(4, veiculo.getCombustivelVeiculo());
	            comandoDeInsercaoVeiculo.setString(5, veiculo.getCorVeiculo());
	            comandoDeInsercaoVeiculo.execute();
	        }

	        // Inserir na tabela TB_ACS_APOLICE
	        try (PreparedStatement comandoDeInsercaoApolice = conexao.prepareStatement(sqlInsertApolice)) {
	            comandoDeInsercaoApolice.setLong(1, idApolice);
	            comandoDeInsercaoApolice.setLong(2, veiculo.getNumeroApolice());

	            // Obtém o ID do usuário associado ao login
	            Long idUsuario = usuarioDao.obterIdUsuarioPorLogin(usuarioLogado);
	            if (idUsuario != null) {
	                comandoDeInsercaoApolice.setLong(3, idUsuario);
	            } else {
	                throw new RuntimeException("ID do usuário não encontrado para o login: " + usuarioLogado);
	            }

	            // Modifique conforme a lógica do seu sistema
	            comandoDeInsercaoApolice.setLong(4, 1);

	            comandoDeInsercaoApolice.execute();
	        }

	        // Inserir na tabela TB_ACS_VEICULO_APOLICE
	        try (PreparedStatement comandoDeInsercaoVeiculoApolice = conexao.prepareStatement(sqlInsertVeiculoApolice)) {
	            comandoDeInsercaoVeiculoApolice.setLong(1, obterProximoIdVeiculoApolice());
	            comandoDeInsercaoVeiculoApolice.setString(2, veiculo.getPlacaVeiculo());
	            comandoDeInsercaoVeiculoApolice.setString(3, veiculo.getCondicaoVeiculo());
	            comandoDeInsercaoVeiculoApolice.setLong(4, idVeiculo);
	            comandoDeInsercaoVeiculoApolice.setLong(5, idApolice);
	            comandoDeInsercaoVeiculoApolice.execute();
	        }

	        conexao.commit();

	    } catch (SQLException e) {
	        try {
	            conexao.rollback();
	        } catch (SQLException rollbackException) {
	            rollbackException.printStackTrace();
	        }
	        throw new RuntimeException(e);
	    } finally {
	        try {
	            conexao.setAutoCommit(true);
	        } catch (SQLException autoCommitException) {
	            autoCommitException.printStackTrace();
	        }
	    }
	}


	
	public ArrayList<Veiculo> listarTodos(String nomeLogin) {
		ArrayList<Veiculo> veiculos = new ArrayList<>();
		try {
			String sqlSelect = "SELECT * FROM veiculo WHERE cpfCnpj = '" + nomeLogin + "'";
			PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
			ResultSet rs = comandoDeSelecao.executeQuery();
			while(rs.next()) {
				Veiculo veiculo = new Veiculo();
				veiculo.setNumeroApolice(rs.getInt("numeroApolice"));
				veiculo.setModeloVeiculo(rs.getString("modeloVeiculo"));
				veiculo.setAnoVeiculo(rs.getString("anoVeiculo"));
				veiculo.setPesoVeiculo(rs.getInt("pesoVeiculo"));
				veiculo.setCorVeiculo(rs.getString("corVeiculo"));
				veiculo.setCombustivelVeiculo(rs.getString("combustivelVeiculo"));
				veiculo.setPlacaVeiculo(rs.getString("placaVeiculo"));
				veiculos.add(veiculo);
			}
			
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}	
		return veiculos;
	}
	
	public boolean realizarLoginGuincho(String nomeLogin, int numeroApolice) {
		try {
			String sqlSelect = "SELECT * FROM veiculo WHERE cpfCnpj = '" + nomeLogin + "' and numeroApolice = '" + numeroApolice + "';";
			PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
			ResultSet rs = comandoDeSelecao.executeQuery();
			
				if(rs.first())
				{
					System.out.println("\nDados encontrados! Bem-vindo!");
					return true;
			}
				else
				{
					System.out.println("\nDados não encontrados no sistema. Tente novamente.\n");
					return false;
			
		}}catch(SQLException e) {
			throw new RuntimeException(e);
			
			
		}	
	}
	
	public static Long obterProximoNumeroApolice() {
		Long id_apolice = null;
		try {
			String sql = "SELECT SEQ_APOLICE_NUMERO.NEXTVAL FROM DUAL";
			PreparedStatement comandoDeGeracao =
			conexao.prepareStatement(sql);
			ResultSet rs = comandoDeGeracao.executeQuery();
			while(rs.next()) {
				id_apolice = rs.getLong(1);
			}
			rs.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return id_apolice;
	}

	
	private Long obterProximoIdVeiculo() {
		Long idVeiculo = null;
		try {
			String sql = "SELECT SEQ_VEICULO_ID.NEXTVAL FROM DUAL";
			PreparedStatement comandoDeGeracao =
			conexao.prepareStatement(sql);
			ResultSet rs = comandoDeGeracao.executeQuery();
			while(rs.next()) {
				idVeiculo = rs.getLong(1);
			}
			rs.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return idVeiculo;
	}
	
	private Long obterProximoIdApolice() {
		Long idApolice = null;
		try {
			String sql = "SELECT SEQ_APOLICE_ID.NEXTVAL FROM DUAL";
			PreparedStatement comandoDeGeracao =
			conexao.prepareStatement(sql);
			ResultSet rs = comandoDeGeracao.executeQuery();
			while(rs.next()) {
				idApolice = rs.getLong(1);
			}
			rs.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return idApolice;
	}
	
	private Long obterProximoIdVeiculoApolice() {
		Long idApolice = null;
		try {
			String sql = "SELECT SEQ_VEICULOAPOLICE_ID.NEXTVAL FROM DUAL";
			PreparedStatement comandoDeGeracao =
			conexao.prepareStatement(sql);
			ResultSet rs = comandoDeGeracao.executeQuery();
			while(rs.next()) {
				idApolice = rs.getLong(1);
			}
			rs.close();
		}catch(SQLException e) {
			throw new RuntimeException(e);
		}
		return idApolice;
	}
}
