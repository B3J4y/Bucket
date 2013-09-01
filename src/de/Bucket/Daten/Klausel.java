package de.Bucket.Daten;

import java.util.ArrayList;
import java.util.List;
/**
 * Datenstruktur zur Abbildung der Klauseln
 * @author jbe
 *
 */
public class Klausel {
	private List<Integer> vars;
	private Integer high;
	
	/**
	 * Konstruktor der die Klausel mit der höchsten  Zahl und einer Liste von
	 * von Variablen erzeugt.
	 * @param high - Höchste mögliche Zahl
	 * @param vars - List der Variablen
	 */
	public Klausel(int high, List<Integer> vars) {
		this.setVars(vars);
		this.setHigh(high);
	}
	
	/**
	 * Initialisiert eine leere Klausel
	 * @param high - Höchste mögliche Zahl
	 */
	public Klausel(int high) {
		this.setVars(new ArrayList<Integer>());
		this.setHigh(high);
	}
	
	
	/**
	 * Fügt eine Variable hinzu
	 * @param numb - neue Zahl
	 * @return Boolean, ob der Vorgang erfolgreich war
	 */
	public Boolean addVar(int numb){
		if(numb <= high){
			vars.add(numb);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * wandelt die Liste von Vars in einen String um.
	 * @return String mit allen Variablen
	 */
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
	
	/**
	 * rechnet die kleinste Zahl der Liste aus
	 * @return Integer - kleinste Zahl aus der List
	 */
	public int getSmallestNumbber(){
		if(vars.size() == 0){
			return 0;
		} else{
			int i = Math.abs(vars.get(0));
			for(int var : vars){
				if(Math.abs(var) < i){
					i = Math.abs(var);
				}
			}
			return i;
		}
	}
	
	/**
	 * Getter
	 * @return
	 */
	public List<Integer> getVars() {
		return vars;
	}
	/**
	 * Setter
	 * @param vars
	 */
	public void setVars(List<Integer> vars) {
		this.vars = vars;
	}

	/**
	 * Getter
	 * @return
	 */
	public Integer getHigh() {
		return high;
	}

	/**
	 * Setter
	 * @param vars
	 */
	public void setHigh(Integer high) {
		this.high = high;
	}
}
