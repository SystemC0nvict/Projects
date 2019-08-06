package airportSecurityState.airportStates;
import airportSecurityState.airportStates.airportStateI;
import airportSecurityState.airportStates.high_risk;
import airportSecurityState.airportStates.mid_risk;
import airportSecurityState.airportStates.low_risk;
import airportSecurityState.util.MyLogger;
import airportSecurityState.util.Results;
import java.util.ArrayList;
import java.util.Arrays;
import java.lang.System;

public class Context{
	private airportStateI state;
	
	public Context(){
		state = new low_risk(new avgs());
		
	}
	
	public void setState(airportStateI state){
      		this.state = state;		
   	}
   	

   	public airportStateI getState(){
    		return state;
   	}
}


