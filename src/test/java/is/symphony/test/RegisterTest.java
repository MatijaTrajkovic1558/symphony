package is.symphony.test;


import is.symphony.qa.resources.http.HTTPClient;
import is.symphony.qa.resources.http.HTTPPost;
import is.symphony.qa.resources.http.HTTPResponse;
import org.junit.jupiter.api.*;


@DisplayName("Register user test class")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("register")
public class RegisterTest {

    private static final String url = "https://reqres.in/api/register";

    @DisplayName("Register user unsuccessfully")
    @Test
    void unsuccessful_register () throws Exception {
        HTTPClient client = HTTPPost.getClient(url);
        HTTPResponse response = client.getResponse();
        Check.forEquals("Bad response code: " + response.getShortMessage(), 400, response.getCode());
        Check.forTrue("Bad response body.", response.getBody().equals("{\"error\":\"Missing email or username\"}"));
    }
}
