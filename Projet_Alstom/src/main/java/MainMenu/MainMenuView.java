package MainMenu;

import java.net.URL;
import java.util.ResourceBundle;

import InterfaceAdmin.InterfaceAdminView;
import InterfaceAdmin.InterfaceAdminViewModel;
import InterfaceTech.InterfaceTechView;
import InterfaceTech.InterfaceTechViewModel;
import ModelObject.GestionnaireConfig;
import ObjectData.GestionnaireDonnees;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import de.saxsys.mvvmfx.ViewTuple;
import de.saxsys.mvvmfx.FluentViewLoader.FxmlViewStep;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainMenuView implements FxmlView<MainMenuViewModel>, Initializable  {

	 @InjectViewModel
	    private MainMenuViewModel viewModel;
	@FXML
		private VBox ap;
	
	 
	 @FXML
	 private void changeWindowToAdmin(){
		 
		 Stage stage = new Stage();
		 FxmlViewStep<InterfaceAdminView, InterfaceAdminViewModel> view = FluentViewLoader.fxmlView(InterfaceAdminView.class);
	     ViewTuple<InterfaceAdminView, InterfaceAdminViewModel> viewTuple =	view.load();
	     Parent root = viewTuple.getView();
	     stage.setScene(new Scene(root));
	     ((Stage)ap.getScene().getWindow()).close();
	     stage.show();
	     
	     
	 }
	 @FXML
	 private void changeWindowToTechnicien(){
		 Stage stage = new Stage();
		 FxmlViewStep<InterfaceTechView, InterfaceTechViewModel> view = FluentViewLoader.fxmlView(InterfaceTechView.class);
	     ViewTuple<InterfaceTechView, InterfaceTechViewModel> viewTuple =	view.load();
	     Parent root = viewTuple.getView();
	     stage.setScene(new Scene(root));
	     ((Stage)ap.getScene().getWindow()).close();
	     stage.show();
		 
	 }
	
	 
	public void initialize(URL location, ResourceBundle resources) {
		 GestionnaireConfig GC = new  GestionnaireConfig();
		 GC.run();
		//GC.test();
		 GestionnaireDonnees GD = new GestionnaireDonnees();
		 GD.run(GC);
		 GD.test();
	}

}
