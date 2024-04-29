/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Persona;
import com.example.models.PersonaDTO;
import com.example.models.Remision;
import com.example.models.RemisionDTO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;

/**
 *
 * @author yohan
 */
@Path("/remision")
@Produces(MediaType.APPLICATION_JSON)
public class RemisionService {

    @PersistenceContext(unitName = "RemisionPU")
    EntityManager entityManager;

    public RemisionService() {
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
            entityManager
                    = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRemisionById(@PathParam("id") long id) {
        try {
            Remision remision = entityManager.find(Remision.class, id);
            if (remision == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Remision no encontrada").build();
            }
            return Response.status(Response.Status.OK).entity(remision).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener la remision").build();
        }
    }
    
    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from Remision u");
        List<Remision> remision = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(remision).build();
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRemision(RemisionDTO remision) {
        JSONObject rta = new JSONObject();
        Remision remisionTMP = new Remision();
        remisionTMP.setFecha(remision.getFecha());
        remisionTMP.setHora(remision.getHora());
        remisionTMP.setOrigen(remision.getOrigen());
        remisionTMP.setDestino(remision.getDestino());

        PersonaService persona = new PersonaService();
        if (persona.returnPersona(Long.parseLong(remision.getConductor()), "conductor")) {
            remisionTMP.setConductor(remision.getConductor());
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al elegir conductor").build();
        }

        VehiculosServices vehiculo = new VehiculosServices();
        if (vehiculo.returnVehiculo(remision.getPlacaCamion())) {
            remisionTMP.setPlacaCamion(remision.getPlacaCamion());
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al elegir Camion").build();
        }
        ArrayList<String> rutas;
        rutas = remision.getRutaSeguir();
        SolicitudesCargaServices solicitud = new SolicitudesCargaServices();
        String temp, fin = "";
        for (String id : rutas) {
            temp = solicitud.ReturnDestino(Long.parseLong(id));
            if (!temp.equals("null")) {
                fin += "->" + temp;
            }
        }
        remisionTMP.setRutaSeguir(fin);
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(remisionTMP);
            entityManager.getTransaction().commit();
            entityManager.refresh(remisionTMP);
            rta.put("Persona_id:", remisionTMP.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            remisionTMP = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @DELETE
    @Path("/remover/{id}")
    public Response removerRemision(@PathParam("id") long id) {
        Remision remision = entityManager.find(Remision.class, id);
        try {

            if (remision != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(remision);
                entityManager.getTransaction().commit();
                return Response.status(Response.Status.OK).entity("Remision eliminada exitosamente").build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Remision no encontrada").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar la Remision").build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePersona(@PathParam("id") long id, RemisionDTO remision) {
        try {
            Remision remisionTMP = entityManager.find(Remision.class, id);
            if (remisionTMP == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
            }
            remisionTMP.setId(id);
            remisionTMP.setFecha(remision.getFecha());
            remisionTMP.setHora(remision.getHora());
            remisionTMP.setOrigen(remision.getOrigen());
            remisionTMP.setDestino(remision.getDestino());

            PersonaService persona = new PersonaService();
            if (persona.returnPersona(Long.getLong(remision.getConductor()), "conductor")) {
                remisionTMP.setConductor(remision.getConductor());
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al elegir conductor").build();
            }

            VehiculosServices vehiculo = new VehiculosServices();
            if (vehiculo.returnVehiculo(remision.getPlacaCamion())) {
                remisionTMP.setPlacaCamion(remision.getPlacaCamion());
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al elegir Camion").build();
            }
            ArrayList<String> rutas;
            rutas = remision.getRutaSeguir();
            SolicitudesCargaServices solicitud = new SolicitudesCargaServices();
            String temp, fin = "";
            for (String ids : rutas) {
                temp = solicitud.ReturnDestino(Long.getLong(ids));
                if (!temp.equals("null")) {
                    fin += "->" + temp;
                }
            }
            remisionTMP.setRutaSeguir(fin);

            entityManager.getTransaction().begin();
            entityManager.merge(remisionTMP);
            entityManager.getTransaction().commit();
            entityManager.refresh(remisionTMP);
            
            return Response.status(Response.Status.OK).entity(persona).build();
        } catch (Exception e) {

            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar la persona").build();
        }
    }
}
