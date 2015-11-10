package jason.algorithm.interval;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

public class InsertInterval {

	public static List<Interval> insertInterval(ArrayList<Interval> intervals,
			Interval newInterval) {

		if (intervals.isEmpty()) {
			// special case to make sure we are not handle empty collection;
			//so we are left to only add to end special case.
			intervals.add(newInterval);
			return intervals;
		}

		int index = Collections.binarySearch(intervals, newInterval, (left,
				right) -> left.start - right.start);
		if (index < 0) {
			// not found.
			index = -index - 1;
		}

		// add to the very end.
		if (index == intervals.size() && intervals.get(index - 1).end < newInterval.start) {
			intervals.add(newInterval);
			return intervals;
		}
		//add to beginng
		if (index==0 && intervals.get(0).start>newInterval.end){
			intervals.add(0, newInterval);
			return intervals;
		}
		
		if (newInterval.start>intervals.get(index - 1).end && newInterval.end<intervals.get(index).start){
			intervals.add(index, newInterval);
			return intervals;
		}

		if (intervals.get(index - 1).end >= newInterval.start) {
			index--;
		}
		if (intervals.get(index).start>newInterval.start){
			intervals.get(index).start=newInterval.start;
		}
		if (intervals.get(index).end >= newInterval.end) {
			return intervals;
		}

		intervals.get(index).end = newInterval.end;
		mergeWithNext(intervals, index);
		return intervals;

	}

	public static void mergeWithNext(ArrayList<Interval> intervals, int index) {
		ListIterator<Interval> li = intervals.listIterator(index);

		Interval current = li.next();
		while (li.hasNext()) {
			Interval next = li.next();
			if (current.end >= next.start) {
				if (current.end <= next.end) {
					current.end = next.end;
					li.remove();
					return;
				} else {
					li.remove();
					continue;
				}
				
			} else {
				return;
			}
		}
	}

}
