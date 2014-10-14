package ds1;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

public class DSNode<E>{
	private DSNode<E> parent;
	private ArrayList<DSNode<E>> children;
	private E thing;


	// Constructor
	// Whenever you construct, make sure that you set
	// up the fields the way you want
	public DSNode(E theThing, DSNode<E> p){
		parent = p;
		children = new ArrayList<DSNode<E>>();
		thing = theThing;
	}


	// this method adds a child to this node
	public DSNode<E> addChild(E certainElement){
		DSNode<E> newNode = new DSNode<E>(certainElement, this);
		children.add(newNode);
		return newNode;
	}
	
	public void addChild(DSNode<E> n){
		if(n.equals(null))
			return;
		children.add(n);
		n.parent = this;
		
	}
	
	public boolean isLeaf(){
		if(children.size() > 0)
			return false;
		return true;
	}

	public ArrayList<DSNode<E>> returnChildren(){
		return children;
	}

	public int countChildren(){
		return children.size();
	}

	//returns the number of nodes in this node tree including self and children
	public BigInteger sizeOfTree(){
		if(sizeHash.containsKey(this))
			return sizeHash.get(this);
		if(children.size() == 0){
			return BigInteger.ONE;
		}

		BigInteger retVal = BigInteger.ONE;
		for(int i = 0; i < children.size(); i++){
			retVal = retVal.add(children.get(i).sizeOfTree());
		}
		sizeHash.put((DSNode<Object>)this, retVal);
		return retVal;
	}
	
	public BigInteger leavesOfTree(){
		BigInteger retVal = BigInteger.ZERO;
		if(leafHash.containsKey(this))
			return leafHash.get(this);
		if(children.size() == 0)
			return BigInteger.ONE;
		for(int i = 0; i < children.size(); i++)
			retVal = retVal.add(children.get(i).leavesOfTree());
		leafHash.put((DSNode<Object>)this, retVal);
		return retVal;
	}
	
	private static HashMap<DSNode<Object>, BigInteger> sizeHash = new HashMap<DSNode<Object>, BigInteger>();
	private static HashMap<DSNode<Object>, BigInteger> leafHash = new HashMap<DSNode<Object>, BigInteger>();

	public DSNode<E> returnParent(){
		return parent;
	}

	public E returnThing(){
		return thing;
	}

	public void setThing(E newThing){
		// could be: anyElement = newThing
		this.thing = newThing;
	}

	public ArrayList<E> linearize(){
		ArrayList<E> treeList = new ArrayList<E>();
		treeList.add(thing);
		for(int i = 0; i < children.size(); i++){
			ArrayList<E> kids = children.get(i).linearize();

			for(int j = 0; j < kids.size(); j++){
				treeList.add(kids.get(j));
			}
		}
		return treeList;
	}
	
	public ArrayList<DSNode<E>> linearizeNodes(){
		ArrayList<DSNode<E>> rList = new ArrayList<DSNode<E>>();
		rList.add(this);
		for(int i = 0; i < children.size(); i++){
			ArrayList<DSNode<E>> kids = children.get(i).linearizeNodes();
			for(int j = 0; j< kids.size(); j++){
				rList.add(kids.get(j));
			}
		}
		return rList;
	}


	public boolean isRoot() {
		if(parent.equals(null))
			return true;
		return false;
	}
}