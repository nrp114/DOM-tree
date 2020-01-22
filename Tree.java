package structures;

import java.util.*;

/**
 * This class implements an HTML DOM Tree. Each node of the tree is a TagNode, with fields for
 * tag/text, first child and sibling.
 * 
 */
public class Tree {
	
	/**
	 * Root node
	 */
	TagNode root=null;
	
	/**
	 * Scanner used to read input HTML file when building the tree
	 */
	Scanner sc;
	
	/**
	 * Initializes this tree object with scanner for input HTML file
	 * 
	 * @param sc Scanner for input HTML file
	 */
	public Tree(Scanner sc) {
		this.sc = sc;
		root = null;
	}
	
	/**
	 * Builds the DOM tree from input HTML file, through scanner passed
	 * in to the constructor and stored in the sc field of this object. 
	 * 
	 * The root of the tree that is built is referenced by the root field of this object.
	 */
	public void build() {

 		/** COMPLETE THIS METHOD **/

		root= buildTree();
	}

	

	private TagNode buildTree() 

	{

		

		String currentLine = null;

		

		if (sc.hasNextLine() == true)

			currentLine = sc.nextLine();

		else

			return null; 

		

		int length = currentLine.length();

		boolean checkTag = false;               //if it is true, then its a tag. If its false, then its a closing tag

		

		if (currentLine.charAt(0) == '<')

		{	

			currentLine=currentLine.substring(1,length-1);

			if (currentLine.charAt(0) == '/')						

				return null;

			else 

				checkTag = true; 

		}

		

		// Create the new TagNode for the tree.

		TagNode temp = new TagNode (currentLine, null, null);

		

		if(checkTag == true)

			temp.firstChild = buildTree();	// Only tags have a firstChild

		

		temp.sibling = buildTree();												// Add to tree if just plain text

		

		return temp;

	}
	/*public void build() {
		/*
		Scanner ch =sc;
		Stack<String> st =new Stack<String>();
		st.push("html");
		TagNode tn = new TagNode("html",null,null);
		root = tn;
		TagNode ptr = tn;
		String strp="";
		String str=ch.nextLine();
		
		
		while(!st.isEmpty()) {
			str = ch.nextLine();
			if(str.charAt(0)=='<') {
				if(str.charAt(1)=='/') {
					String poped = st.pop();
					ptr=root;
					ptr = search(poped,ptr);
				}
				else {
					strp = str.substring(1,str.length()-1);// adding to stack
					st.push(strp);
					
					TagNode tnt = new TagNode(str,null,null); // creating new node
					ptr.firstChild = tnt;
					ptr=tnt;
				}
				
			}
			else {
				TagNode tnt = new TagNode(str,null,null);
				ptr.firstChild = tnt;
				ptr=tnt;
			}
		}
	
		
		root = buildit();
		
	}
	

	private TagNode buildit() {
		int length;
		String str = null;
		boolean bb = sc.hasNextLine();
		
		if (bb == true)
			{str = sc.nextLine();}
		else
			{return null; }
		
		length = str.length();
		boolean y = false;
		if (str.charAt(0) == '<'){						
			str = str.substring(1, length - 1);
			if (str.charAt(0) == '/')						
				{return null;}
			else {
				y = true; 
			}
		}
		TagNode temp = new TagNode (str, null, null);
		if(y == true) {
			temp.firstChild = buildit();		
						}
		temp.sibling = buildit();												
		return temp;
	}
	
	/**
	 * Replaces all occurrences of an old tag in the DOM tree with a new tag
	 * 
	 * @param oldTag Old tag
	 * @param newTag Replacement tag
	 */
	 
	 
	 
	public void replaceTag(String oldTag, String newTag) {
		
		find(root, oldTag, newTag);
		
	}
	
	private void find(TagNode r, String old, String updated) {
			
		if(r==null) {
			return;
		}
		find(r.firstChild,old,updated);
		if(r.tag.equals(old)) {
			r.tag=updated;
		}
		find(r.sibling,old,updated);
	}
	
	/**
	 * Boldfaces every column of the given row of the table in the DOM tree. The boldface (b)
	 * tag appears directly under the td tag of every column of this row.
	 * 
	 * @param row Row to bold, first row is numbered 1 (not 0).
	 */
	public void boldRow(int row) {

		TagNode ptrn= findTable(root);

		if(ptrn == null)

			return;

		ptrn=ptrn.firstChild;

		for(int i=1; i< row; i++)

			ptrn=ptrn.sibling;

		TagNode temp= ptrn.firstChild;

		while(temp!= null)

		{
			temp.firstChild= new TagNode("b", temp.firstChild, null);
			temp=temp.sibling;
		}

	}

	private TagNode findTable(TagNode current)

	{

		if(current == null)
			return null;
		TagNode temp= null;
		if(current.tag.equals("table") == true)
		{
			temp=current;
			return current;
		}
			if(temp == null)

		{
			temp=findTable(current.firstChild);
		}

		if(temp == null)

		{
			temp=findTable(current.sibling);
		}

		return temp;

	}
	
