import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class MushroomMonster {
	public static int methodA(int[] arr) {
		int sum = 0;
		for (int i = 1; i < arr.length; i++) 
			if (arr[i] < arr[i-1]) sum += (arr[i-1] - arr[i]);
		return sum;
	}
	
	public static int methodB(int[] arr) {
		int diff = 0;
		for (int i = 1; i < arr.length; ++i)
			if (arr[i-1] - arr[i] > diff) diff = (arr[i-1] - arr[i]);
		System.out.println(diff);
		int sum = 0;
		for (int i = 1; i < arr.length; ++i) {
			int eat = (arr[i-1] < diff? arr[i-1] : diff);
			sum += eat;
		}
		return sum;
		
	}
	public static void solution(String inputFile, String outputFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
		PrintWriter pw = new PrintWriter(new FileWriter(new File(outputFile)));
		String line = br.readLine();
		int N = Integer.parseInt(line);
		int id = 1;
		for (int i = 0; i < N; ++i) {
			int D = Integer.parseInt(br.readLine());
			String[] lineSplit = br.readLine().split(" ");
			int[] arr = new int[D];
			for (int j = 0; j < D; ++j) {
				arr[j] = (Integer.parseInt(lineSplit[j]));
			}
			int sum1 = methodA(arr);
			int sum2 = methodB(arr);
			pw.println("Case #"+id+": "+sum1 + " " + sum2);
			id++;
		}
		br.close();
		pw.close();
	}
	public static void main(String[] args) throws IOException {
		String inputFile = "src/MushroomMonster.in";
		String outputFile = "src/MushroomMonster.out";
		MushroomMonster.solution(inputFile, outputFile);
	}
	
}
