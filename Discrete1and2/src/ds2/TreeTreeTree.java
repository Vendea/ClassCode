package ds2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

import ds1.DSNode;

public class TreeTreeTree {

	static Scanner scanner;
	static HashMap<Integer, Integer> pathsToLeaves;

	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		String inorder;
		String postorder;
		int numProbs = 0;
		while(scanner.hasNext()){
			inorder = scanner.nextLine();
			postorder = scanner.nextLine();
			String[] ino = inorder.split("\\s");
			String[] posto = postorder.split("\\s");
			ArrayList<Integer> inn = new ArrayList<Integer>();
			ArrayList<Integer> postt = new ArrayList<Integer>(); 
			for(int i = 0; i < ino.length;i++){
				inn.add(Integer.parseInt(ino[i]));
			}
			for(int i = 0; i < posto.length; i++){
				postt.add(Integer.parseInt(posto[i]));
			}
			numProbs++;
			System.out.println("ANSWER "+numProbs+": " + doNextProblem(inn, postt));
		}
	}

	static int doNextProblem(ArrayList<Integer> in, ArrayList<Integer> post) {
		if(in.size()==1)
			return in.get(0);
		if(in.size() == 2)
			return in.get(0)<in.get(1) ? in.get(0):in.get(1);

		pathsToLeaves = new HashMap<Integer, Integer>();
		//System.out.println("Now doing next problem"); //
		//System.out.println("Post list size before cut: " + post.size()); //
		DSNode<Integer> root = new DSNode<Integer>(post.get(post.size()-1), null);
		//System.out.println("Root val is: " + root.returnThing()); //
		post.remove(post.size()-1);
		//System.out.println("Last element in post list is now: " + post.get(post.size()-1));
		DSNode<Integer> tree = buildTree(root, in, post);
		ArrayList<DSNode<Integer>> leaves = findLeaves(tree);
		int[] pathVals = new int[leaves.size()];
		for(int i = 0; i < leaves.size();i++){
			pathVals[i] = findValofPathtoNode(leaves.get(i));
			pathsToLeaves.put(leaves.get(i).returnThing(), pathVals[i]);
		}
		Arrays.sort(pathVals);
		//System.out.println("Theoretically lowest value: " +leaves.get(0).returnThing() + " Or: " + pathVals[0]); //
		//System.out.println("Value of least value path: "+ pathsToLeaves.get(leaves.get(0).returnThing())); //
		//System.out.println("Node of least value path: " + getNode(pathVals, leaves, pathsToLeaves)); //Leaf is key, pathVal is value
		return getNode(pathVals, leaves, pathsToLeaves);
	}
	
	/**
	 * Okay, this works too.  Don't touch.
	 * @param vals
	 * @param nodes
	 * @param hm
	 * @return
	 */
	static int getNode(int[] vals, ArrayList<DSNode<Integer>> nodes, 	HashMap<Integer, Integer> hm) {
		ArrayList<Integer> leastNodes = new ArrayList<Integer>();
		for(int i = 0; i < nodes.size();i++){
			int valueOfNodeToCheck = nodes.get(i).returnThing();
			int pathVal = hm.get(valueOfNodeToCheck);
			if(pathVal == vals[0]){
				leastNodes.add(valueOfNodeToCheck);
			}
		}
		if(leastNodes.size() == 1)
			return leastNodes.get(0);

		int[] tempArr = new int[leastNodes.size()];
		for(int i = 0; i < leastNodes.size();i++){
			tempArr[i] = leastNodes.get(i);
		}

		Arrays.sort(tempArr);
		return tempArr[0];
	}
	
	static int count = 0; //
	static DSNode<Integer> buildTree(DSNode<Integer> tr, ArrayList<Integer> in, ArrayList<Integer> post) {
		
		if(post.size() == 1){
			DSNode<Integer> r = new DSNode<Integer>(post.get(0), null);
			return r;
		}
		
		count++; //
		//System.out.println("Now building tree " + count); //
		
		//System.out.println("In list size: " + in.size()); //
		//System.out.println("Post size after cut: " + post.size()); //
		
		int splitval = in.indexOf(tr.returnThing());
		
		//System.out.println("Splitval = " + splitval + ", root = " + tr.returnThing()); //

		ArrayList<Integer> newinleft = new ArrayList<Integer>();
		ArrayList<Integer> newpostleft = new ArrayList<Integer>();
		ArrayList<Integer> newinright = new ArrayList<Integer>();
		ArrayList<Integer> newpostright = new ArrayList<Integer>();
		DSNode<Integer> rightroot;
		DSNode<Integer> leftroot;
		
		if(splitval!=0){
			
			for(int i = 0; i < splitval;i++){
				newinleft.add(in.get(i));
				System.out.print(in.get(i));
			}
			//System.out.println("newinleft size: "+ newinleft.size()); //
			newpostleft = getPost(newinleft, post);
			leftroot = new DSNode<Integer>(newpostleft.get(newpostleft.size()-1),null);
			tr.addChild(buildTree(leftroot,newinleft,newpostleft));
		}
		
		if(splitval!=in.size()-1){
			for(int i = splitval; i<in.size()-1;i++){
				newinright.add(in.get(i+1));
				//System.out.print(in.get(i+1));
			}
			//System.out.println("newinright size: "+ newinright.size()); //
			newpostright = getPost(newinright, post);
			rightroot = new DSNode<Integer>(newpostright.get(newpostright.size()-1),null);
			tr.addChild(buildTree(rightroot,newinright,newpostright));
		}		
		return tr;
	}

	private static ArrayList<Integer> getPost(ArrayList<Integer> tempin, ArrayList<Integer> post) {
		//System.out.println("Getting new post lists now.");
		HashMap<Integer,Integer> vals = new HashMap<Integer, Integer>();
		int[] indices = new int[tempin.size()];
		for(int i =0; i < tempin.size(); i++){
			int key = post.indexOf(tempin.get(i));
			vals.put(key, tempin.get(i));
			indices[i] = key;
		}
		ArrayList<Integer> rvals = new ArrayList<Integer>();
		Arrays.sort(indices);
		for(int i = 0; i < vals.size(); i++){
			rvals.add(vals.get(indices[i]));
		}
		return rvals;
	}

	static ArrayList<DSNode<Integer>> findLeaves(DSNode<Integer> tree) {
		ArrayList<DSNode<Integer>> tempNodes = new ArrayList<DSNode<Integer>>();
		ArrayList<DSNode<Integer>> search = tree.linearizeNodes();
		for(int i = 0; i < search.size();i++){
			if(search.get(i).isLeaf()){
				tempNodes.add(search.get(i));
			}
		}
		return tempNodes;
	}
	
	/***
	 * OKAY THIS WORKS.  DON'T YOU DARE TOUCH IT.
	 * @param n
	 * @return
	 */
	static int findValofPathtoNode(DSNode<Integer> n) {
		int rval = n.returnThing();
		if(!(n.returnParent()!=null)){
			return n.returnThing();
		}
		DSNode<Integer> p = n.returnParent();
		rval+=(findValofPathtoNode(p));
		return rval;
	}
}
