package jason.algorithm.topcoder;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

//http://topcoder.bgcoder.com/print.php?id=600
public class Graduation {
	public Graduation(){
		super();
		System.out.println("constructor is called");
	}
	
	

	public static class Requirement {
		int index;
		int requiredCourses;
		String eligbleCourses;
		
		int filledCourse=0;
		public static Pattern p=Pattern.compile("^(\\d+)(.+)$");
		public Requirement(String req, int index){
			this.index=index;
			
			Matcher m=p.matcher(req);
			m.find();
			requiredCourses=Integer.parseInt(m.group(1));
			eligbleCourses=m.group(2);
		}
		
		public void fillCourse(char c){
			filledCourse++;
		}
		public void removeCourse(char c){
			filledCourse--;
		}
		public boolean isFilled(){
			return filledCourse==requiredCourses;
		}
	}
	
	/*
	 * Map from course to requirement
	 */
	Map<Character, Requirement> courseToRequirement=new HashMap<>();
	
	/*
	 * Temporary variabled used during DFS
	 */
	boolean[] visited=new boolean[256];
	
	String classTaken;
	Requirement[] reqs;
	public void init(String classesTaken, String[] requirements){

		this.classTaken=classesTaken;
		reqs=new Requirement[requirements.length];
		for (int i=0; i<requirements.length; i++){
			reqs[i]=new Requirement(requirements[i], i);
		}
		courseToRequirement.clear();
		Arrays.fill(visited, false);
		processClassTaken();
	}
	
	
	public String moreClasses(){
		
		for (Requirement req: reqs){
			if (!satisfyRequirement(req)){
				return "0";
			}
		}
		
		List<Character> courses=new ArrayList<>(256);
		for (Character c: courseToRequirement.keySet()){
			if (classTaken.indexOf(c)==-1){
				courses.add(c);
			}
		}
		if (courses.isEmpty()){
			return "";
		}
		return courses.stream().sorted().map(c->String.valueOf(c)).collect(Collectors.joining());
		
		
		
	}
	
	public void processClassTaken(){
		
		//assume that classTaken has no duplication
		for (int i=0; i<classTaken.length(); i++){
			for (int j=0; j<reqs.length; j++){
				Requirement req=reqs[j];
				//this requirement is already filled.
				//don't fill the requirement more than it needs. 
				//so the class already taken can be used for other requirement.
				if (req.isFilled()){
					continue;
				}
				if (req.eligbleCourses.indexOf(classTaken.charAt(i))!=-1){
					req.fillCourse(classTaken.charAt(i));
					courseToRequirement.put(classTaken.charAt(i), req);
				}
			}
		}
	}
		
	
	/*
	 * Try to satisfy this requirement by adding more edge/course
	 */
	public boolean satisfyRequirement(Requirement req){
		
		//start character with sorting order
		// so character with low value will be added first
		for (char c=33; c<=126; c++){
			if (req.isFilled()){
				return true;
			}
			
			//not an course
			if (c>='0' && c<='9'){
				continue;
			}
			//there is no edge between the requirement and this course
			if (req.eligbleCourses.indexOf(c)==-1){
				continue;
			}
			Requirement matched=courseToRequirement.get(c);
			if (matched!=null && matched==req){
				//matched,  but to this requirement, 
				continue;
			}
			Arrays.fill(visited, false);
			visited[c]=true;
			
			/*
			 * Typical bipartite matching DFS walking.
			 * 
			 * We may need to dematch already matched course.
			 * 
			 */
			if (walkPath(matched)){
				req.fillCourse(c);
				courseToRequirement.put(c, req);
				if (matched!=null){
					matched.removeCourse(c);
				}
			}
		}
		
		return false;
	}
	

	
	/*
	 * Whether we can add one more course to the req
	 */
	public boolean walkPath(Requirement req){
		//one course has not requirement match
		if (req==null){
			return true;
		}
		
		for (char c=33; c<=126; c++){
			if (c>='0' && c<='9'){
				continue;
			}
			//there is no edge between the requirement and this course
			if (req.eligbleCourses.indexOf(c)==-1){
				continue;
			}
			
			Requirement matched=courseToRequirement.get(c);
			if (matched!=null && matched==req){
				//matched,  but to this requirement, 
				//we can not move to it.
				continue;
			}
			//this course is visited during traverse
			if (visited[c]){
				continue;
			}
			visited[c]=true;
			if (walkPath(matched)){
				req.fillCourse(c);
				courseToRequirement.put(c, req);
				
				if (matched!=null){
					matched.removeCourse(c);
				}
				return true;
			} 
		}
		
		return false;	
	}

	
	
	@Test
	public void test1(){
		init("A", new String[]{"2ABC","2CDE"});
		assertEquals("BCD", moreClasses());
	}
	
	@Test
	public void test2(){
		init("+/NAMT", new String[]{"3NAMT","2+/","1M"});
		assertEquals("", moreClasses());
	}
	
	@Test
	public void test3(){
		init("A", new String[]{"100%*Klju"});
		assertEquals("0", moreClasses());
	}
	
	@Test
	public void test4(){
		init("", new String[]{"5ABCDE","1BCDE,"});
		assertEquals(",ABCDE", moreClasses());
	}
	@Test
	public void test5(){
		init("CDH", new String[]{"2AP", "3CDEF", "1CDEFH"});
		assertEquals("AEP", moreClasses());
	}
}
