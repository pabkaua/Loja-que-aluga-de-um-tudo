package com.loja.business;

import com.loja.model.ContratoAluguel;
import com.loja.model.Item;
import com.loja.repositories.interfaces.IContratoRepository;

import java.util.List;

public class ContratoBusiness {
    private IContratoRepository contratoRepository;

        private ItemService itemService;
        private UsuarioService usuarioService;

        public ContratoAluguel registrarAluguel(String clienteId, String itemId, int dias){
        }

        public ContratoAluguel processarDevolucao(String contratoId){
        }

        public double calcularValor(Item item, int dias){
        }

        public List<ContratoAluguel> listarAtivos(){
        }

        public List<ContratoAluguel> listarPorCliente(String clienteId){
        }

}
