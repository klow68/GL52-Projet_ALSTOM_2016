package InterfaceAdmin;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import ModelObject.Onglet;
import ModelObject.Parametre;
import ModelObject.Parametre.typeParametre;
import ModelObject.ParametreCombo;
import ModelObject.ParametreInput;
import ModelObject.ParametreInput.typeInput;
import ModelObject.ParametreSelect;
import ObjectData.DataObject;
import ObjectData.GestionnaireDonnees;
import ObjectData.ObjectClass;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class InterfaceAdminView implements FxmlView<InterfaceAdminViewModel>, Initializable {

	@FXML
	private TabPane tabPaneAdmin;

	@InjectViewModel
	private InterfaceAdminViewModel viewModel;

	private HashMap<String, Integer> mapIdCreat;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		viewModel.getGestionaire().run();
		viewModel.getGestionaireDonnees().run(viewModel.getGestionaire());
		mapIdCreat = new HashMap<>();

		GestionnaireDonnees gestionnaireData = viewModel.getGestionaireDonnees();

		for (Onglet onglet : viewModel.getGestionaire().getOnglets()) {
			// permet de retenir l'id quand un object à été charger (-1 =
			// nouveau)
			mapIdCreat.put(String.valueOf(onglet.getName()), -1);
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
			Button save = new Button("Sauvegarder");
			final Label label = new Label("Sélection : ");

			ArrayList<Object> cbList = new ArrayList<>();
			ArrayList<Integer> idList = new ArrayList<>();
			for (ObjectClass objectC : gestionnaireData.getObjets()) {
				if (objectC.getTypeClass().equals(tab.getText())) {
					cbList.add(objectC.getDonnees().get(0).getValue());
					// TODO change to ID
					idList.add((Integer) objectC.getId());
					System.out.println(objectC.getId());
				}
			}

			final ChoiceBox<Object> cb;
			if (cbList.size() > 0) {

				cb = new ChoiceBox<Object>(FXCollections.observableArrayList(cbList));// Texte

				cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					public void changed(ObservableValue ov, Number value, Number new_value) {
						// System.out.println(idList.get(new_value.intValue()));

						// chargement des donnée
						for (ObjectClass objectD : gestionnaireData.getObjets()) {							
							mapIdCreat.put(tab.getText(), idList.get(new_value.intValue()));
							// si bon numéro d'objet à charger
							if (objectD.getId() == idList.get(new_value.intValue())) {
								// selectiond de tous les paramètres
								for (DataObject data : objectD.getDonnees()) {

									// ********************************
									for (Node n : tab.getContent().lookupAll("ChoiceBox")) {
										if (n instanceof ChoiceBox) {
											if (n.getId() != null) {
												n.getId();
												if (Integer.parseInt(n.getId()) == data.getParametre().getId()) {
													Parametre p = viewModel.getGestionaire()
															.getParametre(data.getParametre().getId());
													if (p instanceof ParametreCombo) {
														((ChoiceBox) n).setValue(((ParametreCombo) p)
																.getParametre((int) data.getValue()).getLabel());
													}

												}
											}
										}
									}
									for (Node n : tab.getContent().lookupAll("CheckBox")) {
										if (n instanceof CheckBox) {
											n.getId();
											if (Integer.parseInt(n.getId()) == data.getParametre().getId()) {
												System.out.println("CheckBoxOpen");
												((CheckBox) n).setSelected((boolean) data.getValue());
											}
										}
									}
									for (Node n : tab.getContent().lookupAll("TextField")) {
										if (n instanceof TextField) {
											n.getId();
											if (Integer.parseInt(n.getId()) == data.getParametre().getId()) {
												ParametreInput paramI = (ParametreInput) data.getParametre();
												switch (paramI.getType()) {
												case DOUBLE:
													((TextField) n).setText(Double.toString((double) data.getValue()));
													break;
												case INTEGER:
													((TextField) n)
															.setText(Integer.toString((Integer) data.getValue()));
													break;
												case STRING:
													((TextField) n).setText((String) data.getValue());
													break;
												default:
													System.out.println("Erreur interne : mauvaise type input");
													break;
												}

											}
										}
									}

									// ********************************

								}
							}
						}
					}
				});
			} else {
				cb = new ChoiceBox<>();
			}
			// get base de donnée

			label.setVisible(false);
			cb.setVisible(false);

			creer.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					label.setVisible(false);
					cb.setVisible(false);
					 mapIdCreat.put(tab.getText(), -1);
					 for (Node n : tab.getContent().lookupAll("GridPane")) {
							if (n instanceof GridPane) {
								GridPane gride = (GridPane) n;
								if (gride.getId().contains("Sub")) {
									((GridPane) n).getChildren().clear();
								}
							}
						}
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

			save.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					HashMap<Integer, String> map = new HashMap<>();
					Boolean AsChampNull = false;
					HashMap<Integer, String> name = new HashMap<>();
					for (Node n : tab.getContent().lookupAll("TextField")) {
						boolean first = true;
						if (n instanceof TextField) {
							n.getId();
							// System.out.println(n.getId());
							Parametre p = viewModel.getGestionaire().getParametre(Integer.parseInt(n.getId()));
							System.out.println("id: " + p.getId() + " | value : " + ((TextField) n).getText());
							if (null != ((TextField) n).getText()) {
								if (first){
									name.put(p.getId(), ((TextField) n).getText());
									first = false;
								}
								else
									map.put(p.getId(), ((TextField) n).getText());
								
								
							} else {
								AsChampNull = true;
							}
						}
					}
					for (Node n : tab.getContent().lookupAll("ChoiceBox")) {
						if (n instanceof ChoiceBox) {
							if (n.getId() != null) {
								n.getId();
								// System.out.println(n.getId());
								Parametre p = viewModel.getGestionaire().getParametre(Integer.parseInt(n.getId()));
								System.out.println("id: " + p.getId() + " | value : " + ((ChoiceBox) n).getValue());
								if (null != ((ChoiceBox) n).getValue()) {
									int id = -1;
									for (Parametre param : ((ParametreCombo)p).getSelects()) {
										if(param.getLabel().equals(((ChoiceBox) n).getValue().toString())){
											id = param.getId();
										}
									}
									map.put(p.getId(), Integer.toString(id));
								} else {
									AsChampNull = true;
								}
							}
						}
					}
					for (Node n : tab.getContent().lookupAll("CheckBox")) {
						if (n instanceof CheckBox) {
							n.getId();
							// System.out.println(n.getId());
							Parametre p = viewModel.getGestionaire().getParametre(Integer.parseInt(n.getId()));
							System.out.println("id: " + p.getId() + " | value : " + ((CheckBox) n).isSelected());
							map.put(p.getId(), Boolean.toString(((CheckBox) n).isSelected()));

						}
					}
					System.out.println("map : " + mapIdCreat.get(tab.getText()));
					if (!AsChampNull) {
						System.out.println("save : "+mapIdCreat.get(tab.getText()));
						gestionnaireData.sauvegarde(mapIdCreat.get(tab.getText()), tab.getText(), map, name);
					} else {
						// do something to show the user is a fool
						System.out.println("crétin");
					}
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
			gridButton.add(save, 2, 0);

			gridButton.add(label, 3, 0);
			gridButton.add(cb, 4, 0);

			grid.add(gridParam, 0, 1);

			tab.setContent(grid);

		}

	}

	private int numGridi;

	// num grid strat à 2 pour correspondre à la grid de Param
	private void traitementParametre(ArrayList<Parametre> listeParam, GridPane grid, GridPane gridMain, int numGrid,
			Tab tab) {
		int i = 0;
		numGridi = numGrid;
		for (Parametre param : listeParam) {
			Label label = new Label(param.getLabel() + " : ");

			if (param.getTypePara() == typeParametre.INPUT) {

				ParametreInput paramI = (ParametreInput) param;
				if (paramI.getType() == typeInput.BOOLEAN) {
					CheckBox check = new CheckBox();
					check.setId(Integer.toString(param.getId()));

					// for Sparam
					check.selectedProperty().addListener(new ChangeListener<Boolean>() {
						public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {

							GridPane gridSub = new GridPane();
							if (new_val.booleanValue() == true) {

								gridSub.setId(check.getId() + "Sub");
								// TODO
								// traitementParametre(check.getParametres(),
								// gridSub, gridMain, numGridi + 1, tab);
							} else {
								for (Node n : tab.getContent().lookupAll("GridPane")) {
									if (n instanceof GridPane) {
										GridPane gride = (GridPane) n;
										if (gride.getId().equals(check.getId() + "Sub")) {
											((GridPane) n).getChildren().clear();
										}
									}
								}
							}

						}
					});

					grid.add(check, 1, i);
				} else {
					TextField input = new TextField();
					// afin de pouvoir le récuperé plus tard
					input.setId(Integer.toString(param.getId()));
					grid.add(input, 1, i);
				}

			} else if (param.getTypePara() == typeParametre.COMBO) {
				ParametreCombo paramCombo = (ParametreCombo) param;
				ChoiceBox<String> combo = new ChoiceBox<>();
				combo.setId(Integer.toString(paramCombo.getId()));

				ObservableList<String> selectlist = FXCollections.observableArrayList();
				for (Parametre selectAttribute : paramCombo.getSelects()) {
					ParametreSelect paramS = (ParametreSelect) selectAttribute;

					selectlist.add(paramS.getLabel());
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
							} else {
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
	}

}
