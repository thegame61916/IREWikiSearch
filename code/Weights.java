package iiit.ire.miniproj.indexerutils;

public class Weights {
	public int title = 100;
	public int category = 40;
	public int infobox = 40;
	public int links = 30;
	public int references = 30;
	public int body = 15;
	public int getTitle() {
		return title;
	}
	public void setTitle(int title) {
		this.title = title;
	}
	public int getCategory() {
		return category;
	}
	public void setCategory(int category) {
		this.category = category;
	}
	public int getInfobox() {
		return infobox;
	}
	public void setInfobox(int infobox) {
		this.infobox = infobox;
	}
	public int getLinks() {
		return links;
	}
	public void setLinks(int links) {
		this.links = links;
	}
	public int getReferences() {
		return references;
	}
	public void setReferences(int references) {
		this.references = references;
	}
	public int getBody() {
		return body;
	}
	public void setBody(int body) {
		this.body = body;
	}
	public Weights(int title, int category, int infobox, int links,
			int references, int body) {
		super();
		this.title = title;
		this.category = category;
		this.infobox = infobox;
		this.links = links;
		this.references = references;
		this.body = body;
	}
	public Weights(){
		this.title = 100;
		this.category = 75;
		this.infobox = 75;
		this.links = 60;
		this.references = 60;
		this.body = 50;
	}
}
