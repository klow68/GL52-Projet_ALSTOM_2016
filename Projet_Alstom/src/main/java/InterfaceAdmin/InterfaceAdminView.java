package InterfaceAdmin;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import ModelObject.Onglet;
import ModelObject.Parametre;
import ModelObject.Parametre.typeParametre;
import ModelObject.ParametreCombo;
import ModelObject.ParametreInput;
import ModelObject.ParametreSelect;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InterfaceAdminView implements FxmlView<InterfaceAdminViewModel>, Initializable {

	@FXML
	private TabPane tabPaneAdmin;

	// @FXML
	// private Map<Integer, TextField> mapTextField;
	//
	// @FXML
	// private Map<Integer, ChoiceBox<String>> mapChoiceBox;
	//
	// @FXML
	// private Map<Integer, CheckBox> mapCheckBox;

	@InjectViewModel
	private InterfaceAdminViewModel viewModel;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		viewModel.getGestionaire().run();

		for (Onglet onglet : viewModel.getGestionaire().getOnglets()) {

			// Pour chaque onglet on ajoute le bouton créer et modifier

			Tab tab = new Tab(onglet.getName());
			tab.setId(String.valueOf(onglet.getName()));
			tabPaneAdmin.getTabs().add(tab);
			GridPane grid = new GridPane();
			grid.setId("gridMain" + tab.getText());
			GridPane gridButton = new GridPane();
			gridButton.setId("gridButton" + tab.getText());
			GridPane gridParam = new GridPane();
			gridParam.setId("gridParam" + tab.getText());

			/* Bouton de choix */
			Button creer = new Button("Créer");
			Button ouvrir = new Button("Ouvrir");

			// get base de donnée
			final String[] greetings = new String[] { "A", "B", "C", "D", "E" };// ID
			final Label label = new Label("Sélection : ");
			final ChoiceBox<String> cb = new ChoiceBox<String>(
					FXCollections.observableArrayList("a", "b", "c", "d", "e"));// Texte

			cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
				public void changed(ObservableValue ov, Number value, Number new_value) {
					System.out.println(greetings[new_value.intValue()]);
					if (greetings[new_value.intValue()] == "A") {
						System.out.println("Charger A");
					}
				}
			});
			label.setVisible(false);
			cb.setVisible(false);

			creer.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					label.setVisible(false);
					cb.setVisible(false);
					afficherOnglet(tab, onglet);
				}
			});

			ouvrir.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					label.setVisible(true);
					cb.setVisible(true);
					afficherOnglet(tab, onglet);
				}
			});

			/*
			 * On définit leur id unique
			 **
			 ** On utilise la norme creer_onglet :=> creer_locomotive
			 */

			creer.setId("creer_" + onglet.getName());
			ouvrir.setId("ouvrir_" + onglet.getName());

			grid.add(gridButton, 0, 0);
			gridButton.add(creer, 0, 0);
			gridButton.add(ouvrir, 1, 0);

			gridButton.add(label, 2, 0);
			gridButton.add(cb, 3, 0);

			grid.add(gridParam, 0, 1);

			tab.setContent(grid);

		}

		/*
		 * // Pas besoin de faire de binding il est déjà fait
		 * this.mapTextField.get(3).textProperty().addListener((observable,
		 * oldValue, newValue) -> { System.out.println("textfield changed from "
		 * + oldValue + " to " + newValue); });
		 */
		// set liste property/onglet/select dans le vue model

		// this.viewModel.setListeOnglet(listeOnglet);
		// this.viewModel.setListeProperty(listeProperty);
		// this.viewModel.setListeSelect(listeSelect);

	}

	private int numGridi;

	// num grid strat à 2 pour correspondre à la grid de Param
	private void traitementParametre(ArrayList<Parametre> listeParam, GridPane grid, GridPane gridMain, int numGrid, Tab tab) {
		int i = 0;
		numGridi = numGrid;
		for (Parametre param : listeParam) {
			Label label = new Label(param.getLabel());
			if (param.getTypePara() == typeParametre.INPUT) {
				// TODO if boolean

				ParametreInput paramI = (ParametreInput) param;
				TextField input = new TextField();
				// afin de pouvoir le récuperé plus tard
				input.setId(Integer.toString(param.getId()));
				grid.add(input, 1, i);
				// } else if (param.getTypePara()==typeParametre.CHECK) {
				// // ParametreCheck paramC = (ParametreCheck) param;
				// CheckBox check = new CheckBox();
				// check.setId(Integer.toString(param.getId()));
				//
				// // TODO Sous-type
				//
				// grid.add(check, 1, i);
			} else if (param.getTypePara() == typeParametre.COMBO) {
				ParametreCombo paramCombo = (ParametreCombo) param;
				ChoiceBox<String> combo = new ChoiceBox<>();
				combo.setId(Integer.toString(paramCombo.getId()));

				ObservableList<String> selectlist = FXCollections.observableArrayList();
				for (Parametre selectAttribute : paramCombo.getSelects()) {
					ParametreSelect paramS = (ParametreSelect) selectAttribute;

					// ArrayList<Parametre> list = paramS.getParametres();
					// for (Parametre parametre : list) {
					//
					// }
					selectlist.add(paramS.getLabel());
					// System.out.println(paramS.getLabel());
				}
				combo.setItems(selectlist);

				// for Sparam
				combo.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					public void changed(ObservableValue ov, Number value, Number new_value) {
						int j = 0;
						// foreach des select
						for (Parametre selectAttribute : paramCombo.getSelects()) {

							ParametreSelect paramS = (ParametreSelect) selectAttribute;

							GridPane gridSub = new GridPane();
							if (new_value.intValue() == j) {
								
								gridSub.setId(paramS.getId() + "Sub");
								traitementParametre(paramS.getParametres(), gridSub, gridMain, numGridi + 1, tab);
							}else{
								for (Node n : tab.getContent().lookupAll("GridPane")) {
									if (n instanceof GridPane) {
										GridPane gride = (GridPane) n;
										if (gride.getId().equals(paramS.getId() + "Sub")) {
											((GridPane) n).getChildren().clear();
										}
									}
								}
							}
							j++;
						}
					}
				});
				grid.add(combo, 1, i);
			}

			// grid add element
			grid.add(label, 0, i);
			i++;
		}
		if (numGridi != 2) {
			gridMain.add(grid, 0, numGridi);
		}
	}

	public void afficherOnglet(Tab tab, Onglet onglet) {
		GridPane grid = new GridPane();
		GridPane gridMain = new GridPane();
		for (Node n : tab.getContent().lookupAll("GridPane")) {
			if (n instanceof GridPane) {
				GridPane gride = (GridPane) n;
				if (gride.getId().equals("gridParam" + tab.getText())) {
					grid = (GridPane) n;
				} else if (gride.getId().equals("gridMain" + tab.getText())) {
					gridMain = (GridPane) n;
				}
			}
		}

		traitementParametre(onglet.getParametres(), grid, gridMain, 2, tab);

		// String[] tabTmp = idButton.split("_");
		// int id = viewModel.getIdOnglet(tabTmp[1]);
		//
		// if(id==-1)System.out.println("Erreur interne : onglet non trouvé");
		// else
		// for(Tab tab : tabPaneAdmin.getTabs()){
		//
		// if(tab.getId().equals(String.valueOf(id))){
		// GridPane grid = new GridPane();
		// GridPane gridMain = new GridPane();
		//
		// for (Node n: tab.getContent().lookupAll("GridPane")) {
		// if (n instanceof GridPane) {
		// GridPane gride = (GridPane) n;
		// if (gride.getId().equals("gridParam"+tab.getText())){
		// grid = (GridPane) n;
		// //System.out.println("oui");
		// }
		// else if (gride.getId().equals("gridMain"+tab.getText())){
		// gridMain = (GridPane) n;
		// }
		// }
		// }

		// On refait les boutons

		/* Bouton de choix */
		// Button creer = new Button("Créer");
		// Button ouvrir = new Button("Ouvrir");

		//
		// creer.setOnAction(new EventHandler<ActionEvent>() {
		// @Override
		// public void handle(ActionEvent event) {
		// afficherOnglet(creer.getId(),"creer");
		// }
		// });
		//
		// ouvrir.setOnAction(new EventHandler<ActionEvent>() {
		// @Override
		// public void handle(ActionEvent event) {
		// afficherOnglet(ouvrir.getId(),"ouvrir");
		// }
		// });

		/*
		 * On définit leur id unique
		 **
		 ** On utilise la norme creer_onglet :=> creer_locomotive
		 */
		//
		// creer.setId("creer_" + tabTmp[1]);
		// ouvrir.setId("ouvrir_" +tabTmp[1]);
		//
		// grid.add(creer, 0, 0);
		// grid.add(ouvrir, 1, 0);

		// if(action_bouton == "ouvrir"){
		//
		//
		//
		// }
		//
		//
		// int i = 1;
		//
		// for (JsonProperty property : this.viewModel.getListeProperty()) {
		// if (property.getIdOnglet() == id) {
		// Label label = new Label();
		// label.textProperty().set(property.getProperty());
		// grid.add(label, 0, i);
		//
		// if (property.getType().equals("input")) {
		// TextField input = new TextField();
		// grid.add(input, 1, i);
		//// this.mapTextField.put(property.getId(), input);
		//
		// } else if (property.getType().equals("select")) {
		// ChoiceBox<String> select = new ChoiceBox<>();
		// ObservableList<String> selectlist =
		// FXCollections.observableArrayList();
		// for (JsonSelect selectAttribute : this.viewModel.getListeSelect()) {
		// if (selectAttribute.getIdProperty()==property.getId()){
		// selectlist.add(selectAttribute.getNameSelect());
		// }
		// }
		// select.setItems(selectlist);
		// grid.add(select, 1, i);
		//// this.mapChoiceBox.put(property.getId(), select);
		//
		// } else if (property.getType().equals("checkbox")) {
		// CheckBox check = new CheckBox();
		// grid.add(check, 1, i);
		//// this.mapCheckBox.put(property.getId(), check);
		// }
		//
		// i++;
		// }
		//
		// }
		// gridMain.setRowIndex(grid, 1);
		//
		//
		//
		// }
	}

}
