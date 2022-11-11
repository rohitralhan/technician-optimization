package org.acme.techassign.routing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;

import com.graphhopper.GHRequest;
import com.graphhopper.GHResponse;
import com.graphhopper.GraphHopper;
import com.graphhopper.config.CHProfile;
import com.graphhopper.config.Profile;
import com.graphhopper.util.PointList;
import com.graphhopper.util.shapes.GHPoint;

import org.acme.techassign.data.MySqlGetData;
import org.acme.techassign.domain.TechNJobs;
import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
@IfBuildProperty(name = "app.routing.engine", stringValue = "GRAPHHOPPER", enableIfMissing = true)
public class GHRouter  {

	static GraphHopper hopper;
	private List<TechNJobs> tnj;
	static Map<GHPoint, Map<GHPoint, Long>> distMap;
	
    void onStart(@Observes StartupEvent ev) {               
       System.out.println("The application is starting...");
       hopper = createGraphHopperInstance("/Users/rralhan/rh-projects/optaplanner/technician-assignment/data/openstreetmap/canada-latest.osm.pbf");    
       generateMatrix();
    }

    void onStop(@Observes ShutdownEvent ev) {               
    	System.out.println("The application is stopping...");
    	//graphHopper.close();
    	//graphHopper.clean();   	
    }	
	
    
    static GraphHopper createGraphHopperInstance(String ghLoc) { 
        hopper = new GraphHopper();
        hopper.setOSMFile(ghLoc);
        // specify where to store graphhopper files
        hopper.setGraphHopperLocation("/Users/rralhan/rh-projects/optaplanner/technician-assignment/data/routing-graph-cache");

        // see docs/core/profiles.md to learn more about profiles
        hopper.setProfiles(new Profile("car").setVehicle("car").setWeighting("fastest").setTurnCosts(false)); 

        // this enables speed mode for the profile we called car
        hopper.getCHPreparationHandler().setCHProfiles(new CHProfile("car"));

        // now this can take minutes if it imports or a few seconds for loading of course this is dependent on the area you import
        hopper.importOrLoad();
        return hopper;
    }	    
    
    
    public List<Coordinates> getPath(Coordinates from, Coordinates to) {
    	
    	//GraphHopper hopper = new GraphHopper();
        GHRequest ghRequest = new GHRequest(
                from.latitude().doubleValue(),
                from.longitude().doubleValue(),
                to.latitude().doubleValue(),
                to.longitude().doubleValue());
        
        PointList points = hopper.route(ghRequest).getBest().getPoints();
        return StreamSupport.stream(points.spliterator(), false)
                .map(ghPoint3D -> Coordinates.of(ghPoint3D.lat, ghPoint3D.lon))
                .collect(Collectors.toList());
    }

    public long travelTimeMillis(Coordinates from, Coordinates to) {
        GHRequest ghRequest = new GHRequest(
                from.latitude().doubleValue(),
                from.longitude().doubleValue(),
                to.latitude().doubleValue(),
                to.longitude().doubleValue());
        GHResponse ghResponse = hopper.route(ghRequest);
        System.out.println(ghResponse.toString());
        // TODO return wrapper that can hold both the result and error explanation instead of throwing exception
        if (ghResponse.hasErrors()) {
            //throw new DistanceCalculationException("No route from " + from + " to " + to, ghResponse.getErrors().get(0));
        }
        return ghResponse.getBest().getTime();
    }
    
    
	
	  public long travelTimeMillis(GHPoint from, GHPoint to) {	
	  GHRequest ghRequest = new GHRequest(from.lat, from.lon, to.lat, to.lon); 
	  ghRequest.setProfile("car"); GHResponse
	  ghResponse = hopper.route(ghRequest);
	  // TODO return wrapper that can hold both the result and error explanation instead of throwing exception 
	  if(ghResponse.hasErrors()) { 
		} 
	  	return ghResponse.getBest().getTime(); 
	  }
	 

    public long travelTimeMillis(List<GHPoint> ghp) {
    	GHRequest ghRequest = new GHRequest(ghp); 
        ghRequest.setProfile("car");
        GHResponse ghResponse = hopper.route(ghRequest);
        // TODO return wrapper that can hold both the result and error explanation instead of throwing exception
        if (ghResponse.hasErrors()) {
        	//throw new DistanceCalculationException("No route from " + from + " to " + to, ghResponse.getErrors().get(0));
        } 
        return ghResponse.getBest().getTime();
    }    
    
    
    public long getTravelTime(List<GHPoint> ghp) {
    	long totalTravelTime = 0;
    	for (int i = 0; i < ghp.size() - 1; i++) {
    		totalTravelTime += searchMatrix(ghp.get(i), ghp.get(i+1));
    	}
		return totalTravelTime;
    }
    
    
    private long searchMatrix(GHPoint a, GHPoint b) {
    	long travelTime = distMap.get(a).get(b);
    	return travelTime;
    }
    
    
    public void generateMatrix() {
        MySqlGetData dataGenerator = new MySqlGetData();
        tnj = dataGenerator.getTechsnJobs();   	
    	distMap = new HashMap();

    	
    	for(TechNJobs tj : tnj) {
    		Map<GHPoint, Long> tMap = new HashMap<>();
    		for(TechNJobs tjs : tnj) {
    			if(tjs.getRegion().equals(tj.getRegion()) && tjs.getDistrict().equals(tj.getDistrict())) {
        			if(tjs.getId().equals(tj.getId())) {
    					tMap.put(new GHPoint(tjs.getLati(), tjs.getLongi()), 0L);
    				}else {
    					tMap.put(new GHPoint(tjs.getLati(), tjs.getLongi()), travelTimeMillis(new GHPoint(tj.getLati(), tj.getLongi()), new GHPoint(tjs.getLati(), tjs.getLongi())));
    				}
    			}else {
					//tMap.put(new GHPoint(tjs.getLati(), tjs.getLongi()), (long) Double.POSITIVE_INFINITY);
    				tMap.put(new GHPoint(tjs.getLati(), tjs.getLongi()), travelTimeMillis(new GHPoint(tj.getLati(), tj.getLongi()), new GHPoint(tjs.getLati(), tjs.getLongi())));
    			}
    		}
    		distMap.put(new GHPoint(tj.getLati(), tj.getLongi()), tMap);
    	}
    	System.out.println("Matrix Created");
    }    
    
}
