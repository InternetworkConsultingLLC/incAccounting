package net.internetworkconsulting.accounting.mvc;

import net.internetworkconsulting.accounting.entities.Conversion;
import net.internetworkconsulting.accounting.entities.UnitMeasure;
import net.internetworkconsulting.mvc.CheckTag;
import net.internetworkconsulting.mvc.ComboTag;
import net.internetworkconsulting.mvc.Controller;
import net.internetworkconsulting.mvc.ControllerInterface;
import net.internetworkconsulting.mvc.History;
import net.internetworkconsulting.mvc.LabelTag;
import net.internetworkconsulting.mvc.TextTag;
import net.internetworkconsulting.template.Template;

public class UnitMeasuresConversionsController extends Controller {
	public UnitMeasuresConversionsController(ControllerInterface controller, String document_keyword) { super(controller, document_keyword); }
	public boolean getEnforceSecurity() { return true; }
	
	private UnitMeasure leftUnit;
	public void setLeftUnitMeasure(UnitMeasure value) { leftUnit = value; }
	
	public void createControls(Template document, Object model) throws Exception {
		setDocument(document);
		
		Conversion objModel = (Conversion) getModel();
		
		CheckTag chkDelete = new CheckTag(this, "Conversion " + Conversion.IS_DELETED);
		chkDelete.setName(Conversion.IS_DELETED + objModel.getGuid());
		chkDelete.bind(objModel, Conversion.IS_DELETED);
		
		LabelTag lblLeftUnit = new LabelTag(this, "Conversion " + Conversion.LEFT_UNIT_MEASURES_GUID);
		lblLeftUnit.setValue(leftUnit.getDisplayName());
		
		TextTag txtLeftQty = new TextTag(this, "Conversion " + Conversion.LEFT_QUANTITY);
		txtLeftQty.setName(Conversion.LEFT_QUANTITY + objModel.getGuid());
		txtLeftQty.setFormat("%." + getUser().getSetting(Conversion.SETTING_QUANITY_DECIMALS) + "f");
		txtLeftQty.bind(objModel, Conversion.LEFT_QUANTITY);

		ComboTag cboRightUnit = new ComboTag(this, "Conversion " + Conversion.RIGHT_UNIT_MEASURES_GUID);
		cboRightUnit.setName(Conversion.RIGHT_UNIT_MEASURES_GUID + objModel.getGuid());
		cboRightUnit.setOptions(UnitMeasure.loadOptions(getUser().login(), false));
		cboRightUnit.bind(objModel, Conversion.RIGHT_UNIT_MEASURES_GUID);
		
		TextTag txtRightQty = new TextTag(this, "Conversion " + Conversion.RIGHT_QUANTITY);
		txtRightQty.setName(Conversion.RIGHT_QUANTITY + objModel.getGuid());
		txtRightQty.setFormat("%." + getUser().getSetting(Conversion.SETTING_QUANITY_DECIMALS) + "f");
		txtRightQty.bind(objModel, Conversion.RIGHT_QUANTITY);
		
	}
	public History createHistory() throws Exception { return null; }
	
}
