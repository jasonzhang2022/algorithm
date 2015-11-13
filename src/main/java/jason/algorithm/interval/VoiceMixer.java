package jason.algorithm.interval;

import static org.junit.Assert.*;
import jason.algorithm.interval.VoiceMixer1.Speaker;

import java.util.*;

import org.junit.Test;
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
public class VoiceMixer {
	
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
	
	public static class VolumePoint{
		Volume volume;
		Boolean start=true;
		
		int index;
		ArrayList<Volume> speaker;
		
		public int getPoint(){
			return start?volume.start:volume.end;
		}
		
		public boolean hasNext(){
			return index+1<speaker.size();
		}
		
		public VolumePoint getNextStart(){
			VolumePoint vp=new VolumePoint();
			vp.start=true;
			vp.index=index+1;
			vp.volume=speaker.get(index+1);
			vp.speaker=speaker;
			return vp;
		}
		public VolumePoint getNextEnd(){
			VolumePoint vp=new VolumePoint();
			vp.start=false;
			vp.index=index+1;
			vp.volume=speaker.get(index+1);
			vp.speaker=speaker;
			return vp;
		}
		
	}

	public static class ResultPoint {
		int time;
		int volume;
		public ResultPoint(int time, int volume) {
			super();
			this.time = time;
			this.volume = volume;
		}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + time;
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
			ResultPoint other = (ResultPoint) obj;
			if (time != other.time)
				return false;
			if (volume != other.volume)
				return false;
			return true;
		}
		
		
	}
	
	public static ArrayList<ResultPoint> calculateMaxVolume(ArrayList<ArrayList<Volume>> speakers){
		
	
		/*
		 * This priority queue holds the next time point to be considered from one list
		 */
		PriorityQueue<VolumePoint> frontVolumes=new PriorityQueue<>((v1, v2)->{
			int value1=v1.start?v1.volume.start:v1.volume.end;
			int value2=v2.start?v2.volume.start:v2.volume.end;
			return value1-value2;
		});
		
		//descending order
		/*
		 * The max volume in current live volumes. 
		 * live volume the volumes which are effective.
		 */
		PriorityQueue<Volume> maxVolumes=new PriorityQueue<>((v1, v2)->{
			return -(v1.volume-v2.volume);
		});
		
		//initialize frontVolumes.
		for (int i=0; i<speakers.size(); i++){
			ArrayList<Volume> oneSpeaker=speakers.get(i);
			if (oneSpeaker.isEmpty()){
				continue;
			}
			
			Volume v=oneSpeaker.get(0);
			VolumePoint startPoint=new VolumePoint();
			startPoint.index=0;
			startPoint.volume=v;
			startPoint.start=true;
			startPoint.speaker=oneSpeaker;
			
			
			VolumePoint endPoint=new VolumePoint();
			endPoint.index=0;
			endPoint.volume=v;
			endPoint.start=false;
			endPoint.speaker=oneSpeaker;
		
			
			frontVolumes.offer(startPoint);
			frontVolumes.offer(endPoint);
			
		}
		
		
		ArrayList<ResultPoint> results=new ArrayList<>();
		results.add(new ResultPoint(0, 0)); //start at 0 with volume 0.
		ResultPoint prevPoint=results.get(0);
		while (!frontVolumes.isEmpty()){
			
			//vp is th smallest point we need to process
			//it is the scan point for live volume.
			VolumePoint vp=frontVolumes.peek();
			
			//process all volume point that has a position at vp.getPoint()
			while (!frontVolumes.isEmpty()){
				VolumePoint next=frontVolumes.peek();
				if (next.getPoint()!=vp.getPoint()){
					break;
				}
				frontVolumes.poll(); //remove;
				if (next.start){
					maxVolumes.offer(next.volume);
				} else{
					maxVolumes.remove(next.volume);
					//add this since the next point could start with current end.
					if (next.hasNext()){
						frontVolumes.add(next.getNextStart());
						frontVolumes.add(next.getNextEnd());
					}
				}
			}
			
			
			Volume nextMax=maxVolumes.peek();
			if (nextMax==null){
				ResultPoint rp=new ResultPoint(vp.getPoint(), 0); //voice is decreased to zero.
				results.add(rp);
				prevPoint=rp;
				continue;
			}
			
			if (nextMax.volume!=prevPoint.volume){
				ResultPoint rp=new ResultPoint(vp.getPoint(), nextMax.volume); //voice is decreased to zero.
				results.add(rp);
				prevPoint=rp;
			}
		}
		return results;
		
	}
	
	public static ArrayList<Volume> resultPointsToVolumes(ArrayList<ResultPoint> rps){
		
		ArrayList<Volume> results=new ArrayList<>(rps.size());
		
		ResultPoint pre=rps.get(0);
		for(int i=1; i<rps.size(); i++){
			ResultPoint current=rps.get(i);
			if (pre.volume==0){
				//a period of quiet
			} else {
				results.add(new Volume(pre.time, current.time, pre.volume));
			}
			pre=current;
		}
		return results;
	}
	
	@Test
	public void testZeroStart() {
		ArrayList<Volume> s1=VoiceMixer1.initVolumes(new int[]{0,4,5});
		
		ArrayList<ArrayList<Volume>> speakers=new ArrayList<ArrayList<Volume>>();
		speakers.add(s1);
		
		List<Volume> results=resultPointsToVolumes(calculateMaxVolume(speakers));
		ArrayList<Volume> expected=VoiceMixer1.initVolumes(new int[]{0,4,5});
		for (int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), results.get(i));
		}
	}
	
	
	@Test
	public void testOneEndPointIsNextStartPoint() {
		ArrayList<Volume> s1=VoiceMixer1.initVolumes(new int[]{2,4,5, 4, 7, 8});
		
		ArrayList<ArrayList<Volume>> speakers=new ArrayList<ArrayList<Volume>>();
		speakers.add(s1);
		
		List<Volume> results=resultPointsToVolumes(calculateMaxVolume(speakers));
		ArrayList<Volume> expected=VoiceMixer1.initVolumes(new int[]{2,4,5, 4, 7, 8});
		for (int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), results.get(i));
		}
	}
	
	@Test
	public void testQuitePeriod() {
		ArrayList<Volume> s1=VoiceMixer1.initVolumes(new int[]{2,4,5, 5, 7, 8});
		
		ArrayList<ArrayList<Volume>> speakers=new ArrayList<ArrayList<Volume>>();
		speakers.add(s1);
		
		List<Volume> results=resultPointsToVolumes(calculateMaxVolume(speakers));
		ArrayList<Volume> expected=VoiceMixer1.initVolumes(new int[]{2,4,5, 5, 7, 8});
		for (int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), results.get(i));
		}
	}
	
	
	@Test
	public void testLargeVolumeWin() {
		ArrayList<Volume> s1=VoiceMixer1.initVolumes(new int[]{2,4,5, 4,8,7});
		ArrayList<Volume> s2=VoiceMixer1.initVolumes(new int[]{3,5,6});
		
		ArrayList<ArrayList<Volume>> speakers=new ArrayList<ArrayList<Volume>>();
		speakers.add(s1);
		speakers.add(s2);
		
		List<Volume> results=resultPointsToVolumes(calculateMaxVolume(speakers));
		ArrayList<Volume> expected=VoiceMixer1.initVolumes(new int[]{2,3,5, 3,4,6,4,8,7});
		for (int i=0; i<expected.size(); i++){
			assertEquals(expected.get(i), results.get(i));
		}
	}
	
	@Test
	public void testSimpleOverlap() {

		ArrayList<Volume> s1=VoiceMixer1.initVolumes(new int[]{2, 8, 5});
		ArrayList<Volume> s2=VoiceMixer1.initVolumes(new int[]{3,4,6, 5,6,4, 7,8,7, 9,10,6});
		
		ArrayList<ArrayList<Volume>> speakers=new ArrayList<ArrayList<Volume>>();
		speakers.add(s1);
		speakers.add(s2);
		List<Volume> results=resultPointsToVolumes(calculateMaxVolume(speakers));
		List<Volume> expected=VoiceMixer1.initVolumes(new int[]{2,3,5, 3,4,6, 4,7,5, 7,8,7, 9,10,6});
		
		for (int i=0; i<expected.size(); i++){
			System.out.printf("[%d, %d, %d]\n", results.get(i).start, results.get(i).end, results.get(i).volume);
			assertEquals(expected.get(i), results.get(i));
		}
	}

	@Test
	public void testSimpleOverlapQuestion() {
		ArrayList<Volume> s1=VoiceMixer1.initVolumes(new int[]{2, 5, 10, 6,10,2});
		ArrayList<Volume> s2=VoiceMixer1.initVolumes(new int[]{1,6,1, 8,12,8});
		
		ArrayList<ArrayList<Volume>> speakers=new ArrayList<ArrayList<Volume>>();
		speakers.add(s1);
		speakers.add(s2);
		
		List<Volume> results=resultPointsToVolumes(calculateMaxVolume(speakers));
		List<Volume> expected=VoiceMixer1.initVolumes(new int[]{1,2,1,   2,5,10,   5,6,1,  6,8,2,  8,12,8});

		for (int i=0; i<expected.size(); i++){
			System.out.printf("[%d, %d, %d]\n", results.get(i).start, results.get(i).end, results.get(i).volume);
			assertEquals(expected.get(i), results.get(i));
		}
	}
	
	
}
