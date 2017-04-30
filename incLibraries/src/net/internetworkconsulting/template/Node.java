package net.internetworkconsulting.template;

import java.util.regex.*;

enum NodeTypes {
	Constant, Variable, Block, Parsed
};

class Node {
	public String NodeName;
	public String NodeValue;
	public NodeTypes NodeType;
	public boolean IsTouched;
	public SyntaxInterface Syntax;

	public Node LinkPrevious;
	public Node LinkChild;
	public Node LinkParent;
	public Node LinkNext;

	private Node(SyntaxInterface syntax) {
		Syntax = syntax;
	}
	public Node(SyntaxInterface syntax, String contents) throws Exception {
		Syntax = syntax;
		Initialize(contents);
	}
	private void Initialize(String value) throws Exception {
		String contents = value;

		// find next variable
		String sPattern = Syntax.getVariable().replace("NAME", "\\w+?[[ ]+?\\w+?]*?");
		Pattern rVariable = Pattern.compile(sPattern);
		Matcher mVariable = rVariable.matcher(contents);
		boolean bVariableFind = mVariable.find();
		String sVariableGroup = "";
		int iVariableStart = -1;
		int iVariableEnd = -1;
		if(bVariableFind) {
			sVariableGroup = mVariable.group();
			iVariableStart = mVariable.start();
			iVariableEnd = mVariable.end();
		}

		// find next block
		sPattern = Syntax.getOpenBlock().replace("NAME", "\\w+");
		Pattern rOpen = Pattern.compile(sPattern);
		Matcher mOpen = rOpen.matcher(contents);
		boolean bOpenFind = mOpen.find();
		String sOpenGroup = "";
		int iOpenStart = -1;
		int iOpenEnd = -1;
		if(bOpenFind) {
			sOpenGroup = mOpen.group();
			iOpenStart = mOpen.start();
			iOpenEnd = mOpen.end();
		}

		if(bVariableFind && iVariableStart == 0) {
			// VARIABLE
			// setup node and pass left over to next node
			NodeName = CleanString(Syntax.getVariableReplace(), sVariableGroup);
			NodeType = NodeTypes.Variable;
			String sNext = contents.substring(mVariable.end());
			if(sNext.length() > 0) {
				LinkNext = new Node(Syntax, sNext);
				LinkNext.LinkPrevious = this;
			}
		} else if(bOpenFind && iOpenStart == 0) {
			// BLOCK
			// setup node
			NodeName = CleanString(Syntax.getOpenBlockReplace(), sOpenGroup);
			NodeType = NodeTypes.Block;
			IsTouched = false;

			// find closing
			Pattern rClose = Pattern.compile(Syntax.getCloseBlock().replace("NAME", NodeName));
			Matcher mClose = rClose.matcher(contents);
			boolean bCloseFind = mClose.find();
			String sCloseGroup = "";
			int iCloseStart = -1;
			int iCloseEnd = -1;
			if(bOpenFind) {
				sCloseGroup = mClose.group();				
				iCloseStart = mClose.start();
				iCloseEnd = mClose.end();
			}

			// give child content between open and close
			String sChild = contents.substring(iOpenEnd, iCloseStart);
			if(sChild.length() > 0) {
				LinkChild = new Node(Syntax, sChild);
				LinkChild.LinkParent = this;
			}

			// give next content after close
			String sNext = contents.substring(iCloseEnd);
			if(sNext.length() > 0) {
				LinkNext = new Node(Syntax, sNext);
				LinkNext.LinkPrevious = this;
			}
		} else {
			// STATIC
			// find next block or next variable
			int iNext = Integer.MAX_VALUE;
			if(bVariableFind && iVariableStart < iNext)
				iNext = iVariableStart;
			if(bOpenFind && iOpenStart < iNext)
				iNext = iOpenStart;
			if(iNext == Integer.MAX_VALUE)
				iNext = contents.length();

			// setup node
			NodeName = "";
			NodeType = NodeTypes.Constant;
			NodeValue = contents.substring(0, iNext);

			// pass remainder to next
			String sNext = contents.substring(iNext);
			if(sNext.length() > 0) {
				LinkNext = new Node(Syntax, sNext);
				LinkNext.LinkPrevious = this;
			}
		}
	}
	private String CleanString(String[] removes, String value) {
		String sRet = value;
		for(int cnt = 0;cnt < removes.length;cnt++)
			sRet = sRet.replace(removes[cnt], "");

		return sRet.trim();
	}

	public int Parse(String name) throws Exception {
		int iParsed = 0;

		if(NodeType == NodeTypes.Block && NodeName.equals(name)) {
			Node newNode = new Node(Syntax);
			newNode.LinkNext = this;
			newNode.NodeType = NodeTypes.Parsed;
			newNode.NodeValue = LinkChild.Parse();
			IsTouched = false;
			iParsed++;

			if(LinkParent != null && LinkParent.LinkChild == this) {
				// This == CHILD
				// link new node before this node
				LinkParent.LinkChild = newNode;
				newNode.LinkParent = LinkParent;
				LinkParent = newNode;
			} else if(LinkPrevious != null && LinkPrevious.LinkNext == this) {
				// This == NEXT
				// link new node before this node
				LinkPrevious.LinkNext = newNode;
				newNode.LinkPrevious = LinkPrevious;
				LinkPrevious = newNode;
			} else
				throw new Exception("Invalid linked node - a block cannot be the first node!");
		} else if(LinkChild != null)
			iParsed += LinkChild.Parse(name);

		if(LinkNext != null)
			iParsed += LinkNext.Parse(name);

		return iParsed;
	}
	private String Parse() throws Exception {
		String sRet = "";

		if(NodeType == NodeTypes.Constant)
			sRet += NodeValue;
		else if(NodeType == NodeTypes.Block && IsTouched) {
			sRet += LinkChild.Parse();
			IsTouched = false;
		} else if(NodeType == NodeTypes.Variable) {
			if(NodeValue == null)
				NodeValue = "";
			sRet += NodeValue;
			NodeValue = "";
		} else if(NodeType == NodeTypes.Parsed) {
			sRet += NodeValue;

			if(LinkParent != null && LinkParent.LinkChild == this && LinkNext != null) {
				// remove this CHILD from the tree
				Node nParent = LinkParent;
				Node nNext = LinkNext;

				nParent.LinkChild = nNext;
				nNext.LinkParent = nParent;
			} else if(LinkPrevious != null && LinkPrevious.LinkNext == this && LinkNext != null) {
				// remove this NEXT from the tree
				Node nPrevious = LinkPrevious;
				Node nNext = LinkNext;

				nPrevious.LinkNext = nNext;
				nNext.LinkPrevious = nPrevious;
			} else
				throw new Exception("Invalid linked parsed node!");
		}

		if(LinkNext != null)
			sRet += LinkNext.Parse();

		return sRet;
	}

	public int Touch(String name) {
		int iChanged = 0;

		if(NodeType == NodeTypes.Block && NodeName.equals(name)) {
			IsTouched = true;
			iChanged++;
		}

		if(LinkNext != null)
			iChanged += LinkNext.Touch(name);
		if(LinkChild != null)
			iChanged += LinkChild.Touch(name);

		return iChanged;
	}
	public int Set(String name, String value) {
		int iChanged = 0;

		if(NodeType == NodeTypes.Variable && NodeName.equals(name)) {
			NodeValue = value;
			iChanged++;
		}

		if(LinkChild != null)
			iChanged += LinkChild.Set(name, value);
		if(LinkNext != null)
			iChanged += LinkNext.Set(name, value);

		return iChanged;
	}
	public String Generate() throws Exception {
		return Parse();
	}
}
