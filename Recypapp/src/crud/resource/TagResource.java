package crud.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import server.TagServer;
import crud.data.ListTag;
import crud.data.Tag;



@Path("/tag")
public class TagResource {
	private final TagServer tagServer;

	public TagResource() {
		this.tagServer = TagServer.GetInstance();
	}
	
	@GET
	@Path(value = "retrieve/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Tag getTag(@PathParam("id") Long id) {
		return tagServer.retrieve(id);
	}
	
	@GET
	@Path(value = "retrieve")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public ListTag getTags(){
		ListTag list = new ListTag();
		
		list.setList(tagServer.retrieveList());
		
		return list;
	}
	
	
	@POST
    @Path(value = "delete")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response deleteTag(Long id) {
        boolean hecho = tagServer.delete(id);
        Status estado;
        String respuesta;
        
        if(hecho){
        	estado = Status.OK;
        	respuesta = "Tag eliminado.";
        }
        else{
        	estado = Status.NOT_FOUND;
        	respuesta = "Tag no encontrado.";
        }
        			
        return Response.status(estado).entity(respuesta).build();
    }
	
	
	@POST
	@Path(value = "insert")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response insertTag(Tag tag) {
		boolean hecho = tagServer.insert(tag);
		
		Status estado;
        String respuesta;
        
        if(hecho){
        	estado = Status.OK;
        	respuesta = "Tag insertado.";
        }
        else{
        	estado = Status.INTERNAL_SERVER_ERROR;
        	respuesta = "Tag no insertado.";
        }
        			
        return Response.status(estado).entity(respuesta).build();		
	}
	
	
	@POST
	@Path(value = "update")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response updateTag(Tag tag) {
		boolean hecho = tagServer.update(tag);
		
		Status estado;
        String respuesta;
        
        if(hecho){
        	estado = Status.OK;
        	respuesta = "Tag actualizado.";
        }
        else{
        	estado = Status.INTERNAL_SERVER_ERROR;
        	respuesta = "Tag no actualizado.";
        }
        			
        return Response.status(estado).entity(respuesta).build();		
	}
}
