package raytracer;

public class Ray {
	private Vector origin;
	private Vector direction;
	
	public Ray(Vector origin, Vector direction){
		this.setOrigin(origin);
		this.setDirection(direction);
	}

	public Vector getOrigin() {
		return origin;
	}

	public void setOrigin(Vector origin) {
		this.origin = origin;
	}

	public Vector getDirection() {
		return direction;
	}

	public void setDirection(Vector direction) {
		this.direction = direction.normalize();
	}
	
	public Vector retrievePoint(double distance){
		return origin.add(direction.scalarMult(distance));
	}
}
