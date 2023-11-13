package br.com.portoseguro.andromeda.dominio;

import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;

public class VeiculoTest {

    private Veiculo veiculo;

    @Before
    public void setUp() {
        veiculo = new Veiculo();
    }

    @Test
    public void testCadastrarVeiculo() {
        Veiculo novoVeiculo = new Veiculo(123, "Excelente", 2022, 1500, "Preto", "Gasolina", "Usuario", "ABC123");

        veiculo.cadastrarVeiculo(novoVeiculo, "Usuario");

        assertEquals("Usuario", novoVeiculo.getUsuario());
    }

    @Test
    public void testExibirVeiculosDoUsuario() {
        Veiculo veiculo1 = new Veiculo(123, "Excelente", 2022, 1500, "Preto", "Gasolina", "Usuario1", "ABC123");
        Veiculo veiculo2 = new Veiculo(456, "Bom", 2023, 1600, "Branco", "Diesel", "Usuario2", "XYZ789");

        veiculo.cadastrarVeiculo(veiculo1, "Usuario1");
        veiculo.cadastrarVeiculo(veiculo2, "Usuario2");

        veiculo.exibirVeiculosDoUsuario("Usuario1");

    }
}
