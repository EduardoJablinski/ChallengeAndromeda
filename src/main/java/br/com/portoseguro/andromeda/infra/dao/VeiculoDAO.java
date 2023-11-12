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
	        UsuarioDAO usuarioDao = new UsuarioDAO();
	        long idVeiculo = obterProximoIdVeiculo();
	        long idApolice = obterProximoIdApolice();


	        conexao.setAutoCommit(false);

	        try (PreparedStatement comandoDeInsercaoVeiculo = conexao.prepareStatement(sqlInsertVeiculo)) {
	            comandoDeInsercaoVeiculo.setLong(1, idVeiculo);
	            comandoDeInsercaoVeiculo.setInt(2, veiculo.getAnoVeiculo());
	            comandoDeInsercaoVeiculo.setInt(3, veiculo.getPesoVeiculo());
	            comandoDeInsercaoVeiculo.setString(4, veiculo.getCombustivelVeiculo());
	            comandoDeInsercaoVeiculo.setString(5, veiculo.getCorVeiculo());
	            comandoDeInsercaoVeiculo.execute();
	        }

	        try (PreparedStatement comandoDeInsercaoApolice = conexao.prepareStatement(sqlInsertApolice)) {
	            comandoDeInsercaoApolice.setLong(1, idApolice);
	            comandoDeInsercaoApolice.setLong(2, veiculo.getNumeroApolice());

	            Long idUsuario = usuarioDao.obterIdUsuarioPorLogin(usuarioLogado);
	            if (idUsuario != null) {
	                comandoDeInsercaoApolice.setLong(3, idUsuario);
	            } else {
	                throw new RuntimeException("ID do usuário não encontrado para o login: " + usuarioLogado);
	            }

	            comandoDeInsercaoApolice.setLong(4, 1);

	            comandoDeInsercaoApolice.execute();
	        }

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
	        String sqlSelect = "SELECT A.NUMERO_APOLICE, VA.PLACA_VEICULO FROM TB_ACS_APOLICE A " +
	                            "JOIN TB_ACS_USER U ON A.TB_ACS_USER_ID_USER = U.ID_USER " +
	                            "JOIN TB_ACS_VEICULO_APOLICE VA ON A.ID_APOLICE = VA.TB_ACS_APOLICE_ID_APOLICE " +
	                            "WHERE U.CPF_USER = ?";
	        PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect);
	        comandoDeSelecao.setString(1, nomeLogin);
	        ResultSet rs = comandoDeSelecao.executeQuery();
	        while (rs.next()) {
	            Veiculo veiculo = new Veiculo();
	            veiculo.setNumeroApolice(rs.getLong("NUMERO_APOLICE"));
	            veiculo.setPlacaVeiculo(rs.getString("PLACA_VEICULO"));
	            veiculos.add(veiculo);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return veiculos;
	}


	
	public boolean realizarLoginGuincho(String nomeLogin, int numeroApolice) {
	    try {
	        String sqlSelect = "SELECT A.NUMERO_APOLICE, VA.PLACA_VEICULO FROM TB_ACS_APOLICE A " +
	                "JOIN TB_ACS_USER U ON A.TB_ACS_USER_ID_USER = U.ID_USER " +
	                "JOIN TB_ACS_VEICULO_APOLICE VA ON A.ID_APOLICE = VA.TB_ACS_APOLICE_ID_APOLICE " +
	                "WHERE U.CPF_USER = ? AND A.NUMERO_APOLICE = ?";
	        PreparedStatement comandoDeSelecao = conexao.prepareStatement(sqlSelect, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
	        comandoDeSelecao.setString(1, nomeLogin);
	        comandoDeSelecao.setString(2, String.valueOf(numeroApolice));
	        ResultSet rs = comandoDeSelecao.executeQuery();

	        if (rs.next()) {
	            System.out.println("\nDados encontrados! Bem-vindo!");
	            return true;
	        } else {
	            System.out.println("\nDados não encontrados no sistema. Tente novamente.\n");
	            return false;
	        }
	    } catch (SQLException e) {
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
