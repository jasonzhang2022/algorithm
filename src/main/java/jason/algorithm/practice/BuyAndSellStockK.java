package jason.algorithm.practice;

import java.util.NavigableSet;
import java.util.PriorityQueue;
import java.util.TreeSet;

public class BuyAndSellStockK {
	int[] input;

	public class Profit {
		int lowindex;
		int highindex;
		int minindex;

		// if this Profit is merge, what is benefit reduced.
		int mergeDelta = Integer.MAX_VALUE;

		public int getProfit() {
			return input[highindex] - input[lowindex];
		}

		public Profit() {

		}

		public Profit mergeBenefit(Profit right) {

			Profit profit = new Profit();
			if (input[minindex] < input[right.minindex]) {
				profit.minindex = minindex;
			} else {
				profit.minindex = right.minindex;
			}
			if (minindex != lowindex) {
				profit.lowindex = minindex;
				profit.highindex = right.highindex;
			} else {
				profit.lowindex = lowindex;
				profit.highindex = right.highindex;
			}
			if (profit.getProfit() < getProfit()) {
				// we should keep this;
				if (getProfit() < right.getProfit()) {
					// we should keep right;
					profit.lowindex = right.lowindex;
					profit.highindex = right.highindex;
				} else {
					profit.lowindex = lowindex;
					profit.highindex = highindex;
				}
			} else if (profit.getProfit() < right.getProfit()) {
				// we should keep right;
				profit.lowindex = right.lowindex;
				profit.highindex = right.highindex;
			} else {
				// we keep the merged one.
			}

			return profit;
		}

		public void setMergeDelta(Profit left) {
			if (left == null) {
				mergeDelta = Integer.MAX_VALUE;
				return;
			}
			Profit profit = left.mergeBenefit(this);
			int delta = left.getProfit() + getProfit() - profit.getProfit();
			mergeDelta = delta;
		}
	}

	// http://www.programcreek.com/2014/02/leetcode-best-time-to-buy-and-sell-stock-iii-java/
	public int maximumKTransaction(int[] input, int k) {
		this.input = input;

		// a linked list node is most performant.
		NavigableSet<Profit> set = new TreeSet<>((o1, o2) -> o1.lowindex
				- o2.lowindex);
		PriorityQueue<Profit> profits = new PriorityQueue<>((o1, o2) -> {
			if (o1.mergeDelta < o2.mergeDelta) {
				return -1;
			}
			if (o1.mergeDelta > o2.mergeDelta) {
				return 1;
			}
			return 0;

		});

		int nextIndex = 0;
		Profit prev = null;
		// can be linear;
		while (nextIndex < input.length) {
			Profit profit = scanProfitOpportunity(input, nextIndex);
			if (profit == null) {
				break;
			}
			profit.setMergeDelta(prev);
			set.add(profit);
			nextIndex=profit.highindex;
			prev = profit;
		}
		// linear
		profits.addAll(set);

		while (profits.size() > k) {
			Profit minDelta = profits.poll();
			Profit high = set.higher(minDelta);
			Profit low = set.lower(minDelta);
			Profit nextLow = set.lower(low);

			Profit newProfit = low.mergeBenefit(minDelta);

			set.remove(minDelta);
			set.remove(low);
			set.add(newProfit);

			// merged
			profits.remove(low);

			newProfit.setMergeDelta(nextLow);
			profits.offer(newProfit);
			if (high != null) {
				profits.remove(high);
				high.setMergeDelta(newProfit);
				profits.offer(high);
			}
		}

		int sum = 0;
		for (Profit profit : set) {
			sum += profit.getProfit();
		}

		return sum;

	}

	public Profit scanProfitOpportunity(int[] input, int offset) {
		Profit profit = new Profit();
		profit.minindex = offset;
		for (int i = offset + 1; i < input.length; i++) {
			if (input[i] < input[profit.minindex]) {
				profit.minindex = i;
			}
			if (input[i] > input[i - 1]) {
				profit.highindex = i;
				profit.lowindex = i - 1;
				return profit;
			}
		}
		return null;
	}

	
	
	
}
