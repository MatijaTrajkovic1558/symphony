package is.symphony.test;


import is.symphony.qa.dataset.AdDataSet;
import is.symphony.qa.model.Ad;
import is.symphony.qa.model.UserList;
import is.symphony.qa.resources.convert.Convert;
import is.symphony.qa.resources.http.HTTPClient;
import is.symphony.qa.resources.http.HTTPDelete;
import is.symphony.qa.resources.http.HTTPGet;
import is.symphony.qa.resources.http.HTTPResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Delete user test class")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("delete")
public class DeleteTest {

    private static final String url = "https://reqres.in/api/users";

    @DisplayName("Delete user")
    @Test
    void delete () throws Exception {
        HTTPClient client = HTTPDelete.getClient(url);
        client.addPathParam(2);
        HTTPResponse response = client.getResponse();
        Check.forEquals("Bad response code: " + response.getShortMessage(), 204, response.getCode());
        Check.forTrue("Response body must be empty. ", response.getBody().isEmpty());
    }
}
