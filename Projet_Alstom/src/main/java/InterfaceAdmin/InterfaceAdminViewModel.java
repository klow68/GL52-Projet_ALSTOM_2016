package InterfaceAdmin;

import java.util.ArrayList;

import de.saxsys.mvvmfx.ViewModel;
import model.json.JsonOnglet;
import model.json.JsonProperty;
import model.json.JsonSelect;

public class InterfaceAdminViewModel implements ViewModel {

	private ArrayList<JsonProperty> listeProperty;
	private ArrayList<JsonOnglet> listeOnglet;
	private ArrayList<JsonSelect> listeSelect;
	
	public InterfaceAdminViewModel(){
		
	}

	public void setListeProperty(ArrayList<JsonProperty> listeProperty) {
		this.listeProperty = listeProperty;
	}

	public void setListeOnglet(ArrayList<JsonOnglet> listeOnglet) {
		this.listeOnglet = listeOnglet;
	}

	public void setListeSelect(ArrayList<JsonSelect> listeSelect) {
		this.listeSelect = listeSelect;
	}
	
}
