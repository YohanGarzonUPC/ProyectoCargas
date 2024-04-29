/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Persona;
import com.example.models.Vehiculos;
import com.example.models.VehiculosDTO;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/**
 *
 * @author Kevin Emiro Ospinal
 */
@Path("/vehiculos")
@Produces(MediaType.APPLICATION_JSON)
public class VehiculosServices {

    @PersistenceContext(unitName = "VehiclesPU")
    EntityManager entityManager;

    public VehiculosServices() {
        try {
            entityManager
                    = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    
    @PostConstruct
    public void init() {
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from Vehiculos u order by u.placa ASC");
        List<Vehiculos> vehicles = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(vehicles).build();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getVehiculosById(@PathParam("id") long id) {
        try {
            Vehiculos vehiculo = entityManager.find(Vehiculos.class, id);
            if (vehiculo == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Vehiculo no encontrado").build();
            }
            return Response.status(Response.Status.OK).entity(vehiculo).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener la persona").build();
        }
    }
    public Boolean returnVehiculo(String placa) {
        try {
            Vehiculos vehiculo = entityManager.find(Vehiculos.class, placa);
            if (vehiculo == null) {
                return false;
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVehicles(VehiculosDTO vehiculo) {
        JSONObject rta = new JSONObject();
        Vehiculos vehiculosTmp = new Vehiculos();
        vehiculosTmp.setPlaca(vehiculo.getPlaca());
        vehiculosTmp.setMarca(vehiculo.getMarca());
        vehiculosTmp.setModelo(vehiculo.getModelo());
        vehiculosTmp.setCapacidadCarga(vehiculo.getCapacidadCarga());
        vehiculosTmp.setTpCarroseria(vehiculo.getTpCarroseria());
        PersonaService persona = new PersonaService();
        if (persona.returnPersona(Long.parseLong(vehiculo.getPropietarioCamion()), "propietario camion")) {
            vehiculosTmp.setPropietarioCamion(vehiculo.getPropietarioCamion());
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al elegir propietario").build();
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(vehiculosTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(vehiculosTmp);
            rta.put("vehiculo_id", vehiculosTmp.getPlaca());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            vehiculosTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @DELETE
    @Path("/remove/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeVehicles(@PathParam("id") String placa) {
        JSONObject rta = new JSONObject();
        Vehiculos vehiculostmp = entityManager.find(Vehiculos.class, placa);

        try {
            if (vehiculostmp != null) {
                entityManager.getTransaction().begin();
                rta.put("vehiculo_id", vehiculostmp.getPlaca());
                entityManager.remove(vehiculostmp);
                entityManager.getTransaction().commit();
                entityManager.refresh(vehiculostmp);
            } else {
                rta.put("vehiculo_id", "No encontrado");
            }

        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            vehiculostmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @POST
    @Path("/update/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateVehicles(@PathParam("id") String placa, VehiculosDTO vehiculo) {
        JSONObject rta = new JSONObject();
        Vehiculos q = entityManager.find(Vehiculos.class, placa);

        try {
            if (q != null) {
                Vehiculos vehiculosTmp = new Vehiculos();
                vehiculosTmp.setPlaca(placa);
                vehiculosTmp.setMarca(vehiculo.getMarca());
                vehiculosTmp.setModelo(vehiculo.getModelo());
                vehiculosTmp.setCapacidadCarga(vehiculo.getCapacidadCarga());
                vehiculosTmp.setTpCarroseria(vehiculo.getTpCarroseria());
                rta.put("vehiculo_id", vehiculosTmp.getPlaca());
                entityManager.getTransaction().begin();
                entityManager.merge(vehiculosTmp);
                entityManager.getTransaction().commit();
                entityManager.refresh(vehiculosTmp);
            } else {
                rta.put("vehiculo_id", "No encontrado");
            }

        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            q = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

}
