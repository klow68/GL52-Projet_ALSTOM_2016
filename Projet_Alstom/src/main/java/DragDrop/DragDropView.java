package DragDrop;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import ModelObject.GestionnaireConfig;
import ObjectData.DataObject;
import ObjectData.GestionnaireDonnees;
import ObjectData.ObjectClass;
import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

public class DragDropView implements FxmlView<DragDropViewModel>, Initializable  {

	@FXML
	private Pane pane;
	
	@FXML
	private ScrollPane scrollpane;
	
	@FXML
	private VBox vbox;
	
    @FXML
    private Label convoiLabel;
    
    @FXML
    public HBox convoiBox;
    
    @FXML
    private Label elementsLabel;
    
    @FXML
    private HBox elementsBox;
    
    @FXML
    private Label gestionLabel;
    
    @FXML
    private HBox gestionBox;
    
    @FXML
    private Button chargerConvoi;
    
//    @FXML
//    private Button chargerElements;
    
    @FXML
    private Button save;
    
//    @FXML
//    private Button cancel;

    @InjectViewModel
    private DragDropViewModel viewModel;
    
    private List<DraggableImageView> convoiList;
    private List<DraggableImageView> elementsList;

    public DragDropView instance = this;
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	save.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("SAVE THE CONVOI");
	    		
				boolean valide = false;
				valide = checkConvoi();
				
				if(valide) {
				
		    		List<DraggableImageView> convoiSave = new ArrayList<DraggableImageView>();
		    		List<Node> convoiNodes = convoiBox.getChildren();
		    		
		    		for(Node node : convoiNodes) {
		    			if(((DraggableImageView) node).getElemType() != ImageViewType.SEPARATOR) convoiSave.add((DraggableImageView) node);
		    		}
		    		
		    		List<ObjectClass> objects = new ArrayList<ObjectClass>();
		    		for(DraggableImageView dg : convoiSave) {
		    			
	//	    			System.out.println(dg.getElemType());
		    			System.out.println("RECUPERATION DES OBJECT CLASS");
		    			ObjectClass object = (ObjectClass) dg.getUserData();
		    			
		    			System.out.println("ID : " + object.getId());
		    			System.out.println("Type : " + object.getTypeClass());
		    			ArrayList<DataObject> datas = object.getDonnees();
		    			System.out.println("Données : ");
		    			for(DataObject data : datas) {
		    				System.out.println("Value : " + data.getValue() + "(" + data.getParametre().getLabel() + ")");
		    			}
		    			System.out.println("URL : " + object.getURL());
		    			System.out.println("URL NULL ? WTF");
		    			
		    			objects.add(object);
		    			
		    		}
		    		
		    		viewModel.setConvoiList(convoiSave);
		    		
		    		GestionnaireDonnees gd = new GestionnaireDonnees();
		    		GestionnaireConfig gc = new GestionnaireConfig();
		    		gc.run();
		    		gd.run(gc);
		    		
		    		TextInputDialog dialog = new TextInputDialog("newConvoi");
		    		dialog.setTitle("Nom pour le convoi");
		    		dialog.setHeaderText("Nommez le convoi");
		    		dialog.setContentText("Entrez le nom du convoi :");
	
		    		// Traditional way to get the response value.
		    		Optional<String> result = dialog.showAndWait();
		    		String nomConvoi = "";
		    		if (result.isPresent()){
		    		    System.out.println("Nom du convoi: " + result.get());
		    		    nomConvoi = result.get();
		    		}
	
		    		// The Java 8 way to get the response value (with lambda expression).
		    		result.ifPresent(name -> System.out.println("Your name: " + name));
		    		
		    		gd.exportToJson(nomConvoi, objects);
				} else {
					
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Convoi non valide");
					alert.setHeaderText("Le convoi n'est pas valide");
					alert.setContentText("Le convoi n'est pas valide, le train doit avoir une locomotique a une extremité minimum");
					alert.showAndWait();
					
				}
			}

			
		});
    	
    	chargerConvoi.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				
