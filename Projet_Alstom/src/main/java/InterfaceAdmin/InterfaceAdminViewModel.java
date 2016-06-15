package InterfaceAdmin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.activation.MimetypesFileTypeMap;

import ModelObject.GestionnaireConfig;
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
import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.NumberStringConverter;

public class InterfaceAdminViewModel implements ViewModel {

	private GestionnaireConfig gestionaire;

	private GestionnaireDonnees gestionaireDonnees;

	public InterfaceAdminViewModel() {
		gestionaire = new GestionnaireConfig();
		gestionaireDonnees = new GestionnaireDonnees();
	}

	public GestionnaireDonnees getGestionaireDonnees() {
		return gestionaireDonnees;
	}

	public GestionnaireConfig getGestionaire() {
		return gestionaire;
	}

	/************************ MENU ************************/

	public void closeApp() {
		Platform.exit();
		System.exit(0);
	}

	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Version 1.0");
		alert.setHeaderText("A propos");
		alert.setContentText("\tDAUDAN Florian \t \tDUPONT Arnaud \t \n\tGUIMARAES Patrick \t \tRENARD Lucas \t");

		alert.showAndWait();
	}

	/*****************************************************/

	public void chargerObject(Number new_value, Button urlButton, GestionnaireDonnees gestionnaireData,
			HashMap<String, Integer> mapIdCreat, Tab tab, ArrayList<Integer> idList, Label fileImage) {
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
										Parametre p = this.getGestionaire().getParametre(data.getParametre().getId());
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
										((TextField) n).setText(Integer.toString((Integer) data.getValue()));
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
						fileImage.setText(objectD.getURL());
						// ********************************

					}
				}
			}
		}
	}

	public void UrlButtonAction(Label fileImage, Label error) {
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

	public void exportAction(GestionnaireDonnees gestionnaireData, HashMap<String, Integer> mapIdCreat, Tab tab) {
		gestionnaireData.exporterObjet(mapIdCreat.get(tab.getText()));
	}

	public void importButtonAction(Label error, GestionnaireDonnees gestionnaireData, Tab tab, ArrayList<Object> cbList,
			ArrayList<Integer> idList, Onglet onglet, ChoiceBox<Object> cb) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choisir une image");
		File selectedFile = fileChooser.showOpenDialog(null);
		if (selectedFile != null) {
			String name = selectedFile.getName();
			if (name.substring(name.lastIndexOf(".") + 1).equals("json")) {
				error.setText("");
				// ajouter
				int id = gestionnaireData.importerObject(selectedFile.getPath());
				// refresh data
				gestionnaireData.run(this.getGestionaire());
				// afficher les valeur a remplir

				afficherOnglet(tab, onglet);

				// modifier la selection de cb
				// avec la val ajouter
				ObjectClass oc = gestionnaireData.getObject(id);

				if (oc.getTypeClass().equals(tab.getText())) {
					cbList.add(oc.getDonnees().get(0).getValue());
					idList.add((Integer) oc.getId());
				}
				cb.getItems().add(oc.getDonnees().get(0).getValue());
				cb.setValue(oc.getDonnees().get(0).getValue());

			} else {
				error.setText("Erreur : le fichier n'est pas un fichier json");
			}

		}
	}

	public void creerAction(Tab tab, HashMap<String, Integer> mapIdCreat, Onglet onglet, ChoiceBox<Object> cb,
			Button urlButton, Label label) {
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

	public void ouvrirAction(Tab tab, ChoiceBox<Object> cb, Label label, Button urlButton, Onglet onglet) {
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

	public void saveAction(Tab tab, HashMap<String, Integer> mapIdCreat, GestionnaireDonnees gestionnaireData,
			Label error) {
		Boolean AsChampNull = false;
		ArrayList<String[]> saveResult = new ArrayList<>();
		ArrayList<String[]> textResult = new ArrayList<>();
		for (Node n : tab.getContent().lookupAll("TextField")) {

			if (n instanceof TextField) {
				n.getId();
				Parametre p = this.getGestionaire().getParametre(Integer.parseInt(n.getId()));
				if (null != ((TextField) n).getText() && !((TextField) n).getText().equals("")) {
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
					Parametre p = this.getGestionaire().getParametre(Integer.parseInt(n.getId()));
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
				Parametre p = this.getGestionaire().getParametre(Integer.parseInt(n.getId()));
				String[] toAdd = { Integer.toString(p.getId()), Boolean.toString(((CheckBox) n).isSelected()) };
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
			gestionnaireData.sauvegarde(mapIdCreat.get(tab.getText()), tab.getText(), UrlImage, saveResult);
		} else {
			// do something to show the user is a fool
			String s = "Erreur : ";
			if (UrlImage.equals("")) {
				s = "Il manque l'image. ";
			}
			if (AsChampNull) {
				s += "Un des champs n'est pas remplie. ";
			}
			error.setText(s);
		}
	}

	/**********************************************************/

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
