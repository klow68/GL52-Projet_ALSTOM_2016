package InterfaceAdmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import model.json.JsonOnglet;
import model.json.JsonProperty;
import model.json.JsonSelect;

public class InterfaceAdminView implements FxmlView<InterfaceAdminViewModel>, Initializable {

	@FXML
	private TabPane tabPaneAdmin;

	@InjectViewModel
	private InterfaceAdminViewModel viewModel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		ArrayList<JsonOnglet> listeOnglet = new ArrayList<>();
		ArrayList<JsonProperty> listeProperty = new ArrayList<>();
		ArrayList<JsonSelect> listeSelect = new ArrayList<>();

		File file = new File("src/main/resources/InterfaceAdmin/admin.json");
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

		for (Object o : a) {
			JSONObject jsonObject = (JSONObject) o;
			
			if ((String) jsonObject.get("onglet") != null) {
				JsonOnglet onglet = new JsonOnglet();
				onglet.setId(Integer.parseInt((String) jsonObject.get("id")));
				onglet.setOnglet((String) jsonObject.get("onglet"));
				listeOnglet.add(onglet);
				System.out.println(onglet.getId());
			}
			else if ((String) jsonObject.get("property") != null) {
				JsonProperty property = new JsonProperty();
				property.setId(Integer.parseInt((String) jsonObject.get("idOnglet")));
				property.setIdOnglet(Integer.parseInt((String) jsonObject.get("idOnglet")));
				property.setType((String) jsonObject.get("type"));
				listeProperty.add(property);
			}
			else if ((String) jsonObject.get("nameSelect") != null) {
				JsonSelect select = new JsonSelect();
				select.setId(Integer.parseInt((String) jsonObject.get("id")));
				select.setIdProperty(Integer.parseInt((String) jsonObject.get("idProperty")));
				select.setNameSelect((String) jsonObject.get("nameSelect"));
				listeSelect.add(select);
			}
			else if ((String) jsonObject.get("boolean") != null) {
				JsonSelect select = new JsonSelect();
				select.setId(Integer.parseInt((String) jsonObject.get("id")));
				select.setIdProperty(Integer.parseInt((String) jsonObject.get("idProperty")));
				select.setNameSelect((String) jsonObject.get("nameSelect"));
				listeSelect.add(select);
			}
		}
		
		for (JsonOnglet onglet : listeOnglet) {
			Tab tab = new Tab(onglet.getOnglet());
			tabPaneAdmin.getTabs().add(tab);
		}
		
	}
}
