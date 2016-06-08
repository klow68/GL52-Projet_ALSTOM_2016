package de.saxsys.mvvmfx;

import de.saxsys.mvvmfx.FluentViewLoader.FxmlViewStep;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {
	public static void main(String... args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try{
			stage.setTitle("Hello World Application");

			 FxmlViewStep<HelloWorldView, HelloWorldViewModel> view = FluentViewLoader.fxmlView(HelloWorldView.class);
		     ViewTuple<HelloWorldView, HelloWorldViewModel> viewTuple =	view.load();

		    Parent root = viewTuple.getView();
		    stage.setScene(new Scene(root));
		    stage.show();
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
}
