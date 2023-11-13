package br.com.portoseguro.controller;

import br.com.portoseguro.andromeda.dominio.Veiculo;
import br.com.portoseguro.andromeda.infra.dao.UsuarioDAO;
import br.com.portoseguro.andromeda.infra.dao.VeiculoDAO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.List;

@Path("/usuarios")
public class UsuarioController {

    private UsuarioDAO usuarioDAO = new UsuarioDAO();
    private VeiculoDAO veiculoDAO = new VeiculoDAO();

    @GET
    @Path("/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVeiculosByUsuario(@PathParam("cpf") String cpf) {
        Long idUsuario = usuarioDAO.obterIdUsuarioPorLogin(cpf);

        if (idUsuario != null) {
            List<Veiculo> veiculos = veiculoDAO.listarTodos(idUsuario);

            if (veiculos.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuário não possui veículos cadastrados")
                        .build();
            } else {
                List<VeiculoResponse> veiculosResponse = new ArrayList<>();
                for (Veiculo veiculo : veiculos) {
                    VeiculoResponse veiculoResponse = new VeiculoResponse();
                    veiculoResponse.setPlacaVeiculo(veiculo.getPlacaVeiculo());
                    veiculoResponse.setNumeroApolice(veiculo.getNumeroApolice());
                    veiculosResponse.add(veiculoResponse);
                }

                return Response.ok(veiculosResponse).build();
            }
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuário não encontrado")
                    .build();
        }
    }

    public static class VeiculoResponse {
        private String placaVeiculo;
        private Long numeroApolice;


        public String getPlacaVeiculo() {
            return placaVeiculo;
        }

        public void setPlacaVeiculo(String placaVeiculo) {
            this.placaVeiculo = placaVeiculo;
        }

        public Long getNumeroApolice() {
            return numeroApolice;
        }

        public void setNumeroApolice(Long numeroApolice) {
            this.numeroApolice = numeroApolice;
        }
    }

}
