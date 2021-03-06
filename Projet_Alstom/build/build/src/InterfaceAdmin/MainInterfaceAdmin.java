package InterfaceAdmin;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;

import org.json.simple.parser.ParseException;

import ModelObject.GestionnaireConfig;
import de.saxsys.mvvmfx.FluentViewLoader;
import de.saxsys.mvvmfx.FluentViewLoader.FxmlViewStep;
import de.saxsys.mvvmfx.ViewTuple;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * The Class MainInterfaceAdmin.
 */
public class MainInterfaceAdmin extends Application {
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws FileNotFoundException the file not found exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 * @throws ParseException the parse exception
	 * @throws URISyntaxException the URI syntax exception
	 */
	public static void main(String... args)
			throws FileNotFoundException, IOException, ParseException, URISyntaxException {
		Application.launch(args);

	}

	/** {@inheritDoc} */
	@Override
	public void start(Stage stage) throws Exception {
		try {
			stage.setTitle("Projet GL52 - Interface Admin");

			FxmlViewStep<InterfaceAdminView, InterfaceAdminViewModel> view = FluentViewLoader
					.fxmlView(InterfaceAdminView.class);
			ViewTuple<InterfaceAdminView, InterfaceAdminViewModel> viewTuple = view.load();

			GestionnaireConfig GC = new GestionnaireConfig();
			GC.run();
			Parent root = viewTuple.getView();
			stage.setScene(new Scene(root));
			stage.show();

		} catch (Exception e) {
			System.out.println(e.toString());
		}

	}
}
