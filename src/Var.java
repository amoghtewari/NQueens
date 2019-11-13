import java.util.ArrayList;

public class Var
{
	private int Var_nr;
	private int value;
	private ArrayList<Integer> domain;
  
	Var(int nr, ArrayList<Integer> dom) {
		this.Var_nr = nr;
		this.domain = dom;
		this.value = -1;
	}
  
	public int getVarNr() { return this.Var_nr; }

	public int getvalue() { return this.value; }

	public void setvalue(int val) { this.value = val; }

	public void erasevalue() { setvalue(-1); }

	public ArrayList<Integer> getDomain() { return this.domain; }
}
