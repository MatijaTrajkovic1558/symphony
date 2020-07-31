package is.symphony.test;


import is.symphony.qa.dataset.AdDataSet;
import is.symphony.qa.model.Ad;
import is.symphony.qa.model.UserList;
import is.symphony.qa.resources.convert.Convert;
import is.symphony.qa.resources.http.HTTPClient;
import is.symphony.qa.resources.http.HTTPGet;
import is.symphony.qa.resources.http.HTTPResponse;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Listing users test class")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("list")
public class ListTest {

    private static final String url = "https://reqres.in/api/users";
    private Ad ad;

    @BeforeAll
    void createAd() {
        this.ad = AdDataSet.getAdData();
    }

    @DisplayName("Regular list request")
    @Test
    void regular_request () throws Exception {
        HTTPClient client = HTTPGet.getClient(url);
        HTTPResponse response = client.getResponse();
        Check.forEquals("Bad response code: " + response.getShortMessage(), 200, response.getCode());
        UserList actual = Convert.fromJson(response.getBody(), UserList.class);
        UserList expected = new UserList();
        expected.setPage(1);
        expected.setPer_page(6);
        expected.setTotal(12);
        expected.setTotal_pages(2);
        expected.setAd(this.ad);
        Check.compareListOverview(expected, actual);
        Check.forEquals("Bad number of users. ", 6, actual.getData().size());
    }

    @DisplayName("Page")
    @ParameterizedTest(name = "number {arguments}")
    @ValueSource(ints = {2, 3, 4})
    void page (int page) throws Exception {
        HTTPClient client = HTTPGet.getClient(url);
        client.addQueryParam("page", page);
        HTTPResponse response = client.getResponse();
        Check.forEquals("Bad response code: " + response.getShortMessage(), 200, response.getCode());
        UserList actual = Convert.fromJson(response.getBody(), UserList.class);
        UserList expected = new UserList();
        expected.setPage(page);
        expected.setPer_page(6);
        expected.setTotal(12);
        expected.setTotal_pages(2);
        expected.setAd(this.ad);
        Check.compareListOverview(expected, actual);
        Check.forFalse("Missing users.", actual.getData().isEmpty());
    }

    @DisplayName("Per page")
    @ParameterizedTest(name = "number {0}")
    @CsvSource({"2, 6", "3, 4", "4, 3"})
    void per_page (int per_page, int total_pages) throws Exception {
        HTTPClient client = HTTPGet.getClient(url);
        client.addQueryParam("per_page", per_page);
        HTTPResponse response = client.getResponse();
        Check.forEquals("Bad response code: " + response.getShortMessage(), 200, response.getCode());
        UserList actual = Convert.fromJson(response.getBody(), UserList.class);
        UserList expected = new UserList();
        expected.setPage(1);
        expected.setPer_page(per_page);
        expected.setTotal(12);
        expected.setTotal_pages(total_pages);
        expected.setAd(this.ad);
        Check.compareListOverview(expected, actual);
        Check.forEquals("Bad number of users. ", per_page, actual.getData().size());
    }

    @DisplayName("Delayed response")
    @Test
    void delayed_response () throws Exception {
        HTTPClient client = HTTPGet.getClient(url);
        client.addQueryParam("delay", 5);
        HTTPResponse response = client.getResponse();
        Check.forEquals("Bad response code: " + response.getShortMessage(), 200, response.getCode());
        UserList actual = Convert.fromJson(response.getBody(), UserList.class);
        UserList expected = new UserList();
        expected.setPage(1);
        expected.setPer_page(6);
        expected.setTotal(12);
        expected.setTotal_pages(2);
        expected.setAd(this.ad);
        Check.compareListOverview(expected, actual);
        Check.forEquals("Bad number of users. ", 6, actual.getData().size());
    }
}
