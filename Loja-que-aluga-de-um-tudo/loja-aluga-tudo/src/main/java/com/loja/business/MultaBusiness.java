package com.loja.business;

import com.loja.model.ContratoAluguel;
import com.loja.model.Multa;
import com.loja.repositories.interfaces.MultaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MultaBusiness {

    private MultaRepository multaRepository;
    private static final BigDecimal valorTaxaDiaria = new BigDecimal ("5.50");

    public MultaBusiness(MultaRepository multaRepository){
        this.multaRepository = multaRepository;
    }

    public Multa aplicar(ContratoAluguel contrato){
        if (contrato == null){
            throw new RuntimeException("Não é possível aplicar multa para um contrato nulo.");
        }

        double valorAtraso = calcularAtraso(contrato);

        if (valorAtraso <= 0){
            throw new RuntimeException("Este contrato não possui dias de atraso para aplicação da multa.");
        }

        long dias = ChronoUnit.DAYS.between(contrato.getDataPrevDevolucao(), contrato.getDataEfetivaDevolucao());
        int diasAtraso = (int) dias;

        Multa novaMulta = new Multa(null, contrato, "Atraso na devolução do item", valorTaxaDiaria, diasAtraso, "PENDENTE");

        multaRepository.salvar(novaMulta);
    }

    public double calcularAtraso(ContratoAluguel contrato){
        if (contrato == null){
            return 0.0;
        }
        long diasAtraso = ChronoUnit.DAYS.between(contrato.getDataPrevDevolucao(), LocalDate.now());

        return diasAtraso > 0 ? valorTaxaDiaria.multiply(BigDecimal.valueOf(diasAtraso)).doubleValue() : 0.0;
    }

    public void quitar(String multaId){
        if (multaId == null || multaId.trim().isEmpty()){
            throw new RuntimeException("ID da multa inválido para operação de quitação.");
        }
        Multa existente = multaRepository.buscarPorId(multaId);

        if (existente == null){
            throw new RuntimeException("Multa não encontrada para o ID: " + multaId);
        }

        existente.setStatus("QUITADA");
        multaRepository.salvar(existente);
    }

    public List<Multa> listarPorCliente(String clienteId){
        if (clienteId == null || clienteId.trim().isEmpty()){
            throw new RuntimeException("ID do cliente inválido para a consulta de listagem.");
        }
        return multaRepository.listarPorCliente(clienteId);
    }

    public List<Multa> listar(){
        return multaRepository.listar();
    }
}