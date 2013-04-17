package test;
import org.apache.solr.client.solrj.beans.Field;
public class QUICKRPY {
	@Field("_version_")
	Long _version_;
	@Field("PHRASE")
	String PHRASE;
	
	public String getPHRASE() {
		return PHRASE;
	}

	public String getREPLY() {
		return REPLY;
	}

	public String getREPLY_TYPE() {
		return REPLY_TYPE;
	}
	@Field("REPLY")
	String REPLY;
	
	@Field("REPLY_TYPE")
	String REPLY_TYPE;
	
	
	public void setPHRASE(String phrase) {
		PHRASE = phrase;
	}

	public void setREPLY(String reply) {
		REPLY = reply;
	}
	public void setREPLY_TYPE(String reply_type) {
		REPLY_TYPE = reply_type;
	}
	
}

