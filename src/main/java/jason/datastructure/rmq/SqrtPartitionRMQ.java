package jason.datastructure.rmq;

public class SqrtPartitionRMQ implements RMQ {

	int[] input;
	int[] sections;
	int sectionLen;
	
	@Override
	public int minimum(int start, int end) {
		int startSection=start/sectionLen+1;
		if (start%sectionLen==0) {
			startSection--;
		}
		int endSection=end/sectionLen-1;
		if ((end+1)%sectionLen==0) {
			endSection++;
		}
		int min=Integer.MAX_VALUE;
		if (endSection<startSection) {
			
			for (int i=start; i<=end; i++) {
				if (input[i]<min) {
					min=input[i];
				}
			}
			return min;
		}
		
		for (int i=start; i<startSection*sectionLen; i++) {
			if (input[i]<min) {
				min=input[i];
			}
		}
		
		//check this
		for (int s=startSection; s<=endSection; s++) {
			if (sections[s]<min) {
				min=sections[s];
			}
		}
		
		for (int i=endSection*sectionLen; i<=end; i++) {
			if (input[i]<min) {
				min=input[i];
			}
		}
		
		return min;
		
	}

	
	@Override
	public void init(int[] input) {
		
		sectionLen=(int) Math.ceil(Math.sqrt(input.length));
		this.input=input;
		sections=new int[(int) Math.ceil( ((double)input.length)/((double)sectionLen) )];
		
		for (int i=0; i<sections.length; i++) {
			int sectionMin=Integer.MAX_VALUE;
			
			//last section could be half section
			//i is section index;
			for (int j=0; j<sectionLen; j++) {
				if (i*sectionLen+j<input.length && input[i*sectionLen+j]<sectionMin) {
					sectionMin=input[i*sectionLen+j];
				}
			}
			
			sections[i]=sectionMin;
		}
		
	}

}
