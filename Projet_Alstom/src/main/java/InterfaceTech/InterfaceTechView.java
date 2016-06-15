package InterfaceTech;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ModelObject.GestionnaireConfig;
import ObjectData.DataObject;
import ObjectData.GestionnaireDonnees;
import ObjectData.ObjectClass;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

/**
 * The Class InterfaceTechView.
 */
public class InterfaceTechView implements FxmlView<InterfaceTechViewModel>, Initializable {

	/** The pane. */
	@FXML
	private Pane pane;

	/** The scrollpane. */
	@FXML
	private ScrollPane scrollpane;

	/** The vbox. */
	@FXML
	private VBox vbox;

	/** The convoi label. */
	@FXML
	private Label convoiLabel;

	/** The convoi box. */
	@FXML
	public HBox convoiBox;

	/** The elements label. */
	@FXML
	private Label elementsLabel;

	/** The elements box. */
	@FXML
	private HBox elementsBox;

	/** The gestion label. */
	@FXML
	private Label gestionLabel;

	/** The gestion box. */
	@FXML
	private HBox gestionBox;

	/** The charger convoi. */
	@FXML
	private Button chargerConvoi;

	/** The save. */
	@FXML
	private Button save;

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
	private InterfaceTechViewModel viewModel;

	/** The convoi list. */
	private List<DraggableImageView> convoiList;

	/** The elements list. */
	private List<DraggableImageView> elementsList;

	/** The instance. */
	public InterfaceTechView instance = this;

