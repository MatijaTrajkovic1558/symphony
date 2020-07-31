package is.symphony.test;

import is.symphony.qa.model.UserList;

import static org.junit.jupiter.api.Assertions.*;

class Check {
	
	public static void forEquals(String message, Object expected, Object actual) {
		assertEquals(expected, actual, message);
	}
	
	public static void forTrue(String message, boolean condition) {
		assertTrue(condition, message);
	}
	
	public static void forFalse(String message, boolean condition) {
		assertFalse(condition, message);
	}
	
	public static void forNotNull(String message, Object o) {
		assertNotNull(o, message);
	}
	
	public static void forNull(String message, Object o) {
		assertNull(o, message);
	}
	
	public static void compareListOverview(UserList expected, UserList actual) {
		assertAll("User list response: ",
			() -> assertEquals(expected.getPage(), actual.getPage(), "Bad page id. ")	,
			() -> assertEquals(expected.getPer_page(), actual.getPer_page(), "Bad per_page. "),
			() -> assertEquals(expected.getTotal(), actual.getTotal(), "Bad total. "),
			() -> assertEquals(expected.getTotal_pages(), actual.getTotal_pages(), "Bad total_pages. "),
			() -> assertEquals(expected.getAd(), actual.getAd(), "Bad ad. "));
		}
//
//	public static void shellEvents(EventDetailResponse expected, EventDetailResponse actual) {
//		assertAll("Events: ",
//			() -> assertEquals(expected.getId(), actual.getId(), "Bad id. ")	,
//			() -> assertEquals(expected.getDomain(), actual.getDomain(), "Bad domain. "),
//			() -> assertEquals(expected.getName(), actual.getName(), "Bad name. "),
//			() -> assertEquals(expected.getCode(), actual.getCode(), "Bad code. "),
//			() -> assertEquals(expected.isExternalUrl(), actual.isExternalUrl(), "Bad external url flag. "),
//			() -> assertEquals(expected.getUrl(), actual.getUrl(), "Bad url. "),
//			() -> assertEquals(expected.getEventdate(), actual.getEventdate(), "Bad event date. "),
//			() -> assertEquals(expected.getLocaleventdate(), actual.getLocaleventdate(), "Bad local event date. "),
//			() -> assertEquals(expected.getDayOfWeek(), actual.getDayOfWeek(), "Bad day of week. "),
//			() -> assertEquals(expected.getTimezone(), actual.getTimezone(), "Bad timezone. "),
//			() -> assertEquals(expected.getImages(), actual.getImages() ,"Bad images. "),
//			() -> assertEquals(expected.getOnsale(), actual.getOnsale(), "Bad onsale. "),
//			() -> assertTrue((actual.getOffsale() != null && actual.getOffsale() != null), "Bad offsale. "),
//			() -> assertEquals(expected.getProperties(), actual.getProperties(), "Bad event properties. "),
//			() -> assertEquals(expected.getVenue(), actual.getVenue(), "Bad venue. "),
//			() -> assertTrue(Utils.sameContent(actual.getCategories(), expected.getCategories()), "Bad categories: " + actual.getCategories()),
//			() -> assertEquals(expected.getAttractions(), actual.getAttractions(), "Bad attractions. "),
//			() -> assertEquals(expected.getCurrency(), actual.getCurrency(), "Bad currency. "));
//		}



}



