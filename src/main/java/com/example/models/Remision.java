/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

import java.io.Serializable;
import java.util.ArrayList;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author yohan
 * Fecha y hora de recogida, origen, destino, placa camión, conductor, ruta a seguir.
 */
@Entity
public class Remision implements Serializable {
    private String fecha;
    private String hora;
    private String origen;
    private String destino;
    private String placaCamion;
    private String conductor;
    private String rutaSeguir;
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getRutaSeguir() {
        return rutaSeguir;
    }

    public void setRutaSeguir(String rutaSeguir) {
        this.rutaSeguir = rutaSeguir;
    }

    public Remision(String fecha, String hora, String origen, String destino, String placaCamion, String conductor, String rutaSeguir) {
        this.fecha = fecha;
        this.hora = hora;
        this.origen = origen;
        this.destino = destino;
        this.placaCamion = placaCamion;
        this.conductor = conductor;
        this.rutaSeguir = rutaSeguir;
    }
    public Remision(){
        
    }
}
