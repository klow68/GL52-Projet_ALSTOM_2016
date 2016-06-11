package ModelObject;

public class ParametreSelect extends Parametre {
	
	private Parametre paraSelect = null;


	public ParametreSelect(int _id, String _classe, String _label, typeParametre _type, Parametre _parametre) {
		super(_id, _classe, _label, _type);
		paraSelect = _parametre;
	}


	public Parametre getParaSelect() {
		return paraSelect;
	}

}
