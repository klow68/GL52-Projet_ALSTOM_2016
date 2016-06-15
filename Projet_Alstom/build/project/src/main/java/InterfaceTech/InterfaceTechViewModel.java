package InterfaceTech;

import java.util.ArrayList;
import java.util.List;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The Class InterfaceTechViewModel.
 */
public class InterfaceTechViewModel implements ViewModel {

	/** The convoi list. */
	private List<DraggableImageView> convoiList = new ArrayList<DraggableImageView>();

	/** The elements list. */
	private List<DraggableImageView> elementsList = new ArrayList<DraggableImageView>();

	/**
	 * Sets the convoi list.
	 *
	 * @param convoiList
	 *            the new convoi list
	 */
	public void setConvoiList(List<DraggableImageView> convoiList) {
		this.convoiList = convoiList;
	}

	/**
	 * Sets the elements list.
	 *
	 * @param elementsList
	 *            the new elements list
	 */
	public void setElementsList(List<DraggableImageView> elementsList) {
		this.elementsList = elementsList;
	}

	/**
	 * Close app.
	 */
	public void closeApp() {
		Platform.exit();
		System.exit(0);
	}

	/**
	 * About.
	 */
	public void about() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Version 1.0");
		alert.setHeaderText("A propos");
		alert.setContentText("\tDAUDAN Florian \t \tDUPONT Arnaud \t \n\tGUIMARAES Patrick \t \tRENARD Lucas \t");

		alert.showAndWait();
	}

}