package raytracer;

public class Camera {
	private Vector eye = new Vector(0,0,0);
	private Vector direction = new Vector (0,0,-1); 
	private Vector lookAt;//Either give up direction or lookAt;
	private Vector upDirection = new Vector(0,1,0);
	private Vector rightDirection;
	private Vector vpUp;
	private double screenDist = 1;
	private double screenWidth = 2;
	private double screenHeight;
	
	
	public Camera(Vector eye, Vector direction, Vector upDirection){
		this.setEye(eye);
		this.setDirection(direction);
		this.setUpDirection(upDirection);
		this.setScreenDist(screenDist);
		this.setScreenWidth(screenWidth);
		calcParams();
	}


	
	public Camera() {
		calcParams();
	}



	private void calcParams() {
		
		// right = towards x up
		setRightDirection(direction.crossProduct(upDirection)); 
		
		// the view plane's up direction is the cross product of the camera's direction and the camera's right direction.
		setVpUp(direction.crossProduct(rightDirection));
	}

	public Ray createRay(Vector cord){
		Ray v = new Ray(eye, direction);
		v.setDirection(eye.substract(cord));
		return v;
	}
	
	public Vector getEye() {
		return eye;
	}

	public void setEye(Vector eye) {
		this.eye = eye;
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction.normalize();
	}

	public Vector getLookAt() {
		return lookAt;
	}

	public void setLookAt(Vector lookAt) {
		this.lookAt = lookAt;
	}

	public Vector getUpDirection() {
		return upDirection;
	}

	public void setUpDirection(Vector upDirection) {
		this.upDirection = upDirection.normalize();
	}

	public Vector getRightDirection() {
		return rightDirection;
	}

	public void setRightDirection(Vector rightDirection) {
		this.rightDirection = rightDirection.normalize();
	}

	public double getScreenDist() {
		return screenDist;
	}

	public void setScreenDist(double screenDist) {
		this.screenDist = screenDist;
	}

	public double getScreenWidth() {
		return screenWidth;
	}

	public void setScreenWidth(double screenWidth) {
		this.screenWidth = screenWidth;
	}

	public Vector getVpUp() {
		return vpUp;
	}

	public void setVpUp(Vector vpUp) {
		this.vpUp = vpUp.normalize();
	}
		
}
