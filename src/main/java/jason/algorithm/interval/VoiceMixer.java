package jason.algorithm.interval;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

// The ciritical part is for each speaker the Volume already sorted with timeline
public class VoiceMixer {


    public static class MySpeaker {
        int currentIndex = 0;
        ArrayList<VoiceMixer1.Volume> volumes;

        MySpeaker(VoiceMixer1.Speaker s) {
            volumes = new ArrayList<>(s.volumes.size());
            while (!s.volumes.isEmpty()) {
                volumes.add(s.volumes.poll());
            }
        }

        public int getTimePoint() {
            VoiceMixer1.Volume v = volumes.get(currentIndex/2);
            return currentIndex % 2 == 0 ? v.start : v.end;
        }

        public int isEndpoint() {
            return currentIndex % 2 == 1 ? 1 : 0;
        }

        public int getVolume() {
            return volumes.get(currentIndex / 2).volume;
        }

    }

    public static List<VoiceMixer1.Volume> mix(List<VoiceMixer1.Speaker> ss) {

        LinkedList<VoiceMixer1.Volume> results = new LinkedList<VoiceMixer1.Volume>();

        PriorityQueue<MySpeaker> byVolumes = new PriorityQueue<>(Comparator.comparingInt(MySpeaker::getVolume).reversed());

        PriorityQueue<MySpeaker> byTimePoint = new PriorityQueue<>(Comparator.comparingInt(MySpeaker::getTimePoint));

        for (VoiceMixer1.Speaker s : ss) {
            byTimePoint.offer(new MySpeaker(s));
        }

        while (!byTimePoint.isEmpty()) {
            MySpeaker speaker = byTimePoint.poll();

            int time = speaker.getTimePoint();

            while (true) {
                if (speaker.isEndpoint() == 1) {
                    byVolumes.remove(speaker);
                } else {
                    byVolumes.offer(speaker);
                }

                speaker.currentIndex++;
                if (speaker.currentIndex <= speaker.volumes.size()*2 - 1) {
                    byTimePoint.offer(speaker);
                }


                if (!byTimePoint.isEmpty() && byTimePoint.peek().getTimePoint() == time) {
                    speaker = byTimePoint.poll();
                } else {
                    break;
                }
            }

            if (byVolumes.isEmpty()) {
                // quite period
                results.add(new VoiceMixer1.Volume(time, 0));
            } else if (results.isEmpty() || byVolumes.peek().getVolume() != results.getLast().volume) {
                results.add(new VoiceMixer1.Volume(time, byVolumes.peek().getVolume()));
            }
        }
        return results;
    }


    public static class TesetCase {
        public static LinkedList<VoiceMixer1.Volume> initVolumes(int[] inputs){
            LinkedList<VoiceMixer1.Volume> volumes=new LinkedList<>();
            for (int i=0; i<inputs.length; i+=3){
                VoiceMixer1.Volume v=new VoiceMixer1.Volume(inputs[i], inputs[i+2]);
                v.end=inputs[i+1];
                volumes.add(v);
            }
            return volumes;
        }

        @Test
        public void testSimple() {
            VoiceMixer1.Speaker s1=new VoiceMixer1.Speaker();
            s1.volumes=initVolumes(new int[]{2,4,5});

            List<VoiceMixer1.Speaker> speakers=new ArrayList<>();
            speakers.add(s1);

            List<VoiceMixer1.Volume> expected=initVolumes(new int[]{2,4,5});

            List<VoiceMixer1.Volume> results=mix(speakers);
            for (int i=0; i<expected.size(); i++){
                assertEquals(expected.get(i), results.get(i));
            }
        }

        @Test
        public void testSimpleWithEmpty() {
            VoiceMixer1.Speaker s1=new VoiceMixer1.Speaker();
            s1.volumes=initVolumes(new int[]{2,4,5, 5,7,5});

            List<VoiceMixer1.Speaker> speakers=new ArrayList<>();
            speakers.add(s1);

            List<VoiceMixer1.Volume> expected=initVolumes(new int[]{2,4,5, 5,7,5});

            List<VoiceMixer1.Volume> results=mix(speakers);
            System.out.println(results.stream().map(v->String.format("%d->%d:%d ", v.start, v.end, v.volume)).collect(Collectors.joining(",")));

            for (int i=0; i<expected.size(); i++){
                assertEquals(expected.get(i), results.get(i));
            }
        }

        @Test
        public void testSimpleOverlap() {
            VoiceMixer1.Speaker s1=new VoiceMixer1.Speaker();
            s1.volumes=initVolumes(new int[]{2, 8, 5});

            VoiceMixer1.Speaker s2=new VoiceMixer1.Speaker();
            s2.volumes=initVolumes(new int[]{3,4,6, 5,6,4, 7,8,7, 9,10,6});

            List<VoiceMixer1.Speaker> speakers=new ArrayList<>();
            speakers.add(s1);
            speakers.add(s2);

            List<VoiceMixer1.Volume> expected=initVolumes(new int[]{2,3,5, 3,4,6, 4,7,5, 7,8,7, 9,10,6});

            List<VoiceMixer1.Volume> results=mix(speakers);

            System.out.println(results.stream().map(v->String.format("%d->%d:%d ", v.start, v.end, v.volume)).collect(Collectors.joining(",")));

            for (int i=0; i<expected.size(); i++){
                assertEquals(expected.get(i), results.get(i));
            }
        }

        @Test
        public void testSimpleOverlapQuestion() {
            VoiceMixer1.Speaker s1=new VoiceMixer1.Speaker();
            s1.volumes=initVolumes(new int[]{2, 5, 10, 6,10,2});

            VoiceMixer1.Speaker s2=new VoiceMixer1.Speaker();
            s2.volumes=initVolumes(new int[]{1,6,1, 8,12,8});

            List<VoiceMixer1.Speaker> speakers=new ArrayList<>();
            speakers.add(s1);
            speakers.add(s2);

            List<VoiceMixer1.Volume> expected=initVolumes(new int[]{1,2,1,   2,5,10,   5,6,1,  6,8,2,  8,12,8});

            List<VoiceMixer1.Volume> results=mix(speakers);
            System.out.println(results.stream().map(v->String.format("%d->%d:%d ", v.start, v.end, v.volume)).collect(Collectors.joining(",")));

            for (int i=0; i<expected.size(); i++){
                System.out.printf("[%d, %d, %d]\n", results.get(i).start, results.get(i).end, results.get(i).volume);
                assertEquals(expected.get(i), results.get(i));
            }
        }
    }
}