	/**
	 * Remove all occurrences of a tag from the DOM tree. If the tag is p, em, or b, all occurrences of the tag
	 * are removed. If the tag is ol or ul, then All occurrences of such a tag are removed from the tree, and, 
	 * in addition, all the li tags immediately under the removed tag are converted to p tags. 
	 * 
	 * @param tag Tag to be removed, can be p, em, b, ol, or ul
	 */
	public void removeTag(String tag) {
		
		findandremove(root,tag);
	}
	
	private void findandremove(TagNode r, String toremove) {
		
		if(r==null) {
			return;
		}
		else {
		if( r.firstChild!=null && r.firstChild.tag.equals(toremove)) {
			if(r.firstChild.tag.equals("p") || r.firstChild.tag.equals("em") || r.firstChild.tag.equals("b")) {
				
				TagNode aim = r.firstChild;
				TagNode aimnext = r.firstChild.sibling;
				TagNode aimChild = r.firstChild.firstChild;			
				r.firstChild=aim.firstChild;		
				while(aimChild.sibling!= null) {
					aimChild = aimChild.sibling;
				}
				aimChild.sibling=aimnext;
			}
			else if(r.firstChild.tag.equals("ol") || r.firstChild.tag.equals("ul")) {
				TagNode aim = r.firstChild;
				TagNode aimnext = r.firstChild.sibling;
				TagNode aimChild = r.firstChild.firstChild;
				aimChild.tag="p";
				r.firstChild=aim.firstChild;
				while(aimChild.sibling!= null) {
					
					aimChild = aimChild.sibling;
					aimChild.tag="p";
				}
				
				aimChild.sibling=aimnext;
			
			}
				}
		
		if( r.sibling!=null && r.sibling.tag.equals(toremove)) {
			
			if(r.sibling.tag.equals("p") || r.sibling.tag.equals("em") || r.sibling.tag.equals("b")) {
			//TagNode aim = r.sibling;
			TagNode aimnext = r.sibling.sibling;
			TagNode aimChild = r.sibling.firstChild;
			r.sibling=aimChild;
			while(aimChild.sibling!= null) {
				aimChild = aimChild.sibling;
			}	
			aimChild.sibling=aimnext;
		}
			else if(r.sibling.tag.equals("ol") || r.sibling.tag.equals("ul") ) {
				//TagNode aim = r.sibling;
				TagNode aimnext = r.sibling.sibling;
				TagNode aimChild = r.sibling.firstChild;
				aimChild.tag="p";
				r.sibling=aimChild;
				while(aimChild.sibling!= null) {
					
					aimChild = aimChild.sibling;
					aimChild.tag="p";
				}	
				aimChild.sibling=aimnext;
				
			}
		}
		}
		
		
		
		
		
		findandremove(r.firstChild,toremove);
		findandremove(r.sibling,toremove);
		/*
		TagNode ptr= r;
		
		if(r==null) {
			return;
		}*/
		
	}
	/** 
	 * @param word Word around which tag is to be added
	 * @param tag Tag to be added
	 */
	public void addTag(String word, String tag) {
		/** COMPLETE THIS METHOD **/
		
		addit(root,word,tag);
	}
	
