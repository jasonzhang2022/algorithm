package jason.algorithm.practice;

import java.util.ArrayList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;

import org.junit.Test;
import static org.junit.Assert.*;



public class IntervalTest {
	
	
	public void collectionEquals(ArrayList<Interval> result, ArrayList<Interval> expected){
		assertEquals(result.size(), expected.size());
		
		for (int i=0; i<result.size(); i++){
			assertEquals(result.get(i), expected.get(i));
		}
	}
	
	
	
	
	@Test
	public void intervalTestEmptyCollection(){
		ArrayList<Interval> intervals=new ArrayList<>();
		
		Interval toBeInserted=new Interval(2, 3);
		
		InsertInterval.insertInterval(intervals, toBeInserted);
		assertThat(intervals, contains(toBeInserted));
		
		
	}
	

	@Test
	public void intervalTestAddToEnd(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(1, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(80, 90);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(1, 10));
		expected.add(new Interval(20, 30));
		expected.add(new Interval(40, 50));
		expected.add(new Interval(60, 70));
		expected.add(new Interval(80, 90));
		
		
		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	
	
	@Test
	public void intervalTestAddToMiddle(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(1, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(55, 57);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(1, 10));
		expected.add(new Interval(20, 30));
		expected.add(new Interval(40, 50));
		expected.add(new Interval(55, 57));
		expected.add(new Interval(60, 70));
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	
	@Test
	public void intervalTestAddToBeggin(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(7, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(1, 5);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(1, 5));
		expected.add(new Interval(7, 10));
		expected.add(new Interval(20, 30));
		expected.add(new Interval(40, 50));
		
		expected.add(new Interval(60, 70));
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	
	@Test
	public void intervalTestMergeNotEqualStart(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(7, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(38, 42);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(7, 10));
		expected.add(new Interval(20, 30));
		expected.add(new Interval(38, 50));
		expected.add(new Interval(60, 70));
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	
	@Test
	public void intervalTestMergeNotEqualsStartMergeBefore(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(7, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(25, 42);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(7, 10));
		expected.add(new Interval(20, 50));
		expected.add(new Interval(60, 70));
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	@Test
	public void intervalTestMergeEqualStart(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(7, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(40, 42);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(7, 10));
		expected.add(new Interval(20, 30));
		expected.add(new Interval(40, 50));
		expected.add(new Interval(60, 70));
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	
	@Test
	public void intervalTestMergeInside(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(7, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(41, 43);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(7, 10));
		expected.add(new Interval(20, 30));
		expected.add(new Interval(40, 50));
		expected.add(new Interval(60, 70));
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	
	@Test
	public void intervalTestMergeExtendEnd(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(7, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(41, 53);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(7, 10));
		expected.add(new Interval(20, 30));
		expected.add(new Interval(40, 53));
		expected.add(new Interval(60, 70));
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	
	@Test
	public void intervalTestMultipleMerge1(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(7, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(25, 63);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(7, 10));
		expected.add(new Interval(20, 70));
	
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
	
	@Test
	public void intervalTestMultipleMerge2(){
		ArrayList<Interval> intervals=new ArrayList<>();
		intervals.add(new Interval(7, 10));
		intervals.add(new Interval(20, 30));
		intervals.add(new Interval(40, 50));
		intervals.add(new Interval(60, 70));
		
		Interval toBeInserted=new Interval(25, 73);
		
		ArrayList<Interval> expected=new ArrayList<>();
		expected.add(new Interval(7, 10));
		expected.add(new Interval(20, 73));
	
		

		InsertInterval.insertInterval(intervals, toBeInserted);
		collectionEquals(intervals, expected);
	}
}
