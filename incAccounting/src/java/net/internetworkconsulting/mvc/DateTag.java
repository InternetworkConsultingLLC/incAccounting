package net.internetworkconsulting.mvc;

import java.text.SimpleDateFormat;
import java.util.Date;
import net.internetworkconsulting.accounting.entities.Option;
import net.internetworkconsulting.template.Template;
import net.internetworkconsulting.template.HtmlSyntax;

public class DateTag extends Tag {
	private ComboTag cboMonth;
	private ComboTag cboDay;
	private TextTag txtYear;
	
	public DateTag(ControllerInterface controller, String property, Object model) throws Exception { super(controller, property, model); }
	public DateTag(ControllerInterface controller, String prefix, String property, String unique_key, Object model) throws Exception { super(controller, prefix, property, unique_key, model); }
	public DateTag(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }

	public void createControls(Template document, Object model) throws Exception {
		setDocument(new Template(readTemplate("~/templates/Date.html"), new HtmlSyntax()));

		cboMonth = new ComboTag(this, "Month");
		cboMonth.setName(getName() + "Month");
		SimpleDateFormat sdfParse = new SimpleDateFormat("yyyy-MM-dd");		
//		SimpleDateFormat sdfFormat = new SimpleDateFormat("MM MMM");
		SimpleDateFormat sdfFormat = new SimpleDateFormat("MM");
		cboMonth.getOptions().add(new Option("", ""));
		for(int cnt = 1; cnt < 13; cnt++) {
			Date dt = sdfParse.parse(String.format("2000-%02d-01", cnt));
			cboMonth.getOptions().add(new Option(sdfFormat.format(dt), String.format("%02d", cnt)));
		}
		cboMonth.setIsReadOnly(this.getIsReadOnly());
		if(this.getOnChangeEvents().size() > 0)
			cboMonth.addOnChangeEvent(new Event() { public void handle() throws Exception { } });
		
		cboDay = new ComboTag(this, "Day");
		cboDay.setName(getName() + "Day");
		cboDay.getOptions().add(new Option("", ""));
		for(int cnt = 1; cnt < 32; cnt++) 
			cboDay.getOptions().add(new Option(String.format("%02d", cnt), String.format("%02d", cnt)));
		cboDay.setIsReadOnly(this.getIsReadOnly());
		if(this.getOnChangeEvents().size() > 0)
			cboDay.addOnChangeEvent(new Event() { public void handle() throws Exception { } });

		txtYear = new TextTag(this, "Year");
		txtYear.setName(getName() + "Year");
		txtYear.setIsReadOnly(this.getIsReadOnly());
		if(this.getOnChangeEvents().size() > 0)
			txtYear.addOnChangeEvent(new Event() { public void handle() throws Exception { } });
	}
	public void updateControls() throws Exception { 
		if(getIsReadOnly())
			return;

		if(cboMonth.getValue() != null && 
			cboMonth.getValue().length() == 2 && 
			cboDay.getValue() != null && 
			cboDay.getValue().length() == 2 && 
			txtYear.getValue() != null && 
			txtYear.getValue().length() == 4
		) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date dtTemp = sdf.parse(txtYear.getValue() + "-" + cboMonth.getValue() + "-" + cboDay.getValue());
			setIsValueChanged(setValue(sdf.format(dtTemp)));
		} else {
			setIsValueChanged(setValue(null));
		}
	}
	public void beforePopulate() throws Exception {
		SimpleDateFormat sdf = null;
		if(getValue() != null && getValue().length() == ("yyyy-MM-dd").length())
			sdf = new SimpleDateFormat("yyyy-MM-dd");
		if(getValue() != null && getValue().length() == ("yyyy-MM-dd HH:mm:ss").length())
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(getValue() != null && getValue().length() == ("yyyy-MM-dd HH:mm").length())
			sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		if(sdf == null)
			return;
			
		Date dt = sdf.parse(getValue());

		sdf = new SimpleDateFormat("yyyy");
		String sYear = sdf.format(dt);

		sdf = new SimpleDateFormat("MM");
		String sMonth = sdf.format(dt);

		sdf = new SimpleDateFormat("dd");
		String sDay = sdf.format(dt);

		cboMonth.setValue(sMonth);
		cboDay.setValue(sDay);
		txtYear.setValue(sYear);
	}
	
}
