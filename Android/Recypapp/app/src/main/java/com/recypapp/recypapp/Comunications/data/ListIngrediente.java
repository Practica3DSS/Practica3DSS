package com.recypapp.recypapp.Comunications.data;

import java.util.List;

public class ListIngrediente {
	private List<Ingrediente> ingredientes;

	public ListIngrediente() {

	}
	
	public ListIngrediente(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}

	public List<Ingrediente> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<Ingrediente> ingredientes) {
		this.ingredientes = ingredientes;
	}
}
