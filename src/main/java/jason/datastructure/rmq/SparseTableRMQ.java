package jason.datastructure.rmq;

public class SparseTableRMQ implements RMQ {

	int[][] tables;
	int columnLen;
	int[] input;
	@Override
	public int minimum(int i, int j) {
		
		int min=Integer.MAX_VALUE;
		int len=j-i+1;
		int start=i;
		for (int k=0; k<columnLen; k++) {
			int mask=(1<<k);
			if ( (mask & len)!=0) {
			
				if (input[tables[start][k]]<min) {
					min=input[tables[start][k]];
				}
				start+=mask;
			}	
		}
		
		return min;
	}

	
	@Override
	public void init(int[] inpput) {
		this.input=inpput;
		int inputLen=inpput.length;
		columnLen=(int)Math.ceil( Math.log(inputLen)/Math.log(2)  );
		
		tables=new int[inputLen][columnLen];
		for (int i=0; i<inputLen; i++) {
			tables[i][0]=i; //initilize first column;
		}
		
		for (int col=1; col<columnLen; col++) {
			
			for (int row=0; row<inputLen; row++) {
				
				int sectionLen=(1<<col);
				int subSectionLen=(1<<(col-1));
				int leftMinValue=input[tables[row][col-1]];
				if (row+subSectionLen>=inputLen) {
					//we do not have second section
					tables[row][col]=tables[row][col-1];
					continue;
				}
				int rightMinValue=input[tables[row+subSectionLen][col-1]];
				if (leftMinValue<rightMinValue) {
					tables[row][col]=tables[row][col-1];
				} else {
					tables[row][col]=tables[row+subSectionLen][col-1];
				}
			}
		}
	}

}