	/** {@inheritDoc} */
	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {

		save.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				boolean valide = false;
				valide = checkConvoi();

				if (valide) {

					List<DraggableImageView> convoiSave = new ArrayList<DraggableImageView>();
					List<Node> convoiNodes = convoiBox.getChildren();

					for (Node node : convoiNodes) {
						if (((DraggableImageView) node).getElemType() != ImageViewType.SEPARATOR)
							convoiSave.add((DraggableImageView) node);
					}

					List<ObjectClass> objects = new ArrayList<ObjectClass>();
					for (DraggableImageView dg : convoiSave) {

						// System.out.println(dg.getElemType());
						System.out.println("RECUPERATION DES OBJECT CLASS");
						ObjectClass object = (ObjectClass) dg.getUserData();

						System.out.println("ID : " + object.getId());
						System.out.println("Type : " + object.getTypeClass());
						ArrayList<DataObject> datas = object.getDonnees();
						System.out.println("Données : ");
						for (DataObject data : datas) {
							System.out
									.println("Value : " + data.getValue() + "(" + data.getParametre().getLabel() + ")");
						}
						System.out.println("URL : " + object.getURL());
						System.out.println("URL NULL ? WTF");

						objects.add(object);

					}

					viewModel.setConvoiList(convoiSave);

					GestionnaireDonnees gd = new GestionnaireDonnees();
					GestionnaireConfig gc = new GestionnaireConfig();
					gc.run();
					gd.run(gc);

					TextInputDialog dialog = new TextInputDialog("newConvoi");
					dialog.setTitle("Nom pour le convoi");
					dialog.setHeaderText("Nommez le convoi");
					dialog.setContentText("Entrez le nom du convoi :");

					// Traditional way to get the response value.
					Optional<String> result = dialog.showAndWait();
					String nomConvoi = "";
					if (result.isPresent()) {
						System.out.println("Nom du convoi: " + result.get());
						nomConvoi = result.get();
					}

					// The Java 8 way to get the response value (with lambda
					// expression).
					result.ifPresent(name -> System.out.println("Your name: " + name));

					gd.exportToJson(nomConvoi, objects);
				} else {

					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Convoi non valide");
					alert.setHeaderText("Le convoi n'est pas valide");
					alert.setContentText(
							"Le convoi n'est pas valide, le train doit avoir une locomotique a une extremité minimum");
					alert.setResizable(true);
					alert.getDialogPane().setPrefSize(400, 200);
					alert.showAndWait();

				}
			}

		});

		chargerConvoi.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {

				chargerElements();

				GestionnaireDonnees gd = new GestionnaireDonnees();
				GestionnaireConfig gc = new GestionnaireConfig();
				gc.run();
				gd.run(gc);

				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Choisissez un convoi");
				fileChooser.setInitialDirectory(new File("src/main/resources/data/convois"));
				File jsonFile = fileChooser.showOpenDialog(null);

				List<Integer> elementsIds = gd.parseJsonConvoi(jsonFile);

				convoiBox.getChildren().clear();

				convoiBox.getChildren()
						.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, instance));
				ObservableList<Node> elements = elementsBox.getChildren();

				for (Integer elementId : elementsIds) {
					for (int i = 0; i < elements.size(); i++) {

						Node node = elements.get(i);
						ObjectClass oc = (ObjectClass) node.getUserData();
						int id = oc.getId();
						if (id == elementId.intValue()) {

							convoiBox.getChildren().add((DraggableImageView) elementsBox.getChildren()
									.get(elementsBox.getChildren().indexOf(node)));
							convoiBox.getChildren().add(new DraggableImageView("DragDrop/Images/insert.png",
									ImageViewType.SEPARATOR, instance));

						}
					}
				}

			}

		});

		convoiList = new ArrayList<DraggableImageView>();
		convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));

		chargerElements();

		List<DraggableImageView> gestionList = new ArrayList<DraggableImageView>();
		gestionList.add(new DraggableImageView("DragDrop/Images/corbeille.png", ImageViewType.RECYCLE_BIN, this));

		viewModel.setConvoiList(convoiList);
		viewModel.setElementsList(elementsList);

		convoiBox.getChildren().addAll(convoiList);
		elementsBox.getChildren().addAll(elementsList);
		gestionBox.getChildren().addAll(gestionList);

	}

	/**
	 * Charger elements.
	 */
	private void chargerElements() {

		elementsList = new ArrayList<DraggableImageView>();
		elementsBox.getChildren().clear();

		// Chargement des elements
		GestionnaireDonnees gestionnaireDonnees = new GestionnaireDonnees();
		GestionnaireConfig GC = new GestionnaireConfig();
		GC.run();
		gestionnaireDonnees.run(GC);

		ArrayList<ObjectClass> objects = gestionnaireDonnees.getObjets();

		for (ObjectClass object : objects) {
			ArrayList<DataObject> datas = object.getDonnees();

			DraggableImageView dg = new DraggableImageView(object.getURL(), ImageViewType.IMAGE, this);
			dg.setUserData(object);

			elementsBox.getChildren().add(dg);

		}

	}

	/**
	 * Update list.
	 *
	 * @param id
	 *            the id
	 */
	public void updateList(int id) {

		convoiBox.getChildren().add(id - 1,
				new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
		convoiBox.getChildren().add(id + 1,
				new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));

	}

	/**
	 * Adds the separator.
	 */
	public void addSeparator() {

		convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));

	}

	/**
	 * Check convoi.
	 *
	 * @return true, if successful
	 */
	private boolean checkConvoi() {

		if (convoiBox.getChildren().size() < 3)
			return false;

		String type1 = ((ObjectClass) convoiBox.getChildren().get(1).getUserData()).getTypeClass();
		String type2 = ((ObjectClass) convoiBox.getChildren().get(convoiBox.getChildren().size() - 2).getUserData())
				.getTypeClass();

		if ((type1.equals("Loco")) || (type2.equals("Loco")))
			return true;
		else
			return false;
	}

	/**
	 * Gets the convoi box.
	 *
	 * @return the convoi box
	 */
	public HBox getConvoiBox() {
		return convoiBox;
	}

	/**
	 * Sets the convoi box.
	 *
	 * @param convoiBox
	 *            the new convoi box
	 */
	public void setConvoiBox(HBox convoiBox) {
		this.convoiBox = convoiBox;
	}

	/**
	 * Gets the elements box.
	 *
	 * @return the elements box
	 */
	public HBox getElementsBox() {
		return elementsBox;
	}

	/**
	 * Sets the elements box.
	 *
	 * @param elementsBox
	 *            the new elements box
	 */
	public void setElementsBox(HBox elementsBox) {
		this.elementsBox = elementsBox;
	}

	/**
	 * Gets the gestion box.
	 *
	 * @return the gestion box
	 */
	public HBox getGestionBox() {
		return gestionBox;
	}

	/**
	 * Sets the gestion box.
	 *
	 * @param gestionBox
	 *            the new gestion box
	 */
	public void setGestionBox(HBox gestionBox) {
		this.gestionBox = gestionBox;
	}

}
