/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

import java.util.ArrayList;

/**
 *
 * @author yohan
 */
public class RemisionDTO {
    private String fecha;
    private String hora;
    private String origen;
    private String destino;
    private String placaCamion;
    private String conductor;
    private ArrayList<String> rutaSeguir;
    
    public RemisionDTO(){
        
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }

    public String getDestino() {
        return destino;
    }

    public void setDestino(String destino) {
        this.destino = destino;
    }

    public String getPlacaCamion() {
        return placaCamion;
    }

    public void setPlacaCamion(String placaCamion) {
        this.placaCamion = placaCamion;
    }

    public String getConductor() {
        return conductor;
    }

    public void setConductor(String conductor) {
        this.conductor = conductor;
    }

    public ArrayList<String> getRutaSeguir() {
        return rutaSeguir;
    }

    public void setRutaSeguir(ArrayList<String> rutaSeguir) {
        this.rutaSeguir = rutaSeguir;
    }
    
}
