package InterfaceAdmin;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.simple.parser.ParseException;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FluentViewLoader.FxmlViewStep;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainInterfaceAdmin extends Application {
	public static void main(String... args) throws FileNotFoundException, IOException, ParseException, URISyntaxException {
		Application.launch(args);
		
		
	}

	@Override
	public void start(Stage stage) throws Exception {
		try{
			stage.setTitle("Hello World Application");

			 FxmlViewStep<InterfaceAdminView, InterfaceAdminViewModel> view = FluentViewLoader.fxmlView(InterfaceAdminView.class);
		     ViewTuple<InterfaceAdminView, InterfaceAdminViewModel> viewTuple =	view.load();

		    Parent root = viewTuple.getView();
		    stage.setScene(new Scene(root));
		    stage.show();
		    
		    
		}catch(Exception e){
			System.out.println(e.toString());
		}
		
		
	}
}
