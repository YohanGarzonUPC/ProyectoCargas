/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

import com.sun.istack.NotNull;
import java.io.Serializable;
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Kevin Emiro Ospinal
 */
@Entity
public class Vehiculos implements Serializable{
    @Id
    private String placa;
   
    @NotNull
    @Column(name = "create_at", updatable = false)
    @Temporal(TemporalType.DATE)
    private Calendar createdAt;
    @NotNull
    @Column(name = "updated_at")
    @Temporal(TemporalType.DATE)
    private Calendar updatedAt;
    
    private String marca;
    private String modelo;
    private Long capacidadCarga;
    private String tpCarroseria;
    private String propietarioCamion;

    public String getPropietarioCamion() {
        return propietarioCamion;
    }

    public void setPropietarioCamion(String propietarioCamion) {
        this.propietarioCamion = propietarioCamion;
    }

    public Vehiculos() {
    }

    public Vehiculos(String placa, Calendar createdAt, Calendar updatedAt, String marca, String modelo, Long capacidadCarga, String tpCarroseria, String propietarioCamion) {
        this.placa = placa;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.marca = marca;
        this.modelo = modelo;
        this.capacidadCarga = capacidadCarga;
        this.tpCarroseria = tpCarroseria;
        this.propietarioCamion = propietarioCamion;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public Long getCapacidadCarga() {
        return capacidadCarga;
    }

    public void setCapacidadCarga(Long capacidadCarga) {
        this.capacidadCarga = capacidadCarga;
    }

    public String getTpCarroseria() {
        return tpCarroseria;
    }

    public void setTpCarroseria(String tpCarroseria) {
        this.tpCarroseria = tpCarroseria;
    }  
    
}
