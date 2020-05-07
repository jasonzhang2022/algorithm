package jason.algorithm.recursion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import com.google.common.base.Splitter;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;

/**
 * Each function call logs two lines
 * one line at entering the function: start name, 
 * one line at leaving the function: end name,
 * @author jason
 *
 */
/*
 * Same as construct a multiway tree give 
 */
public class ProcessFunctionCallLog {
	public  static MultiTreeNode processRecursive(BufferedReader reader) throws IOException{
		MultiTreeNode root = new MultiTreeNode(null, null);
		MultiTreeNode currentNode = root;
		String line = null;
		while ((line = reader.readLine())!= null) {
			String[] parts = line.split(" ");
			String prefix = parts[0];
			String funcName = parts[1];
			 if (prefix.equals("START")) {
			 	MultiTreeNode newNode = new MultiTreeNode(funcName, currentNode);
			 	currentNode.children.add(newNode);
			 	currentNode = newNode;
			 } else if (prefix.equals("END")) {
			 	assert funcName.equals(currentNode.name): "function should be closed";
			 	currentNode = currentNode.parent;
			 } else {
			 	throw new RuntimeException("not expected");
			 }
		}
		if (root.children.isEmpty()){
			return null;
		}
		if (root.children.size() ==1){
			return root.children.getFirst();
		}
		return root;
	}
	
	

	public static class TestCase {

		@Test
		public void testLogParserRecursive1() throws IOException {
			String log = "";
			BufferedReader reader = new BufferedReader(new StringReader(log));


			MultiTreeNode root = processRecursive(reader);
			assertNull(root);
		}

		@Test
		public void testLogParserRecursive2() throws IOException {
			String log = "START A\nEND A\n";
			BufferedReader reader = new BufferedReader(new StringReader(log));

			MultiTreeNode root = processRecursive(reader);
			assertThat(root.children, hasSize(0));
			assertThat(root.name, equalTo("A"));
		}

		@Test
		public void testLogParserRecursive() throws IOException {
			String log = "START A\n"
					+ "START B\n"
					+ "START B1\n"
					+ "END B1\n"
					+ "START B2\n"
					+ "END B2\n"
					+ "END B\n"
					+ "START C\n"
					+ "END C\n"
					+ "END A\n"
					+ "START D\n"
					+ "END D\n";
			BufferedReader reader = new BufferedReader(new StringReader(log));


			MultiTreeNode root = processRecursive(reader);
			assertThat(root.children, hasSize(2));
			assertThat(root.children.get(0).name, equalTo("A"));
			assertThat(root.children.get(1).name, equalTo("D"));
			assertThat(root.children.get(1).children, hasSize(0));

			MultiTreeNode A = root.children.get(0);
			assertThat(A.children, hasSize(2));
			assertThat(A.children.get(0).name, equalTo("B"));
			assertThat(A.children.get(1).name, equalTo("C"));
			assertThat(A.children.get(1).children, hasSize(0));

			MultiTreeNode B = A.children.get(0);
			assertThat(B.children, hasSize(2));
			assertThat(B.children.get(0).name, equalTo("B1"));
			assertThat(B.children.get(1).name, equalTo("B2"));
			assertThat(B.children.get(0).children, hasSize(0));
			assertThat(B.children.get(1).children, hasSize(0));

			assertThat(root.toString(), equalTo("A(B(B1,B2),C),D"));
		}
	}
}
