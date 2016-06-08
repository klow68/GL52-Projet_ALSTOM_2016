package model.json;

public class JsonSelect {
	
	private Integer id;
	private Integer idProperty;
	private String nameSelect;
	
	public JsonSelect(){
		
	}
	
	public JsonSelect(Integer id, Integer idProperty, String nameSelect){
		this.id = id;
		this.idProperty = idProperty;
		this.nameSelect = nameSelect;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getIdProperty() {
		return idProperty;
	}

	public void setIdProperty(Integer idProperty) {
		this.idProperty = idProperty;
	}

	public String getNameSelect() {
		return nameSelect;
	}

	public void setNameSelect(String nameSelect) {
		this.nameSelect = nameSelect;
	}
}
