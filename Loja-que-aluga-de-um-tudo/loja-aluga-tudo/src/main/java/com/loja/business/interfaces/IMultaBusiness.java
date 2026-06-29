package com.loja.business.interfaces;

import com.loja.model.ContratoAluguel;
import com.loja.model.Multa;
import java.util.Map;

public interface IMultaBusiness{

    void aplicar(ContratoAluguel contrato);

    void quitar(String multaId);

    Map<String, Multa> listarPorCliente(String clienteId);

    Map<String, Multa> listar();

    void deletarMulta(String Id);

    boolean possuiMultaPendente(String clienteId);
}