package ModelObject;

public abstract class  Parametre{
	
	protected int id = -1;
	protected String classe = null;
	protected String label = null;
	public enum typeParametre{COMBO,SELECT,INPUT}
	protected typeParametre typePara = null;
	
	public Parametre(int _id, String _classe,String _label,typeParametre _type ){
		id = _id;
		classe = _classe;
		label = _label;
		typePara = _type;
	}


	public int getId() {
		return id;
	}

	public String getClasse() {
		return classe;
	}

	public String getLabel() {
		return label;
	}


	public typeParametre getTypePara() {
		return typePara;
	}
	
	public static typeParametre convertTypePara(String para){
		typeParametre ret = null;
		switch(para){
		case("combo"):ret = typeParametre.COMBO;break;
		case("select"): ret = typeParametre.SELECT;break;
		case("input"): ret = typeParametre.INPUT;break;
		}
		return ret;
	}



	
	
}


