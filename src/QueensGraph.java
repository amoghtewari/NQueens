

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import javafx.util.Pair;



public class QueensGraph{
	private int n;
	private int numSols;
	public int calls;
	private long visited_nodes_number;
  
	QueensGraph(int n) {
		this.n = n;
		this.calls = 0;
		this.numSols = 0;
		this.visited_nodes_number = 0L;
	}

  
	public int getn() { return this.n; }
 
	public int getNumBacktracks() { return (int)(this.visited_nodes_number - this.numSols); }

	public int getSolutionsNumber() { return this.numSols; }

	public long getVisitedNodesNumber() { return this.visited_nodes_number; }


  
	public void writeCFile(String filename) {
		Set<String> hash_Set = new HashSet<>();
    
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			writer.println("CSP " + this.n + "-Queens");
			writer.println("Variables:{Domains}");
			for (int i = 1; i < this.n + 1; i++) {
				writer.print("Q" + i + " : ");
				writer.print("{");
				for (int j = 1; j < this.n + 1; j++) {
					writer.print(String.valueOf(j) + ",");
				}
				writer.println("} ");
			} 
      
			writer.println("Constraints: ");
      
			for (int a = 1; a < this.n + 1; a++) {
				for (int b = 1; b < this.n + 1; b++) {
					for (int c = 1; c < this.n + 1; c++) {
						for (int d = 1; d < this.n + 1; d++) {
							if (a == b && c != d) {
								hash_Set.add("Q" + a + "!=Q" + b);
							}
							if (Math.abs(c - d) == Math.abs(a - b)) {
								hash_Set.add("Q" + a + "-Q" + b + "!=" + Math.abs(a - b));
							}
						} 
					} 
				} 
			} 

      
			writer.println(hash_Set);
			writer.close();
		}
		catch (FileNotFoundException e) {
      
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
      
			e.printStackTrace();
		} 
	}

  
	public void FOR(String filename) {
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			forwardChecking(new Board(this.n), writer);

      
			writer.close();
		}
		catch (FileNotFoundException e) {
      
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
      
			e.printStackTrace();
		} 
	}

  
	public void MAC(String filename) {
		try {
			PrintWriter writer = new PrintWriter(filename, "UTF-8");
			maintainingArcConsistency(new Board(this.n), writer);

      
			writer.close();
		}
		catch (FileNotFoundException e) {
      
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
      
			e.printStackTrace();
		} 
	}

  
	private void forwardChecking(Board board, PrintWriter writer) {
		ArrayList<Var> to_assign = board.notAssignedVars();
		if (to_assign.size() == 0) {
			this.numSols++;
      String Assignment = board.retBeautifiedAssignment();
      	writer.println(Assignment);
      	Assignment = board.retAssignment();
      	writer.println(Assignment);
      	writer.println("-------------");
		} else {
			Var var = getnextToAssign(board, to_assign);
			ArrayList<Integer> domain = orderValues(var.getDomain());
			for (int i = 0; i < domain.size(); i++) {
        
				this.visited_nodes_number++;
				Map<Integer, ArrayList<Var>> var2ValRecords = new HashMap<>();
				if (testVal(board, var, ((Integer)domain.get(i)).intValue(), var2ValRecords)) {
          
					var.setvalue(((Integer)domain.get(i)).intValue());
					forwardChecking(board, writer);
					var.erasevalue();
				} 
        
				undovar2ValRecords(var2ValRecords);
			} 
		} 
	}

  
	private void maintainingArcConsistency(Board board, PrintWriter writer) {
		ArrayList<Var> to_assign = board.notAssignedVars();
		if (to_assign.size() == 0) {
			this.numSols++;
			String Assignment = board.retBeautifiedAssignment();
			writer.println(Assignment);
			Assignment = board.retAssignment();
			writer.println(Assignment);
			writer.println("-------------");
		} else {
      
			Var var = getnextToAssign(board, to_assign);
			ArrayList<Integer> domain = orderValues(var.getDomain());
			for (int i = 0; i < domain.size(); i++) {
        
				this.visited_nodes_number++;
				Map<Integer, ArrayList<Var>> var2ValRecords = new HashMap<>();
				if (testVal(board, var, ((Integer)domain.get(i)).intValue(), var2ValRecords)) {
          
					var.setvalue(((Integer)domain.get(i)).intValue());
					Map<Integer, ArrayList<Var>> var2ValRecords2 = new HashMap<>();
					if (modifiedAC3(var, board, var2ValRecords2)) {
						maintainingArcConsistency(board, writer);
					}
					undovar2ValRecords(var2ValRecords2);
					var.erasevalue();
				} 
        
				undovar2ValRecords(var2ValRecords);
			} 
		} 
	}

  
	private boolean modifiedAC3(Var var, Board board, Map<Integer, ArrayList<Var>> var2ValRecords2) {
		ArrayList<Integer> domainX = var.getDomain();
		for (int i = 0; i < domainX.size(); i++) {
      
			int x = ((Integer)var.getDomain().get(i)).intValue();
			Integer lValue = domainX.get(i);
			if (lValue != Integer.valueOf(var.getvalue())) {
        
				domainX.remove(lValue);
				i--;
        
				if (var2ValRecords2.containsKey(Integer.valueOf(x))) {
					((ArrayList<Var>)var2ValRecords2.get(Integer.valueOf(x))).add(var);
				} else {
          
					ArrayList<Var> temp2 = new ArrayList<>();
					temp2.add(var);
					var2ValRecords2.put(Integer.valueOf(x), temp2);
				} 
			} 
		} 

    
		Var[] Vars = board.getVars();
    
		Queue<Pair<Var, Var>> q = new LinkedList<>();

    
		for (int i = 0; i < board.getSize(); i++) {
			if (Vars[i].getvalue() == -1) {
				q.add(new Pair<>(Vars[i], var));
			}
		} 
		int k = 0;
		while (q.size() > 0) {
			Pair<Var, Var> x = q.remove();
			Var r1 = x.getKey();
			Var r2 = x.getValue();
      
			if (revise(r1, r2, var2ValRecords2) || k == 0) {
				k++;
				if (r1.getDomain().size() == 0) {
					return false;
				}
        
				for (int i = 0; i < board.getSize(); i++) {
					Var X = Vars[i];
					if (X.getvalue() == -1 && X != r1 && X != r2) {
						q.add(new Pair<>(X, r1));
					}
				} 
			} 
		} 


    
		return true;
	}


  
	private boolean revise(Var r1, Var r2, Map<Integer, ArrayList<Var>> var2ValRecords2) {
		this.calls++;
		boolean revised = false;
		for (int i = 0; i < r1.getDomain().size(); i++) {
			int x = ((Integer)r1.getDomain().get(i)).intValue();
			boolean flag = false;
			for (int j = 0; j < r2.getDomain().size(); j++) {
				int y = ((Integer)r2.getDomain().get(j)).intValue();
        
				if (satisfy(r1.getVarNr(), r2.getVarNr(), x, y)) {
					flag = true;
				}
			} 
			
			if (!flag) {
				r1.getDomain().remove(Integer.valueOf(x));
				if (var2ValRecords2.containsKey(Integer.valueOf(x))) {
					((ArrayList<Var>)var2ValRecords2.get(Integer.valueOf(x))).add(r1);
				} else {
          
					ArrayList<Var> temp2 = new ArrayList<>();
					temp2.add(r1);
					var2ValRecords2.put(Integer.valueOf(x), temp2);
				} 
        
				revised = true;
			} 
		} 

		return revised;
	}

  
	private boolean satisfy(int a, int b, int c, int d) {
		int i = a;
		int j = b;
		int Qi = c;
		int Qj = d;
    
		if (i != j && 
				Qi == Qj) {
			return false;
		}

    
		if (Math.abs(Qi - Qj) == Math.abs(i - j)) {
			return false;
		}
    
		return true;
	}



  
	private Var getnextToAssign(Board board, List<Var> to_assign) {
		Var MRV = to_assign.get(0);
		for (Var v : to_assign) {
			if (v.getDomain().size() < MRV.getDomain().size())
				MRV = v; 
		} 
		return MRV;
	}


  
	private ArrayList<Integer> orderValues(ArrayList<Integer> domain) {
		Comparator<Integer> comp = new Comparator<Integer>()
		{
			public int compare(Integer first, Integer second)
			{
				int x1 = (QueensGraph.this.n - 1 - first.intValue() < first.intValue()) ? (QueensGraph.this.n - 1 - first.intValue()) : first.intValue();
				int x2 = (QueensGraph.this.n - 1 - second.intValue() < second.intValue()) ? (QueensGraph.this.n - 1 - second.intValue()) : second.intValue();
				return Integer.valueOf(x1).compareTo(Integer.valueOf(x2));
			}
		};
    
		ArrayList<Integer> sorted = new ArrayList<>(domain);
		Collections.sort(sorted, comp);
		return sorted;
	}


  
	private boolean testVal(Board board, Var Var, int col, Map<Integer, ArrayList<Var>> var2ValRecords) {
		Var[] Vars = board.getVars();
    
		ArrayList<Var> temp = new ArrayList<>();
		var2ValRecords.put(Integer.valueOf(col), temp);
    
		for (int i = 0; i < board.getSize(); i++) {
			if (Vars[i] != Var && Vars[i].getvalue() == -1 && Vars[i].getDomain().contains(Integer.valueOf(col))) {
				Vars[i].getDomain().remove(Integer.valueOf(col));
				((ArrayList<Var>)var2ValRecords.get(Integer.valueOf(col))).add(Vars[i]);
				if (Vars[i].getDomain().size() == 0) {
					return false;
				}
			} 
		} 
		for (int i = Var.getVarNr() - 1, j = col - 1; i >= 0 && j >= 0; i--, j--) {
			if (Vars[i].getvalue() == -1 && Vars[i].getDomain().contains(Integer.valueOf(j))) {
				Vars[i].getDomain().remove(Integer.valueOf(j));
        
				if (var2ValRecords.containsKey(Integer.valueOf(j))) {
					((ArrayList<Var>)var2ValRecords.get(Integer.valueOf(j))).add(Vars[i]);
				} else {
					ArrayList<Var> temp2 = new ArrayList<>();
					temp2.add(Vars[i]);
					var2ValRecords.put(Integer.valueOf(j), temp2);
				} 
        
				if (Vars[i].getDomain().size() == 0) {
					return false;
				}
			} 
		} 
    
		for (int i = Var.getVarNr() - 1, j = col + 1; i >= 0 && j < board.getSize(); i--, j++) {
			if (Vars[i].getvalue() == -1 && Vars[i].getDomain().contains(Integer.valueOf(j))) {
				Vars[i].getDomain().remove(Integer.valueOf(j));
        
				if (var2ValRecords.containsKey(Integer.valueOf(j))) {
					((ArrayList<Var>)var2ValRecords.get(Integer.valueOf(j))).add(Vars[i]);
				} else {
					ArrayList<Var> temp2 = new ArrayList<>();
					temp2.add(Vars[i]);
					var2ValRecords.put(Integer.valueOf(j), temp2);
				} 
        
				if (Vars[i].getDomain().size() == 0) {
					return false;
				}
			} 
		} 
    
		for (int i = Var.getVarNr() + 1, j = col - 1; i < board.getSize() && j >= 0; i++, j--) {
			if (Vars[i].getvalue() == -1 && Vars[i].getDomain().contains(Integer.valueOf(j))) {
				Vars[i].getDomain().remove(Integer.valueOf(j));
        
				if (var2ValRecords.containsKey(Integer.valueOf(j))) {
					((ArrayList<Var>)var2ValRecords.get(Integer.valueOf(j))).add(Vars[i]);
				} else {
					ArrayList<Var> temp2 = new ArrayList<>();
					temp2.add(Vars[i]);
					var2ValRecords.put(Integer.valueOf(j), temp2);
				} 
        
				if (Vars[i].getDomain().size() == 0) {
					return false;
				}
			} 
		} 
    
		for (int i = Var.getVarNr() + 1, j = col + 1; i < board.getSize() && j < board.getSize(); i++, j++) {
			if (Vars[i].getvalue() == -1 && Vars[i].getDomain().contains(Integer.valueOf(j))) {
				Vars[i].getDomain().remove(Integer.valueOf(j));
        
				if (var2ValRecords.containsKey(Integer.valueOf(j))) {
					((ArrayList<Var>)var2ValRecords.get(Integer.valueOf(j))).add(Vars[i]);
				} else {
					ArrayList<Var> temp2 = new ArrayList<>();
					temp2.add(Vars[i]);
					var2ValRecords.put(Integer.valueOf(j), temp2);
				} 
        
				if (Vars[i].getDomain().size() == 0) {
					return false;
				}
			} 
		} 
		return true;
	}
  
	private void undovar2ValRecords(Map<Integer, ArrayList<Var>> var2ValRecords) {
		for (Map.Entry<Integer, ArrayList<Var>> entry : var2ValRecords.entrySet()) {
			for (Var r : entry.getValue()) {
				if (!r.getDomain().contains(entry.getKey()))
					r.getDomain().add(entry.getKey()); 
			} 
		} 
	}
}


