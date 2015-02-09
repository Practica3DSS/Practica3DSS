package com.recypapp.recypapp.Comunications.data;

import java.util.List;

public class ListReceta {
	private List<Receta> list;

	public ListReceta() {
	}
	
	public ListReceta(List<Receta> list) {
		this.list = list;
	}

	public List<Receta> getList() {
		return list;
	}

	public void setList(List<Receta> list) {
		this.list = list;
	}
}
