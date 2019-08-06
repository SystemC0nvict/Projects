
package fileVisitors.util;

public class MyLogger{

    /*DEBUG_VALUE=4 [Print to stdout everytime a constructor is called]
      DEBUG_VALUE=3 []
      DEBUG_VALUE=2 [prints out the sum of characters of words in the tree]
      DEBUG_VALUE=1 [prints out method calls from Treebuilder]
      DEBUG_VALUE=0 [No output should be printed from the applicatio to stdout. It is ok to write to the output file though" ]
    */

    public static enum DebugLevel {RELEASE, RUN, CHECKS, TREE, CONSTRUCTOR
                                   };

    private static DebugLevel debugLevel;


    public static void setDebugValue (int levelIn) {
	switch (levelIn) {
	  case 4: debugLevel = DebugLevel.CONSTRUCTOR; break;
	  //case 3: debugLevel = DebugLevel.RUN; break;
	  case 2: debugLevel = DebugLevel.CHECKS; break;
	  case 1: debugLevel = DebugLevel.TREE; break;
	  case 0: debugLevel = DebugLevel.RELEASE; break;
	}
    }

    public static void setDebugValue (DebugLevel levelIn) {
	debugLevel = levelIn;
    }

    // @return None
    public static void writeMessage (String  message  ,
                                     DebugLevel levelIn ) {
	if (levelIn == debugLevel)
	    System.out.println(message);
    }

    /**
	 * @return String
	 */
    public String toString() {
	return "Debug Level is " + debugLevel;
    }
}
