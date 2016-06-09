package InterfaceAdmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import model.json.JsonOnglet;
import model.json.JsonProperty;
import model.json.JsonSelect;

public class InterfaceAdminView implements FxmlView<InterfaceAdminViewModel>, Initializable {

	@FXML
	private TabPane tabPaneAdmin;

	@FXML
	private Map<Integer, TextField> mapTextField;
	
	@FXML
	private Map<Integer, ChoiceBox<String>> mapChoiceBox;
	
	@FXML
	private Map<Integer, CheckBox> mapCheckBox;

	@InjectViewModel
	private InterfaceAdminViewModel viewModel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		mapTextField = new HashMap();
		mapChoiceBox = new HashMap();
		mapCheckBox = new HashMap();
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
			} else if ((String) jsonObject.get("property") != null) {
				JsonProperty property = new JsonProperty();
				property.setId(Integer.parseInt((String) jsonObject.get("id")));
				property.setIdOnglet(Integer.parseInt((String) jsonObject.get("idOnglet")));
				property.setProperty((String) jsonObject.get("property"));
				property.setType((String) jsonObject.get("type"));
				listeProperty.add(property);
			} else if ((String) jsonObject.get("nameSelect") != null) {
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
			GridPane grid = new GridPane();
			int i = 0;
			for (JsonProperty property : listeProperty) {
				if (property.getIdOnglet() == onglet.getId()) {
					Label label = new Label();
					label.textProperty().set(property.getProperty());
					grid.add(label, 0, i);

					if (property.getType().equals("input")) {
						TextField input = new TextField();
						grid.add(input, 1, i);
						this.mapTextField.put(property.getId(), input);

					} else if (property.getType().equals("select")) {
						ChoiceBox<String> select = new ChoiceBox<>();
						ObservableList<String> selectlist = FXCollections.observableArrayList();
						for (JsonSelect selectAttribute : listeSelect) {
							if (selectAttribute.getIdProperty()==property.getId()){
								selectlist.add(selectAttribute.getNameSelect());
							}
						}
						select.setItems(selectlist);
						grid.add(select, 1, i);
						this.mapChoiceBox.put(property.getId(), select);
						
					} else if (property.getType().equals("checkbox")) {
						CheckBox check = new CheckBox();
						grid.add(check, 1, i);
						this.mapCheckBox.put(property.getId(), check);
					}

					i++;
				}
			}
			tab.setContent(grid);

		}

		// Pas besoin de faire de binding il est déjà fait
		this.mapTextField.get(3).textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);
		});
		
		// set liste property/onglet/select dans le vue model
		this.viewModel.setListeOnglet(listeOnglet);
		this.viewModel.setListeProperty(listeProperty);
		this.viewModel.setListeSelect(listeSelect);
	}
}
