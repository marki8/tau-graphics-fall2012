package raytracer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import GeometricPrimitives.Box;
import GeometricPrimitives.Cylinder;
import GeometricPrimitives.GeometricPrimitive;
import GeometricPrimitives.Rectangle;
import GeometricPrimitives.Sphere;
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
	int sceneHeight, sceneWidth;
	
	
	public SceneParser() {
		//this.scene = m_scene;
	}

	public Scene parse(BufferedReader br, int height, int width) throws IOException {

		String line, obj = null, param, valueStr;
		double value;
		
		sceneHeight = height;
		sceneWidth = width;
		
		// TODO: deal with cases of illegal input
		
		while((line = br.readLine()) != null) {
			if(line.length() == 0){
				continue;
			}
			else if (line.charAt(line.length() - 1) == ':') {
				obj = line.substring(0, line.length() - 1);
				//geoList.add((GeometricPrimitive) addObject(obj));
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
		return scene;
	}

	private void addParams(String obj, String[] args) {
		// TODO Auto-generated method stub
		
	}

	private void addObject(String obj) {
		if(obj.equals("scene")) scene = new Scene(sceneHeight, sceneWidth);
		else if(obj.equals("camera")) scene.setCam(new Camera());
		else if(obj.equals("light-directed")) scene.addLight(new ParallelDirectionalLight());
		else if(obj.equals("light-point:")) scene.addLight(new PointLight());
		//if(obj.equals("light-area")) return new ;
		else if(obj.equals("rectangle")) scene.addGeomObject(new Rectangle());
		//if(obj.equals("disc")) return new ;
		else if(obj.equals("sphere")) scene.addGeomObject(new Sphere());
		else if(obj.equals("box")) scene.addGeomObject(new Box());
		else if(obj.equals("cylinder")) scene.addGeomObject(new Cylinder());
		
		
		// ?????? IF NOT RECOGNIZED THROW ERROR?
	}


	

}
