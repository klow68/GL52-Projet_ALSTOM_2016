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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class DragDropView implements FxmlView<DragDropViewModel>, Initializable  {

	@FXML
	private VBox vbox;
	
    @FXML
    private Label convoiLabel;
    
    @FXML
    private HBox convoiBox;
    
    @FXML
    private Label elementsLabel;
    
    @FXML
    private HBox elementsBox;
    
    @FXML
    private Label gestionLabel;
    
    @FXML
    private HBox gestionBox;

    @InjectViewModel
    private DragDropViewModel viewModel;
    
    private List<DraggableImageView> convoiList;
    private List<DraggableImageView> elementsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    	
    	DraggableImageView test = new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE);
    	test.setId("Plus");
    	
    	test.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//            	List<DraggableImageView> convoiList_temp= new ArrayList<>();
//            	convoiList_temp.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE));
//            	convoiBox.getChildren().addAll(convoiList_temp);
            	
            	convoiBox.getChildren().add(1, new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE));
            }
        });
        
    	convoiList = new ArrayList<DraggableImageView>();
    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR));
    	convoiList.add(new DraggableImageView("DragDrop/Images/voiture.jpg", ImageViewType.IMAGE));
    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR));
    	convoiList.add(new DraggableImageView("DragDrop/Images/wagon.jpg", ImageViewType.IMAGE));
    	convoiList.add(new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR));
    	convoiList.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE));
    	convoiList.add(test);

    	elementsList = new ArrayList<DraggableImageView>();
    	elementsList.add(new DraggableImageView("DragDrop/Images/voiture.jpg", ImageViewType.IMAGE));
    	elementsList.add(new DraggableImageView("DragDrop/Images/wagon.jpg", ImageViewType.IMAGE));
    	elementsList.add(new DraggableImageView("DragDrop/Images/loco.jpg", ImageViewType.IMAGE));
    	
    	List<DraggableImageView> gestionList = new ArrayList<DraggableImageView>();
    	gestionList.add(new DraggableImageView("DragDrop/Images/corbeille.png", ImageViewType.IMAGE));
    	
    	viewModel.setConvoiList(convoiList);
    	viewModel.setElementsList(elementsList);
    	
    	convoiBox.getChildren().addAll(convoiList);
    	elementsBox.getChildren().addAll(elementsList);
    	gestionBox.getChildren().addAll(gestionList);
    	
    }

}
