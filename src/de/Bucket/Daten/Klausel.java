package de.Bucket.Daten;

import java.util.ArrayList;
import java.util.List;

public class Klausel {
	private List<Integer> vars;
	private Integer high;
	
	public Klausel(int high, List<Integer> vars) {
		this.setVars(vars);
		this.setHigh(high);
	}
	public Klausel(int high) {
		this.setVars(new ArrayList<Integer>());
		this.setHigh(high);
	}
	
	public void addVar(int numb){
		//TODO else Exception
		if(numb <= high){
			vars.add(numb);
		}
	}
	
	public String varsToString(){
		String str = "{";
		for(Integer i : vars){
			str += String.valueOf(i) + ", ";
		}
		if(vars.size() != 0){
			str = str.substring(0, str.length() - 2);
		}
		return str + "}";
	}
	
	public int getSmallestNumbber(){
		int i = Math.abs(vars.get(0));
		for(int var : vars){
			if(Math.abs(var) < i){
				i = Math.abs(var);
			}
		}
		return i;
	}
	
	public List<Integer> getVars() {
		return vars;
	}

	public void setVars(List<Integer> vars) {
		this.vars = vars;
	}

	public Integer getHigh() {
		return high;
	}

	public void setHigh(Integer high) {
		this.high = high;
	}
}
