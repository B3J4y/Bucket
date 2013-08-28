package de.Bucket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.Bucket.Daten.*;

/**
 * Implementierung der Bucket Eliminierung
 * 
 * @author jbe
 */
public class BucketElimination {
	
	private int high;
	
	/**
	 * Liest die Datei die als Path eingegeben wird aus und wandelt das DIMACS Format in die
	 * Klauselstruktur um.
	 * 
	 * @param path - String
	 * @return Eine Liste der eingegebenen Klauseln
	 * @throws IOException
	 */
	public List<Klausel> readOutFile(String path) throws IOException{
		List<Klausel> klauseln = new ArrayList<Klausel>();
		
		File file = new File(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line ="";
		Boolean doSmth = false;
		int intKlausel = 0;
		
		READLINE:for(line = br.readLine(); line != null;line = br.readLine()){
			if(line.split(" ")[0].contains("c")){
				
			} else {
				try{
					if((line.split(" ")[0].contains("p")) && (! doSmth)){
						intKlausel = 0;
						doSmth = true;
						if(line.split(" ")[1].contains("cnf")){
							int klauselHigh = Integer.parseInt(line.split(" ")[3]);
							for(int i = 0; i < klauselHigh; i++){
								high = Integer.parseInt(line.split(" ")[2]);
								Klausel klausel = new Klausel(Integer.parseInt(line.split(" ")[2]));
								klauseln.add(klausel);
							}
						}else {
							System.out.println("Error, nach p muss ein cnf kommen!");
							klauseln = new ArrayList<Klausel>();
							break READLINE;
						}
					} else {
						if(doSmth && (line.matches("(-*[0-9]\\s)+[0-9]")) && (line.length() > 2)){
							if(intKlausel == (klauseln.size() - 1)){
								doSmth = false;
							}
							String[] strNumbs = line.split(" ");
							int countNumbs = 0;
							for(String str : strNumbs){
								int myNumb = Integer.parseInt(str);
								if(myNumb != 0 ){
									if(! klauseln.get(intKlausel).addVar(myNumb)){
										klauseln = new ArrayList<Klausel>();
										System.out.println("Error, Zahl ist größer als in der p cnf definiert");
										break READLINE;
									}
								}else{
									if(countNumbs != (strNumbs.length - 1)){
										klauseln = new ArrayList<Klausel>();
										System.out.println("Error, die 0 ist an der falschen Stelle");
										break READLINE;
									}
								}
								
								countNumbs++;
							}
							intKlausel++;
						} else {
							System.out.println("Error, Formatfehler! Erste Zeile p Zahl Zahl, danach nur Zeilen mit Zahlen");
							klauseln = new ArrayList<Klausel>();
							break READLINE;
						}
					}
				} catch (NumberFormatException e){
					System.out.println("Error, Bustaben und Zahlen wurden vermischt. Guck dir das Format nochmal an und probiere es mit einheitlichen Zahlen");
					klauseln = new ArrayList<Klausel>();
					break;
				}
			}
		};
		br.close();
		return klauseln;
	}
	
	/**
	 * Verteilt die Klauseln nach dem Algorithmus auf die einzelnen Buckets.
	 * 
	 * @param klauseln - Liste der Klauseln
	 * @return Die Liste der Buckets in dem sich die Klauseln befinden
	 */
	public List<Bucket> klauselToBuckets(List<Klausel> klauseln){
		List<Bucket> initBuckets = new ArrayList<Bucket>();
		for(Klausel kls : klauseln){
			if(initBuckets.size() == 0){
				initBuckets.add(new Bucket(kls));
			} else {
				Boolean isBucket = true;
				for(Bucket buck : initBuckets){
					if(buck.addKlausel(kls)){
						isBucket = false;
						break;
					}
				}
				if(isBucket){
					initBuckets.add(new Bucket(kls));
				}
			}
		}
		return initBuckets;
	}
	/**
	 * Führt das Eliminierungsverfahren innerhalb der Buckets durch und entscheidet ob die Klauselmenge
	 * SAT oder UNSAT ist.
	 * @param buckets Liste der vorgefertigten Buckets
	 */
	public void sortBuckets(List<Bucket> buckets){
		Boolean sat = true;
		for(int i = 0; i < high; i++){
			System.out.println("Processing Bucket " + (i+1));
			List<Klausel> positives = new ArrayList<Klausel>();
			List<Klausel> negatives = new ArrayList<Klausel>();
			try{
				for(Klausel kls : buckets.get(i).getKlauseln()){
					if(kls.getVars().contains(kls.getSmallestNumbber())){
						positives.add(kls);
					} else {
						negatives.add(kls);
					}
				}
				if((positives.size() == 0) || (negatives.size() == 0) ){
				} else{
					for(Klausel klsPos: positives){
						for(Klausel klsNeg : negatives){
							String str = "";
							str += klsPos.varsToString() + " ° " + klsNeg.varsToString();
	
							Set<Integer> set = new HashSet<Integer>();
							klsPos.getVars().remove(new Integer(klsPos.getSmallestNumbber()));
							klsNeg.getVars().remove(new Integer(klsNeg.getSmallestNumbber() * (-1)));
							
							
							set.addAll(klsPos.getVars());
							set.addAll(klsNeg.getVars());
							Klausel newKlausel = new Klausel(high, new ArrayList<Integer>(set));
							str += " = " + newKlausel.varsToString();
							
							Boolean isBucket = true;
							if(set.size() == 0){
								sat = false;
							} else {
								for(Bucket buck : buckets){
									if(buck.addKlausel(newKlausel)){
										isBucket = false;
										break;
									}
								}
								if(isBucket){
									buckets.add(new Bucket(newKlausel));
								}
								System.out.println(str);
							}
						}
					}
				}
			} catch(IndexOutOfBoundsException e){
				
			}
		}
		if(sat){
			System.out.println("SAT");
		} else {
			System.out.println("UNSAT");
		}
	}
	
	public static void main(String[] args) throws IOException {
		BucketElimination bucEli = new BucketElimination();
		List<Klausel> klauseln = bucEli.readOutFile(args[0]);
		if(klauseln.size() != 0){
			List<Bucket> buckets = bucEli.klauselToBuckets(klauseln);
			bucEli.sortBuckets(buckets);
		}
	}
}
