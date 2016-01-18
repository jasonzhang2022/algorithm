package jason.algorithm.recursion;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import org.junit.Test;


public class ProcessFunctionCallLogLeftChildRightSibling {

	public static class Node {
		String name;
		Node leftChild;
		Node rightSibling;
		public Node(String name) {
			super();
			this.name = name;
		}
	}
	
	
	public Node process(BufferedReader reader) throws IOException{
		Node root=new Node(null);
		processSub(reader, root, null);
		return root;
	}
	
	public void processSub(BufferedReader reader, Node parent, Node child) throws IOException{
		String line;
		while ((line=reader.readLine())!=null){
			String[] segments=line.split("\\s+");
			if (segments[0].equals("START")) {
				Node newNode=new Node(segments[1]);
				if (child==null) {
					parent.leftChild=newNode;
					child=newNode;
				} else {
					child.rightSibling=newNode;
					child=newNode;
				}
				processSub(reader, child, null);
			} else if (segments[0].equals("END")){
				return;
			}
		} 
		
		
	}
	
	@Test
	public void testLogParserRecursive() throws IOException {
		String log="START A\n"
				+"START B\n"
				+"START B1\n"
				+"END B1\n"
				+"START B2\n"
				+"END B2\n"
				+"END B\n"
				+"START C\n"
				+"END C\n"
				+"END A\n"
				+"START D\n"
				+"END D\n";
		 BufferedReader reader=new BufferedReader(new StringReader(log));
		 
				
		 Node root=process(reader);
		 assertThat(root.leftChild.name, equalTo("A"));
		 Node D=root.leftChild.rightSibling;
		 assertThat(D.name, equalTo("D"));
		 assertNull(D.leftChild);
		 assertNull(D.rightSibling);
		 
		 
		 
		 Node A=root.leftChild;
		 assertThat(A.leftChild.name, equalTo("B"));
		 Node C=A.leftChild.rightSibling;
		 assertThat(C.name, equalTo("C"));
		 assertNull(C.leftChild);
		 assertNull(C.rightSibling);
		
		
		 Node B=A.leftChild;
		 assertThat(B.leftChild.name, equalTo("B1"));
		 assertThat(B.leftChild.rightSibling.name, equalTo("B2"));
	}
	
	
}
