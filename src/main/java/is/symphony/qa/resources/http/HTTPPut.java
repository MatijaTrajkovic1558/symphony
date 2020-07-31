package is.symphony.qa.resources.http;

public final class HTTPPut extends HTTPClient {
	
	private HTTPPut(String url) throws Exception {
		super();
		this.method = "PUT";
		this.url = url;
	}
	
	public static HTTPClient getClient(String url) throws Exception {
		return new HTTPPut(url);
	}
}
