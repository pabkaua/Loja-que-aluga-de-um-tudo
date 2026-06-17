package com.loja.repositories;

import com.loja.model.Multa;
import java.util.List;

public interface MultaRepository {

    void salvar(Multa multa);

    Multa buscarPorId(String id);

    List<Multa> listarPorCliente(String ClienteId);

    List<Multa> listarPendentes();
    
    List<Multa> listar();
}