package de.Bucket.Daten;

import java.util.ArrayList;
import java.util.List;
/**
 * Datenstruktur zur Abbildung der Buckets
 * @author jbe
 *
 */
public class Bucket {
	private List<Klausel> klauseln;
	private int smallestNumber;
	
	/**
	 * Konstruktur der ein leeres Bucket initialisiert. 
	 * @param numb
	 */
	public Bucket(int numb){
		smallestNumber = numb;
		klauseln = new ArrayList<Klausel>();
	}

	/**
	 * Konstruktur, der gleich eine Klausel in das Bucket initialisiert
	 * @param kls - Klausel
	 */
	public Bucket(Klausel kls){
		if(kls.getVars().size() > 0){
			smallestNumber = kls.getSmallestNumbber();
			klauseln = new ArrayList<Klausel>();
			klauseln.add(kls);
		}
	}
	
	
	/**
	 * Fügt eine Klausel zu dem Bucket hinzu
	 * @param kls - Klausel
	 * @return Boolean, über den Erfolg des Vorgangs
	 */
	public boolean addKlausel(Klausel kls){
		if(kls.getSmallestNumbber() == smallestNumber){
			klauseln.add(kls);
			return true;
		} else {
			return false;
		}
	}
	/**
	 * Getter
	 * @return
	 */
	public List<Klausel> getKlauseln() {
		return klauseln;
	}
	
	/**
	 * Setter
	 * @param vars
	 */
	public void setKlauseln(List<Klausel> klauseln) {
		this.klauseln = klauseln;
	}

	/**
	 * Getter
	 * @return
	 */
	public int getSmallestNumber() {
		return smallestNumber;
	}
	
}
