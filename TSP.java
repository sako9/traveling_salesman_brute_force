package brute_force;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TSP {
	public ArrayList<Point> points;	
	
	public TSP(){
		points = new ArrayList<>();
	}
	
	public static void printList(List<Point> p){
		for(int i = 0; i < p.size(); i++){
			System.out.print("("+p.get(i).x + "," + p.get(i).y + ")");
		}
		System.out.println("");
	}
	
	public void readPointsFromFile(String fileName){
		try{
			for(Scanner sc = new Scanner(new File(fileName)); sc.hasNext();){
				String line = sc.nextLine();
				if(Character.isDigit(line.charAt(0))){
					String[] filePoints = line.split(" ");
					points.add(new Point(Double.parseDouble(filePoints[1]),
							Double.parseDouble(filePoints[2])));
				}
			}
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public void readPointsFromFile(File tspFile){
		points.clear();
		try{
			for(Scanner sc = new Scanner(tspFile); sc.hasNext();){
				String line = sc.nextLine();
				if(Character.isDigit(line.charAt(0))){
					String[] filePoints = line.split(" ");
					points.add(new Point(Double.parseDouble(filePoints[1]),
							Double.parseDouble(filePoints[2])));
				}
			}
		}catch(FileNotFoundException e){
			System.out.println(e.getMessage());
		}
	}
	
	public double distance(Point a, Point b){
		return Math.sqrt(Math.pow((b.x - a.x),2) + Math.pow((b.y - a.y),2));
	}
	
	public double totalDistance(){
		double dist = 0.0;
		for(int i = 0 ; i < points.size() -1; i++){
			dist += distance(points.get(i),points.get(i+1)); 
		}
		return dist;
	}
	
	public double totalDistance(List<Point> p){
		double dist = 0.0;
		for(int i = 0 ; i < p.size() -1; i++){
			dist += distance(p.get(i),p.get(i+1)); 
		}
		return dist;
	}

}
