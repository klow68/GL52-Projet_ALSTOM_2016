package MainMenu;

import de.saxsys.mvvmfx.ViewModel;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The Class MainMenuViewModel.
 */
public class MainMenuViewModel implements ViewModel {
	
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
