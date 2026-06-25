package com.loja.business;

import com.loja.model.Fornecedor;
import com.loja.repositories.interfaces.IFornecedorRepository;

public class FornecedorBusiness {
    private IFornecedorRepository fornecedorRepository;

    public FornecedorBusiness(IFornecedorRepository fornecedorRepository) {
        this.fornecedorRepository = fornecedorRepository;
    }

    public void cadastrar(Fornecedor fornecedor) {
        if (fornecedorRepository.buscarPorCnpj(fornecedor.getCnpj()) != null) {
            throw new RuntimeException("CNPJ já cadastrado: " + fornecedor.getCnpj());
        }
        fornecedorRepository.salvar(fornecedor);
    }

    public void atualizar(String id, Fornecedor dados) {
        Fornecedor existente = fornecedorRepository.buscarPorId(id);
        if (existente == null) {
            throw new RuntimeException("Fornecedor não encontrado: " + id);
        }
        existente.setNome(dados.getNome());
        existente.setCnpj(dados.getCnpj());
        existente.setTelefone(dados.getTelefone());
        fornecedorRepository.salvar(existente);
    }

    public void desativar(String id) {
        Fornecedor existente = fornecedorRepository.buscarPorId(id);
        if (existente == null) {
            throw new RuntimeException("Fornecedor não encontrado: " + id);
        }
        existente.setAtivo(false);
        fornecedorRepository.salvar(existente);
    }
}