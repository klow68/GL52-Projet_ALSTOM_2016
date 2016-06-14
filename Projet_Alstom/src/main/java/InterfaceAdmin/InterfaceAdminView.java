package InterfaceAdmin;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import javax.activation.MimetypesFileTypeMap;

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
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

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
			Button export = new Button("Export");
			Button importB = new Button("Import");
			Button urlButton = new Button("url");
			Label fileImage = new Label();
			fileImage.setWrapText(true);
			fileImage.setId("urlId");
			Label error = new Label();
			error.setWrapText(true);
			error.setTextFill(Color.RED);

			urlButton.setVisible(false);
			final Label label = new Label("Sélection : ");

			ArrayList<Object> cbList = new ArrayList<>();
			ArrayList<Integer> idList = new ArrayList<>();
			for (ObjectClass objectC : gestionnaireData.getObjets()) {
				if (objectC.getTypeClass().equals(tab.getText())) {
					cbList.add(objectC.getDonnees().get(0).getValue());
					idList.add((Integer) objectC.getId());
				}
			}

			final ChoiceBox<Object> cb;
			if (cbList.size() > 0) {

				cb = new ChoiceBox<Object>(FXCollections.observableArrayList(cbList));// Texte

				cb.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
					public void changed(ObservableValue ov, Number value, Number new_value) {
						if (new_value.intValue() != -1) {
							urlButton.setVisible(true);
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
														((TextField) n)
																.setText(Double.toString((double) data.getValue()));
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
					}
				});
			} else {
				cb = new ChoiceBox<>();
			}
			// get base de donnée

			label.setVisible(false);
			cb.setVisible(false);

			urlButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Choisir une image");
					File selectedFile = fileChooser.showOpenDialog(null);
					if (selectedFile != null) {
						String mimetype = new MimetypesFileTypeMap().getContentType(selectedFile);
						String type = mimetype.split("/")[0];
						if (type.equals("image")) {
							fileImage.setText(selectedFile.getName());
							error.setText("");
						} else {
							error.setText("Erreur : Ce n'est pas une image");
							fileImage.setText("");
						}
					}
				}
			});

			export.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					gestionnaireData.exporterObjet(mapIdCreat.get(tab.getText()));
				}
			});

			importB.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Choisir une image");
					File selectedFile = fileChooser.showOpenDialog(null);
					if (selectedFile != null) {
						String name = selectedFile.getName();
						if (name.substring(name.lastIndexOf(".") + 1).equals("json")) {
							gestionnaireData.importerObject(selectedFile.getPath());
							error.setText("");
						} else {
							error.setText("Erreur : le fichier n'est pas un fichier json");
						}

					}
				}
			});

			creer.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					label.setVisible(false);
					cb.setVisible(false);
					urlButton.setVisible(true);
					mapIdCreat.put(tab.getText(), -1);
					for (Node n : tab.getContent().lookupAll("GridPane")) {
						if (n instanceof GridPane) {
							GridPane gride = (GridPane) n;
							if (gride.getId().contains("Sub")) {
								((GridPane) n).getChildren().clear();
							}
							if (gride.getId().equals("gridParam" + tab.getText())) {
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
					cb.valueProperty().set(null);
					urlButton.setVisible(true);
					for (Node n : tab.getContent().lookupAll("GridPane")) {
						if (n instanceof GridPane) {
							GridPane gride = (GridPane) n;
							if (gride.getId().contains("Sub")) {
								((GridPane) n).getChildren().clear();
							}
							if (gride.getId().equals("gridParam" + tab.getText())) {
								((GridPane) n).getChildren().clear();
							}
						}
					}
					afficherOnglet(tab, onglet);
				}
			});

			save.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					Boolean AsChampNull = false;
					ArrayList<String[]> saveResult = new ArrayList<>();
					ArrayList<String[]> textResult = new ArrayList<>();
					for (Node n : tab.getContent().lookupAll("TextField")) {

						if (n instanceof TextField) {
							n.getId();
							Parametre p = viewModel.getGestionaire().getParametre(Integer.parseInt(n.getId()));
							if (null != ((TextField) n).getText()) {
								String[] toAdd = { Integer.toString(p.getId()), ((TextField) n).getText() };
								textResult.add(toAdd);
							} else {
								AsChampNull = true;
							}
						}
					}
					if (textResult.size() > 0) {
						saveResult.add(textResult.get(0));
						textResult.remove(0);
					}
					for (Node n : tab.getContent().lookupAll("ChoiceBox")) {
						if (n instanceof ChoiceBox) {
							if (n.getId() != null) {
								n.getId();
								Parametre p = viewModel.getGestionaire().getParametre(Integer.parseInt(n.getId()));
								if (null != ((ChoiceBox) n).getValue()) {
									int id = -1;
									for (Parametre param : ((ParametreCombo) p).getSelects()) {
										if (param.getLabel().equals(((ChoiceBox) n).getValue().toString())) {
											id = param.getId();
										}
									}
									String[] toAdd = { Integer.toString(p.getId()), Integer.toString(id) };
									saveResult.add(toAdd);
								} else {
									AsChampNull = true;
								}
							}
						}
					}
					for (Node n : tab.getContent().lookupAll("CheckBox")) {
						if (n instanceof CheckBox) {
							n.getId();
							Parametre p = viewModel.getGestionaire().getParametre(Integer.parseInt(n.getId()));
							String[] toAdd = { Integer.toString(p.getId()),
									Boolean.toString(((CheckBox) n).isSelected()) };
							saveResult.add(toAdd);
						}
					}
					saveResult.addAll(textResult);

					String UrlImage = "";
					for (Node n : tab.getContent().lookupAll("Label")) {
						if (n instanceof Label) {
							n.getId();
							if (n.getId() != null && n.getId().equals("urlId")) {
								UrlImage = ((Label) n).getText();
							}
						}
					}

					if (!AsChampNull && !UrlImage.equals("")) {
						error.setText("");
						// gestionnaireData.sauvegarde(mapIdCreat.get(tab.getText()),
						// tab.getText(), saveResult, UrlImage);
					} else {
						// do something to show the user is a fool
						String s = "Erreur : ";
						if (UrlImage.equals("")) {
							s = "Il manque l'image. ";
						}
						if(AsChampNull){
							s += "Un des champs n'est pas remplie. ";
						}
						error.setText(s);
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
			gridButton.add(export, 6, 0);
			gridButton.add(importB, 7, 0);

			gridButton.add(urlButton, 0, 1);
			gridButton.add(fileImage, 1, 1);

			gridButton.add(error, 0, 2);

			grid.add(gridParam, 0, 1);

			ScrollPane sp = new ScrollPane();
			sp.setFitToWidth(true);
			sp.setContent(grid);

			tab.setContent(sp);

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
					ParametreInput p = (ParametreInput) param;
					if (typeInput.DOUBLE == p.getType()) {
						input.setTextFormatter(new TextFormatter<>(new NumberStringConverter()));
					} else if (typeInput.INTEGER == p.getType()) {
						input.setTextFormatter(new TextFormatter<>(new IntegerStringConverter()));
					} else if (typeInput.STRING == p.getType()) {

					}
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
