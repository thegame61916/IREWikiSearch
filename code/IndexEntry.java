package iiit.ire.miniproj.indexer;


import iiit.ire.miniproj.indexerutils.Fields;

import java.util.HashMap;
import java.util.Map;

public class IndexEntry {
	private int termCount;
	private Map<Fields,Integer>fields=null;
	public IndexEntry(int termCount) {
		fields=new HashMap<Fields,Integer>();
		this.termCount = termCount; 
		fields.put(Fields.title, 0);
		fields.put(Fields.infobox, 0);
		fields.put(Fields.body, 0);
		fields.put(Fields.category, 0);
		fields.put(Fields.links, 0);
		fields.put(Fields.references, 0);
	}
	public void increaseTitleCount(){
		fields.put(Fields.title, fields.get(Fields.title) + 1);
	}
	public void increaseBodyCount(){
		fields.put(Fields.body, fields.get(Fields.body) + 1);
	}
	public void increaseInfoBoxCount(){
		fields.put(Fields.infobox, fields.get(Fields.infobox) + 1);
	}
	public void increaseCategoryCount(){
		fields.put(Fields.category, fields.get(Fields.category) + 1);
	}
	public void increaseLinksCount(){
		fields.put(Fields.links, fields.get(Fields.links) + 1);
	}
	public void increaseReferencesCount(){
		fields.put(Fields.references, fields.get(Fields.references) + 1);
	}
	
	public int getTitleCount(){
		return fields.get(Fields.title);
	}
	public int getBodyCount(){
		return fields.get(Fields.body);
	}
	public int getInfoBoxCount(){
		return fields.get(Fields.infobox);
	}
	public int getCategoryCount(){
		return fields.get(Fields.category);
	}
	public int getLinksCount(){
		return fields.get(Fields.links);
	}
	public Map<Fields, Integer> getFields() {
		return fields;
	}
	public void setFields(Map<Fields, Integer> fields) {
		this.fields = fields;
	}
	public int getReferencesCount(){
		return fields.get(Fields.references);
	}
	
	public int getTermCount() {
		return termCount;
	}
	public void setTermCount(int termCount) {
		this.termCount = termCount;
	}
	
}
