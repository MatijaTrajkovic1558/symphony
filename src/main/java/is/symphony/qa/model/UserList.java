package is.symphony.qa.model;

import lombok.Data;

import java.util.List;

@Data
public class UserList {

    int page;
    int per_page;
    int total;
    int total_pages;
    List<is.symphony.qa.model.Data> data;
    Ad ad;

}
