/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.models;

/**
 *
 * @author Kevin Emiro Ospinal
 */
public class SolicitudesCargaDTO {
    
    private String fecha;
    private String propietarioCarga;
    private String origen;    
    private String destino;
    private long dimesionesX;
    private long dimesionesY;
    private long dimesionesZ;
    private long peso;
    private long valorAsegurado;
    private String empaque;

    public SolicitudesCargaDTO() {
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getPropietarioCarga() {
        return propietarioCarga;
    }

    public void setPropietarioCarga(String propietarioCarga) {
        this.propietarioCarga = propietarioCarga;
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

    public long getDimesionesX() {
        return dimesionesX;
    }

    public void setDimesionesX(long dimesionesX) {
        this.dimesionesX = dimesionesX;
    }

    public long getDimesionesY() {
        return dimesionesY;
    }

    public void setDimesionesY(long dimesionesY) {
        this.dimesionesY = dimesionesY;
    }

    public long getDimesionesZ() {
        return dimesionesZ;
    }

    public void setDimesionesZ(long dimesionesZ) {
        this.dimesionesZ = dimesionesZ;
    }

    public long getPeso() {
        return peso;
    }

    public void setPeso(long peso) {
        this.peso = peso;
    }

    public long getValorAsegurado() {
        return valorAsegurado;
    }

    public void setValorAsegurado(long valorAsegurado) {
        this.valorAsegurado = valorAsegurado;
    }

    public String getEmpaque() {
        return empaque;
    }

    public void setEmpaque(String empaque) {
        this.empaque = empaque;
    }
  
}
