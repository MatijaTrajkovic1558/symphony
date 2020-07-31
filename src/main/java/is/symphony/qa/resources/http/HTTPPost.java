package is.symphony.qa.resources.http;

public final class HTTPPost extends HTTPClient {
	
	private HTTPPost(String url) throws Exception {
		super();
		this.method = "POST";
		this.url = url;
	}
	
	public static HTTPClient getClient(String url) throws Exception {
		return new HTTPPost(url);
	}
}
