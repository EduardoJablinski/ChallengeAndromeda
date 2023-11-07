package br.com.portoseguro.andromeda.dominio;

import static org.junit.Assert.assertEquals;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;

public class ModalTest {

    private Modal modal;

    @Before
    public void setUp() {
        modal = new Modal("123", 7286313, "Endereco", 1, "123", "Descricao", 1, 0);
    }

    @Test
    public void testTipoGuincho() {
        modal.tipoOcorrencia = 1; 
        modal.acessoVeiculo = 1;
        modal.cargaVeiculo = 1; 

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        modal.tipoGuincho(modal);

        System.setOut(System.out);

        String output = outContent.toString().trim();

        String expectedOutput = "Tipo de Ocorrência: Acidente de Trânsito\n" +
                "Tipo de Guincho: Guincho Leve, veículo com carga pesada\n" +
                "Endereço da Ocorrência: Endereco\n" +
                "Local de Difícil Acesso?: Sim\n" +
                "Telefone de Contato: 123\n" +
                "Descrição da Ocorrência: Descricao";
        assertEquals(expectedOutput, output);
    }
}