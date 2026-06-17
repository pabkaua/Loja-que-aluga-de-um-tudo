package com.loja.model;

public class Multa {
    private String id;
    private ContratoAluguel contrato;
    private String motivo;
    private double valor;
    private int diasAtraso;
    private String status;

    public Multa(String id, ContratoAluguel contrato, String motivo, double valor, int diasAtraso, String status){
        this.id = id;
        this.contrato = contrato;
        this.motivo = motivo;
        this.valor = valor;
        this.diasAtraso = diasAtraso;
        this.status = status;
    }

    public String getId(){
        return id;
    }

    public void setId(String id){
        this.id = id;
    }

    public ContratoAluguel getContrato(){
        return contrato;
    }

    public void setContrato(ContratoAluguel contrato){
        this.contrato = contrato;
    }

    public String getMotivo(){
        return motivo;
    }

    public void setMotivo(String motivo){
        this.motivo = motivo;
    }

    public double getValor(){
        return valor;
    }

    public void setValor(double valor){
        this.valor = valor;
    }

    public int getDiasAtraso(){
        return diasAtraso;
    }

    public void setDiasAtras(int diasAtraso){
        this.diasAtraso = diasAtraso;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }
}