package is.symphony.qa.resources.http;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.*;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.nio.charset.Charset;
import java.util.*;
import java.util.Map.Entry;


public abstract class HTTPClient {
	
	protected String url = null;
	protected String method = "GET";
	private String body = null;
	private String charset = "UTF-8";
	private Map<String,String> headers = new HashMap<String, String>();
	private boolean hasQueryParams = false;
	
	private class HttpDeleteBase extends HttpPost {
		
		public HttpDeleteBase(String url) {
	        super(url);
	    }
		
		@Override
	    public String getMethod() {
	        return "DELETE";
	    }
	}
	
	public String getMethod() {
		return this.method;
	}
	
	public HTTPClient setUrl(String url) {
		this.url = url;
		return this;
	}
	
	public String getUrl() {
		return this.url;
	}
	
	public HTTPClient setRequestHeader(String name, String value) {
		this.headers.put(name, value);
		return this;
	}
	
	public HTTPClient setBody(String body) {
		this.body = body;
		return this;
	}
	
	public HTTPClient setCharset(String charset) {
		this.charset = charset;
		return this;
	}
	
	public HTTPClient setAccept(String value) {
		this.headers.put("Accept", value);
		return this;
	}
	
	public HTTPClient setContentType(String value) {
		this.headers.put("Content-Type", value + ";charset=" + this.charset);
		return this;
	}
	
	public HTTPClient setContentType(String value, String chset) {
		this.headers.put("Content-Type", value + ";charset=" + chset);
		this.charset = chset;
		return this;
	}
	
	public HTTPClient setAcceptLanguage(String value) {
		this.setRequestHeader("Accept-Language", value);
		return this;
	}
	
	public HTTPClient setTMPSCorrelationId(String value) {
		this.setRequestHeader("TMPS-Correlation-Id", value);
		return this;
	}

	public HTTPClient setAuthorization(String type, String value) {
		this.setRequestHeader("Authorization", type + " " + value);
		return this;
	}

	public HTTPClient setBearerAuthorization(String value) {
		this.setRequestHeader("Authorization", "Bearer " + value);
		return this;
	}
	
	private void addHeaderFields(HttpUriRequest request) {
		Set<String> keys = this.headers.keySet();
		for(String key:keys) request.setHeader(key, this.headers.get(key));
    }
	
	public String headersToString() {
		StringBuilder sb = new StringBuilder();
		for(Entry<String,String> entry:headers.entrySet())
			sb.append("[").append(entry.getKey()).append(":").append(entry.getValue()).append("]");
		return sb.toString();
	}
	
	public String getHeaderField(String value) {
		return this.headers.get(value);
	}
	
	private HTTPResponse getHTTPResponse(HttpResponse response) throws Exception {
		HTTPResponse httpresponse = new HTTPResponse();
		httpresponse.setCode(response.getStatusLine().getStatusCode());
		httpresponse.setCodeMessage(response.getStatusLine().getReasonPhrase());
		HttpEntity responseEntity = response.getEntity();
		if(responseEntity != null) {
			httpresponse.setBody(EntityUtils.toString(responseEntity, "UTF-8"));
		}
		Header caching = response.getFirstHeader("Cache-Control");
		if(caching != null) httpresponse.cache = caching.getValue();
		for(Header header:response.getAllHeaders()) {
			httpresponse.headers.put(header.getName(), header.getValue());
		}
		return httpresponse;
	}
	
	private String getIterableAsString(Iterable<?> iterable) {
		StringBuilder sb = new StringBuilder();
		List<Object> values = new LinkedList<Object>();
		Iterator<?> iterator = iterable.iterator();
		Object element;
		while(iterator.hasNext()) {
			element = iterator.next();
			if(element != null) values.add(element);
		}
		if(values.isEmpty()) return null;
		iterator = values.iterator();
		while(iterator.hasNext()) {
			sb.append(iterator.next().toString());
			if(iterator.hasNext()) sb.append(",");
		}
		return sb.toString();
	}
	
