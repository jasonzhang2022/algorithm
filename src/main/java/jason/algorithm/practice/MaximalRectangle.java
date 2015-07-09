package jason.algorithm.practice;

//http://www.programcreek.com/2014/05/leetcode-maximal-rectangle-java/
public class MaximalRectangle {

	
	
	public static int max(int[][] matrix){
		int rowlen=matrix.length;
		int collen=matrix[0].length;
		
		
		
		/*
		 * width[i][j]
		 * height[i][j]
		 * 
		 * Is the max rectangule with width=width[i][j]
		 * and height=height[i][j]
		 * 
		 * The bottom/right index is i and j.
		 * 
		 * 
		 */
		int[][] width=new int[rowlen][collen];
		int[][] height=new int[rowlen][collen];
		
		int max=0;
		if (matrix[0][0]==0){
			width[0][0]=0;
			height[0][0]=0;
		} else{
			width[0][0]=1;
			height[0][0]=1;
			max=1;
		}
		for (int c=1; c<collen; c++){
			if (matrix[0][c]==1){
				width[0][c]=width[0][c-1]+1;
				height[0][c]=1;
				if (width[0][c]>max){
					max=width[0][c];
				}
			} else{
				width[0][c]=0;
				height[0][c]=0;
			}
		}
		for (int r=1; r<rowlen; r++){
			if (matrix[r][0]==1){
				width[r][0]=1;
				height[r][0]=height[r-1][0]+1;
				if (height[r][0]>max){
					max=height[r][0];
				}
			} else{
				width[r][0]=0;
				height[r][0]=0;
			}
		}
		
		for (int r=1; r<rowlen; r++){
			for (int c=1; c<collen; c++){
				if (matrix[r][c]==0){
					width[r][c]=0;
					height[r][c]=0;
				} else {
					
					//case1. extend the rectangule [r][c-1]
					int w1=width[r][c-1]+1;
					int h1=Math.min(height[r][c-1], height[r-1][c]+1);
					
					//case 2: extend the rectangule [r-1][c]
					int w2=Math.min(width[r][c-1]+1, width[r-1][c]);
					int h2=height[r-1][c]+1;
					
					if (w1*h1>w2*h2){
						width[r][c]=w1;
						height[r][c]=h1;
					} else if (w1*h1<w2*h2){
						width[r][c]=w2;
						height[r][c]=h2;
					} else {
						//difficult,if equal, this could have an impact 
						//on subsequent result. Given this, 
						//Dynamic Programming doesn't work for 
					}
					
					
					if (width[r][c]*height[r][c]>max){
						max=width[r][c]*height[r][c];
					}
				}
			}
		}
		
		
		
		
		
	}
	
	
}
