package InterfaceAdmin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
        
        File file = new File("src/main/resources/InterfaceAdmin/admin.json");
        JSONParser parser = new JSONParser();

		JSONArray a = null;
		try {
			a = (JSONArray) parser.parse(new FileReader(file));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		  for (Object o : a)
		  {
		    JSONObject voiture = (JSONObject) o;

		    String id = (String) voiture.get("id");
		    System.out.println(id);

		  }
    }
}
