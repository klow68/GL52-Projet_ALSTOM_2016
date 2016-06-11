package DragDrop;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;

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
    private Button save;
    
    @FXML
    private Button cancel;

    @InjectViewModel
    private DragDropViewModel viewModel;
    
    private List<DraggableImageView> convoiList;
    private List<DraggableImageView> elementsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	save.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				System.out.println("SAVE THE CONVOI");
	    		
	    		List<DraggableImageView> convoiSave = new ArrayList<DraggableImageView>();
	    		List<Node> convoiNodes = convoiBox.getChildren();
	    		
	    		for(Node node : convoiNodes) {
	    			if(((DraggableImageView) node).getElemType() != ImageViewType.SEPARATOR) convoiSave.add((DraggableImageView) node);
	    		}
	    		
	    		for(DraggableImageView dg : convoiSave) {
	    			System.out.println(dg.getElemType());
	    		}
	    		
	    		viewModel.setConvoiList(convoiSave);				
			}
    		
		});
        
    	convoiList = new ArrayList<DraggableImageView>();
    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
    	convoiList.add(new DraggableImageView("DragDrop/Images/voiture.jpg", ImageViewType.IMAGE, this));
    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
    	convoiList.add(new DraggableImageView("DragDrop/Images/wagon.jpg", ImageViewType.IMAGE, this));
    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
    	convoiList.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE, this));
    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
//    	convoiList.add(test);

    	elementsList = new ArrayList<DraggableImageView>();
    	elementsList.add(new DraggableImageView("DragDrop/Images/voiture.jpg", ImageViewType.IMAGE, this));
    	elementsList.add(new DraggableImageView("DragDrop/Images/wagon.jpg", ImageViewType.IMAGE, this));
    	elementsList.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE, this));
    	elementsList.add(new DraggableImageView("DragDrop/Images/wagon.jpg", ImageViewType.IMAGE, this));
    	elementsList.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE, this));
    	
    	List<DraggableImageView> gestionList = new ArrayList<DraggableImageView>();
    	gestionList.add(new DraggableImageView("DragDrop/Images/corbeille.png", ImageViewType.RECYCLE_BIN, this));
    	
    	
    	
    	viewModel.setConvoiList(convoiList);
    	viewModel.setElementsList(elementsList);
    	
    	convoiBox.getChildren().addAll(convoiList);
    	elementsBox.getChildren().addAll(elementsList);
    	gestionBox.getChildren().addAll(gestionList);
    	
    }

	public void updateList(int id) {

		System.out.println("UPDATE LIST : adding separators");
		convoiBox.getChildren().add(id-1, new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
		convoiBox.getChildren().add(id+1, new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, this));
		
		
	}
	
	@FXML
	public void saveConvoi() {
		
		System.out.println("SAVE THE CONVOI");
		
		List<DraggableImageView> convoiSave = new ArrayList<DraggableImageView>();
		List<Node> convoiNodes = convoiBox.getChildren();
		
		for(Node node : convoiNodes) {
			convoiSave.add((DraggableImageView) node);
		}
		
		for(DraggableImageView dg : convoiSave) {
			System.out.println(dg.getElemType());
		}
		
		viewModel.setConvoiList(convoiSave);
		
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
