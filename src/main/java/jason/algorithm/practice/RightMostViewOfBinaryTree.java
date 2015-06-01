package jason.algorithm.practice;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

import org.junit.Test;



public class RightMostViewOfBinaryTree<T> {

	public static class TreeNode<T> {
		T val;
		TreeNode<T> left;
		TreeNode<T> right;

		TreeNode(T x) {
			val = x;
		}
	}
	
	
	public void rightView(TreeNode<T> root, Consumer<T> consumer){
		
		Queue<TreeNode<T>> nodes=new LinkedList<>();
		Queue<Integer> levels=new LinkedList<Integer>();
		
		nodes.offer(root);
		levels.offer(0);
		int preLevel=-1;
		
		TreeNode<T> preNode=null;
		while (!nodes.isEmpty()){
			TreeNode<T> currentNode=nodes.poll();
			int level=levels.poll();
			if (level!=preLevel && preNode!=null){
				consumer.accept(preNode.val);
			}
			
		
			if (currentNode.left!=null){
				nodes.offer(currentNode.left);
				levels.offer(level+1);
			}
			if (currentNode.right!=null){
				nodes.offer(currentNode.right);
				levels.offer(level+1);
			}
			preLevel=level;
			preNode=currentNode;
		}
		
		//last node;
		consumer.accept(preNode.val);
	}
	
	
	
	@Test
	public void test(){
		
		TreeNode<Integer> root=new TreeNode<>(1);
		
		TreeNode<Integer> node5=new TreeNode<>(5);
		TreeNode<Integer> node2=new TreeNode<>(2);
		node2.right=node5;
		root.left=node2;
		
		root.right=new TreeNode<>(3);
		
		
		int[] results=new int[3];
		
		Consumer<Integer> consumer=new Consumer<Integer>(){
			int index=0;

			@Override
			public void accept(Integer t) {
				results[index++]=t;
				
			}
			
		};
		
		
		RightMostViewOfBinaryTree<Integer> tree=new RightMostViewOfBinaryTree<>();
		
		tree.rightView(root, consumer);
		
		int[] expected={1,3,5};
		assertTrue(Arrays.equals(results, expected));
		
		
	}

	
	
	
}