	private String getArrayString(Object[] values) {
		return this.getIterableAsString(Arrays.asList(values));
	}
	
	
	public HTTPClient addQueryParam(String name, Object...values) throws Exception {
			StringBuilder sb = new StringBuilder(this.url);
			if(!hasQueryParams) sb.append("?");
			else sb.append("&");
			sb.append(name);
			if(values != null ) {	
				boolean first = true;
				for(Object value:values) {
					if(value != null) {
						String str = null;
							if(value instanceof Iterable) 
								str = this.getIterableAsString((Iterable<?>)value);
							else 
								str = value.toString();
							if(str != null) {
								if(first) {
									sb.append("=");
									first = false;
								}
								else sb.append(",");
								sb.append(str);
							}
						}
					}
				}
			this.url = sb.toString();
			this.hasQueryParams = true;
			return this;
	}
	
	public HTTPClient addQueryParam(String name, Object values) throws Exception {
		StringBuilder sb = new StringBuilder(this.url);
		if(!hasQueryParams) sb.append("?");
			else sb.append("&");
		sb.append(name);
		if(values != null ) {	
					String value = null;
					if(values instanceof Iterable) value = this.getIterableAsString((Iterable<?>)values);
					else if(values instanceof Object[]) value = this.getArrayString((Object[])values);
					else value = values.toString();
					if(value != null) {
							sb.append("=");
							sb.append(value);
					}
		}
		this.url = sb.toString();
		this.hasQueryParams = true;
		return this;
	}
	
	public HTTPClient addQueryParam(String name) throws Exception {
		Object[] objects = null;
		return this.addQueryParam(name, objects);
	}
	
	public HTTPClient repeatQueryParam(String name, Iterable<?> values) throws Exception {
		if(values == null) return this.addQueryParam(name);
		Iterator<?> iterator = values.iterator();
		while(iterator.hasNext()) this.addQueryParam(name, iterator.next());
		return this;
	}
	
	public HTTPClient repeatQueryParam(String name, Object...values) throws Exception {
		if(values == null) return this.addQueryParam(name);
		for(Object object:values) this.addQueryParam(name, object);
		return this;
	}
	
	public HTTPClient addPathParam(Object value) throws Exception {
		if(value == null) throw new IllegalArgumentException("Value cannot be null.");
		StringBuilder sb = new StringBuilder(this.url);
		if(this.url.endsWith("/")) sb.append(value);
		else sb.append("/").append(value);
		this.url = sb.toString();
		return this;
	}
	
	private HttpClient createClient() {
		RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build();
		return HttpClientBuilder.create().setDefaultRequestConfig(config).build();
	}
	
	public HTTPResponse getResponse() throws Exception {
		if(StringUtils.isBlank(this.url)) throw new Exception("URL is not set.");
		HttpClient client = this.createClient();
		HttpResponse http_response = null;
					
				if(this.method.equals("GET")) {
					HttpGet get_request = new HttpGet(this.url);
					this.addHeaderFields(get_request);
					http_response = client.execute(get_request);
				}
				
				if(this.method.equals("POST")) {
					HttpPost post_request = new HttpPost(this.url);
					this.addHeaderFields(post_request);
					if(this.body != null) {
						post_request.setEntity(new ByteArrayEntity(this.body.getBytes(Charset.forName(this.charset))));
					}
					http_response = client.execute(post_request);
				}
		
				if(this.method.equals("PATCH")) {
					HttpPatch patch_request = new HttpPatch(this.url);
					this.addHeaderFields(patch_request);
					if(this.body != null) {
						patch_request.setEntity(new ByteArrayEntity(this.body.getBytes(Charset.forName(this.charset))));
					}
					http_response = client.execute(patch_request);
				}
				
				if(this.method.equals("PUT")) {
					HttpPut put_request = new HttpPut(this.url);
					this.addHeaderFields(put_request);
					if(this.body != null) { 
						put_request.setEntity(new ByteArrayEntity(this.body.getBytes(Charset.forName(this.charset))));
					}
					http_response = client.execute(put_request);
				}
				
				if(this.method.equals("DELETE")) {
					HttpPost delete_request = new HttpDeleteBase(this.url);
					this.addHeaderFields(delete_request);
					if(this.body != null) { 
						delete_request.setEntity(new ByteArrayEntity(this.body.getBytes(Charset.forName(this.charset))));
					}
					http_response = client.execute(delete_request);
				}
				
				return this.getHTTPResponse(http_response);
	}
}