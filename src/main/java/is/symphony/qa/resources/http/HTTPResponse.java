package is.symphony.qa.resources.http;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public final class HTTPResponse {
	 Integer code = null;
	 String codeMessage = null;
	 String body = null;
	 String cache =  null;
	 Map<String, String> headers = new HashMap<String, String>();
	  
	 public int getCode() {
		 return this.code;
	 }
	 
	 public String getCodeMessage() {
		 return this.codeMessage;
	 }
	 
	 public String getBody() { 
		 return this.body;
	 }
	 
	  public String getCache() {
		 return this.cache;
	 }
	 
	 public boolean isCached() {
		 return !((this.cache != null) && ((this.cache.toLowerCase().contains("no-cache")) || (this.cache.toLowerCase().contains("no-store"))));
	 }
	 
	 public String getHeader(String name) {
		 return this.headers.get(name);
	 }
	 
	 Map<String, String> getHeaders() {
		return headers;
	}

	void setCode(Integer code) {
		this.code = code;
	}

	void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}

	void setBody(String body) {
		this.body = body;
	}

	void setCache(String cache) {
		this.cache = cache;
	}

	void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	@Override
	 public String toString() {
		 StringBuilder response = new StringBuilder();
		 response.append("Code: " + this.code.toString()).append(" ").append(this.codeMessage);
		 response.append(" | Headers:: ");
		 for(Entry<String, String> entry:this.headers.entrySet()) response.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
		 response.append(" | Body:: ");
		 response.append(this.body);
		 return response.toString();
	 }

	 public String getResponseHeaders() {
		 StringBuilder headers = new StringBuilder();
		 for(Entry<String, String> entry:this.headers.entrySet()) 
			 headers.append(entry.getKey()).append(": ").append(entry.getValue()).append(", ");
		 return headers.toString();
		 
	 }
	 
	 public String headersToString() {
		 StringBuilder headers = new StringBuilder();
		 headers.append(this.code.toString()).append(" ").append(this.codeMessage).append("\n");
		 for(Entry<String, String> entry:this.headers.entrySet()) headers.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
		 return headers.toString();
	 }
	 
	 public boolean hasHeader(String name, String value) {
		  for(Entry<String,String> entry:headers.entrySet()) 
			 if(entry.getKey().toLowerCase().equals(name.toLowerCase()) && entry.getValue().toLowerCase().equals(value.toLowerCase())) return true;
		 return false;
	 }
	 
	 public String getShortMessage() {
		 StringBuilder sb = new StringBuilder();
		 sb.append("Code: ").append(this.code.toString());
		 sb.append(" Message: ").append(this.body);
		 return sb.toString();
	 }
 }