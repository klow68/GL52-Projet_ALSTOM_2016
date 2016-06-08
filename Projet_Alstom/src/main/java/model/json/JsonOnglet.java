package model.json;

public class JsonOnglet {

	private Integer id;
	private String onglet;

	public JsonOnglet (){
		
	}
	
	public JsonOnglet (Integer id, String onglet){
		this.id = id;
		this.onglet = onglet;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOnglet() {
		return onglet;
	}

	public void setOnglet(String onglet) {
		this.onglet = onglet;
	}

}
