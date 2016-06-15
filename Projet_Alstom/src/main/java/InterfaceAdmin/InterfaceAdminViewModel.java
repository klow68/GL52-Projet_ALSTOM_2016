package InterfaceAdmin;

import java.util.ArrayList;

import ModelObject.GestionnaireConfig;
import ObjectData.GestionnaireDonnees;
import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import model.json.JsonOnglet;
import model.json.JsonProperty;
import model.json.JsonSelect;

public class InterfaceAdminViewModel implements ViewModel {

	private ArrayList<JsonProperty> listeProperty;
	private ArrayList<JsonOnglet> listeOnglet;
	private ArrayList<JsonSelect> listeSelect;
	
	private GestionnaireConfig gestionaire;
	
	private GestionnaireDonnees gestionaireDonnees;

	public InterfaceAdminViewModel(){
		gestionaire = new GestionnaireConfig();
		gestionaireDonnees = new GestionnaireDonnees();
	}
	
	public GestionnaireDonnees getGestionaireDonnees() {
		return gestionaireDonnees;
	}
	
	public GestionnaireConfig getGestionaire() {
		return gestionaire;
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

	public ArrayList<JsonProperty> getListeProperty() {
		return listeProperty;
	}

	public ArrayList<JsonOnglet> getListeOnglet() {
		return listeOnglet;
	}

	public ArrayList<JsonSelect> getListeSelect() {
		return listeSelect;
	}
	
	public void closeApp(){
		Platform.exit();
	    System.exit(0);
	}
	
	public void about(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Version 1.0");
		alert.setHeaderText("A propos");
		alert.setContentText("\tDAUDAN Florian \t \tDUPONT Arnaud \t \n\tGUIMARAES Patrick \t \tRENARD Lucas \t");

		alert.showAndWait();
	}
	
	
}
