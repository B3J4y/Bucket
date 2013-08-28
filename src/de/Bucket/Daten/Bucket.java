package de.Bucket.Daten;

import java.util.ArrayList;
import java.util.List;

public class Bucket {
	private List<Klausel> klauseln;
	private int smallestNumber;
	
	public Bucket(int numb){
		smallestNumber = numb;
		klauseln = new ArrayList<Klausel>();
	}

	public Bucket(Klausel kls){
		smallestNumber = kls.getSmallestNumbber();
		klauseln = new ArrayList<Klausel>();
		klauseln.add(kls);
	}
	
	public boolean addKlausel(Klausel kls){
		if(kls.getSmallestNumbber() == smallestNumber){
			klauseln.add(kls);
			return true;
		} else {
			return false;
		}
	}
	
	public List<Klausel> getKlauseln() {
		return klauseln;
	}

	public void setKlauseln(List<Klausel> klauseln) {
		this.klauseln = klauseln;
	}

	public int getSmallestNumber() {
		return smallestNumber;
	}
	
}
