package crud.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import server.RecetaServer;
import crud.data.ListReceta;
import crud.data.MensajeRespuesta;
import crud.data.Receta;

@Path("/receta")
public class RecetaResource {
	private final RecetaServer recetaServer;

	public RecetaResource() {
		this.recetaServer = RecetaServer.GetInstance();
	}
	
	@GET
	@Path(value = "retrieve/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Receta getReceta(@PathParam("id") Long id) {
		return recetaServer.retrieve(id);
	}

	@GET
	@Path(value = "retrieve")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public ListReceta getRecetas() {
		ListReceta list = new ListReceta();
		
		list.setList(recetaServer.retrieveList());
		
		return list;
	}
	
	@GET
	@Path(value = "retrieveByUser/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public ListReceta getRecetasByUser(@PathParam("id") Long id) {
		ListReceta list = new ListReceta();
		
		list.setList(recetaServer.retrieveList(id));
		
		return list;
	}
	
	//http://localhost:8081/Recypapp/rest/receta/query?idTag=351&idUser=101&name=Org%C3%ADa&comensales=1&min_d=0&max_d=210
	@GET
	@Path(value = "query")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public ListReceta getRecetas(@QueryParam("idTag") Long idTag, @QueryParam("idUser") Long idUser, @QueryParam("name") String name, 
			@QueryParam("comensales") int comensales, @QueryParam("min_d") int min_d,  @QueryParam("max_d") int max_d) {
		ListReceta list = new ListReceta();
		
		list.setList(recetaServer.retrieveList(idTag, idUser, name, comensales, min_d, max_d));
		
		return list;
	}
	
    @POST
    @Path(value = "delete")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response deleteReceta(Long id) {
        boolean hecho = recetaServer.delete(id);
        Status estado;
        MensajeRespuesta respuesta = new MensajeRespuesta();
        
        if(hecho){
        	estado = Status.OK;
        	respuesta.setRespuesta("Receta eliminada.");
        }
        else{
        	estado = Status.NOT_FOUND;
        	respuesta.setRespuesta("Receta no encontrada.");
        }
        			
        return Response.status(estado).entity(respuesta).build();
    }
    
	@POST
	@Path(value = "insert")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response insertReceta(Receta receta) {
		boolean hecho = recetaServer.insert(receta);
		
		Status estado;
		MensajeRespuesta respuesta = new MensajeRespuesta();
        
        if(hecho){
        	estado = Status.OK;
        	respuesta.setRespuesta("Receta insertada.");
        }
        else{
        	estado = Status.INTERNAL_SERVER_ERROR;
        	respuesta.setRespuesta("Receta no insertada.");
        }
        			
        return Response.status(estado).entity(respuesta).build();		
	}
	
	/*
		Recordad en la aplicación cliente:
		ClientResponse response = webResource.type("application/json").header("pass", pass).post(ClientResponse.class, request);
	*/
	@POST
	@Path(value = "update")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response updateReceta(@HeaderParam("pass") String pass, Receta receta) {
		boolean hecho = recetaServer.update(receta, pass);
		
		Status estado;
		MensajeRespuesta respuesta = new MensajeRespuesta();
        
        if(hecho){
        	estado = Status.OK;
        	respuesta.setRespuesta("Receta actualizada.");
        }
        else{
        	estado = Status.INTERNAL_SERVER_ERROR;
        	respuesta.setRespuesta("Receta no actualizada.");
        }
        			
        return Response.status(estado).entity(respuesta).build();		
	}
}