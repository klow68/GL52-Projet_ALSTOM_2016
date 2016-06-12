package InterfaceAdmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import ModelObject.GestionnaireConfig;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.ViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.FluentViewLoader.FxmlViewStep;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.json.JsonOnglet;
import model.json.JsonProperty;
import model.json.JsonSelect;

public class InterfaceAdminViewModel implements ViewModel {

	private ArrayList<JsonProperty> listeProperty;
	private ArrayList<JsonOnglet> listeOnglet;
	private ArrayList<JsonSelect> listeSelect;
	
	private GestionnaireConfig gestionaire;

	public InterfaceAdminViewModel(){
		gestionaire = new GestionnaireConfig();
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
	
//	public Integer getIdOnglet(String labelOnglet){
//		
//		int id = -1;
//		for (JsonOnglet onglet : listeOnglet){
//			if( onglet.getOnglet().equals(labelOnglet)) id = onglet.getId();
//		}
//		return id;
//	}

	public ArrayList<JsonProperty> getListeProperty() {
		return listeProperty;
	}

	public ArrayList<JsonOnglet> getListeOnglet() {
		return listeOnglet;
	}

	public ArrayList<JsonSelect> getListeSelect() {
		return listeSelect;
	}
	
	public void rechercheToutObjetBDD(String type){
		//NE PAS APPELER SINON BOUM TOUT EXPLOSE ET TU MEURS DU DECES !
		File file = new File("src/main/resources/InterfaceAdmin/"+type+".json");
		JSONParser parser = new JSONParser();

		JSONArray a = null;
		try {
			a = (JSONArray) parser.parse(new FileReader(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	
}
