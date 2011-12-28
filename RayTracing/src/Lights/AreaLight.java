package Lights;

import raytracer.Intersection;
import raytracer.Ray;
import raytracer.Scene;
import raytracer.Vector;

public class AreaLight extends Light {

	private int N=2;
	private Vector p0 = new Vector(0,0,0);
	private Vector p1 = new Vector(0,0,0); 
	private Vector p2 = new Vector(0,0,0);
	PointLight[][] plList;
	
	public AreaLight(){
		update();
	}
	
	private void update() {
		plList = new PointLight[N][N];
		Vector d1 = p1.substract(p0);
		Vector d2 = p2.substract(p0);
		double d1Step = d1.length() / N;
		double d2Step = d2.length() / N;
		d1 = d1.normalize();
		d2 = d2.normalize();
		double NN = 1.0 / (double)(N*N) / 255.0;
		for ( int i = 0; i < N; i++ ) {
			for ( int j = 0; j < N; j++ ){
				plList[i][j] = new PointLight();
				Vector pos = d1.scalarMult(i*d1Step)
						.add(d2.scalarMult(j*d2Step))
						.add(p0);
				plList[i][j].setPosition(pos);
				plList[i][j].setIntensity(getIntensity().scalarMult(NN));
			}
		}
	}

	@Override
	public void setIntensity(Vector intensity) {
		super.setIntensity(intensity);
		update();
	}
	
	@Override
	public Vector findLightImpact(Scene scene, Ray ray, Intersection hit) {
		Vector sum = new Vector(0,0,0);
		for ( PointLight[] plline : plList )
			for ( PointLight pl : plline )
				sum = sum.add(pl.findLightImpact(scene, ray, hit));
		return sum;
	}
	
	public int getN() {
		return N;
	}

	public void setN(int n) {
		N = n;
		update();
	}

	public Vector getP0() {
		return p0;
	}

	public void setP0(Vector p0) {
		this.p0 = p0;
		update();
	}

	public Vector getP1() {
		return p1;
	}

	public void setP1(Vector p1) {
		this.p1 = p1;
		update();
	}

	public Vector getP2() {
		return p2;
	}

	public void setP2(Vector p2) {
		this.p2 = p2;
		update();
	}

}
