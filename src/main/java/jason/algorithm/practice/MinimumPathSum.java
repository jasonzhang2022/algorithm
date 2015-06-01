package jason.algorithm.practice;

import java.util.BitSet;
import java.util.PriorityQueue;

//http://www.programcreek.com/2014/05/leetcode-minimum-path-sum-java/
public class MinimumPathSum {
	
	public static class QueueEntry  {
		int sum;
		int row;
		int col;

		public int getSum() {
			return sum;
		}

		public void setSum(int sum) {
			this.sum = sum;
		}

		public QueueEntry(int sum, int row, int col) {
			super();
			this.sum = sum;
			this.row = row;
			this.col = col;
		}

	
	
		
	}
	
	public static int minimumPathSum(int[][] input){
		
		int column=input[0].length;
		int row=input.length;
		
		//int[][] neighbors={{-1, -1}, {-1, 0}, {-1, 1}, {0, 1}, {1,1}, {1, 0}, {1, -1}, {0, -1}};
		//diagnoal walk is not allowed
		int[][] neighbors={ {-1, 0}, {0, 1},  {1, 0}, {0, -1}}; 
		
		QueueEntry[][] entries=new QueueEntry[row][column];
		BitSet found=new BitSet(row*column);
		
		
		PriorityQueue<QueueEntry> queue=new PriorityQueue<QueueEntry>(row*column, (a, b)->{
			return a.sum-b.sum;
		});
		
		QueueEntry first=new QueueEntry(input[0][0], 0, 0);
		entries[0][0]=first;
		queue.add(first);
		
		while (queue.peek()!=null){
			QueueEntry entry=queue.poll();
			
			int rowIndex=entry.row;
			int colIndex=entry.col;
			found.set(rowIndex*column+colIndex);
			
			if (entry.col==(column-1) && entry.row==(row-1)){
				//the last found.
				break;
			}
			
			
			//walk to 8 edge
			for (int[] delta: neighbors){
				int neighborRow=rowIndex+delta[0];
				int neighborCol=colIndex+delta[1];
				if (neighborCol<0 || neighborRow<0 || neighborCol>=column || neighborRow>=row){
					continue;
				}
				if (found.get(neighborRow*column+neighborCol)){
					//already found min Path
					continue;
				}
				
				QueueEntry neighborQueueEntry=entries[neighborRow][neighborCol];
				if (neighborQueueEntry==null){ //never reached to neighbor before
					neighborQueueEntry=new QueueEntry(entry.sum+input[neighborRow][neighborCol], neighborRow, neighborCol);
					entries[neighborRow][neighborCol]=neighborQueueEntry;
					queue.offer(neighborQueueEntry);
					continue;
				} else{
					if (entry.sum+input[neighborRow][neighborCol]<neighborQueueEntry.sum){
						queue.remove(neighborQueueEntry);
						neighborQueueEntry.sum=entry.sum+input[neighborRow][neighborCol];
						queue.offer(neighborQueueEntry);
					}
				}
			}
			
			
		}
		
		
		return entries[row-1][column-1].sum;
	}
	
	
	//solution from web: http://www.programcreek.com/2014/05/leetcode-minimum-path-sum-java/
	public static int minPathSum(int[][] grid) {
	    if(grid == null || grid.length==0)
	        return 0;
	 
	    int m = grid.length;
	    int n = grid[0].length;
	 
	    int[][] dp = new int[m][n];
	    dp[0][0] = grid[0][0];    
	 
	    // initialize top row
	    for(int i=1; i<n; i++){
	        dp[0][i] = dp[0][i-1] + grid[0][i];
	    }
	 
	    // initialize left column
	    for(int j=1; j<m; j++){
	        dp[j][0] = dp[j-1][0] + grid[j][0];
	    }
	 
	    // fill up the dp table
	    for(int i=1; i<m; i++){
	        for(int j=1; j<n; j++){
	            if(dp[i-1][j] > dp[i][j-1]){
	                dp[i][j] = dp[i][j-1] + grid[i][j];
	            }else{
	                dp[i][j] = dp[i-1][j] + grid[i][j];
	            }
	        }
	    }
	 
	    return dp[m-1][n-1];
	}

}
