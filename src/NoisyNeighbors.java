import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.stream.IntStream;


public class NoisyNeighbors{
	
	public static class State  implements Comparable{
		int state;
		int N;
		int unhappiness;
		int[] potential_unhappiness;
		public State(int s, int n, int un, int[] po) {
			state = s;
			N = n;
			unhappiness = un;
			potential_unhappiness = po;
		}
		@Override
		public int compareTo(Object arg0) {
			State that = (State) arg0;
			if (this.unhappiness != that.unhappiness) return Integer.compare(this.unhappiness, that.unhappiness);
			if (this.N != that.N) return Integer.compare(this.N, that.N);
			return Integer.compare(IntStream.of(this.potential_unhappiness).sum(),IntStream.of(that.potential_unhappiness).sum() );
		}
	}
	public static void addOne(State curr,  PriorityQueue<State> al, int R, int C, HashSet<Integer> existing_states) {
		
		int curr_state = curr.state;
		for (int i = 0; i < R*C; ++i) {
			if ((curr_state & (1L << i)) != 0) {
				continue;
			}
			else {
				int next_state = curr_state | (1 << i);
				if (existing_states.contains(next_state)) continue;
				int unhapp = curr.unhappiness + curr.potential_unhappiness[i];
				int[] po_unhapp = new int[R*C];
				po_unhapp= Arrays.copyOf(curr.potential_unhappiness, R*C);
				if (i >= C) po_unhapp[i-C] += 1;
				if (i < (R-1)*C) po_unhapp[i+C] += 1;
				if (i % C != 0) po_unhapp[i-1] += 1;
				if (i% C != (C-1)) po_unhapp[i+1] += 1;
				NoisyNeighbors.State next = new NoisyNeighbors.State (next_state, curr.N+1, unhapp, po_unhapp);
				
				existing_states.add(next_state);
				al.add(next);
		        
		        
			}
		}
	}
	public static int minUnhappiness(int R, int C, int N) {
		System.out.println(R + "\t" + C + "\t" + N);
		PriorityQueue<State> al = new PriorityQueue<State>();
		int[] po = new int[R*C];
		NoisyNeighbors.State start = new  NoisyNeighbors.State(0, 0, 0, po);
		HashSet<Integer> existing_states = new HashSet<Integer>();
		existing_states .add(0);
		al.add(start);
		while (true) {
			if (al.isEmpty()) break;
			State curr = al.poll();
			if (curr.N == N) {
				return curr.unhappiness;
			}
			addOne(curr, al, R, C, existing_states);
		}
		return -1;
	}
	
	public static void solution(String inputFile, String outputFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
		PrintWriter pw = new PrintWriter(new FileWriter(new File(outputFile)));
		String line = br.readLine();
		int N = Integer.parseInt(line);
		int id = 1;
		while ((line = br.readLine()) != null) {
			String[] sp = line.split(" ");
			int min = minUnhappiness(Integer.parseInt(sp[0]),Integer.parseInt(sp[1]),Integer.parseInt(sp[2]));
			pw.println("Case #"+id+": "+min);
			id++;
		}
		br.close();
		pw.close();
	}
	
	public static void main(String[] args) throws IOException {
		String inputFile = "src/NoisyNeighbors.in";
		String outputFile = "src/NoisyNeighbors.out";
		NoisyNeighbors.solution(inputFile, outputFile);
		// NoisyNeighbors.minUnhappiness(3, 3, 8);
		
	}
}
