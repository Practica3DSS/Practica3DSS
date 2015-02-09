package com.recypapp.recypapp.Comunications.data;

public class Ingrediente {
	private long idIngrediente;
	
	private String nombre;
	
	private String cantidad;

	public Ingrediente() {
	}

	public Ingrediente(long id, String nombre, String cantidad) {
		this.idIngrediente = id;
		this.nombre = nombre;
		this.cantidad = cantidad;
	}

	public long getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(long idIngrediente) {
		this.idIngrediente = idIngrediente;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getCantidad() {
		return cantidad;
	}

	public void setCantidad(String cantidad) {
		this.cantidad = cantidad;
	}

    @Override
    public String toString() {
        return  "Nombre: " + nombre  + " Cantidad: " + cantidad;
    }
}
