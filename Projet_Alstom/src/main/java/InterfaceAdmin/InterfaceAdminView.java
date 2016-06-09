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
import de.saxsys.mvvmfx.ViewModel;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
		
		for(JsonOnglet onglet : listeOnglet){
			
			//Pour chaque onglet on ajoute le bouton créer et modifier
			
			Tab tab = new Tab(onglet.getOnglet());
			tab.setId(String.valueOf(onglet.getId()));
			tabPaneAdmin.getTabs().add(tab);
			GridPane grid = new GridPane();
			
			/*Bouton de choix */
			Button creer = new Button("Créer");
			Button ouvrir = new Button("Ouvrir");
			

			creer.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	afficherOnglet(creer.getId(),"creer");
	            }
	        });
			
			ouvrir.setOnAction(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	            	afficherOnglet(ouvrir.getId(),"ouvrir");
	            }
	        });


			/*On définit leur id unique
			**
			**On utilise la norme creer_onglet :=> creer_locomotive
			*/
			
			creer.setId("creer_" + onglet.getOnglet());
			ouvrir.setId("ouvrir_" + onglet.getOnglet());
			
			grid.add(creer, 0, 0);
			grid.add(ouvrir, 1, 0);
					
			
		
			
			tab.setContent(grid);	
			
		}

		/*
		// Pas besoin de faire de binding il est déjà fait
		this.mapTextField.get(3).textProperty().addListener((observable, oldValue, newValue) -> {
			System.out.println("textfield changed from " + oldValue + " to " + newValue);
		});
		*/
		// set liste property/onglet/select dans le vue model
		this.viewModel.setListeOnglet(listeOnglet);
		this.viewModel.setListeProperty(listeProperty);
		this.viewModel.setListeSelect(listeSelect);
		
	}
	
	
	public void afficherOnglet(String idButton, String action_bouton){
		
		String[] tabTmp = idButton.split("_");
		int id = viewModel.getIdOnglet(tabTmp[1]);
		
		if(id==-1)System.out.println("Erreur interne : onglet non trouvé");
		else for(Tab tab : tabPaneAdmin.getTabs()){
			if(tab.getId().equals(String.valueOf(id))){
				
				GridPane grid = new GridPane();
				//On refait les boutons
				
				/*Bouton de choix */
				Button creer = new Button("Créer");
				Button ouvrir = new Button("Ouvrir");
				

				creer.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	afficherOnglet(creer.getId(),"creer");
		            }
		        });
				
				ouvrir.setOnAction(new EventHandler<ActionEvent>() {
		            @Override
		            public void handle(ActionEvent event) {
		            	afficherOnglet(ouvrir.getId(),"ouvrir");
		            }
		        });


				/*On définit leur id unique
				**
				**On utilise la norme creer_onglet :=> creer_locomotive
				*/
				
				creer.setId("creer_" + tabTmp[1]);
				ouvrir.setId("ouvrir_" +tabTmp[1]);
				
				grid.add(creer, 0, 0);
				grid.add(ouvrir, 1, 0);
				
				if(action_bouton == "ouvrir"){
					
					ChoiceBox<String> select = new ChoiceBox<>();
					ObservableList<String> selectlist = FXCollections.observableArrayList();
					final String[] greetings = new String[] { "A", "B", "C", "D", "E" };//ID
					final Label label = new Label("Sélection : ");
				    final ChoiceBox<String> cb = new ChoiceBox<String>(
				        FXCollections.observableArrayList("a", "b", "c", "d", "e"));//Texte

				    cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				          public void changed(ObservableValue ov, Number value, Number new_value) {
				            System.out.println(greetings[new_value.intValue()]);
				          }
				        });

				   grid.add(label,2,0);
				   grid.add(cb, 3, 0);
					//for (JsonSelect selectAttribute : this.viewModel.getListeSelect()) {
						//if (selectAttribute.getIdProperty()==property.getId()){
							//selectlist.add(selectAttribute.getNameSelect());
						//}
				}
				
				int i = 1;

				for (JsonProperty property : this.viewModel.getListeProperty()) {
					if (property.getIdOnglet() == id) {
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
							for (JsonSelect selectAttribute : this.viewModel.getListeSelect()) {
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
		}
	}
}
