package airportSecurityState.airportStates;

import java.util.ArrayList;
import java.util.Arrays;
import airportSecurityState.util.MyLogger;

public class avgs{
	//average traffic per day
	public float avgT;
	//average prohibited items
	public float avgP;
	//list number of people across all days
	public ArrayList <Integer> averPeople;
	//list of number of prohibited items over all days
	public ArrayList <Integer> averProhib;
	//current day
	int curD;
	//count of persons in a day
	int countPer;
	//number of prohibited items in a day
	int countProhib;
	//list of prohibitted items
	public String[] prohib = {"Gun", "NailCutter", "Blade", "Knife"};
	
	public avgs(){
		avgT = 0;
		avgP = 0;
		curD = 0;
		averPeople = new ArrayList();
		averProhib = new ArrayList();
	}
	
	public void calcavgT(int day){
		
		MyLogger.writeMessage(("day "+ Integer.toString(day)), MyLogger.DebugLevel.AVGCHECK);
		if(curD == 0)curD = day;
		if(day < curD){
			System.err.println("Cannot have a day that is before the current day.");
			System.exit(1);
		}
		//case for when it is still the same day
		if(day == curD){
			countPer++;
			avgT = countPer;
			for(Integer object: averPeople){
				avgT += object;
			}
			avgT = avgT/(averPeople.size() + 1);
			
		}
		//new day
		else{
			averPeople.add(countPer);
			
			countPer = 1;
			avgT = countPer;
			for(Integer object: averPeople){
				avgT += object;
			}
			//avgT = avgT/(averPeople.size() + 1);
			avgT = avgT/(day);
		}
		MyLogger.writeMessage(Float.toString(avgT), MyLogger.DebugLevel.AVGCHECK);
	
	}
	
	//get average of prohibitted items
	public void calcavgP(int day, String item){
		//case for when it is still the same day
		if(day == curD){
			//prohibitted item found
			if(Arrays.asList(prohib).contains(item)){
				countProhib++;
				avgP = countProhib;
				for(Integer object: averProhib){
					avgP += object;
				}
				avgP = avgP/(averProhib.size() + 1);
				
			}
		}
		//new day
		else{
			if(Arrays.asList(prohib).contains(item)){
				averProhib.add(countProhib);
				countProhib = 1;
				avgP = countProhib;
				for(Integer object: averProhib){
					avgP += object;
				}
				//avgP = avgP/(averProhib.size() + 1);
				avgP = avgP/(day);
			}
			//curD = day;
		}
		MyLogger.writeMessage(Float.toString(avgP), MyLogger.DebugLevel.AVGCHECK);
	}
}
