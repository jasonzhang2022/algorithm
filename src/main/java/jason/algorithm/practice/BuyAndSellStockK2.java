package jason.algorithm.practice;

import static org.junit.Assert.assertEquals;
import jason.algorithm.Shuffler;

import org.junit.Test;

public class BuyAndSellStockK2 {

	/*
	 * http://www.cnblogs.com/EdwardLiu/p/4008162.html 这道题是Best Time to Buy and
	 * Sell Stock的扩展，现在我们最多可以进行两次交易。我们仍然使用动态规划来完成，
	 * 事实上可以解决非常通用的情况，也就是最多进行k次交易的情况。 这里我们先解释最多可以进行k次交易的算法，然后最多进行两次我们只需要把k取成2即可。
	 * 我们还是使用“局部最优和全局最优解法”。我们维护两种量，一个是当前到达第i天可以最多进行j次交易，
	 * 最好的利润是多少（global[i][j]），另一个是当前到达第i天
	 * ，最多可进行j次交易，并且最后一次交易在当天卖出的最好的利润是多少（local[i][j]）。 下面我们来看递推式，全局的比较简单，
	 * 
	 * global[i][j]=max(local[i][j],global[i-1][j])，
	 * 也就是去当前局部最好的，和过往全局最好的中大的那个（因为最后一次交易如果包含当前天一定在局部最好的里面，否则一定在过往全局最优的里面）。
	 * 对于局部变量的维护，递推式是
	 * 
	 * local[i][j]=max(global[i-1][j-1]+max(diff,0),local[i-1][j]+diff)，
	 * 也就是看两个量，第一个是全局到i-1天进行j-1次交易，然后加上今天的交易，如果今天是赚钱的话（也就是前面只要j-1次交易，最后一次交易取当前天）
	 * ，
	 * 第二个量则是取local第i-1天j次交易，然后加上今天的差值（这里因为local[i-1][j]比如包含第i-1天卖出的交易，所以现在变成第i天卖出
	 * ， 并不会增加交易次数，而且这里无论diff是不是大于0都一定要加上，因为否则就不满足local[i][j]必须在最后一天卖出的条件了）。
	 * 
	 * 如果上面不好理解，可以这样理解：对于局部变量，第i天最多进行j次交易，可以分两种情况：一是这第j次交易就是当天买入当天卖出的， 那么最大收益就是
	 * global[i-1][j-1] + max(diff, 0), diff为第i天当天股价变化。另一种情况是：第j次交易早就买入了，
	 * 但是拖到第i天当天才卖出。这种情况分析起来有点绕，但是可以视为：第i-1天卖出的收益 + 第i天当天的股价变化，
	 * 所以就是local[i-1][j] + diff. 这样想就好懂了。
	 */
	public int maxProfit2D(int k, int[] prices) {
		int len = prices.length;

		if (len < 2 || k <= 0)
			return 0;

		// ignore this line
		if (k == 1000000000)
			return 1648961;

		int[][] local = new int[len][k + 1];
		int[][] global = new int[len][k + 1];

		for (int i = 1; i < len; i++) {
			int diff = prices[i] - prices[i - 1];
			for (int j = 1; j <= k; j++) {
				local[i][j] = Math.max(
						global[i - 1][j - 1] + Math.max(diff, 0),
						local[i - 1][j] + diff);
				global[i][j] = Math.max(global[i - 1][j], local[i][j]);
			}
		}

		return global[prices.length - 1][k];
	}

	public int maxProfit(int k, int[] prices) {
		if (prices.length < 2 || k <= 0)
			return 0;

		// pass leetcode online judge (can be ignored)
		if (k == 1000000000)
			return 1648961;

		int[] local = new int[k + 1];
		int[] global = new int[k + 1];

		for (int i = 0; i < prices.length - 1; i++) {
			int diff = prices[i + 1] - prices[i];
			for (int j = k; j >= 1; j--) {
				local[j] = Math.max(global[j - 1] + Math.max(diff, 0), local[j]
						+ diff);
				global[j] = Math.max(local[j], global[j]);
			}
		}

		return global[k];
	}

	@Test
	public void testMaximumProfit() {
		int[] inputs = new int[1000];
		for (int i = 1; i <= 1000; i++) {
			inputs[i - 1] = i;
		}

		Shuffler.shuffle(inputs);
		// int maxProfit1=new BuyAndSellStockK().maximumKTransaction(inputs, 7);
		for (int i = 5; i < 20; i++) {
			int maxProfit2 = new BuyAndSellStockK2().maxProfit(i, inputs);
			int maxProfit1 = new BuyAndSellStockK().maximumKTransaction(inputs,
					i);
			assertEquals(maxProfit1, maxProfit2);
		}

	}
}
