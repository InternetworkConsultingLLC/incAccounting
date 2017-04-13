package net.internetworkconsulting.data;

public interface ListableInterface {
	String getEditController();
	String getListController();
	String getTitle();
	String getSelect();

	void deleteRow(String guid) throws Exception;
}
