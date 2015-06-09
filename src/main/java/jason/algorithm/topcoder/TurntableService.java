package jason.algorithm.topcoder;

import jason.algorithm.Swaper;

import java.util.BitSet;
import java.util.HashSet;


/**
 * suppose both person and entree position is counted clock wise from 0 to n-1.
 * 
 * No matter how you turn, for each turn, an entree number i ends at person 0.
 * We cann't have two turn event with the same i at person 0 since this results 
 * a duplicated turn. The turn time can be reduced by removing the duplication.
 * 
 * @author jason.zhang
 *
 */
public class TurntableService {
	
	
	/**
	 * 
	 * @param entreePosition the entree at person 0.
	 * @param person the target person
	 * @return the entree number for target person when 'entree' is before person 0. 
	 */
	public static int getEntreePosition(int entreePosition, int person, int n){
		return (entreePosition+person)%n;
	}
	
	/**
	 * 
	 * @param fromEntree
	 * @param toEntree
	 * @return the time spent on turn starting with fromEntree before person 0 and ending with toEntree before person 0.
	 */
	public static int turnToPosition(int fromEntree, int toEntree, int n){
		
	
		int one=Math.abs(fromEntree-toEntree);
		int theOther=n-one;
		//the turn +1.
		return Math.min(one, theOther)+1;
	
		/*
		int dist=0;
		if (fromEntree<toEntree){
			int counterClockDist=toEntree-fromEntree;
			int clockDist=n-(toEntree-fromEntree); // n-1-(toEntree -fromEntree)+1;
			if (counterClockDist<clockDist){
				dist=counterClockDist;
			} else{
				dist=clockDist;
			}
			
		} else {
			int clockDist=fromEntree-toEntree;
			int counterClockDist=n-(fromEntree-toEntree);
			if (counterClockDist<clockDist){
				dist=counterClockDist;
			} else{
				dist=clockDist;
			}
		}
		//spend dist+1 seconds to turn.
		return dist+1;
		 */
		 
		
		
	}
	
	/*
	 * What are the possible combinations?
	 * The start position is entree 0 at 0.
	 */
	public static int calculateTime(String[] favorites){
		int n=favorites.length;
		
		//favs[i] records ith person favorites 
		BitSet[] favs=new BitSet[n];
		for (int i=0; i<favorites.length; i++){
			BitSet bits=new BitSet(n);
			String[] ints=favorites[i].split(" ");
			for (String s: ints){
				int temp=Integer.parseInt(s);
				bits.set(temp);
			}
			favs[i]=bits;
		}
		
		//calculate which person can be satisfied when an entree i is before person 0
		BitSet[] satisfiedMatrix=new BitSet[n];
		for (int entreePosition=0; entreePosition<n; entreePosition++){
			//place entree before person 0.
			
			satisfiedMatrix[entreePosition]=new BitSet();
			for (int person=0; person<n; person++){
				int entreeBeforePerson=getEntreePosition(entreePosition, person, n);
				if (favs[person].get(entreeBeforePerson)){
					satisfiedMatrix[entreePosition].set(person);
				}
			}
		}
		
		
		int[] entrees=new int[n];
		//first entree has to be zero.
		HashSet<Integer> unsatisfiedPersons=new HashSet<Integer>();
		for (int entree=0; entree<n; entree++){
			entrees[entree]=entree;
		}
		for (int person=0; person<n; person++){
			unsatisfiedPersons.add(person); 
		}
		
		
		
		
		//option1: don't eat at position 1.
		int ret1=nextTurn(satisfiedMatrix, entrees, 1, unsatisfiedPersons, 0, n);
		
	
		//option2 eat at position 1
		int ret2=Integer.MAX_VALUE;
		HashSet<Integer> unsattisfiedClone=new HashSet<>(unsatisfiedPersons);
		for (int person=0; person<n; person++){
			if (satisfiedMatrix[0].get(person)){
				unsattisfiedClone.remove(person);
			}
		}
		if (unsattisfiedClone.size()<n){
			//somebody is satisfied at position 0.
			ret2=nextTurn(satisfiedMatrix, entrees, 1, unsattisfiedClone, 0, n);
			if (ret2<Integer.MAX_VALUE){
				ret2+=15; //spend 15 second eating
			}
		}
		return Math.min(ret1, ret2);	
	}
	
	/*
	 * Go over all permutations for remainingEntree[entreeOffset...end]
	 */
	public static int nextTurn(BitSet[] satisfiedMatrix, int[] remainingEntree, int entreeOffset, HashSet<Integer> unsatisfiedPerson, int fromEntree, int n ){
		
		//don't need to turn since all person is satisfied.
		if (unsatisfiedPerson.isEmpty()){
			return 0;
		}
		if (entreeOffset>=remainingEntree.length){
			//no entree anymore
			return Integer.MAX_VALUE;
		}
		
		
		int minTime=Integer.MAX_VALUE;
		
		for (int j=entreeOffset; j<remainingEntree.length; j++){
			
			//Let the entree at j be the first entree we want to try.
			Swaper.swap(remainingEntree, entreeOffset, j);
			

			int turnTime=turnToPosition(fromEntree, remainingEntree[entreeOffset],n);	
		
			//option 1. we skip this turn since noboday eat although someone could be satisfied.
			int ret1=nextTurn(satisfiedMatrix, remainingEntree, entreeOffset+1, unsatisfiedPerson, fromEntree, n);
			
			//option2 we turn to this entree.
			HashSet<Integer> unsatisfiedClone=new HashSet<>(unsatisfiedPerson);
			
			int ret2=Integer.MAX_VALUE;
			int turnToEntree=remainingEntree[entreeOffset];
			if (satisfiedMatrix[turnToEntree].cardinality()>0){
				//someone is satisfied by judge favorite.
				for (int person=0; person<n; person++){
					if (satisfiedMatrix[turnToEntree].get(person)){
						unsatisfiedClone.remove(person);
					}
				}
				//someone is indeeded satisfied
				if (unsatisfiedPerson.size()>unsatisfiedClone.size()){
					ret2=turnTime+15 ; //turnTime and 15 seconds for eating.
					int childRet=nextTurn(satisfiedMatrix, remainingEntree, entreeOffset+1, unsatisfiedClone, turnToEntree, n);
					if (childRet==Integer.MAX_VALUE){
						ret2=Integer.MAX_VALUE;
					}  else{
						ret2+=childRet;
					}
				}
			}
			minTime=Math.min(minTime, Math.min(ret1, ret2));
			
			Swaper.swap(remainingEntree, entreeOffset, j);
		}
		return minTime;
		
	}

}
