package jason.algorithm.greedy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import jason.algorithm.greedy.ActivitySelection.Activity;

//process at interval graph color problem
public class Activity_lecturehall_graphcolor {
	
	Map<Activity, List<Activity>> incidenceGraph = new IdentityHashMap<>();
	Map<Activity, BitSet> excludedColor=new IdentityHashMap<>();
	Map<Activity, Integer> colors=new IdentityHashMap<>();
	int nextColorIndex=1;
	public  Collection<LinkedList<Activity>> minimumLectureHall(int[] start, int[] end){
		
		//sort by start time.
		List<Activity> activities=new ArrayList<>(start.length);
		for (int i=0; i<start.length; i++){
			activities.add(new Activity(start[i], end[i]));
		}
		Collections.sort(activities, (a, b)->a.start-b.start);
		
		//construct incidence graph
		for (int i=0; i<start.length; i++){
			Activity current=activities.get(i);
			
			for (int j=i+1; j<start.length; j++){
				Activity nearby=activities.get(j);
				if (nearby.start<current.end){
					
					if (incidenceGraph.containsKey(nearby)){
						incidenceGraph.get(nearby).add(current);
					} else {
						List<Activity> list=new LinkedList<>();
						list.add(current);
						incidenceGraph.put(nearby, list);
					}
					
				} else{
					break;
				}
			}
			
		}
		
		
		
		colors.put(activities.get(0), 0);
		for (int i=1; i<start.length; i++){
			colors.put(activities.get(i), findColorUtil(activities.get(i)));
		}
		
		ArrayList<LinkedList<Activity>> ret=new ArrayList<>(nextColorIndex);
		for (int i=0; i<nextColorIndex; i++){
			ret.add(new LinkedList<Activity>());
		}
		for (int i=0; i<start.length; i++){
			Activity current=activities.get(i);
			int color=colors.get(current);
			ret.get(color).add(current);
		}
		
		
		
		return ret;
		
		
	}
	
	private int findColorUtil(Activity activity){
		if (colors.containsKey(activity)){
			return colors.get(activity);
		}
		
		
		BitSet usedColor=new BitSet();
		if (!incidenceGraph.containsKey(activity)){
			//compatible with any vertex
			colors.put(activity, 0);
			return 0;
		}
		for (Activity incompatible: incidenceGraph.get(activity)){
			usedColor.set(findColorUtil(incompatible), true);
		}
		
		if (usedColor.cardinality()==nextColorIndex){
			colors.put(activity, nextColorIndex++);
		} else{
			colors.put(activity, usedColor.nextClearBit(0));
		}
		return colors.get(activity);
	}
	
	@Test
	public void testFirstFinish(){
		int[] start={1, 3, 0, 5, 8, 5};
		int[] end={2, 4, 6, 7, 9, 9};
		
		Collection<LinkedList<Activity>> results=minimumLectureHall(start, end);
		
		assertThat(results.size(), equalTo(3));
		for (LinkedList<Activity> oneHall: results){
			System.out.println(Arrays.toString(oneHall.toArray()));
		}
		
	}

}
