package InterfaceTech;

import java.util.ArrayList;
import java.util.List;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class InterfaceTechViewModel implements ViewModel {

    private List<DraggableImageView> convoiList = new ArrayList<DraggableImageView>();
    private List<DraggableImageView> elementsList = new ArrayList<DraggableImageView>();
    
	public void setConvoiList(List<DraggableImageView> convoiList) {
		this.convoiList = convoiList;
	}
	public void setElementsList(List<DraggableImageView> elementsList) {
		this.elementsList = elementsList;
	}
    
    
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
    
}