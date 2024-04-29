/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Persona;
import com.example.models.SolicitudesCarga;
import com.example.models.SolicitudesCargaDTO;
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
@Path("/solicitudesCarga")
@Produces(MediaType.APPLICATION_JSON)
public class SolicitudesCargaServices {

    @PersistenceContext(unitName = "SolicitudesCargaPU")
    EntityManager entityManager;

    @PostConstruct
    public void init() {
        try {
            entityManager = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public SolicitudesCargaServices() {
        try {
            entityManager
                    = PersistenceManager.getInstance().getEntityManagerFactory().createEntityManager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    

    @GET
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from SolicitudesCarga u order by u.propietarioCarga ASC");
        List<SolicitudesCarga> solicitudesCarga = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(solicitudesCarga).build();
    }

    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSolicitudesCargaById(@PathParam("id") long id) {
        try {
            SolicitudesCarga solicitudesCarga = entityManager.find(SolicitudesCarga.class, id);
            if (solicitudesCarga == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Solicitudes de Carga no encontrada").build();
            }
            return Response.status(Response.Status.OK).entity(solicitudesCarga).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener la persona").build();
        }
    }
    public String ReturnDestino(Long id) {
        try {
            SolicitudesCarga solicitudesCarga = entityManager.find(SolicitudesCarga.class, id);
            if (solicitudesCarga == null) {
                return "null";
            }
            return solicitudesCarga.getDestino();
        } catch (Exception e) {
            e.printStackTrace();
            return "null";
        }
    }

    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createVehicles(SolicitudesCargaDTO solicitud) {
        JSONObject rta = new JSONObject();
        SolicitudesCarga solicitudesCargaTmp = new SolicitudesCarga();
        solicitudesCargaTmp.setFecha(solicitud.getFecha());
        
        PersonaService persona = new PersonaService();
        if (persona.returnPersona(Long.parseLong(solicitud.getPropietarioCarga()), "propietario carga")) {
            solicitudesCargaTmp.setPropietarioCarga(solicitud.getPropietarioCarga());
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al elegir propietario").build();
        }
        solicitudesCargaTmp.setOrigen(solicitud.getOrigen());
        solicitudesCargaTmp.setDestino(solicitud.getDestino());
        solicitudesCargaTmp.setDimesionesX(solicitud.getDimesionesX());
        solicitudesCargaTmp.setDimesionesY(solicitud.getDimesionesY());
        solicitudesCargaTmp.setDimesionesZ(solicitud.getDimesionesZ());
        solicitudesCargaTmp.setPeso(solicitud.getPeso());
        solicitudesCargaTmp.setValorAsegurado(solicitud.getValorAsegurado());
        solicitudesCargaTmp.setEmpaque(solicitud.getEmpaque());
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(solicitudesCargaTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(solicitudesCargaTmp);
            rta.put("solicitudesCarga_id", solicitudesCargaTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            solicitudesCargaTmp = null;
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
    public Response removeVehicles(@PathParam("id") long id) {
        JSONObject rta = new JSONObject();
        SolicitudesCarga solicitudesCargatmp = entityManager.find(SolicitudesCarga.class, id);

        try {
            if (solicitudesCargatmp != null) {
                entityManager.getTransaction().begin();
                rta.put("solicitudesCarga_id", solicitudesCargatmp.getId());
                entityManager.remove(solicitudesCargatmp);
                entityManager.getTransaction().commit();
                entityManager.refresh(solicitudesCargatmp);
            } else {
                rta.put("solicitudesCarga_id", "No encontrado");
            }

        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            solicitudesCargatmp = null;
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
    public Response updateVehicles(@PathParam("id") long id, SolicitudesCarga solicitud) {
        JSONObject rta = new JSONObject();
        SolicitudesCarga q = entityManager.find(SolicitudesCarga.class, id);

        try {
            if (q != null) {
                SolicitudesCarga solicitudesCargaTmp = new SolicitudesCarga();
                solicitudesCargaTmp.setId(id);
                solicitudesCargaTmp.setFecha(solicitud.getFecha());
                solicitudesCargaTmp.setPropietarioCarga(solicitud.getPropietarioCarga());
                solicitudesCargaTmp.setOrigen(solicitud.getOrigen());
                solicitudesCargaTmp.setDestino(solicitud.getDestino());
                solicitudesCargaTmp.setDimesionesX(solicitud.getDimesionesX());
                solicitudesCargaTmp.setDimesionesY(solicitud.getDimesionesY());
                solicitudesCargaTmp.setDimesionesZ(solicitud.getDimesionesZ());
                solicitudesCargaTmp.setPeso(solicitud.getPeso());
                solicitudesCargaTmp.setValorAsegurado(solicitud.getValorAsegurado());
                solicitudesCargaTmp.setEmpaque(solicitud.getEmpaque());
                rta.put("solicitudesCarga_id", solicitudesCargaTmp.getId());
                entityManager.getTransaction().begin();
                entityManager.merge(solicitudesCargaTmp);
                entityManager.getTransaction().commit();
                entityManager.refresh(solicitudesCargaTmp);
            } else {
                rta.put("solicitudesCarga_id", "No encontrado");
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
