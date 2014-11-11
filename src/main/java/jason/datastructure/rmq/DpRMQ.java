package jason.datastructure.rmq;


public class DpRMQ implements RMQ {

	@Override
	public int minimum(int i, int j) {
		int start=i;
		int end=j;
		if (i>j) {
			start=j;
			end=i;
		}
		return tables[start][end];
	}

	int[][] tables;
	
	@Override
	public void init(int[] inpput) {
		tables=new int[inpput.length][inpput.length];
		
		for (int start=0; start<inpput.length-1; start++) {
			int  previousMin=inpput[start];
			tables[start][start]=previousMin;
			for (int end=start+1; end<inpput.length; end++) {
				if (inpput[end]<previousMin) {
					previousMin=inpput[end];
				}
				tables[start][end]=previousMin;
			}
		}
		
	}

	
}
