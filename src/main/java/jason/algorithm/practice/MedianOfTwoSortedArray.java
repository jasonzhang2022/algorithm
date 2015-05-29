package jason.algorithm.practice;

public class MedianOfTwoSortedArray {

	public static double median(int[] A, int[] B) {
		int m=A.length;
		int n=B.length;
		
		if ((m+n)%2==1){
			return (double)kth(A, 0, A.length-1, B, 0, B.length-1, (m+n)/2);
		} else {
			return (kth(A, 0, A.length-1, B, 0, B.length-1, (m+n)/2)+kth(A, 0, A.length-1, B, 0, B.length-1, (m+n)/2-1))*0.5;
		}
		
		
	}

	/**
	 * 
	 * @param A
	 * @param aStart
	 * @param aEndInclusive
	 * @param B
	 * @param bStart
	 * @param bEndInclusive
	 * @param k
	 *            kth element counts from 0;
	 * @return
	 */
	public static int kth(int[] A, int aStart, int aEndInclusive, int[] B,
			int bStart, int bEndInclusive, int k) {

		if (aStart > aEndInclusive) {
			// A has no element
			return B[bStart + k ];
		}
		if (bStart > bEndInclusive) {
			return A[aStart + k ];
		}
		if (k == 0) {
			// find the first element
			if (A[aStart] < B[bStart]) {
				return A[aStart];
			} else {
				return B[bStart];
			}
		}
		
		int aLen=aEndInclusive-aStart+1;
		int bLen=bEndInclusive-bStart+1;

		int aPortionLen=aLen*k/(aLen+bLen);
		
		//did not use bLen*k/(aLen+bLen) to avoid integer devision issue.
		//always we have k+1 elements.
		int bPortionLen=k-aPortionLen-1;
		
		int aMid=aStart+aPortionLen;
		int bMid=bStart+bPortionLen;
		
		
		if (A[aMid]>B[bMid]){
			k=aPortionLen;
			aEndInclusive=aMid;
			bStart=bMid+1;
		} else {
			k=bPortionLen;
			bEndInclusive=bMid;
			aStart=aMid+1;
		}
		return kth(A, aStart, aEndInclusive, B, bStart, bEndInclusive, k);
	}
}
