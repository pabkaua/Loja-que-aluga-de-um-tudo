package com.loja.business;

import com.loja.model.ContratoAluguel;
import com.loja.model.Multa;
import com.loja.repositories.interfaces.IMultaRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class MultaBusiness {

    private IMultaRepository multaRepository;
    private static final BigDecimal valorFixoPenalidade = new BigDecimal("20.00");
    private static final BigDecimal valorTaxaDiaria = new BigDecimal ("5.50");

    public MultaBusiness(IMultaRepository multaRepository){
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

        Multa novaMulta = new Multa(null, contrato, "Atraso na devolução do item", valorFixoPenalidade, valorTaxaDiaria, diasAtraso, "PENDENTE");

        multaRepository.salvar(novaMulta);

        return novaMulta;
    }

    public double calcularAtraso(ContratoAluguel contrato){
        if (contrato == null){
            return 0.0;
        }
        LocalDate dataFinalCalculo = contrato.getDataEfetivaDevolucao() != null ? 
                                     contrato.getDataEfetivaDevolucao() : LocalDate.now();

        long diasAtraso = ChronoUnit.DAYS.between(contrato.getDataPrevDevolucao(), dataFinalCalculo);

        if (diasAtraso <= 0){
            return 0.0;
        }

        BigDecimal totalDiario = valorTaxaDiaria.multiply(BigDecimal.valueOf(diasAtraso));
        BigDecimal resultadoFinal = valorFixoPenalidade.add(totalDiario);

        return resultadoFinal.doubleValue();
    }

    public void quitar(String multaId){
        if (multaId == null || multaId.trim().isEmpty()){
            throw new RuntimeException("ID da multa inválido para operação de quitação.");
        }
        Multa existente = multaRepository.buscar(multaId);

        if (existente == null){
            throw new RuntimeException("Multa não encontrada para o ID: " + multaId);
        }

        existente.setStatus("QUITADA");
        multaRepository.salvar(existente);
    }

    public List<Multa> listarPorCliente(String clienteId) {
        if (clienteId == null || clienteId.trim().isEmpty()) {
            throw new RuntimeException("ID do cliente inválido para a consulta de listagem.");
        }
        
        List<Multa> multasFiltradas = new java.util.ArrayList<>();

        for (Multa multa : multaRepository.listar().values()) {
        
            if (multa.getContrato() != null 
                    && multa.getContrato().getCliente() != null 
                    && clienteId.equals(multa.getContrato().getCliente().getId())) {
    
                multasFiltradas.add(multa);
            }
        }

        return multasFiltradas;
    }
}