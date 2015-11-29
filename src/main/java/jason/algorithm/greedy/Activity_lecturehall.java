package jason.algorithm.greedy;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import jason.algorithm.greedy.ActivitySelection.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

/**
 * Introduction to algorithm: 16.1-4
 * issue: does not have equal finish time correctly. Need a Navigable multimap
 * @author jason
 *
 */
public class Activity_lecturehall {

	
	public  Collection<LinkedList<Activity>> minimumLectureHall(int[] start, int[] end){
	
		//sort by start time.
		List<Activity> activities=new ArrayList<>(start.length);
		for (int i=0; i<start.length; i++){
			activities.add(new Activity(start[i], end[i]));
		}
		Collections.sort(activities, (a, b)->a.start-b.start);
		
		/*
		 * A list<Activity> is a lecture hall that holds the activity that occurs
		 * in the hall so far right now.
		 * It is indexed by finish time
		 */
		TreeMap<Activity, LinkedList<Activity>> lectureHalls=new TreeMap<>((a,b)->a.end-b.end);
		
		for (Activity activity: activities){
			
			Activity temp=new Activity(0, activity.start);
			Map.Entry<Activity, LinkedList<Activity>> oneEntry=lectureHalls.floorEntry(temp);
			if (oneEntry==null){
				//there is no any lecture hall that has time less that start time.
				//add this to hall
				LinkedList<ActivitySelection.Activity> oneHall=new LinkedList<ActivitySelection.Activity>();
				oneHall.add(activity);
				lectureHalls.put(activity, oneHall);
				//logN
			} else{
				oneEntry.getValue().add(activity);
				lectureHalls.remove(oneEntry.getKey()); //logN
				lectureHalls.put(activity, oneEntry.getValue());//logN
			}
		}
		return lectureHalls.values();
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
