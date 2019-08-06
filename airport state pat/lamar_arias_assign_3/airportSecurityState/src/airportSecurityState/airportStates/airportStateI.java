package airportSecurityState.airportStates;
import airportSecurityState.util.Results;

public interface airportStateI{
	public void changeState(Context context);
	public String performOPs();
	public Results tightenOrLoosenSecurity(int day, String item, Results out,Context cont);
	
}
