import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.PriorityQueue;


public class HikingDeer {
	private static class TimeEvent implements Comparable {
		double time;
		int num;
		boolean isStart;
		public TimeEvent(double t, int n, boolean isS) {
			time = t;
			num = n;
			isStart = isS;
		}
		@Override
		public int compareTo(Object o) {
			TimeEvent that = (TimeEvent) o;
			if (this.time == that.time) {
				if (!this.isStart) return -1;
				if (!that.isStart) return 1;
				return 0;
			}
			return Double.compare(this.time, that.time);
		}
		
		public String toString() {
			return "(" + time + "," + num + "," + isStart + ") ";
		}
		
	}	
	public static int minEncounters(ArrayList<Integer> pos, ArrayList<Long> time, int H){
		PriorityQueue<TimeEvent> q = new PriorityQueue<TimeEvent>();
		ArrayList<Double> time_left = new ArrayList<Double>();
		for (int i = 0; i < H; ++i) {
			double tleft = (((360-pos.get(i)) / ((double)360)) * time.get(i));
			time_left.add(tleft);
			q.add(new TimeEvent(0, 1, true));
			q.add(new TimeEvent(time_left.get(i), 1, false));
			q.add(new TimeEvent(time_left.get(i), 0, true));
			q.add(new TimeEvent(time_left.get(i) + time.get(i), 0, false));	
		}
		for (int i = 0; i < H; ++i) {
			int j = 1;
			while (j <= H + 1) {
				q.add(new TimeEvent(time_left.get(i) + time.get(i)*j, j, true));
				q.add(new TimeEvent(time_left.get(i) + time.get(i)*(j+1), j, false));
				j++;
			}
		}
		// for (TimeEvent t : q) System.out.print(t.toString());
		// System.out.println();
		int numEncounters = 0;
		int minNum = H;
		while(!q.isEmpty()) {
			double start_time = q.peek().time;
			while (!q.isEmpty() && q.peek().time == start_time) {
				TimeEvent t= q.poll();
				if(t.isStart) {
					numEncounters += t.num;
					if (t.num > H) return minNum;
				}
				else {
					numEncounters -= t.num;
				}
			}
			System.out.println(start_time + "\t" + numEncounters + "\t");
			if (numEncounters < minNum) minNum = numEncounters;
			
		}
		return minNum;
	}
	public static void solution(String inputFile, String outputFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
		PrintWriter pw = new PrintWriter(new FileWriter(new File(outputFile)));
		String line = br.readLine();
		int N = Integer.parseInt(line);
		int id = 1;
		while ((line = br.readLine()) != null) {
			
			int G = Integer.parseInt(line);
			ArrayList<Integer> pos = new ArrayList<Integer>();
			ArrayList<Long> time = new ArrayList<Long>();
			int H = 0;
			for (int i = 0; i < G; ++i) {
				line = br.readLine();			
				String[] sp = line.split(" ");
				int p = Integer.parseInt(sp[0]);
				int h = Integer.parseInt(sp[1]);
				long start_time = Long.parseLong(sp[2]);
				H += h;
				for (int j = 0; j < h; ++j) {
					pos.add(p);
					time.add(start_time + j);
				}
			}
			int min = minEncounters(pos, time, H) ;
			pw.println("Case #"+id+": "+min);
			id++;
		}
		br.close();
		pw.close();
	}
	public static void main(String[] args) throws IOException {
		String inputFile = "src/HikingDeer.in";
		String outputFile = "src/HikingDeer.out";
		HikingDeer.solution(inputFile, outputFile);
		// NoisyNeighbors.minUnhappiness(3, 3, 8);
		
	}
}
