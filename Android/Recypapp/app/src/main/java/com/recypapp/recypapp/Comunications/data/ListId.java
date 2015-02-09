package com.recypapp.recypapp.Comunications.data;

import java.util.List;

public class ListId {
	List<Long> id;

	public ListId() {
	}

	public ListId(List<Long> ids) {
		this.id = ids;
	}

	public List<Long> getId() {
		return id;
	}

	public void setId(List<Long> id) {
		this.id = id;
	}
}
