//39,65,68,49,22,4,45,93,81,94,89,29,100,18,90,83,48,43,37,76,34,46,25,77,92,3,40,64,70,56,91,6,41,84,44,99,61,66,19,23,60,16,71,67,11,88,15,97,51,30,26,42,8,1,73,12,95,21,31,36,79,10,62,69,63,20,28,53,13,82,87,35,86,24,27,32,17,2,55,50,52,38,54,75,7,78,85,57,74,9,59,33,98,58,47,72,80,5,96,14,
//above is the string I get on output.
//Ian Berigan
//17428946
//CS211 BurritoTSP Problem final 5% project.
package BurritoTSP;
import java.util.*;
import java.io.*;

public class BurritoTSP {

	public static void main(String[] args) throws IOException {
		List<String> raw = readTextFile("E:\\BTSPtextFile.txt");
		List<Location> locations = getLocations(raw);
		Location theShop = new Location("n/a","Our Shop","N/A","53.38195","-6.59192");
		List<Location> ordered = new ArrayList<Location>();
		Location start = theShop;
		for(int i = 0 ; i < 100; i++) {
			Location save = findclosestunvisited(start,locations,ordered);
			ordered.add(save);
			start = save;
		}
		for(Location l:ordered) {
			System.out.print(l.orderNumber + ",");
		}
		
	}
	
	public static Location findclosestunvisited(Location current,List<Location> locations,List<Location> visited) {
		double distanceTo = Double.MAX_VALUE;
		Location temp = null;
		for(Location l: locations) {
			if(distancefrompoint(current,l) < distanceTo && !visited.contains(l)) {
				temp = l;
				distanceTo = distancefrompoint(current,l);
			}
		}
		return temp;
	}

	public static List<String> readTextFile(String path) throws IOException{	
    	List<String> list = new ArrayList<String>();
    	File file = new File(path);
    	BufferedReader br = new BufferedReader(new FileReader(file));
    	String s;
    	while((s = br.readLine()) != null) {
    		list.add(s);			
    	}	
     	br.close();		
    	return list;	
     }
	
	public static Double toRad(Double value) {
	    return value * Math.PI / 180;
	}
	
	public static double distancefrompoint(Location l1,Location l2) {
		return LongLatDistance(l1.Lat,l1.Long,l2.Lat,l2.Long);
	}
	
    public static double LongLatDistance(String latu1, String longu1, String latu2, String longu2){
        final int R = 6371; 
        Double lat1 = Double.parseDouble(latu1);
        Double lon1 = Double.parseDouble(longu1);
        Double lat2 = Double.parseDouble(latu2);
        Double lon2 = Double.parseDouble(longu2);
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
        Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * 
        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance*1000;
    }
	
	public static List<Location> getLocations(List<String> raw){
		List<Location> locations = new ArrayList<Location>();
		for(String data:raw) {
			String[] datasplit = data.split(",");
			locations.add(new Location(datasplit[0],datasplit[1],datasplit[2],datasplit[3],datasplit[4]));
		}
		return locations;
	}

}

class minuteswaitingComparator implements Comparator<Location>{
	@Override
	public int compare(Location l1,Location l2) {
		double time1 = Double.parseDouble(l1.time);
		double time2 = Double.parseDouble(l2.time);
		if(time1<time2) {
			return -1;
		} else if ( time2 < time1) {
			return 1;
		} else {
			return 0;
		}
	}
}

class distancefromShopComparator implements Comparator<Location>{
	@Override
	public int compare(Location l1,Location l2) {
		if(distancefromshop(l1)<distancefromshop(l2)) {
			return -1;
		} else if (distancefromshop(l2)<distancefromshop(l1)) {
			return 1;
		} else {
			return 0;
		}
	}
	
	
	public double distancefromshop(Location l) {
		Location theShop = new Location("n/a","Our Shop","N/A","53.38195","-6.59192");
		return distancefrompoint(theShop,l);
	}
	
	public static Double toRad(Double value) {
	    return value * Math.PI / 180;
	}
	
	public static double distancefrompoint(Location l1,Location l2) {
		return LongLatDistance(l1.Long,l1.Lat,l2.Long,l2.Lat);
	}
	
	public static double LongLatDistance(String latu1, String longu1, String latu2, String longu2){
        final int R = 6371; 
        Double lat1 = Double.parseDouble(latu1);
        Double lon1 = Double.parseDouble(longu1);
        Double lat2 = Double.parseDouble(latu2);
        Double lon2 = Double.parseDouble(longu2);
        Double latDistance = toRad(lat2-lat1);
        Double lonDistance = toRad(lon2-lon1);
        Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) + 
        Math.cos(toRad(lat1)) * Math.cos(toRad(lat2)) * 
        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        Double distance = R * c;
        return distance*1000;
    }
}

class Location{
	public String orderNumber;
	public String address;
	public String time;
	public String Long;
	public String Lat;
	public boolean visited;
	
	public Location(String orderNumber,String address,String time,String Long,String Lat) {
		this.orderNumber = orderNumber;
		this.address = address;
		this.time = time;
		this.Long = Long;
		this.Lat = Lat;
		this.visited = false;
	}
	@Override
	public String toString() {
		return orderNumber + " " + address + " " + time + " " + Long + " " + Lat + " " + visited;
	}
}