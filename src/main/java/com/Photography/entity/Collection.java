package com.Photography.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Component
@Document(collection = "collections")
public class Collection {

    @Id
    private String id;
    
    @Indexed(unique = true)
    private String name;

    @DBRef
    private List<Image> images=new ArrayList<>();

	public Collection() {
		super();
	}

	public Collection(String id, String name, List<Image> images) {
		super();
		this.id = id;
		this.name = name;
		this.images = images;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<Image> getImages() {
		return images;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setImages(List<Image> images) {
		this.images = images;
	}

	@Override
	public String toString() {
		return "Collection [id=" + id + ", name=" + name + ", images=" + images + "]";
	}
}