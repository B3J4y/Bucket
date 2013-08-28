package de.Bucket;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import de.Bucket.Daten.*;

public class BucketElimination {
	public List<Klausel> readOutFile(String path) throws IOException{
		//TODO mehrere cnfs verarbeiten
		List<Klausel> klauseln = new ArrayList<Klausel>();
		
		File file = new File(path);
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String line ="";
		Boolean doSmth = false;
		int intKlausel = 0;
		
		READLINE:for(line = br.readLine(); line != null;line = br.readLine()){
			System.out.println(line);
			if(line.split(" ")[0].contains("c")){
				
			} else {
				try{
					if((line.split(" ")[0].contains("p")) && (! doSmth)){
						intKlausel = 0;
						doSmth = true;
						if(line.split(" ")[1].contains("cnf")){
							int klauselHigh = Integer.parseInt(line.split(" ")[3]);
							for(int i = 0; i < klauselHigh; i++){
								Klausel klausel = new Klausel(Integer.parseInt(line.split(" ")[2]));
								klauseln.add(klausel);
							}
						}else {
							System.out.println("Error, nach p muss ein cnf kommen!");
							break;
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
									klauseln.get(intKlausel).addVar(myNumb);
								}else{
									if(countNumbs != (strNumbs.length - 1)){
										System.out.println("Error, die 0 ist an der falschen Stelle");
										break READLINE;
									}
								}
								
								countNumbs++;
							}
							intKlausel++;
						} else {
							System.out.println("Error, Formatfehler! Erste Zeile p Zahl Zahl, danach nur Zeilen mit Zahlen");
						}
					}
				} catch (NumberFormatException e){
					System.out.println("Error, Bustaben und Zahlen wurden vermischt. Guck dir das Format nochmal an und probiere es mit einheitlichen Zahlen");
					break;
				}
			}
		};
		br.close();
		return klauseln;
	}
	
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
	
	public static void main(String[] args) throws IOException {
		BucketElimination bucEli = new BucketElimination();
		List<Klausel> klauseln = bucEli.readOutFile(args[0]);
		bucEli.klauselToBuckets(klauseln);
		System.out.println("fertig");
	}
}
