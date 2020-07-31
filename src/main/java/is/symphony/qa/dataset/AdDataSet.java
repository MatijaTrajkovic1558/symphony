package is.symphony.qa.dataset;

import is.symphony.qa.model.Ad;

public class AdDataSet {

    private static Ad createAdDataObject() {
        Ad ad = new Ad();

        ad.setCompany("StatusCode Weekly");
        ad.setUrl("http://statuscode.org/");
        ad.setText("A weekly newsletter focusing on software development, infrastructure, the server, performance, and the stack end of things.");

        return ad;
    }

    public static Ad getAdData() {
        Ad staticAd = createAdDataObject();
        return staticAd;
    }
}
