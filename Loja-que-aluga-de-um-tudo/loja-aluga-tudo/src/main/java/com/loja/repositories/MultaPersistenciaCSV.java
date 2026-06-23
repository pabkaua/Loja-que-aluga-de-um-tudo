package com.loja.repositories;

import com.loja.model.ContratoAluguel;
import com.loja.model.Multa;
import com.loja.repositories.interfaces.MultaRepository;

import java.io.*;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class MultaPersistenciaCSV implements MultaRepository {
    private String caminhoArquivo;
    private Map<String, Multa> multas;

    public MultaPersistenciaCSV(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
        this.multas = new HashMap<>();
        this.carregarDados();
    }

    public String getCaminhoArquivo() {
        return caminhoArquivo;
    }
    public void setCaminhoArquivo(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    @Override
    public void salvar(Multa multa) {
        if (multa.getId() == null) {
            long maiorId = 0;

            for (String idExistente : this.multas.keySet()) {
                if (idExistente != null && idExistente.matches("\\d+")) {
                    try {
                        long idNumerico = Long.parseLong(idExistente);
                        if (idNumerico > maiorId) {
                            maiorId = idNumerico;
                        }
                    } catch (NumberFormatException e) {
                        System.err.println("Erro ao converter ID numérico válido: " + idExistente);
                    }
                }
            }

            multa.setId(String.valueOf(maiorId + 1));
        }
        multas.put(multa.getId(), multa);
        this.salvarDados(); 
    }

    @Override
    public Multa buscar(String id) {
        return multas.get(id);
    }

    @Override
    public Map<String, Multa> listar() {
        return Collections.unmodifiableMap(this.multas);
    }

    @Override
    public Map<String, Multa> listarPorStatus(String status) {
        return this.multas.entrySet().stream()
                .filter(valorfiltrado -> valorfiltrado.getValue().getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public Map<String, Multa> listarPorCliente(String clienteId) {
        return this.multas.entrySet().stream()
                .filter(valorfiltrado -> valorfiltrado.getValue().getContrato() != null 
                        && valorfiltrado.getValue().getContrato().getCliente() != null 
                        && valorfiltrado.getValue().getContrato().getCliente().getId().equals(clienteId))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public boolean atualizar(Multa multa) {
        if (this.multas.containsKey(multa.getId())) {
            multas.put(multa.getId(), multa);
            this.salvarDados();
            return true;
        }
        return false;
    }

    @Override
    public boolean deletar(String id) {
        if (this.multas.containsKey(id)) {
            this.multas.remove(id);
            this.salvarDados();
            return true;
        }
        return false;
    }

    @Override
    public void carregarDados() {
        File arquivo = new File(this.caminhoArquivo);
        if (!arquivo.exists()) {
            return;
        }

        try (BufferedReader leitor = new BufferedReader(new FileReader(this.caminhoArquivo))) {
            String linha = leitor.readLine();

            if (linha != null && linha.toLowerCase().startsWith("id;contratoid")) {
                linha = leitor.readLine();
            }

            while (linha != null) {
                String[] dados = linha.split(";");

                if (dados.length >= 7) {
                    String id = dados[0];
                    String contratoId = dados[1];
                    String motivo = dados[2];
                    BigDecimal valorDiario = new BigDecimal(dados[3]);
                    int diasAtraso = Integer.parseInt(dados[5]);
                    String status = dados[6];

                    ContratoAluguel contrato = new ContratoAluguel();
                    contrato.setId(contratoId);

                    Multa multa = new Multa(id, contrato, motivo, valorDiario, diasAtraso, status);
                    this.multas.put(multa.getId(), multa);
                }
                linha = leitor.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void salvarDados() {
        try (BufferedWriter escritor = new BufferedWriter(new FileWriter(this.caminhoArquivo))) {
            escritor.write("id;contratoId;motivo;valorDiario;valorTotal;diasAtraso;status");
            escritor.newLine();

            for (Multa multa : this.multas.values()) {
                String linha = multa.getId() + ";" +
                               (multa.getContrato() != null ? multa.getContrato().getId() : "null") + ";" +
                               multa.getMotivo() + ";" +
                               multa.getValorDiario() + ";" +
                               multa.getValorTotal() + ";" +
                               multa.getDiasAtraso() + ";" +
                               multa.getStatus();
                escritor.write(linha);
                escritor.newLine();
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}