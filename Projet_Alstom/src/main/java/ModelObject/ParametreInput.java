package ModelObject;

public class ParametreInput extends Parametre {

	enum typeInput{INTEGER,DOUBLE,STRING}
	private typeInput type = null;
	
	public ParametreInput(int _id, String _classe, String _label, typeParametre _typePara,typeInput _typeInput) {
		super(_id, _classe, _label, _typePara);
		type = _typeInput;
	}

	public typeInput getType() {
		return type;
	}


}
