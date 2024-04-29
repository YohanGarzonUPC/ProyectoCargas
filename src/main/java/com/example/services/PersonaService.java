/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.services;

import com.example.PersistenceManager;
import com.example.models.Persona;
import com.example.models.PersonaDTO;
import com.example.models.Remision;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.json.simple.JSONObject;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;

/**
 *
 * @author Yohan
 */
@Path("/persona")
@Produces(MediaType.APPLICATION_JSON)
public class PersonaService {

    @PersistenceContext(unitName = "PersonaPU")
    EntityManager entityManager;

    public PersonaService() {
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
    @Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        Query q = entityManager.createQuery("select u from Persona u");
        List<Persona> persona = q.getResultList();
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(persona).build();
    }
    @GET
    @Path("/get/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPersonaById(@PathParam("id") long id) {
        try {
            Persona persona = entityManager.find(Persona.class, id);
            if (persona == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
            }
            return Response.status(Response.Status.OK).entity(persona).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al obtener la persona").build();
        }
    }
    public Boolean returnPersona(Long id,String tipo) {
        
        try {
            Persona persona = entityManager.find(Persona.class, id);
            
            if (persona == null) {
                return false;
            }
            if(persona.getTipo().equalsIgnoreCase(tipo)){
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


    @POST
    @Path("/add")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPersona(PersonaDTO persona) {
        JSONObject rta = new JSONObject();
        Persona personaTmp = new Persona();
        personaTmp.setName(persona.getName());
        personaTmp.setAddress(persona.getAddress());
        personaTmp.setCity(persona.getCity());
        personaTmp.setTelephone(persona.getTelephone());
        personaTmp.setEmail(persona.getEmail());
        if (persona.getTipo().equalsIgnoreCase("conductor")||persona.getTipo().equalsIgnoreCase("propietario carga")||persona.getTipo().equalsIgnoreCase("propietario camion")) {
            personaTmp.setTipo(persona.getTipo());
        }else{
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al agregar tipo").build();
        }
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(personaTmp);
            entityManager.getTransaction().commit();
            entityManager.refresh(personaTmp);
            rta.put("Persona_id:", personaTmp.getId());
        } catch (Throwable t) {
            t.printStackTrace();
            if (entityManager.getTransaction().isActive()) {
                entityManager.getTransaction().rollback();
            }
            personaTmp = null;
        } finally {
            entityManager.clear();
            entityManager.close();
        }
        return Response.status(200).header("Access-Control-Allow-Origin",
                "*").entity(rta).build();
    }

    @DELETE
    @Path("/remover/{id}")
    public Response removerPersona(@PathParam("id") long id) {
        Persona personaTmp = entityManager.find(Persona.class, id);
        try {
            
            if (personaTmp != null) {
                entityManager.getTransaction().begin();
                entityManager.remove(personaTmp);
                entityManager.getTransaction().commit();
                return Response.status(Response.Status.OK).entity("Persona eliminada exitosamente").build();
            }else{
                return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
            }    
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al eliminar la persona").build();
        }
    }

    @PUT
    @Path("/update/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updatePersona(@PathParam("id") long id, PersonaDTO personaDTO) {
        try {
            Persona persona = entityManager.find(Persona.class, id);
            if (persona == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Persona no encontrada").build();
            }

            persona.setName(personaDTO.getName());
            persona.setAddress(personaDTO.getAddress());
            persona.setCity(personaDTO.getCity());
            persona.setTelephone(personaDTO.getTelephone());
            persona.setEmail(personaDTO.getEmail());
            persona.setTipo(personaDTO.getTipo());

            entityManager.getTransaction().begin();
            entityManager.merge(persona);
            entityManager.getTransaction().commit();
            entityManager.refresh(persona);

            return Response.status(Response.Status.OK).entity(persona).build();
        } catch (Exception e) {

            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity("Error al actualizar la persona").build();
        }
    }
}
