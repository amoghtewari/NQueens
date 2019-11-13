
public class NQueens{
	public static void main(String[] args) {
		int n = Integer.parseInt(args[1]);
		QueensGraph n_queens_problem = new QueensGraph(n);
		if (args[0].equalsIgnoreCase("FOR")) {
			long time_start_forw = System.currentTimeMillis();
   
			n_queens_problem.FOR(args[3]);
   
			long time_end_forw = System.currentTimeMillis();
			
			n_queens_problem.writeCFile(args[2]);
			
			System.out.println("Time to solve in ms: " + (time_end_forw - time_start_forw));
			System.out.println("Total Backtracking steps:" + n_queens_problem.getNumBacktracks());
			System.out.println("Total Solutions: " + n_queens_problem.getSolutionsNumber());
			} 
 
		if (args[0].equalsIgnoreCase("MAC")) {
			long time_start_forw = System.currentTimeMillis();
			
			n_queens_problem.MAC(args[3]);
			
			long time_end_forw = System.currentTimeMillis();
			
			n_queens_problem.writeCFile(args[2]);
			System.out.println("Time to solve in ms: " + (time_end_forw - time_start_forw));
			System.out.println("AC3 Calls" + n_queens_problem.calls);
			
			System.out.println("Total Backtracking steps:" + n_queens_problem.getNumBacktracks());
			System.out.println("Total Solutions: " + n_queens_problem.getSolutionsNumber());
			} 
		}
	}