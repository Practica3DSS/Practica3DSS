package server;

import java.util.ArrayList;
import java.util.List;

import model.BDTag;


public class TagServer {
	private static TagServer instance = null;
	
	private TagServer(){}
	
	public static TagServer GetInstance(){
		if(instance == null)
			instance = new TagServer();
		
		return instance;
	}
	
	public crud.data.Tag retrieve(long id){
		return entityToData(BDTag.getTagPorId(id));
	}
	
	
	public List<crud.data.Tag> retrieveList(){
		List<crud.data.Tag>listTags;
		List<model.Tag> listEntity = BDTag.listarTags();
		
		listTags = new ArrayList<crud.data.Tag>(listEntity.size());
		
		for(int i = 0; i < listEntity.size(); ++i){
			listTags.add(entityToData(listEntity.get(i)));
		}
		
		return listTags;
	}

	
	public boolean insert(crud.data.Tag tag) {
		return BDTag.insertar(dataToEntity(tag));
	}
	
	public boolean update(crud.data.Tag tag) {
			return BDTag.actualizar(dataToEntity(tag));
	}
	
	public boolean delete(long id){
		return BDTag.eliminar(id);
	}
	
	
	
	private static crud.data.Tag entityToData(model.Tag entity){
		crud.data.Tag data = null; 
		
		if(entity != null){
			data = new crud.data.Tag(entity.getIdTag(), entity.getNombre());
		}
		
		return data;
	}
	
	
	private model.Tag dataToEntity(crud.data.Tag data){
		model.Tag entity = null; 
		
		if(data != null){
			entity = new model.Tag(data.getNombre());
			entity.setIdTag(data.getIdTag());
		}
		
		return entity;	
	}
	
}
