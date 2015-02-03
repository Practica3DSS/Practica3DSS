package server;

public class ImagenServer {
	public static crud.data.Imagen entityToData(model.Imagen entity){
		crud.data.Imagen data = null;
		
		if(entity !=  null){
			data = new crud.data.Imagen();	
			data.setId(entity.getId());
			data.setFileName(entity.getFileName());
			data.setMimeType(entity.getMimeType());
			data.setImageFile(entity.getImageFile());
		}
		
		return data; 
	}
	
	public static model.Imagen dataToEntity(crud.data.Imagen data){
		model.Imagen entity = null;
		
		if(data != null){
			entity = new model.Imagen();
			entity.setId(data.getId());
			entity.setFileName(data.getFileName());
			entity.setMimeType(data.getMimeType());
			entity.setImageFile(data.getImageFile());
		}
		
		return entity;
	}
}
