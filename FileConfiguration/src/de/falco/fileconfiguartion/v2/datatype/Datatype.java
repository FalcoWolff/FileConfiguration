package de.falco.fileconfiguartion.v2.datatype;

public abstract class Datatype<T> {
	
	protected T value;
	
	protected abstract String convertToString();
	protected abstract void loadFromString(String value);
	protected abstract boolean isDatatype(String value);
	
	protected Datatype() {
	}
	
	public Datatype(T value) {
		this.value = value;
	}
	

}
