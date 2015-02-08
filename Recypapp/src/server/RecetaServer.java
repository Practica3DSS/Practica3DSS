package server;

import java.util.ArrayList;
import java.util.List;

import model.BDReceta;
import model.BDTag;
import model.BDUsuario;
import crud.data.ListId;
import crud.data.ListIngrediente;

public class RecetaServer {
	private static RecetaServer instance = null;
	
	private RecetaServer(){
		
	}
	
	public static RecetaServer GetInstance(){
		if(instance == null)
			instance = new RecetaServer();
		
		return instance;
	}
	
	public crud.data.Receta retrieve(Long id) {
		return entityToData(BDReceta.getRecetaPorId(id));
	}
	
	public List<crud.data.Receta> retrieveList() {
		List<crud.data.Receta> listData;
		List<model.Receta> listEntity = BDReceta.obtenerLista();
		
		listData = new ArrayList<crud.data.Receta>(listEntity.size());
		
		for(int i = 0; i < listEntity.size(); ++i){
			listData.add(entityToData(listEntity.get(i)));
		}
		
		return listData;
	}
	
	public List<crud.data.Receta> retrieveList(long user_id) {
		List<crud.data.Receta> listData;
		List<model.Receta> listEntity = BDReceta.obtenerLista(user_id);
		
		listData = new ArrayList<crud.data.Receta>(listEntity.size());
		
		for(int i = 0; i < listEntity.size(); ++i){
			listData.add(entityToData(listEntity.get(i)));
		}
		
		return listData;
	}
	
	public List<crud.data.Receta> retrieveList(long idTag, long idUsuario, String name_contains, int cantidad_comensales, 
    		int min_duration, int max_duration) {
		List<crud.data.Receta> listData;
		List<model.Receta> listEntity = BDReceta.obtenerLista(idTag, idUsuario, name_contains, cantidad_comensales, 
	    		min_duration, max_duration);
		
		listData = new ArrayList<crud.data.Receta>(listEntity.size());
		
		for(int i = 0; i < listEntity.size(); ++i){
			listData.add(entityToData(listEntity.get(i)));
		}
		
		return listData;
	}
	
	public boolean insert(crud.data.Receta receta) {
		return BDReceta.insertar(dataToEntity(receta));
	}
	
	public boolean update(crud.data.Receta receta, String pass) {
		if(BDUsuario.getUsuarioPorIdSinRecetas(receta.getUsuario()).getPassword().equals(pass))
			return BDReceta.actualizar(dataToEntity(receta));
		else
			return false;
	}
	
	public boolean delete(long id){
		return BDReceta.eliminar(id);
	}
	
	private crud.data.Receta entityToData(model.Receta entity){
		crud.data.Receta data = null; 
		
		if(entity != null){
			ListIngrediente ingredientes = new ListIngrediente(new ArrayList<crud.data.Ingrediente>());
			ListId tags = new ListId(new ArrayList<Long>());
			
			for(model.Ingrediente ingrediente : entity.getIngredientes()){
				ingredientes.getIngredientes().add(new crud.data.Ingrediente(ingrediente.getIdIngrediente(), ingrediente.getNombre(), 
						ingrediente.getCantidad()));
			}
			
			for(model.Tag tag : entity.getTags()){
				tags.getId().add(tag.getIdTag());
			}
			
			data = new crud.data.Receta(entity.getIdReceta(), entity.getNombre(), entity.getDescripcion(), entity.getDuracion(),
					entity.getCantidad_comensales(), ImagenServer.entityToData(entity.getImagen()), entity.getUsuario().getIdUsuario(),
					entity.getUsuario().getNick(), ingredientes, tags);
		}
		
		return data;
	}
	
	private model.Receta dataToEntity(crud.data.Receta data){
		model.Receta entity = null;
		
		if(data != null){
			entity = new model.Receta(data.getNombre(), data.getDescripcion(), data.getDuracion(),
					data.getCantidad_comensales(), ImagenServer.dataToEntity(data.getImagen()), 
					BDUsuario.getUsuarioPorIdSinRecetas(data.getUsuario()), null);
			entity.setIdReceta(data.getIdReceta());
			
			List<model.Ingrediente> ingredientes = new ArrayList<model.Ingrediente>();
			List<model.Tag> tags = new ArrayList<model.Tag>();
			
			if(data.getIngredientes().getIngredientes() != null){
				for(crud.data.Ingrediente ingrediente : data.getIngredientes().getIngredientes()){
					model.Ingrediente i = new model.Ingrediente(ingrediente.getNombre(), ingrediente.getCantidad(), entity);
				
					i.setIdIngrediente(ingrediente.getIdIngrediente());
				
					ingredientes.add(i);
				}
			}
			
			if(data.getTags().getId() != null){
				for(Long id : data.getTags().getId()){
					tags.add(BDTag.getTagPorId(id));
				}
			}
			
			entity.setIngredientes(ingredientes);
			entity.setTags(tags);
		}
		
		return entity;	
	}
}
