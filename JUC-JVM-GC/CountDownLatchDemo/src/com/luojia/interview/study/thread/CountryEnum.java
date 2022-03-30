package com.luojia.interview.study.thread;

public enum CountryEnum {

	ONE(1, "齐"), TWO(2, "楚"), THERE(3, "燕"), FOUR(4, "赵"), FIVE(5, "魏"), SIX(6, "韩");

	private Integer retCode;
	private String retMessage;

	public static CountryEnum forEach_countryEnum(int index) {
		CountryEnum[] myArray = CountryEnum.values();
		for (CountryEnum element : myArray) {
			if(index == element.getRetCode()) {
				return element;
			}
		}
		return null;
	}
	
	public Integer getRetCode() {
		return retCode;
	}

	public String getRetMessage() {
		return retMessage;
	}

	public void setRetCode(Integer retCode) {
		this.retCode = retCode;
	}

	public void setRetMessage(String retMessage) {
		this.retMessage = retMessage;
	}

	private CountryEnum(Integer retCode, String retMessage) {
		this.retCode = retCode;
		this.retMessage = retMessage;
	}

}
