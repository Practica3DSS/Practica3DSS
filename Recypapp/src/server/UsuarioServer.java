package server;

import java.util.ArrayList;
import java.util.List;

import model.BDUsuario;

public class UsuarioServer {
	private static UsuarioServer instance = null;
	
	private UsuarioServer(){
		
	}
	
	public static UsuarioServer GetInstance(){
		if(instance == null)
			instance = new UsuarioServer();
		
		return instance;
	}
	
	public crud.data.Usuario retrieve(Long id) {
		return entityToData(BDUsuario.getUsuarioPorIdSinRecetas(id));
	}
	
	public crud.data.Usuario retrieveByEmail(String email) {
		return entityToData(BDUsuario.getUsuarioPorEmailSinRecetas(email));
	}
	
	public List<crud.data.Usuario> retrieveList() {
		List<crud.data.Usuario> listData;
		List<model.Usuario> listEntity = BDUsuario.obtenerListaSinRecetas();
		
		listData = new ArrayList<crud.data.Usuario>(listEntity.size());
		
		for(int i = 0; i < listEntity.size(); ++i){
			listData.add(entityToData(listEntity.get(i)));
		}
		
		return listData;
	}
	
	public boolean insert(crud.data.Usuario user) {
		return BDUsuario.insertar(dataToEntity(user));
	}
	
	public boolean update(crud.data.Usuario user, String pass) {
		if(BDUsuario.getUsuarioPorIdSinRecetas(user.getIdUsuario()).equals(pass))
			return BDUsuario.actualizar(dataToEntity(user));
		else
			return false;
	}
	
	public boolean delete(long id){
		return BDUsuario.eliminar(id);
	}
	
	private crud.data.Usuario entityToData(model.Usuario entity){
		crud.data.Usuario data = null; 
		
		if(entity != null){
			data = new crud.data.Usuario(entity.getIdUsuario(), entity.getNick(), entity.getPassword(), entity.getEmail(), 
					ImagenServer.entityToData(entity.getImagen()));
		}
		
		return data;
	}
	
	private model.Usuario dataToEntity(crud.data.Usuario data){
		model.Usuario entity = null; 
		
		if(data != null){
			entity = new model.Usuario(data.getNick(), data.getPassword(), data.getEmail(), 
					ImagenServer.dataToEntity(data.getImagen()));
			entity.setIdUsuario(data.getIdUsuario());
		}
		
		return entity;	
	}
}
