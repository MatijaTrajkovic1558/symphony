package is.symphony.qa.resources.http;

public final class HTTPGet extends HTTPClient {
	
	private HTTPGet(String url) throws Exception {
		super();
		this.method = "GET";
		this.url = url;
	}
	
	public static HTTPClient getClient(String url) throws Exception {
		return new HTTPGet(url);
	}
}
