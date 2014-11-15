package jason.datastructure.rmq;

public class SparseTableRMQ implements RMQ {

	int[][] tables;
	int columnLen;
	int[] input;

	@Override
	public int minimum(int i, int j) {
		int len=j-i+1;
		int start=i;
		int k=(int) Math.floor( Math.log(len)/Math.log(2) );
		
		
		int min1=input[tables[start][k]];
		
		int min2=input[tables[j+1-(1<<k)][k]];
		if (min1<min2) {
			return min1;
		} else {
			return min2;
		}
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
