package shipModules;

public class PowerSystem extends ShipModule {

	private int maxPower = 10;
	private int currentPower = 10;

	public int getMaxPower() {
		return maxPower;
	}

	public void setMaxPower(int maxPower) {
		this.maxPower = maxPower;
	}

	public int getCurrentPower() {
		return currentPower;
	}

	public void setCurrentPower(int currentPower) {
		this.currentPower = currentPower;
	}

	@Override
	public String toString() {
		String s = super.toString();
		s += "\n        " + currentPower + " / " + maxPower
				+ " gigavolts left.";
		return s;
	}

}
