package iiit.ire.miniproj.queryprocessor;


public class SearchResult {
	String docId = null;
	String title = null;
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public SearchResult(String docId, String title) {
		super();
		this.docId = docId;
		this.title = title;
	}

	
}