	private void addit(TagNode r, String word, String tag) {
		
		if(r==null) {
			return;
		}
		
		addit(r.firstChild,word,tag);
		addit(r.sibling,word,tag);	
		
		if( r.firstChild != null) {
			//String temp = r.firstChild.tag.toLowerCase();
		
				while(r.firstChild != null) {
					TagNode rp = findit(r,r.firstChild.tag, word, tag);
					siblingfind(rp,word,tag);
					break;
				}
			
		}		
		
	}
	private TagNode findit(TagNode r, String rstring, String target, String tag) {
		int indexcheck=0;
		String temp = rstring.toLowerCase();
		String tgp = target.toLowerCase();
		int index = temp.indexOf(tgp);
		while( temp.contains(tgp)) {
			temp = rstring.substring(indexcheck).toLowerCase();
			index = temp.indexOf(tgp)+indexcheck;
			indexcheck=index+tgp.length();
			temp = rstring.substring(indexcheck).toLowerCase();
			
			TagNode next=r.firstChild.sibling;
			TagNode one = null;
			if(index!= 0 ) 
					{
			one = new TagNode(rstring.substring(0,index),null,null);
					}
			TagNode two = new TagNode(rstring.substring(index,index + target.length()),null,null);
			TagNode three=null;
			if(index+target.length() < rstring.length()) {
			 three = new TagNode(rstring.substring(target.length()+index),null,null);
			}
			TagNode toadd = new TagNode(tag,null,null);
			
			boolean checker = okafter(two,three);
			boolean checker2 = okbefore(two,one);
			
			if(checker==true && checker2==true) {

				if(one != null) {
					r.firstChild=one;
					one.sibling=toadd;
				}
				else {
					r.firstChild=toadd;
					
				}
				toadd.firstChild=two;
				toadd.sibling=three;
				if(three == null) {
					toadd.sibling=next;
				}
				else {
					three.sibling=next;
				}
				if(indexcheck==index+target.length()) {
					r=r.firstChild;
				}
				else {
					r=r.firstChild.sibling;
				}
				return r;
			}
			
			
			
		}
			return r.firstChild;
	}

	
	private void siblingfind(TagNode r , String target, String tag) {
		if(r==null) {
			return;
		}
		String tgp = target.toLowerCase();
		TagNode rsibling = r;
		if( rsibling.sibling != null && rsibling.sibling.sibling != null &&( rsibling.sibling.tag.equals("em") ||  rsibling.sibling.tag.equals("b"))) {
			rsibling = rsibling.sibling;
		}
		if(rsibling.sibling != null && rsibling.sibling.tag.toLowerCase().contains(tgp)) {
			
			int indexcheck=0;
			String rstring = rsibling.sibling.tag;
			tgp = target.toLowerCase();
			String temp = rstring.toLowerCase();
			int index = temp.indexOf(tgp);
		
		while(rsibling.sibling != null) {

			rstring = rsibling.sibling.tag;
			temp = rstring.substring(indexcheck).toLowerCase();
			index = temp.indexOf(tgp);
			if(index==-1) {
				rsibling=rsibling.sibling;
				indexcheck=0;
				continue;
			}
			index = temp.indexOf(tgp)+indexcheck;
			indexcheck=index+target.length();
			
			TagNode next=rsibling.sibling.sibling;
			TagNode one = null;
			if(index!= 0 ) 
					{
			one = new TagNode(rstring.substring(0,index),null,null);
					}
			TagNode two = new TagNode(rstring.substring(index,index + target.length()),null,null);
			TagNode three=null;
			if(index+target.length() < rstring.length()) {
			 three = new TagNode(rstring.substring(target.length()+index),null,null);
			
			}
			TagNode toadd = new TagNode(tag,null,null);
			
			boolean checker = okafter(two,three);
			boolean checker2 = okbefore(two,one);
			
			if(checker==true && checker2==true) {
				if(one != null) {
					rsibling.sibling=one;
					one.sibling=toadd;
				}
				else {
					rsibling.sibling=toadd;
				}
				toadd.firstChild=two;
				toadd.sibling=three;
				if(three == null) {
					toadd.sibling=next;
				}
				else {
					three.sibling=next;
				}
				if(one != null) {
					rsibling = rsibling.sibling.sibling;
				}
				else {
					rsibling = rsibling.sibling;
				}
				indexcheck=0;
			}
	
		}
	}
		else {
			siblingfind(r.sibling,target,tag);
		}
	}
	
	private boolean okafter(TagNode main, TagNode after) {
		
		if(after == null) {
			return true;
		}
				
		String two = after.tag;
		//String one = main.tag;
		if(two.charAt(0)=='.' || two.charAt(0)==','|| two.charAt(0)=='?' || two.charAt(0)==';' || two.charAt(0)==':'|| two.charAt(0)=='!') {
			
			if(two.length()!=1 && (two.charAt(1)=='.' || two.charAt(1)==','|| two.charAt(1)=='?' || two.charAt(1)==';' || two.charAt(1)==':'|| two.charAt(1)=='!')) {
				return false;
			}
			else {
				String toa = after.tag.substring(0,1);
				after.tag = after.tag.substring(1);
				main.tag = main.tag +toa;
				return true;
			}
		}
		else if(two.charAt(0)==' ') {
			return true;
		}
		return false;
	}
	
	private boolean okbefore(TagNode main, TagNode before) {
		if(before==null) {
			return true;
		}
		
		String endd = before.tag;
		if(endd.charAt(endd.length()-1)==' ') {
			return true;
		}
		return false;
	}
	
	/**
	 * Gets the HTML represented by this DOM tree. The returned string includes
	 * new lines, so that when it is printed, it will be identical to the
	 * input file from which the DOM tree was built.
	 * 
	 * @return HTML string, including new lines. 
	 */
	public String getHTML() {
		StringBuilder sb = new StringBuilder();
		getHTML(root, sb);
		return sb.toString();
	}
	
	private void getHTML(TagNode root, StringBuilder sb) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			if (ptr.firstChild == null) {
				sb.append(ptr.tag);
				sb.append("\n");
			} else {
				sb.append("<");
				sb.append(ptr.tag);
				sb.append(">\n");
				getHTML(ptr.firstChild, sb);
				sb.append("</");
				sb.append(ptr.tag);
				sb.append(">\n");	
			}
		}
	}
	
	/**
	 * Prints the DOM tree. 
	 *
	 */
	public void print() {
		print(root, 1);
	}
	
	private void print(TagNode root, int level) {
		for (TagNode ptr=root; ptr != null;ptr=ptr.sibling) {
			for (int i=0; i < level-1; i++) {
				System.out.print("      ");
			};
			if (root != this.root) {
				System.out.print("|----");
			} else {
				System.out.print("     ");
			}
			System.out.println(ptr.tag);
			if (ptr.firstChild != null) {
				print(ptr.firstChild, level+1);
			}
		}
	}
}
