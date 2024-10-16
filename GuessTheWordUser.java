
public class GuessTheWordUser {

	private final int MAX_POINT = 12;
	private int userAbility = 0;
	private int pointObtained = 0;
	private final int DIFF_LEVEL = 5;
	private int diffLevel;
	private final int STANDARD = DIFF_LEVEL;
	private final int MID = 6;
	private final int MAX = 7;

	
	public int getUserAbility() {
		
		return userAbility;	
	}
	
	public void setUserAbility(int newUA) {
		
		userAbility += newUA;
	}
	
	public void resetUserAbility() {
		
		userAbility = 0;
	}
	
	public int getPointObtained() {
		
		return pointObtained;	
	}
	
	public String toStringPointObtained() {
		String str;
		return str = "Points: " + getPointObtained();
		
	}
	
	
	public void setPointObtained(int newPO) {
		
		pointObtained += newPO;	
	}
	
	public void resetPointObtained() {
		
		pointObtained = 0;
	}
	
	public int getDiffLevel() {
		
		return diffLevel;
	}
	
	public int getMaxPoint() {
		
		return MAX_POINT;
	}
	
	public void setDiffLevel(String diffIn) {
		
		String diff = diffIn; 
		
		if (diff.equalsIgnoreCase("standard")) {
			diffLevel = STANDARD;
		}
		if (diff.equalsIgnoreCase("mid")) {
			diffLevel = MID;
		}
		if (diff.equalsIgnoreCase("max")) {
			diffLevel = MAX;
		}	
	}

}
