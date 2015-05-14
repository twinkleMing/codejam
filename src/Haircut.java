import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.Arrays;


public class Haircut {
	public class  Heap{
		public int N;
		public int[] index;
		public int[] time;
		public int[] current_time;
		
		public boolean isEmpty() {
			int min_barber = index[1];
			int use_time = current_time[min_barber];
			for (int i = 1; i <= N; ++i) {
				if (current_time[i] != use_time) return false;
			}
			return true;
		}
		
		public Heap(int N, int[] timep) {
			this.N = N;
			this.time = new int[N+1];
			this.current_time = new int[N+1];
			this.index = new int[N+1];
			for (int i =1; i < N+1; ++i) {
				this.time[i] = timep[i-1];
				this.current_time[i] = 0;
				this.index[i] = i;
				
			}
			
			
		}
		public void heapDown(int k) {
			int barber = this.index[k];
			int barber_time = current_time[barber];
			int child;
			for (; 2*k <= N; k = child) {
				child = 2*k;
				if (child != N ) {
					if (current_time[index[child]] == current_time[index[child+1]] && index[child+1] < index[child]) child++;
					else if (current_time[index[child]] > current_time[index[child+1]]) child++;
				}
					
				if (barber_time > current_time[index[child]] || (barber_time == current_time[index[child]] && index[k] > index[child])) {
					int tmp = index[k];
					index[k] = index[child];
					index[child] = tmp;					
				}
				
				else break;
			}
			index[k] = barber;
			
		}
		public int min() {
			return index[1];
		}
		
		public int deleteMin() {

			int min_barber = index[1];
			int use_time = current_time[min_barber];
			for (int i = 1; i <= N; ++i) {
				current_time[i] -= use_time;
			}
			index[1] = min_barber;
			current_time[min_barber] = time[min_barber];
			// System.out.println("===================");
			// System.out.println(Arrays.toString(current_time));
			// System.out.println(Arrays.toString(index));
			// System.out.println(min_barber);
			heapDown(1);
			// System.out.println(Arrays.toString(current_time));
			// System.out.println(Arrays.toString(index));
			// System.out.println(min_barber);
			return min_barber;
			
		}
	}
	 public static long gcdOf(long m, long n) {
	        long r;
	        
	        while(n != 0) { 
	            r = m % n; 
	            m = n; 
	            n = r; 
	        }
	        
	        return m;
	    }
	    
	    public static long lcmOf(long m, long n) {
	        return (long) ((((long) m) * ((long) n)) / gcdOf(m, n));
	    }
	    
	    
	public long circle(int[] arr) {	
		long p = lcmOf(arr[0], arr[1]);
		for (int i = 2; i < arr.length; ++i) p = lcmOf(p, (long)arr[i]);
		long sum = 0;
		for (int i = 0; i < arr.length; ++i) {
			sum += p/arr[i];
		}
		System.out.println(p + "\t" + sum);
		return sum;
	}
	public int haircut(int[] arr, int B, long N) {
		Heap barbers = new Heap(B, arr);
		int barber = 1;
		for (int i = 1; i <= N; ++i) {
			barber = barbers.deleteMin();
		}
		return barber;
	}

	public static void solution(String inputFile, String outputFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
		PrintWriter pw = new PrintWriter(new FileWriter(new File(outputFile)));
		String line = br.readLine();
		int N = Integer.parseInt(line);
		int id = 1;
		for (int i = 0; i < N; ++i) {
			
			String[] lineSplit = br.readLine().split(" ");
			int B = Integer.parseInt(lineSplit[0]);
			long n = Long.parseLong(lineSplit[1]);
			int[] arr = new int[B];
			String[] lineSplit2 = br.readLine().split(" ");
			for (int j = 0; j < B; ++j) {
				arr[j] = (Integer.parseInt(lineSplit2[j]));
			}
			System.out.println(id + "\t" + B + "\t" + n);
			Haircut h= new Haircut();
			int ans = 1;
			if (n < 1000) {
				ans = h.haircut(arr, B, n);
				
			}
			else {
				long c = h.circle(arr);
				if (n%c == 0) ans = h.haircut(arr, B, c);
				
				else ans = h.haircut(arr, B, n%c);
			}
			pw.println("Case #"+id+": "+ans);
			id++;
		}
		br.close();
		pw.close();
	}
	
	
	public static void main(String[] args) throws IOException {


		String inputFile = "src/Haircut.in";
		String outputFile = "src/Haircut.out";
		Haircut.solution(inputFile, outputFile);
/*
		Haircut h = new Haircut();

		int [] arr = {1, 7, 14, 4, 5};
		

		int ans = h.haircut(arr, 5, 30);

		System.out.println(ans);
*/

	}
}
