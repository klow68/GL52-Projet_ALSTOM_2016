package InterfaceAdmin;

import java.net.URL;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class InterfaceAdminView implements FxmlView<InterfaceAdminViewModel>, Initializable {

	@FXML
	private TabPane tabPaneAdmin;
	
	@InjectViewModel
    private InterfaceAdminViewModel viewModel;
	
	@Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tab tab = new Tab("Wagon");
        tabPaneAdmin.getTabs().add(tab);
    }
}
