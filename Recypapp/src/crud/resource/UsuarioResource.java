package crud.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import server.UsuarioServer;
import crud.data.ListUsuario;
import crud.data.UserLoggin;
import crud.data.Usuario;

@Path("/usuario")
public class UsuarioResource {
	private final UsuarioServer usuarioServer;

	public UsuarioResource() {
		this.usuarioServer = UsuarioServer.GetInstance();
	}
	
	@GET
	@Path(value = "retrieve/{id}")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Usuario getUsuario(@PathParam("id") Long id) {
		return usuarioServer.retrieve(id);
	}

	@GET
	@Path(value = "retrieve")
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public ListUsuario getUsuarios() {
		ListUsuario list = new ListUsuario();
		
		list.setList(usuarioServer.retrieveList());
		
		return list;
	}
	
	@POST
	@Path(value = "retrieveByEmail")
	@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
	@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Usuario getUsuarioByEmail(UserLoggin userData) {
		Usuario user = usuarioServer.retrieveByEmail(userData.getEmail());
		
		if(user.getPassword().equals(userData.getPassword())){
			user.setPassword(""); //Evitamos que el password circule innecesariamente.
		}
		else{
			user = new Usuario();
			user.setIdUsuario(-1); //Indicamos que fallo la identificación.
		}
		
		return user;
	}
	
    @POST
    @Path(value = "delete")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
    public Response deleteUsuario(Long id) {
        boolean hecho = usuarioServer.delete(id);
        Status estado;
        String respuesta;
        
        if(hecho){
        	estado = Status.OK;
        	respuesta = "Usuario eliminado.";
        }
        else{
        	estado = Status.NOT_FOUND;
        	respuesta = "Usuario no encontrado.";
        }
        			
        return Response.status(estado).entity(respuesta).build();
    }
    
	@POST
	@Path(value = "insert")
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON + ";charset=utf-8"})
	public Response insertUsuario(Usuario usuario) {
		boolean hecho = usuarioServer.insert(usuario);
		
		Status estado;
        String respuesta;
        
        if(hecho){
        	estado = Status.OK;
        	respuesta = "Usuario insertado.";
        }
        else{
        	estado = Status.INTERNAL_SERVER_ERROR;
        	respuesta = "Usuario no insertado.";
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
	public Response updateUsuario(@HeaderParam("pass") String pass, Usuario usuario) {
		boolean hecho = usuarioServer.update(usuario, pass);
		
		Status estado;
        String respuesta;
        
        if(hecho){
        	estado = Status.OK;
        	respuesta = "Usuario actualizado.";
        }
        else{
        	estado = Status.INTERNAL_SERVER_ERROR;
        	respuesta = "Usuario no actualizado.";
        }
        			
        return Response.status(estado).entity(respuesta).build();		
	}
}