//				elementsBox.getChildren().clear();
				chargerElements();
//				elementsBox.getChildren().addAll(convoiBox.getChildren());
				
				GestionnaireDonnees gd = new GestionnaireDonnees();
	    		GestionnaireConfig gc = new GestionnaireConfig();
	    		gc.run();
	    		gd.run(gc);
	    		
	    		FileChooser fileChooser = new FileChooser();
	    		fileChooser.setTitle("Choisissez un convoi");
	    		fileChooser.setInitialDirectory(new File("src/main/resources/data/convois"));
	    		File jsonFile = fileChooser.showOpenDialog(null);
	    		
	    		List<Integer> elementsIds = gd.parseJsonConvoi(jsonFile);
	    		
	    		for(Integer i : elementsIds) System.out.println(i);
	    		
	    		convoiBox.getChildren().clear();
	    		
//	    		ObservableList<Node> elements = elementsBox.getChildren();
//	    		 convoi = convoiBox.getChildren();
//	    		convoiList.clear();
	    		convoiBox.getChildren().add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, instance));
	    		ObservableList<Node> elements = elementsBox.getChildren();
//	    		elements.addAll(elementsBox.getChildren());
	    		for(Integer elementId : elementsIds) {
	    			for(int i = 0; i < elements.size(); i++) {
	    				
	    				Node node = elements.get(i);
	    				ObjectClass oc = (ObjectClass) node.getUserData();
	    				int id = oc.getId();
	    				if(id == elementId.intValue()) {
	    					
	    					System.out.println("AJOUT DE ELEMENT ID = " + id);
	    					
	    					
	    					System.out.println("ADD TO CONVOI BOX FROM ELEMENTS BOX");
	    					convoiBox.getChildren().add((DraggableImageView) elementsBox.getChildren().get(elementsBox.getChildren().indexOf(node)));
	    					System.out.println("REMOVE FROM ELEMENTS BOX");
//	    					elementsBox.getChildren().remove(node);
	    					
	    					System.out.println("ADD SEPARATOR");
	    					convoiBox.getChildren().add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, instance));
	    					
	    				}
	    			}
	    		}
				
			}	
				
		});
    	
    	
        
    	convoiList = new ArrayList<DraggableImageView>();
    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
//    	convoiList.add(new DraggableImageView("DragDrop/Images/voiture.jpg", ImageViewType.IMAGE, this));
//    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
//    	convoiList.add(new DraggableImageView("DragDrop/Images/wagon.jpg", ImageViewType.IMAGE, this));
//    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
//    	convoiList.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE, this));
//    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
//    	convoiList.add(test);

    	
    	
    	chargerElements();
    	
