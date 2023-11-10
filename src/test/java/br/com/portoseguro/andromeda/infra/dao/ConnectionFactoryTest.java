package br.com.portoseguro.andromeda.infra.dao;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.sql.Connection;
import org.junit.Test;

public class ConnectionFactoryTest {
	@Test
	public void conectaComSucesso() {
		try {
			Connection conexao = new
			ConnectionFactory().getConnection();
			assertFalse(conexao.isClosed());
			conexao.close();
			assertTrue(conexao.isClosed());
		}
		catch(Exception e) {
			fail("Conexão não foi criada com sucesso");
		}
	}
}