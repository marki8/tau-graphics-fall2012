package raytracer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import GeometricPrimitives.GeometricPrimitive;
import GeometricPrimitives.Rectangle;
import Lights.Light;
import Lights.ParallelDirectionalLight;
import Lights.PointLight;

import java.io.StreamTokenizer;
import java.util.LinkedList;
import java.util.List;

public class SceneParser {

	Scene scene;
	List<GeometricPrimitive> geoList = new LinkedList<>();
	List<Light> lightList = new LinkedList<>();
	
	public SceneParser(Scene m_scene) {
		this.scene = m_scene;
	}

	public void parse(BufferedReader br) throws IOException {

		String line, obj = null, param, valueStr;
		double value;
		
		// TODO: deal with cases of illegal input
		
		while((line = br.readLine()) != null) {
			if(line.length() == 0){
				continue;
			}
			else if (line.charAt(line.length() - 1) == ':') {
				obj = line.substring(0, line.length() - 1);
				addObject(obj);
				
				System.out.println("object: " + obj); //????
			}
			else {
				int equalsLoc = line.indexOf('=');
				int tmpEqualsLoc = equalsLoc; 
				int firstCharLocationBeforEq, firstCharLocationAfterEq;
				tmpEqualsLoc--;
				while (line.charAt(tmpEqualsLoc) == ' '){
					tmpEqualsLoc--;
				}
				firstCharLocationBeforEq = tmpEqualsLoc;
				tmpEqualsLoc = equalsLoc+1;
				while (line.charAt(tmpEqualsLoc) == ' '){
					tmpEqualsLoc++;
				}
				firstCharLocationAfterEq = tmpEqualsLoc;
				param = line.substring(0, firstCharLocationBeforEq+1);
				//valueStr = line.substring(firstCharLocationAfterEq, line.length());
				String[] args = line.substring(firstCharLocationAfterEq).trim().toLowerCase().split("\\s+");
				
				if(obj == null){
					// ????
				}
				
				addParams(obj, args);
				
				System.out.print("param : " + param + " value: "); //????
				for(int i=0; i<args.length; i++){
					System.out.print(args[i] + " ");
				}
				System.out.println();
			}
			
		}

	
		/*Vector v0 = new Vector(-30, 30, -300);
		Vector v1 = new Vector(30, 30, -300);
		Vector v2 = new Vector(-30, -30, -300);
		Rectangle rec = new Rectangle(v0,v1,v2);
		Surface s1 = new Surface();
		s1.setMtlDiffuse(new Vector(0.98,0.48,0.4));
		s1.setMtlSpecular(new Vector(0.7,0.7,0.7));
		s1.setMtlShininess(20);
		rec.setSurface(s1);
		scene.addGeomObject(rec);
		
		v0 = new Vector(0, 30, -400);
		v1 = new Vector(150, 30, -400);
		v2 = new Vector(-100, -30, -400);
		Rectangle rec2 = new Rectangle(v0,v1,v2);
		Surface s2 = new Surface();
		s2.setMtlDiffuse(new Vector(0.5,0.6,0.7));
		rec2.setSurface(s2);
		scene.addGeomObject(rec2);*/	
	}

	private void addParams(String obj, String[] args) {
		// TODO Auto-generated method stub
		
	}

	private Object addObject(String obj) {
		if(obj.equals("scene")) return new Scene();
//		if(obj.equals("camera")) return new Camera();
//		if(obj.equals("light-directed")) return new ParallelDirectionalLight();
//		if(obj.equals("light-point:")) return new PointLight();
//		//if(obj.equals("light-area")) return new ;
//		if(obj.equals("rectangle")) return new Rectangle();
//		//if(obj.equals("disc")) return new ;
//		if(obj.equals("sphere")) return new Sphere();
//		if(obj.equals("box")) return new Box();
//		if(obj.equals("cylinder")) return new Cylinder();
		return null;
	}


	

}
