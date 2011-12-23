package GeometricPrimitives;

import raytracer.Ray;
import raytracer.Vector;

public class Box extends GeometricPrimitive {

	private Rectangle r0, r1, r2, r3, r4, r5;
	
	public Box(Rectangle r0,Rectangle r1,Rectangle r2,Rectangle r3,Rectangle r4,Rectangle r5){
		this.setR0(r0);
		this.setR1(r1);
		this.setR2(r2);
		this.setR3(r3);
		this.setR4(r4);
		this.setR5(r5);
	}
	
	@Override
	public Vector getIntersection(Ray r) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Vector findNormal() {
		// TODO Auto-generated method stub
		return null;
	}

	public Rectangle getR0() {
		return r0;
	}

	public void setR0(Rectangle r0) {
		this.r0 = r0;
	}

	public Rectangle getR1() {
		return r1;
	}

	public void setR1(Rectangle r1) {
		this.r1 = r1;
	}

	public Rectangle getR2() {
		return r2;
	}

	public void setR2(Rectangle r2) {
		this.r2 = r2;
	}

	public Rectangle getR3() {
		return r3;
	}
	public void setR3(Rectangle r3) {
		this.r3 = r3;
	}
	
	public Rectangle getR4() {
		return r4;
	}

	public void setR4(Rectangle r4) {
		this.r4 = r4;
	}

	public Rectangle getR5() {
		return r5;
	}

	public void setR5(Rectangle r5) {
		this.r5 = r5;
	}
}
