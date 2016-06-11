package ModelObject;

public class ParametreInput extends Parametre {

	enum typeInput{INTEGER,DOUBLE,STRING}
	private typeInput type = null;
	


	public ParametreInput(int _id, String _classe, String _label, typeParametre _typePara,String input) {
		super(_id, _classe, _label, _typePara);
		type = convertInput(input);
	}

	public typeInput getType() {
		return type;
	}
	
	private typeInput convertInput(String input){
		switch(input){
		case("double") : return typeInput.DOUBLE;
		case("integer"): return typeInput.INTEGER;
		case("string"): return typeInput.STRING;
		default : 
			System.out.println("Erreur interne : mauvaise type input"); return null;
		}
	}

}
