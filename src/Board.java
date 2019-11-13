
import java.util.ArrayList;

public class Board
{
	private Var[] Vars;
	private int size;
  
	public Board(int size) {
		this.size = size;
		this.Vars = new Var[size];
		initBoard();
	}

	public Var[] getVars() { return this.Vars; }

	public int getSize() { return this.size; }

	private void initBoard() {
		ArrayList<Integer> domain = new ArrayList<>();
		for (int i = 0; i < this.size; i++) {
			domain.add(Integer.valueOf(i));
		}
		for (int i = 0; i < this.size; i++) {
			this.Vars[i] = new Var(i, new ArrayList<>(domain));
		}
	}
  
	public ArrayList<Var> notAssignedVars() {
		ArrayList<Var> notAssigned = new ArrayList<>();
		for (int i = 0; i < this.size; i++) {
      
			if (this.Vars[i].getvalue() == -1) {
				notAssigned.add(this.Vars[i]);
			}
		} 
		return notAssigned;
	}

  
	public String retAssignment() {
		String result = "{";
		for (int i = 0; i < this.size - 1; i++) {
			result = String.valueOf(result) + this.Vars[i].getvalue() + ", ";
		}
		result = String.valueOf(result) + this.Vars[this.size - 1].getvalue() + "}";

		return result;
	}

	public String retBeautifiedAssignment() {
		String result = "";
		for (int i = 0; i < this.size; i++) {
			int x = this.Vars[i].getvalue();
			for (int j = 0; j < this.size; j++) {
				if (j == x) {
					result = String.valueOf(result) + "Q ";
				} else {
					result = String.valueOf(result) + "X ";
				} 
			} 
			result = String.valueOf(result) + System.lineSeparator();
		} 
		return result;
	}
}
