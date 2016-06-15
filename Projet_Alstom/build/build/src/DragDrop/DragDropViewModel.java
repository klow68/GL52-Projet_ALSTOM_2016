package DragDrop;

import java.util.ArrayList;
import java.util.List;

import de.saxsys.mvvmfx.ViewModel;

public class DragDropViewModel implements ViewModel {

    private List<DraggableImageView> convoiList = new ArrayList<DraggableImageView>();
    private List<DraggableImageView> elementsList = new ArrayList<DraggableImageView>();
    
	public void setConvoiList(List<DraggableImageView> convoiList) {
		this.convoiList = convoiList;
	}
	public void setElementsList(List<DraggableImageView> elementsList) {
		this.elementsList = elementsList;
	}
    
    
    
    
}