//    	elementsList.add(new DraggableImageView("DragDrop/Images/voiture.jpg", ImageViewType.IMAGE, this));
//    	elementsList.add(new DraggableImageView("DragDrop/Images/wagon.jpg", ImageViewType.IMAGE, this));
//    	elementsList.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE, this));
//    	elementsList.add(new DraggableImageView("DragDrop/Images/wagon.jpg", ImageViewType.IMAGE, this));
//    	elementsList.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE, this));
    	
    	List<DraggableImageView> gestionList = new ArrayList<DraggableImageView>();
    	gestionList.add(new DraggableImageView("DragDrop/Images/corbeille.png", ImageViewType.RECYCLE_BIN, this));
    	
    	
    	
    	viewModel.setConvoiList(convoiList);
    	viewModel.setElementsList(elementsList);
    	
    	convoiBox.getChildren().addAll(convoiList);
    	elementsBox.getChildren().addAll(elementsList);
    	gestionBox.getChildren().addAll(gestionList);
    	
    }

	private void chargerElements() {
		// TODO Auto-generated method stub
		
		elementsList = new ArrayList<DraggableImageView>();
		elementsBox.getChildren().clear();
		System.out.println("Chargement des elements");
		GestionnaireDonnees gestionnaireDonnees = new GestionnaireDonnees();
		GestionnaireConfig GC = new GestionnaireConfig();
		GC.run();
		gestionnaireDonnees.run(GC);
		
		ArrayList<ObjectClass> objects = gestionnaireDonnees.getObjets();
		
		for(ObjectClass object : objects) {
			System.out.println("ID : " + object.getId());
			System.out.println("Type : " + object.getTypeClass());
			ArrayList<DataObject> datas = object.getDonnees();
			System.out.println("Données : ");
			for(DataObject data : datas) {
				System.out.println("Value : " + data.getValue() + "(" + data.getParametre().getLabel() + ")");
			}
			System.out.println("URL : " + object.getURL());
			System.out.println("URL NULL ? WTF");
			DraggableImageView dg = new DraggableImageView(object.getURL(), ImageViewType.IMAGE, this);
			dg.setUserData(object);
//			elementsList.add(dg);
			elementsBox.getChildren().add(dg);
			
			
		}
		
//		elementsBox.getChildren().addAll(elementsList);
	}

	public void updateList(int id) {

		System.out.println("UPDATE LIST : adding separators");
		convoiBox.getChildren().add(id-1, new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
		convoiBox.getChildren().add(id+1, new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
		
		
	}
	
	@FXML
	public void saveConvoi() {
		
		System.out.println("SAVE THE CONVOI !");
		
		List<DraggableImageView> convoiSave = new ArrayList<DraggableImageView>();
		List<Node> convoiNodes = convoiBox.getChildren();
		
		for(Node node : convoiNodes) {
			convoiSave.add((DraggableImageView) node);
		}
		
		for(DraggableImageView dg : convoiSave) {
			
//			System.out.println(dg.getElemType());
			System.out.println("RECUPERATION DES OBJECT CLASS");
			ObjectClass object = (ObjectClass) dg.getUserData();
			
			System.out.println("ID : " + object.getId());
			System.out.println("Type : " + object.getTypeClass());
			ArrayList<DataObject> datas = object.getDonnees();
			System.out.println("Données : ");
			for(DataObject data : datas) {
				System.out.println("Value : " + data.getValue() + "(" + data.getParametre().getLabel() + ")");
			}
			System.out.println("URL : " + object.getURL());
			System.out.println("URL NULL ? WTF");
			
		}
		
		viewModel.setConvoiList(convoiSave);
		
	}
	
	public void addSeparator() {
		
		System.out.println("ADD SEPARATOR");
		convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
		
	}

	private boolean checkConvoi() {
		
		if(convoiBox.getChildren().size() < 3) return false;
		
		System.out.println(((ObjectClass) convoiBox.getChildren().get(1).getUserData()).getTypeClass() + " : " + 1);
		System.out.println(((ObjectClass) convoiBox.getChildren().get(convoiBox.getChildren().size()-2).getUserData()).getTypeClass() + " : " + (convoiBox.getChildren().size()-2));
		String type1 = ((ObjectClass) convoiBox.getChildren().get(1).getUserData()).getTypeClass();
		String type2 = ((ObjectClass) convoiBox.getChildren().get(convoiBox.getChildren().size()-2).getUserData()).getTypeClass();
		
		if((type1.equals("Loco")) || (type2.equals("Loco"))) return true;
		else return false;
	}
	
	public HBox getConvoiBox() {
		return convoiBox;
	}

	public void setConvoiBox(HBox convoiBox) {
		this.convoiBox = convoiBox;
	}

	public HBox getElementsBox() {
		return elementsBox;
	}

	public void setElementsBox(HBox elementsBox) {
		this.elementsBox = elementsBox;
	}

	public HBox getGestionBox() {
		return gestionBox;
	}

	public void setGestionBox(HBox gestionBox) {
		this.gestionBox = gestionBox;
	}

	
	
	
}
