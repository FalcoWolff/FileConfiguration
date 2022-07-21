package de.falco.fileconfiguartion.v2.datatype;

public class Number extends Datatype{

	private int number;
	
	public Number(int number) {
		this.number = number;
	}

	@Override
	protected String convertToString() {
		return null;
	}

	@Override
	protected void loadFromString(String value) {
		
	}

	@Override
	protected boolean isDatatype(String value) {
		try {
			number = Integer.parseInt(value);
			return true;
		}catch(NumberFormatException ex) {
			
		}
		
		return false;
	}
	
	
	
	public int getNumber() {
		return number;
	}
	
	public void setNumber(int number) {
		this.number = number;
	}

}
