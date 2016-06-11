package DragDrop;

import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FluentViewLoader.FxmlViewStep;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainDragDrop extends Application {
	public static void main(String... args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try{
			stage.setTitle("DragDrop Application");

			FxmlViewStep<DragDropView, DragDropViewModel> view = FluentViewLoader.fxmlView(DragDropView.class);
		    ViewTuple<DragDropView, DragDropViewModel> viewTuple =	view.load();

		    Parent root = viewTuple.getView();
		    
		    stage.setScene(new Scene(root));
		    stage.setMaximized(true);
		    stage.show();
		    
		}catch(Exception e){
			System.out.println(e.toString());
		}
	}
}
