package InterfaceAdmin;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;

import ModelObject.Onglet;
import ObjectData.GestionnaireDonnees;
import ObjectData.ObjectClass;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

/**
 * The Class InterfaceAdminView.
 */
public class InterfaceAdminView implements FxmlView<InterfaceAdminViewModel>, Initializable {

	/** The tab pane admin. */
	@FXML
	private TabPane tabPaneAdmin;

	/**
	 * Close app.
	 */
	@FXML
	private void closeApp() {
		viewModel.closeApp();
	}

	/**
	 * About.
	 */
	@FXML
	private void about() {
		viewModel.about();
	}

	/** The view model. */
	@InjectViewModel
	private InterfaceAdminViewModel viewModel;

	/** The map id creat. */
	private HashMap<String, Integer> mapIdCreat;

	/** {@inheritDoc} */
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
			error.setStyle("-fx-color: red");

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
						viewModel.chargerObject(new_value, urlButton, gestionnaireData, mapIdCreat, tab, idList,
								fileImage);
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
					viewModel.UrlButtonAction(fileImage, error);
				}
			});

			export.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					viewModel.exportAction(gestionnaireData, mapIdCreat, tab);
				}
			});

			importB.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					viewModel.importButtonAction(error, gestionnaireData, tab, cbList, idList, onglet, cb);
				}
			});

			creer.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					viewModel.creerAction(tab, mapIdCreat, onglet, cb, urlButton, label);
				}
			});

			ouvrir.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					viewModel.ouvrirAction(tab, cb, label, urlButton, onglet);
				}
			});

			save.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					viewModel.saveAction(tab, mapIdCreat, gestionnaireData, error);
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
			gridButton.add(fileImage, 1, 1, 6, 1);

			gridButton.add(error, 0, 2, 6, 1);

			grid.add(gridParam, 0, 1);

			ScrollPane sp = new ScrollPane();
			sp.setFitToWidth(true);
			sp.setContent(grid);

			tab.setContent(sp);

		}

	}

}
