package jason.algorithm.interval;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.PriorityQueue;

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
	
	public static class VolumeInterval  {
		public int start;
		public int end;
		public int volume;
		
		public VolumeInterval(int start, int volume) {
			super();
			this.start = start;
			this.volume = volume;
		}


		public VolumeInterval(int start, int end, int volume) {
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
			VolumeInterval other = (VolumeInterval) obj;
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
		List<VolumeInterval> volumes;
		int nextIndex=0;
	}
	public static  List<VolumeInterval> mix(List<Speaker> speakers){
		List<VolumeInterval> results=new LinkedList<VolumeInterval>();
		PriorityQueue<VolumeInterval> activeVolumes=new PriorityQueue<>((a,b)->{
			return b.volume-a.volume;
		});
	
		VolumeInterval currentInterval=null;
		int currentTimeline=0;
		while (!speakers.isEmpty() ||  !activeVolumes.isEmpty()){
			
			
			for (ListIterator<Speaker> speakerIterator=speakers.listIterator(); speakerIterator.hasNext();){
				
				Speaker speaker=speakerIterator.next();
				VolumeInterval nextVolume=speaker.volumes.get(speaker.nextIndex);
			
				if (nextVolume.start==currentTimeline){
					//do we have new active volume.
					activeVolumes.offer(nextVolume);
				} else if (nextVolume.end==currentTimeline){
					//does any new active reach end
					activeVolumes.remove(nextVolume);
					speaker.nextIndex++;
					if (speaker.nextIndex==speaker.volumes.size()){
						speakerIterator.remove();
					}
				}
			}
			
		
			//volume ends in current timeline, we have a quite period
			if (activeVolumes.isEmpty() ){
				if ( currentInterval!=null){
					/*
					 * Since currentInterval is not null activeVolumes should not be empty in last iteration.
					 * It becomes empty because of removing action before.
					 */
					currentInterval.end=currentTimeline; 
					results.add(currentInterval);
					currentInterval=null;
				} 
			} else  {
				if (currentInterval==null){
					//create a new one.
					currentInterval=new VolumeInterval(currentTimeline, activeVolumes.peek().volume);
				} else {
					//extend current one.
					if (activeVolumes.peek().volume!=currentInterval.volume){
						currentInterval.end=currentTimeline;
						results.add(currentInterval);
						
						currentInterval=new VolumeInterval(currentTimeline, activeVolumes.peek().volume);
					}
				}
			}
			
			currentTimeline++;
			
			
		}
		
		return results;
	}
	
	
	
	public List<VolumeInterval> initVolumes(int[] inputs){
		List<VolumeInterval> volumes=new ArrayList<>(inputs.length/3);
		for (int i=0; i<inputs.length; i+=3){
			VolumeInterval v=new VolumeInterval(inputs[i], inputs[i+2]);
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
		
		List<VolumeInterval> expected=initVolumes(new int[]{2,4,5});
		
		List<VolumeInterval> results=mix(speakers);
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
		
		List<VolumeInterval> expected=initVolumes(new int[]{2,4,5, 5,7,5});
		
		List<VolumeInterval> results=mix(speakers);
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
		
		List<VolumeInterval> expected=initVolumes(new int[]{2,3,5, 3,4,6, 4,7,5, 7,8,7, 9,10,6});
		
		List<VolumeInterval> results=mix(speakers);
		for (int i=0; i<expected.size(); i++){
			System.out.printf("[%d, %d, %d]\n", results.get(i).start, results.get(i).end, results.get(i).volume);
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
		
		List<VolumeInterval> expected=initVolumes(new int[]{1,2,1,   2,5,10,   5,6,1,  6,8,2,  8,12,8});
		
		List<VolumeInterval> results=mix(speakers);
		for (int i=0; i<expected.size(); i++){
			System.out.printf("[%d, %d, %d]\n", results.get(i).start, results.get(i).end, results.get(i).volume);
			assertEquals(expected.get(i), results.get(i));
		}
	}
}
