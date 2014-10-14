package ds1;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import ds1.Game.DSGameNode;

@SuppressWarnings("unused")
public class Demo{

	public static void main(String[] args){
		//Nim nim = new Nim(2,2,2);
		//DSGameNode<Object> tree = nim.buildTree(nim.board);
		//System.out.println("Nim 2,2,2 has this many leaves: " + tree.leavesOfTree());
		TicTacToe ttt = new TicTacToe();
		//NimCopy n = new NimCopy(1,1,1);
		//BattleshipGUI b = new BattleshipGUI();
		//TicTacToeGUI t = new TicTacToeGUI();
		//BattleshipMine g = new BattleshipMine();
		//BattleshipMineCopy g = new BattleshipMineCopy();

		/*DSNode<Integer> root = new DSNode<Integer>(6, null);
		DSNode<Integer> a, b, c, d, e, f, g, h, i;
		a = root.addChild(2);
		d = root.addChild(8);
		h = root.addChild(4);

		b = a.addChild(1);
		c = b.addChild(5);
		c.addChild(0);

		e = d.addChild(9);

		e.addChild(8);
		f = e.addChild(3);

		g = f.addChild(6);

		g.addChild(9);
		g.addChild(5);

		h.addChild(0);
		h.addChild(0);
		i = h.addChild(2);

		i.addChild(2);
		i.addChild(1);
		i.addChild(7);
		i.addChild(6);

		System.out.println("A has size " + root.leavesOfTree());
		System.out.println("A has size " + root.sizeOfTree());
		System.out.println(root.linearize());
		 */

		//Build the graph of 5-letter words
		//DSGraph<String> G = new DSGraph<String>();
		
		/*try{
			FileReader f = new FileReader("/home/hochberg/Documents/words5.txt");
			BufferedReader reader = new BufferedReader(f);
			String line = null;
			while((line = reader.readLine()) != null){
				G.addVertex(line);
				DSArrayList<String> v = G.getVertices();
				for(int i = 0; i < v.size(); i++)
					String otherString = v.get(i);
					if(hammingDistance(line, otherString) == 1)
						G.connect(line, otherString);
			}
		} 
		catch(IOException x){
			System.err.format("IOException: %s\n", x);
		}
		
		G.print();
		
		//BFS on graph to find shortest path b/t place and thing
		G.shortestPath("thing", "place");*/
		
		/*DSQueue<String, Integer> q = new DSQueue<String, Integer>();
		q.print();
		q.push("a", 0);
		q.push("b", 0);
		q.print();
		q.pop();
		q.print();*/
	}
}