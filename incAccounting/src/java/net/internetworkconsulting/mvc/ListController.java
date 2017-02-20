/*
 * INC's Servlet MVC Classes
 * Copyright (C) 2016 Internetwork Consulting LLC
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation, version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see http://www.gnu.org/licenses/.
 */
package net.internetworkconsulting.mvc;

import java.util.List;
import net.internetworkconsulting.data.Row;
import net.internetworkconsulting.data.RowInterface;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public abstract class ListController extends Controller {	
	private TextTag txtSearch;

	public abstract String getEditController();
	public abstract String getListController();
	public abstract String getTitle();
	public abstract List<String> getColumns();
	public abstract Class getRowClass();
	
	public ListController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	
	public void createControls(Template document, Object model) throws Exception {		
		String sButton = getRequest().getParameter("Button");
		String sGuid = getRequest().getParameter("GUID");
		if(sButton != null && sButton.equals("Delete") && sGuid != null && sGuid.length() == 32)
			deleteRow(sGuid);
		
		setDocument(new Template(read_url("~/templates/List.html"), new HtmlSyntax()));

		txtSearch = new TextTag(this, "Keywords");
		
		ButtonTag btnSearch = new ButtonTag(this, "Search");
		btnSearch.setValue("Search");
		btnSearch.addOnClickEvent(new Event() { public void handle() throws Exception { btnSearch_OnClick(); } });
	}
	public History createHistory() throws Exception { 
		return new History(getTitle(), getRequest(), getUser());
	}
	public abstract void deleteRow(String guid) throws Exception;
	void btnSearch_OnClick() throws Exception {}
	public void populateDocument() throws Exception {
		List<RowInterface> lstRows = loadSearch(getRowClass(), getColumns(), txtSearch.getValue());

		if(lstRows.size() > 0) {
			for(String sColumn : getColumns()) {
				getDocument().set("Column Name", sColumn.replace(" ", "&nbsp;"));
				getDocument().touch("Header");
				getDocument().parse("Header");
			}

			for(RowInterface ri : lstRows) {
				for(String column : getColumns()) {
					if(ri.get(column) != null)
						getDocument().set("Value", ri.get(column).toString());
					else
						getDocument().set("Value", "");
					getDocument().set("Edit Controller", getEditController());
					getDocument().set("List Controller", getListController());
					getDocument().set("GUID", (String) ri.get("GUID"));
					getDocument().touch("Column");
					getDocument().parse("Column");
				}

				getDocument().touch("Row");
				getDocument().parse("Row");
			}
		}

		getDocument().set("Title", getTitle());
		getDocument().set("Edit Controller", getEditController());
		getDocument().set("List Controller", getListController());
		
		super.populateDocument();
	}

	private List loadSearch(Class cls, List<String> columns, String search) throws Exception {
		return Row.loadSearch(getUser().login(), cls, columns, search);
	}
	
}
