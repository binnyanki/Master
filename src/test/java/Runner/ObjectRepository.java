package Runner;

public class ObjectRepository {
	
	public static final String searchTextbox = Locators.XPATH+"~"+"//*[@id='twotabsearchtextbox']";
	public static final String searchBtn = Locators.XPATH+"~"+"//*[@id='twotabsearchtextbox']/../../following-sibling::div//input";
	public static final String shopByDisplaySizeText = Locators.XPATH+"~"+"//*[contains(text(),'Shop by display size')]";
}
