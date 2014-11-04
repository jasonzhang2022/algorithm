package jason.solution.integermultiple;

import java.util.ArrayList;

public class Helper {

	public static ArrayList<Byte> toByteArray(String op1){
		ArrayList<Byte> resultsArrayList = new ArrayList<>(op1.length());
		for (int i = op1.length() - 1; i >= 0; i--) {
			resultsArrayList.add(Byte.parseByte(op1.substring(i, i + 1)));
		}
		return resultsArrayList;
	}
	
	public static void addPosition(ArrayList<Byte> results, int position, byte value) {
		if (value==0) {
			return;
		}
		for (int j=results.size(); j<=position; j++) {
			results.add(new Byte((byte) 0));
		}

		int result = value + results.get(position);
		int remaining = result % 10;
		results.set(position, (byte) remaining);

		addPosition(results, position + 1, (byte) (result / 10));
	}

	private static void minusPosition(ArrayList<Byte> results, int position, byte value) {
		if (results.size()<position) {
			throw new UnsupportedOperationException("do not support minus resulting in negative result");
		}
		//13-9=13+10-9-10
		if (results.get(position)<value) {
			addPosition(results, position, ((byte)(10-value)));
			minusPosition(results, position+1, ((byte)1));
		} else {
			results.set(position, ((byte)(results.get(position) - value )));
		}
	}
	public static  String outputNumber(ArrayList<Byte> results) {
		StringBuilder sBuilder = new StringBuilder();
		boolean seeNonZero = false;
		for (int i = results.size() - 1; i >= 0; i--) {
			if (seeNonZero) {
				sBuilder.append(String.valueOf(results.get(i)));
			} else {
				if (results.get(i) != 0) {
					sBuilder.append(String.valueOf(results.get(i)));
					seeNonZero = true;
				}
			}

		}

		String result = sBuilder.toString();
		if (result.length() == 0) {
			result = "0";
		}
		return result;
	}

	public static String add(String op1, String op2) {
		ArrayList<Byte> resultsArrayList = toByteArray(op1);
		add(resultsArrayList , op2, 0);
		return outputNumber(resultsArrayList);
	}
	
	
	public static void add(ArrayList<Byte> resultsArrayList, String op2, int startPosition) {
		for (int i = op2.length() - 1; i >= 0; i--) {
			Byte byte1 = Byte.parseByte(op2.substring(i, i + 1));
			int position=op2.length() - 1 - i+startPosition;
			addPosition(resultsArrayList, position, byte1);
		}
	}
	
	
	
	//op1-op2
	public static String minus(String op1, String op2) {
		ArrayList<Byte> resultsArrayList = toByteArray(op1);

		for (int i = op2.length() - 1; i >= 0; i--) {
			Byte byte1 = Byte.parseByte(op2.substring(i, i + 1));
			int position=op2.length() - 1 - i;
			minusPosition(resultsArrayList, position, byte1);
		}

		return outputNumber(resultsArrayList);
	}
}
