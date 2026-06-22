package com.loja.repositories.interfaces;

import com.loja.model.Multa;
import java.util.Map;

public interface MultaRepository {

    public void salvar(Multa multa);
    public Multa buscar(String id);
    
    public Map<String, Multa> listar();
    public Map<String, Multa> listarPorCliente(String ClienteId);
    public Map<String, Multa> listarPorStatus(String status);

    public boolean atualizar(Multa multa);
    public boolean deletar(String id);

    public void carregarDados();
    public void salvarDados();
}