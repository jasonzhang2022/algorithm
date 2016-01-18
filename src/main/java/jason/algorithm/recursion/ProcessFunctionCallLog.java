package jason.algorithm.recursion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

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
	public MultiTreeNode process(BufferedReader reader) throws IOException{
		//a fake root to holding all root-level function
		MultiTreeNode root=new MultiTreeNode(null, null);
		process(reader, root);
		return root;
	}
	
	
	public void process(BufferedReader reader, MultiTreeNode context) throws IOException{
		
		 String line;
		 while ((line=reader.readLine())!=null){
			 String[] segments=line.split("\\s+");
			 if (segments[0].equals("START")){
				 String funcName=segments[1];
				 MultiTreeNode newNode=new MultiTreeNode(funcName, context);
				 context.children.add(newNode);
				 //switch context
				 context=newNode;
			 } else if (segments[0].equals("END")){
				 context=context.parent;
			 }
		 }
	}
	@Test
	public void testLogParserSimple() throws IOException {
		String log="START A\n"
				+"END A\n";
		 BufferedReader reader=new BufferedReader(new StringReader(log));
		 
				
		 MultiTreeNode root=process(reader);
		 assertThat(root.children, hasSize(1));
		 assertThat(root.children.get(0).name, equalTo("A"));
	}
	@Test
	public void testLogParser() throws IOException {
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
		 
				
		 MultiTreeNode root=process(reader);
		 assertThat(root.children, hasSize(2));
		 assertThat(root.children.get(0).name, equalTo("A"));
		 assertThat(root.children.get(1).name, equalTo("D"));
		 assertThat(root.children.get(1).children, hasSize(0));
		 
		 MultiTreeNode A=root.children.get(0);
		 assertThat(A.children, hasSize(2));
		 assertThat(A.children.get(0).name, equalTo("B"));
		 assertThat(A.children.get(1).name, equalTo("C"));
		 assertThat(A.children.get(1).children, hasSize(0));
		
		 MultiTreeNode B=A.children.get(0);
		 assertThat(B.children, hasSize(2));
		 assertThat(B.children.get(0).name, equalTo("B1"));
		 assertThat(B.children.get(1).name, equalTo("B2"));
		 assertThat(B.children.get(0).children, hasSize(0));
		 assertThat(B.children.get(1).children, hasSize(0));
	}
	
	public MultiTreeNode processRecursive(BufferedReader reader) throws IOException{
		//a fake root to holding all root-level function
		MultiTreeNode root=new MultiTreeNode(null, null);
		while (true) {
			MultiTreeNode child=processOneFunctionCall(reader);
			if (child!=null){
				child.parent=root;
				root.children.add(child);
			} else{
				break;
			}
		}
		if (root.children.isEmpty()){
			return null;
		}
		if (root.children.size()==1){
			return root.children.getFirst();
		}
		process(reader, root);
		return root;
	}
	
	
	//we don't use parent information. Parent information is in the recursion stack
	public MultiTreeNode processOneFunctionCall(BufferedReader reader) throws IOException{
		String[] startSegments=retrieveStartOrEndLine(reader);
		if (startSegments==null){
			return null;
		}
		if (startSegments[0].equals("END")){
			//this end if the end of parent 
			//there is no functionCall anymore.
			reader.reset();
			return null;
		}
		
		MultiTreeNode root=new MultiTreeNode(startSegments[1]);
		while (true) {
			MultiTreeNode child=processOneFunctionCall(reader);
			if (child!=null){
				child.parent=root;
				root.children.add(child);
			} else{
				break;
			}
		}
		
		//child is null, Strip end line.
		reader.readLine();
		return root;
	}
	
	
	public String[] retrieveStartOrEndLine(BufferedReader reader) throws IOException{
		
		//we will set a mark just before the valid line : START or END.
		String line;
		reader.mark(1000);
		while ((line=reader.readLine())!=null){
			String[] segments=line.split("\\s+");
			if (segments[0].equals("START")){
				return segments;
			} else if (segments[0].equals("END")){
				return segments;
			} else{
				reader.mark(1000);
			}
		}
		//reach the end.
		return null;
		
	}
	
	
	@Test
	public void testLogParserSimpleRecursive() throws IOException {
		String log="START A\n"
				+"END A\n";
		 BufferedReader reader=new BufferedReader(new StringReader(log));
		 
				
		 MultiTreeNode root=processRecursive(reader);
		 assertThat(root.children, hasSize(1));
		 assertThat(root.children.get(0).name, equalTo("A"));
	}
	
	@Test
	public void testLogParserRecursive1() throws IOException {
		String log="";
		 BufferedReader reader=new BufferedReader(new StringReader(log));
		 
				
		 MultiTreeNode root=processRecursive(reader);
		 assertNull(root);
	}
	@Test
	public void testLogParserRecursive2() throws IOException {
		String log="START A\nEND A\n";
		 BufferedReader reader=new BufferedReader(new StringReader(log));
		 
		 MultiTreeNode root=processRecursive(reader);
		 assertThat(root.children, hasSize(0));
		 assertThat(root.name, equalTo("A"));
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
		 
				
		 MultiTreeNode root=processRecursive(reader);
		 assertThat(root.children, hasSize(2));
		 assertThat(root.children.get(0).name, equalTo("A"));
		 assertThat(root.children.get(1).name, equalTo("D"));
		 assertThat(root.children.get(1).children, hasSize(0));
		 
		 MultiTreeNode A=root.children.get(0);
		 assertThat(A.children, hasSize(2));
		 assertThat(A.children.get(0).name, equalTo("B"));
		 assertThat(A.children.get(1).name, equalTo("C"));
		 assertThat(A.children.get(1).children, hasSize(0));
		
		 MultiTreeNode B=A.children.get(0);
		 assertThat(B.children, hasSize(2));
		 assertThat(B.children.get(0).name, equalTo("B1"));
		 assertThat(B.children.get(1).name, equalTo("B2"));
		 assertThat(B.children.get(0).children, hasSize(0));
		 assertThat(B.children.get(1).children, hasSize(0));
		 
		 assertThat(root.toString(), equalTo("A(B(B1,B2),C),D"));
	}
}
