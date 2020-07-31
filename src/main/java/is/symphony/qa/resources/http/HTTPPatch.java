package is.symphony.qa.resources.http;

public final class HTTPPatch extends HTTPClient {
	
	private HTTPPatch(String url) throws Exception {
		super();
		this.method = "PATCH";
		this.url = url;
	}
	
	public static HTTPClient getClient(String url) throws Exception {
		return new HTTPPatch(url);
	}
}
