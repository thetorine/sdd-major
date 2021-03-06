package spacegame.gameplay;

//VELOCITY
public class VelocityVector {
	public float xCoord = 0;
	public float yCoord = 0; 
	public float xVelocity;
	public float yVelocity;
	public float vehicleDrag = 0.15f;
	public float rotation = 0;
	public float angularVelocity;
	public float angularDrag = 0.4f;
	public float velocityLength;
	
	public VelocityVector(float x, float y) {
		this.xVelocity = x;
		this.yVelocity = y;
	}

	//accelerates in the current direction
	public void accelerate(int power, float delta) {
		xVelocity += power*Math.sin(rotation)*delta;
		yVelocity -= power*Math.cos(rotation)*delta;
		velocityLength = calculateVelocityLength();
	}

	//decellerates in the current direction
	public void decelerate(float power, float delta) {
		if(velocityLength > 0) {
			xVelocity += power*Math.sin(getVelocityDirection()+Math.PI)*delta;
			yVelocity -= power*Math.cos(getVelocityDirection()+Math.PI)*delta;
			velocityLength = calculateVelocityLength();
		}
	}

	//accelerates in a specific direction
	public void accelerateInDirection(int power, float rotation, float delta) {
		xVelocity += power*Math.sin(rotation)*delta;
		yVelocity -= power*Math.cos(rotation)*delta;
		velocityLength = calculateVelocityLength();
	}

	//rotates in a particular direction given by direction
	public void steerInDirection(boolean direction, double power, float delta) {
		angularVelocity = (float) (angularVelocity + (direction ? power*delta : -power*delta));
	}

	//changes the velocity to a given amount
	public void setVelocity(int velocity) {
		float scaler = velocity/velocityLength;
		xVelocity = xVelocity*scaler;
		yVelocity = yVelocity*scaler;
		velocityLength = calculateVelocityLength();
	}

	//changes the velocity with a particular direction
	public void setVelocityWithDirection(int velocity, float direction) {
		xVelocity = (float) (velocity*Math.sin(direction));
		yVelocity = (float) (-velocity*Math.cos(direction));
		velocityLength = calculateVelocityLength();
	}

	//resets the velocity
	public void removeVelocity() {
		xVelocity = 0;
		yVelocity = 0;
		velocityLength = 0;
		angularVelocity = 0;
	}

	//modifies the position of the player with the velocity vectors and drift.
	public void onUpdate(int delta) {
		float deltaSec = delta/1000f;
		xCoord += xVelocity*deltaSec;
		yCoord += yVelocity*deltaSec;
		rotation += angularVelocity*deltaSec;
		
		xVelocity = xVelocity - deltaSec*vehicleDrag*xVelocity;
		yVelocity = yVelocity - deltaSec*vehicleDrag*yVelocity;
		angularVelocity = angularVelocity- deltaSec*angularDrag*angularVelocity*10;
		
		velocityLength = calculateVelocityLength();
	}
	
	public float getVelocityDirection() {
		return (float) (Math.atan2(yVelocity, xVelocity)+Math.PI/2f);
	}
	
	public void setCoords(float x, float y) {
		this.xCoord = x;
		this.yCoord = y;
	}
	
	private float calculateVelocityLength() {
		return (float) Math.sqrt(Math.pow(xVelocity, 2)+Math.pow(yVelocity, 2));
	}
}
