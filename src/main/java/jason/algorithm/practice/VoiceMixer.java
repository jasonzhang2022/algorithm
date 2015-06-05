package jason.algorithm.practice;

import static org.junit.Assert.*;

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
		public Volume(int start, int end, int volume) {
			super();
			this.start = start;
			this.end = end;
			this.volume = volume;
		}
		
	}
	
	public static class VolumePoint{
		Volume volume;
		Boolean start=true;
		
		Iterator<Volume> myIter;
		
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
		
	
		
		PriorityQueue<VolumePoint> frontVolumes=new PriorityQueue<>((v1, v2)->{
			int value1=v1.start?v1.volume.start:v1.volume.end;
			int value2=v2.start?v2.volume.start:v2.volume.end;
			return value1-value2;
		});
		
		//descending order
		PriorityQueue<Volume> maxVolumes=new PriorityQueue<>((v1, v2)->{
			return -(v1.volume-v2.volume);
		});
		
		int[] nextIndex=new int[speakers.size()];
		Arrays.fill(nextIndex, 1);
		
		
		
		for (int i=0; i<speakers.size(); i++){
			ArrayList<Volume> oneSpeaker=speakers.get(i);
			if (oneSpeaker.isEmpty()){
				continue;
			}
			
			Iterator<Volume> myIter=oneSpeaker.iterator();
			Volume v=myIter.next();
			VolumePoint startPoint=new VolumePoint();
			startPoint.myIter=myIter;
			startPoint.volume=v;
			startPoint.start=true;
			
			
			VolumePoint endPoint=new VolumePoint();
			endPoint.myIter=myIter;
			endPoint.volume=v;
			endPoint.start=false;
		
			
			frontVolumes.offer(startPoint);
			frontVolumes.offer(endPoint);
			
		}
		
		Volume maxVolume=null;
		ArrayList<ResultPoint> results=new ArrayList<>();
		results.add(new ResultPoint(0, 0)); //start at 0 with volume 0.
		while (!frontVolumes.isEmpty()){
			VolumePoint vp=frontVolumes.poll();
			Volume preMaxVolume=maxVolume;
			if (vp.start){
				maxVolumes.offer(vp.volume);
				maxVolume=maxVolumes.peek();
				
				//at this point, switch to volume.
				if (preMaxVolume==null || preMaxVolume.volume!=maxVolume.volume){
					 addResultPoint(results,new ResultPoint(vp.volume.start, vp.volume.volume));
				}
				
				
			}  else {
				maxVolumes.remove(vp.volume);
				//add the next volume in the sequence
				if (vp.myIter.hasNext()){
					Iterator<Volume> myIter=vp.myIter;
					Volume v=myIter.next();
					VolumePoint startPoint=new VolumePoint();
					startPoint.myIter=myIter;
					startPoint.volume=v;
					startPoint.start=true;
					
					VolumePoint endPoint=new VolumePoint();
					endPoint.myIter=myIter;
					endPoint.volume=v;
					endPoint.start=false;
					
					
					frontVolumes.offer(startPoint);
					frontVolumes.offer(endPoint);
				}
				maxVolume=maxVolumes.peek();
				if (maxVolume==null){
					//no effective volume 
					//at this point, switch to volume.
					 addResultPoint(results,new ResultPoint(vp.volume.end, 0));
				} else{
					//at this point, switch to volume.
					if (preMaxVolume==null || preMaxVolume.volume!=maxVolume.volume){
						//at this point, switch to volume.
						 addResultPoint(results, new ResultPoint(vp.volume.end, maxVolume.volume));
					}
					
				}
			}
		}
		return results;
		
	}
	
	public static void addResultPoint(ArrayList<ResultPoint> results, ResultPoint rp){
		
		if (results.isEmpty()){
			results.add(rp);
			return;
		}
		ResultPoint lastResult=results.get(results.size()-1);
		if (lastResult.time!=rp.time){
			results.add(rp);
			return;
		}
		if (rp.volume>lastResult.volume){
			//multi source produce volume at the same time point. Use the larger one.
			lastResult.volume=rp.volume;
		}
		return;
		
	}
	
	
	
	@Test
	public void testEqualStart() {
		ArrayList<Volume> speaker1=new ArrayList<Volume>();
		speaker1.add(new Volume(3,7,5));
		
		ArrayList<Volume> speaker2=new ArrayList<Volume>();
		speaker2.add(new Volume(3,5,10));
		
		ArrayList<ArrayList<Volume>> speakers=new ArrayList<>();
		speakers.add(speaker1);
		speakers.add(speaker2);
		
		ArrayList<ResultPoint> results=calculateMaxVolume(speakers);
		
		assertEquals(4, results.size());
		assertEquals(results.get(0), new ResultPoint(0, 0));
		assertEquals(results.get(1), new ResultPoint(3, 10));
		assertEquals(results.get(2), new ResultPoint(5, 5));
		assertEquals(results.get(3), new ResultPoint(7, 0));
	}
	
	@Test
	public void testBoundTransition() {
		ArrayList<Volume> speaker1=new ArrayList<Volume>();
		speaker1.add(new Volume(4,5,10));
		speaker1.add(new Volume(8,10,3));
		
		ArrayList<Volume> speaker2=new ArrayList<Volume>();
		speaker2.add(new Volume(3,8,7));
		
		ArrayList<ArrayList<Volume>> speakers=new ArrayList<>();
		speakers.add(speaker1);
		speakers.add(speaker2);
		
		ArrayList<ResultPoint> results=calculateMaxVolume(speakers);
		
		assertEquals(6, results.size());
		assertEquals(results.get(0), new ResultPoint(0, 0));
		assertEquals(results.get(1), new ResultPoint(3, 7));
		assertEquals(results.get(2), new ResultPoint(4, 10));
		assertEquals(results.get(3), new ResultPoint(5, 7));
		assertEquals(results.get(4), new ResultPoint(8, 3));
		assertEquals(results.get(5), new ResultPoint(10, 0));
	}
}
