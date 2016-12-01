package iiit.ire.miniproj.queryprocessor;

import java.util.List;

public class Query {
	
	List <String>freeTerms = null;
	List <String>titleTerms = null;
	List <String>bodyTerms = null;
	List <String>categoryTerms = null;
	List <String>infoboxTerms = null;
	List <String>linksTerms = null;
	List <String>referenceTerms = null;
	public Query(List<String> freeTerms, List<String> titleTerms,
			List<String> bodyTerms, List<String> categoryTerms,
			List<String> infoboxTerms, List<String> linksTerms,
			List<String> referenceTerms) {
		super();
		this.freeTerms = freeTerms;
		this.titleTerms = titleTerms;
		this.bodyTerms = bodyTerms;
		this.categoryTerms = categoryTerms;
		this.infoboxTerms = infoboxTerms;
		this.linksTerms = linksTerms;
		this.referenceTerms = referenceTerms;
	}
	public List<String> getFreeTerms() {
		return freeTerms;
	}
	public void setFreeTerms(List<String> freeTerms) {
		this.freeTerms = freeTerms;
	}
	public List<String> getTitleTerms() {
		return titleTerms;
	}
	public void setTitleTerms(List<String> titleTerms) {
		this.titleTerms = titleTerms;
	}
	public List<String> getBodyTerms() {
		return bodyTerms;
	}
	public void setBodyTerms(List<String> bodyTerms) {
		this.bodyTerms = bodyTerms;
	}
	public List<String> getCategoryTerms() {
		return categoryTerms;
	}
	public void setCategoryTerms(List<String> categoryTerms) {
		this.categoryTerms = categoryTerms;
	}
	public List<String> getInfoboxTerms() {
		return infoboxTerms;
	}
	public void setInfoboxTerms(List<String> infoboxTerms) {
		this.infoboxTerms = infoboxTerms;
	}
	public List<String> getLinksTerms() {
		return linksTerms;
	}
	public void setLinksTerms(List<String> linksTerms) {
		this.linksTerms = linksTerms;
	}
	public List<String> getReferenceTerms() {
		return referenceTerms;
	}
	public void setReferenceTerms(List<String> referenceTerms) {
		this.referenceTerms = referenceTerms;
	}
	
}
