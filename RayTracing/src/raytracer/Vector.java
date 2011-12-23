package raytracer;



public class Vector {
	
	private double x,y,z;
	private double len;
	/**
	 * @param args
	 */
	
	public Vector(double x,double y,double z) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.len = Math.sqrt(x*x + y*y + z*z);
	}
	
	public boolean equals(Vector v){
		return (this.x == v.x && this.y == v.y && this.z == v.z);
	}
	
	public Vector add(Vector v){
		return new Vector(this.x + v.x, this.y + v.y, this.z + v.z);
	}
	
	public Vector substract(Vector v){
		return new Vector(this.x - v.x, this.y - v.y, this.z - v.z);
	}
	
	public Vector scalarMult(double r){
		return new Vector(this.x * r, this.y * r, this.z * r);
	}
	
	public double length() {
		return len;
	}
	
	public Vector normalize(){
		return new Vector(x/len, y/len, z/len);
	}
	
	public double dotProduct(Vector v){
		return ((this.x * v.x) + (this.y * v.y) + (this.z * v.z)); 
	}
	
	public Vector crossProduct(Vector v){
		return new Vector(this.y * v.z - this.z * v.y, this.z * v.x - this.x * v.z, this.x * v.y - this.y * v.x);
	}
	
	public double distance(Vector v)
	{
		return (substract(v).length());
	}
	
	public String toString(){
		return "(" + x + "," + y + "," + z + ")";
	}

	public int getX() {
		return (int)x;
	}

	public double getDoubleX() {
		return x;
	}
	
	public void setX(double x) {
		this.x = x;
	}

	public int getY() {
		return (int)y;
	}

	public double getDoubleY() {
		return y;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public int getZ() {
		return (int)z;
	}
	
	public double getDoubleZ() {
		return z;
	}
	
	public void setZ(double z) {
		this.z = z;
	}
	
	public static Vector vectorReflection(Vector v, Vector normal) {
		double dp = v.dotProduct(normal);
		if(dp<0) 
			dp=0;
		
		return (v.substract(normal.scalarMult(2).scalarMult(dp)));
						
	}
}
