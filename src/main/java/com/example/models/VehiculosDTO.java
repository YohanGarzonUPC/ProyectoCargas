/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

/**
 *
 * @author Kevin Emiro Ospinal
 */
public class VehiculosDTO {
    
    private String placa;
    private String marca;
    private String modelo;
    private Long capacidadCarga;
    private String tpCarroseria;
    private String propietarioCamion;

    public VehiculosDTO() {
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

    public String getPropietarioCamion() {
        return propietarioCamion;
    }

    public void setPropietarioCamion(String propietarioCamion) {
        this.propietarioCamion = propietarioCamion;
    }

    
}
