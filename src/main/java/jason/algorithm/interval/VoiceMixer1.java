package jason.algorithm.interval;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.stream.Collectors;

import org.junit.Test;

import static org.junit.Assert.*;

/*
 * 第二题是这样的：
n个Speaker，S1, S2, ...Sn
每个Speaker在不同的时间段有不同的音量如：

S1: {[2,5], vol=10}, {[6,10], vol=2}, ...
S2: {[1,6], vol=1}, {[8,12], vol=8}, ...
...

请输出每个时间段及这个时间段内最大的音量
比如，只有S1和S2的话，输出就是
[1,2],vol=1, [2,5], vol=10, [5,6], vol = 1, [6,8], vol = 2, [8,12], vol = 8.
 */
public class VoiceMixer1 {
	public static class Volume {
		int start;
		int end;
		int volume;
		public Volume(int start,  int volume) {
			super();
			this.start = start;
			this.volume = volume;
		}
		public Volume(int start, int end, int volume) {
			super();
			this.start = start;
			this.end = end;
			this.volume = volume;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + end;
			result = prime * result + start;
			result = prime * result + volume;
			return result;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Volume other = (Volume) obj;
			if (end != other.end)
				return false;
			if (start != other.start)
				return false;
			if (volume != other.volume)
				return false;
			return true;
		}
		
	}
	public static class Speaker {
		Queue<Volume> volumes;
		int nextIndex=0;
	}
	public static  List<Volume> mix(List<Speaker> speakers){
		
		List<Volume> results=new LinkedList<Volume>();
		Volume previousVolume=new Volume(0, 0, 0);
		PriorityQueue<Volume> activeVolumes=new PriorityQueue<>((a,b)->{
			return b.volume-a.volume;
		});
		int currentTimeline=0;
		while (!speakers.isEmpty() ||  !activeVolumes.isEmpty()){
			for (ListIterator<Speaker> speakerIterator=speakers.listIterator(); speakerIterator.hasNext();){
				
				Speaker speaker=speakerIterator.next();
				if (speaker.volumes.isEmpty()){
					speakerIterator.remove();
					continue;
				}
				Volume nextVolume=speaker.volumes.peek();
				if (nextVolume.start>currentTimeline){
					continue;
				}
				speaker.volumes.poll();
				activeVolumes.offer(nextVolume);
			}
			
			//remove those volumes that at the top of heap, but has a end time which is past.
			while (!activeVolumes.isEmpty() && activeVolumes.peek().end<=currentTimeline){
				Volume v=activeVolumes.poll();
				System.out.printf("remove %d: %d->%d:%d\n ",currentTimeline, v.start, v.end, v.volume);
			}
			
			//all volumes at activeVolume should have a start time <= current time line.
			
			if (activeVolumes.isEmpty() ){
				//we have a quite period
				if (previousVolume.volume!=0){
					previousVolume.end=currentTimeline;
					previousVolume=new Volume(currentTimeline, 0);
					results.add(previousVolume);
				}
			} else  {
				Volume volume=activeVolumes.peek();
				/*
				 * volume has the loudest volume at current time point.
				 * 
				 * if previousVolume.volume is louder than this one and ends at current time, it should be
				 * removed. 
				 * 
				 */
				if (volume.volume!=previousVolume.volume){
					previousVolume.end=currentTimeline;
					previousVolume=new Volume(currentTimeline, volume.volume);
					results.add(previousVolume);
				}
			}
			currentTimeline++;
		}
		
		return results.stream().filter(v->v.volume!=0).collect(Collectors.toList());
	}
	
	
	
	public static LinkedList<Volume> initVolumes(int[] inputs){
		LinkedList<Volume> volumes=new LinkedList<>();
		for (int i=0; i<inputs.length; i+=3){
			Volume v=new Volume(inputs[i], inputs[i+2]);
			v.end=inputs[i+1];
			volumes.add(v);
		}
		return volumes;
	}
	
	@Test
	public void testSimple() {
		Speaker s1=new Speaker();
		s1.volumes=initVolumes(new int[]{2,4,5});
		
		List<Speaker> speakers=new ArrayList<>();
		speakers.add(s1);
		
		List<Volume> expected=initVolumes(new int[]{2,4,5});
		
		List<Volume> results=mix(speakers);
		for (int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), results.get(i));
		}
	}
	
	@Test
	public void testSimpleWithEmpty() {
		Speaker s1=new Speaker();
		s1.volumes=initVolumes(new int[]{2,4,5, 5,7,5});
		
		List<Speaker> speakers=new ArrayList<>();
		speakers.add(s1);
		
		List<Volume> expected=initVolumes(new int[]{2,4,5, 5,7,5});
		
		List<Volume> results=mix(speakers);
		System.out.println(results.stream().map(v->String.format("%d->%d:%d ", v.start, v.end, v.volume)).collect(Collectors.joining(",")));
		
		for (int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), results.get(i));
		}
	}
	
	@Test
	public void testSimpleOverlap() {
		Speaker s1=new Speaker();
		s1.volumes=initVolumes(new int[]{2, 8, 5});
		
		Speaker s2=new Speaker();
		s2.volumes=initVolumes(new int[]{3,4,6, 5,6,4, 7,8,7, 9,10,6});
		
		List<Speaker> speakers=new ArrayList<>();
		speakers.add(s1);
		speakers.add(s2);
		
		List<Volume> expected=initVolumes(new int[]{2,3,5, 3,4,6, 4,7,5, 7,8,7, 9,10,6});
		
		List<Volume> results=mix(speakers);
		
		System.out.println(results.stream().map(v->String.format("%d->%d:%d ", v.start, v.end, v.volume)).collect(Collectors.joining(",")));
		
		for (int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), results.get(i));
		}
	}
	
	@Test
	public void testSimpleOverlapQuestion() {
		Speaker s1=new Speaker();
		s1.volumes=initVolumes(new int[]{2, 5, 10, 6,10,2});
		
		Speaker s2=new Speaker();
		s2.volumes=initVolumes(new int[]{1,6,1, 8,12,8});
		
		List<Speaker> speakers=new ArrayList<>();
		speakers.add(s1);
		speakers.add(s2);
		
		List<Volume> expected=initVolumes(new int[]{1,2,1,   2,5,10,   5,6,1,  6,8,2,  8,12,8});
		
		List<Volume> results=mix(speakers);
		System.out.println(results.stream().map(v->String.format("%d->%d:%d ", v.start, v.end, v.volume)).collect(Collectors.joining(",")));
				
		for (int i=0; i<expected.size(); i++){
			System.out.printf("[%d, %d, %d]\n", results.get(i).start, results.get(i).end, results.get(i).volume);
			assertEquals(expected.get(i), results.get(i));
		}
	}
}
