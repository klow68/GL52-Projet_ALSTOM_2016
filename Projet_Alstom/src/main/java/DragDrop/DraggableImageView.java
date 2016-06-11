package DragDrop;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;

public class DraggableImageView extends ImageView {
	
	private ImageViewType elemType;
	private DragDropView parent;
	private DraggableImageView instance;
	
	public DraggableImageView(String url, ImageViewType elemType, DragDropView parent) {
		
		this.elemType = elemType;
		this.parent = parent;
		this.instance = this;
		setImage(new Image(url));
		
		updateRights();
		updateSize();
		
	}
	
	public DraggableImageView(DraggableImageView copy) {
		
		this.elemType = copy.getElemType();
		
		setImage(copy.getImage());
		
		updateRights();
		updateSize();
		
	}

	private void updateSize() {

		setFitHeight(150);
		
		if(elemType == ImageViewType.SEPARATOR) {
			setFitWidth(50);
		} else {
			setFitWidth(200);
		}
		
	}

	private void updateRights() {

		if(this.elemType == ImageViewType.IMAGE) {
		
			setOnDragDetected(new EventHandler <MouseEvent>() {
	            public void handle(MouseEvent event) {
	                /* drag was detected, start drag-and-drop gesture*/
	                System.out.println("onDragDetected");
	                
	                /* allow any transfer mode */
	                Dragboard db = startDragAndDrop(TransferMode.ANY);
	                
	                /* put a string on dragboard */
	                ClipboardContent content = new ClipboardContent();
	                content.putImage(getImage());
	                db.setContent(content);
	                
	                event.consume();
	            }
	        });
		
		}

        setOnDragOver(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data is dragged over the target */
                System.out.println("onDragOver");
                
                event.acceptTransferModes(TransferMode.ANY);
                /* accept it only if it is  not dragged from the same node 
                 * and if it has a string data */
                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                
                event.consume();
            }
        });

        setOnDragEntered(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture entered the target */
                System.out.println("onDragEntered");
                /* show to the user that it is an actual gesture target */
                if (event.getGestureSource() != this &&
                        event.getDragboard().hasString()) {
//                    setFill(Color.GREEN);
                }
                
                event.consume();
            }
        });

        setOnDragExited(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* mouse moved away, remove the graphical cues */
//                setFill(Color.BLACK);
                
                event.consume();
            }
        });
        
        setOnDragDropped(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* data dropped */
                System.out.println("onDragDropped");
                /* if there is a string data on dragboard, read it and use it */
                Dragboard db = event.getDragboard();
                boolean success = false;
                DraggableImageView source = (DraggableImageView) event.getGestureSource();
                
                boolean separator = false;
                boolean belongsToConvoi = false;
                
                if (db.hasImage()) {
                    
                	System.out.println("HAS IMAGE");
                	
                	if(source.getElemType() == ImageViewType.IMAGE && getElemType() == ImageViewType.RECYCLE_BIN) {
                		
                		if(!parent.getElementsBox().getChildren().contains(source)) {
	                		parent.getConvoiBox().getChildren().remove(parent.getConvoiBox().getChildren().indexOf(source)+1);
	                		parent.getElementsBox().getChildren().add(source);
                		}
                	} else {
                	
	                	if(getElemType() == ImageViewType.SEPARATOR) separator = true;
	                	if(parent.convoiBox.getChildren().contains(source)) belongsToConvoi = true;
	                	if((source.getElemType() == ImageViewType.IMAGE)) {
	                		if((separator && !belongsToConvoi) || !separator) {
			                    Image temp = source.getImage();
			                    if(separator && source.getElemType() == ImageViewType.IMAGE) {
			                    	source.setVisible(false);
			                    	source.setFitWidth(10);
			                    } else {
			                    	source.setImage(getImage());
			                    }
			                    setImage(temp);
			                    setElemType(source.getElemType());
	                		}
	                	}
                	}
                	
                    success = true;
                }
                /* let the source know whether the string was successfully 
                 * transferred and used */
                event.setDropCompleted(success);
                
                updateRights();
                updateSize();
                
				System.out.println(parent.convoiBox.getChildren().contains(instance));
				if(separator && !belongsToConvoi) {
					parent.convoiBox.getChildren().add(parent.convoiBox.getChildren().indexOf(instance), new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, parent));
					parent.convoiBox.getChildren().add(parent.convoiBox.getChildren().indexOf(instance)+1, new DraggableImageView("DragDrop/Images/insert.png", ImageViewType.SEPARATOR, parent));
				}
                event.consume();
            }
        });

        setOnDragDone(new EventHandler <DragEvent>() {
            public void handle(DragEvent event) {
                /* the drag-and-drop gesture ended */
                System.out.println("onDragDone");
                /* if the data was successfully moved, clear it */
                if (event.getTransferMode() == TransferMode.MOVE) {
//                    source.setText("");
                }
                
                event.consume();
            }
        });
		
	}

	public ImageViewType getElemType() {
		return elemType;
	}

	public void setElemType(ImageViewType elemType) {
		this.elemType = elemType;
	}
	
	

}
