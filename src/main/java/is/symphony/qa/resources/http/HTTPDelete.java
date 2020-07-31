package is.symphony.qa.resources.http;

public final class HTTPDelete extends HTTPClient{
	
	public HTTPDelete(String url) throws Exception {
		super();
		this.method = "DELETE";
		this.url = url;
	}

	public static HTTPClient getClient(String url) throws Exception {
		return new HTTPDelete(url);
	}
}
