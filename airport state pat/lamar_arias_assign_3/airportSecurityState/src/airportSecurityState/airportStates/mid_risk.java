package airportSecurityState.airportStates;
import airportSecurityState.airportStates.airportStateI;
import airportSecurityState.airportStates.low_risk;
import airportSecurityState.airportStates.high_risk;
import airportSecurityState.airportStates.avgs;
import airportSecurityState.util.MyLogger;
import airportSecurityState.airportStates.avgs;
import java.util.ArrayList;
import airportSecurityState.util.Results;

public class mid_risk implements airportStateI{
	public String opID = "2 3 5 8 9";
	public avgs avobj;
	public void changeState(Context context){
		context.setState(this);
	}
	
	public mid_risk(avgs av){
		avobj = new avgs();
		avobj.avgT = av.avgT;
		avobj.avgP = av.avgP;
		avobj.averPeople = (ArrayList<Integer>)av.averPeople.clone();
		avobj.averProhib = (ArrayList<Integer>)av.averProhib.clone();
		avobj.curD = av.curD;
		avobj.countPer = av.countPer;
		avobj.countProhib = av.countProhib;
	}
	
	public Results tightenOrLoosenSecurity(int day, String item, Results out,Context cont){
		avobj.calcavgT(day);
		avobj.calcavgP(day,item);
		//high risk
		if(avobj.avgT >= 8 || avobj.avgP >= 2){
			cont.setState(new high_risk(avobj));
			cont.getState().changeState(cont);
			//changeState(cont);
		}
		//mid risk
		else if((avobj.avgT < 8 && avobj.avgT >= 4) || (avobj.avgP < 2 && avobj.avgP >= 1)){
			
		}
		//low risk
		else if((avobj.avgT < 4 && avobj.avgT >= 0) || (avobj.avgP < 1 && avobj.avgP >= 0)){
			//cont.state = new low_risk();
			//airportStateI banana = new low_risk(avobj);
			cont.setState(new low_risk(avobj));
			cont.getState().changeState(cont);
			//changeState(cont);
			
		}
		//out.output.add(state.toString());
		//adds opIDs to results for output later
		out.output.add(cont.getState().performOPs());
		MyLogger.writeMessage(cont.getState().toString(),MyLogger.DebugLevel.SCHANGE);
		return out;
	}
	
	public String performOPs(){
		return opID;
	}
	
	public String toString(){
		return "MID_RISK";
	}
}
