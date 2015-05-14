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


public class Dijkstra {
	HashMap<String, String> map = new HashMap<String, String>();
	
	public Dijkstra() {
		map.put("11", "1");
		map.put("i1","i");
		map.put("j1","j");
		map.put("k1","k");
		
		map.put("1i", "i");
		map.put("ii","-1");
		map.put("ji","-k");
		map.put("ki","j");
		
		map.put("1j", "j");
		map.put("ij","k");
		map.put("jj","-1");
		map.put("kj","-i");
		
		map.put("1k", "k");
		map.put("ik","-j");
		map.put("jk","i");
		map.put("kk","-1");
		
	}
	
	public int reduceChar (String loopArray, int L, char checkChar) { 
		char[] charArray = new char[L];
		boolean[] symbolArray = new boolean[L];
		charArray[0] = loopArray.charAt(0);
		symbolArray[0] = true;
		Integer finalLength = Integer.MAX_VALUE;
		
		StringBuffer checkNext = new StringBuffer("  ");
		
		for (int i = 1; i < L; ++i) { 
			if ( charArray[i-1] == checkChar) {
				if (symbolArray[i-1] == true) return i;
				else finalLength = Integer.min(finalLength, i + 2*L);
			}
			System.out.println(i + "\t" + charArray[i-1] + "\t" + checkNext.toString());
			checkNext.setCharAt(0, charArray[i-1]);
			checkNext.setCharAt(1, loopArray.charAt(i));
			String res = map.get(checkNext.toString());
			if (res.startsWith("-")) {
				symbolArray[i] = !symbolArray[i-1];
				charArray[i] = res.charAt(1);
			}
			else {
				symbolArray[i] = symbolArray[i-1];
				charArray[i] = res.charAt(0);
			}
		}
		char loopChar = charArray[L-1];
		boolean loopSymbol = symbolArray[L-1];
		checkNext = new StringBuffer("  ");
		checkNext.setCharAt(0, loopChar);
		for (int i = 0; i < L; ++i) {
			checkNext.setCharAt(1, charArray[i]);
			String res = map.get(checkNext.toString());
			if (res.startsWith("-") && res.charAt(1) == checkChar) {
				if (loopSymbol)finalLength = Integer.min(finalLength, 3*L + i);
				else finalLength = Integer.min(finalLength, L + i);
			}
			if (res.charAt(0) == checkChar) {
				if (loopSymbol) finalLength = Integer.min(finalLength, i + L);
				else finalLength = Integer.min(finalLength, i + 3 * L);
				
			}
		}
		return finalLength;
		
	}
	
	public char[] finalCharOfArray(String arr, boolean sym) {
		char finalChar = arr.charAt(0);
		boolean finalSymbol = sym;
		StringBuffer checkNext = new StringBuffer("  ");
		for (int i = 1; i < arr.length(); ++i) {
			checkNext.setCharAt(0, finalChar);
			checkNext.setCharAt(1, arr.charAt(i));
			
			String res = map.get(checkNext.toString());
			if (res.startsWith("-")) {
				finalSymbol = !finalSymbol;
				finalChar = res.charAt(1);
			}
			else {
				finalChar = res.charAt(0);
			}
		}
		char[] res = new char[2];
		if (finalSymbol) res[0] = '+';
		else res[0] = '-';
		res[1] = finalChar;
		return res;
		
	}
	
	public boolean isK(String left, String loopArray, int L, long lenLeft) {
		// char checkChar = 'k';
		char[] res = finalCharOfArray(loopArray, true);
		char loopChar = res[1];
		boolean loopSymbol = res[0] == '+';
		char finalChar = '1';
		boolean finalSymbol = true;
		int m = (int) (lenLeft / L) % 4;
		int n = (int) lenLeft % L;
		switch (m) {
			case 0: { finalChar = '1'; finalSymbol = true; break; } 
			case 1: { finalChar = loopChar; finalSymbol = loopSymbol; break; } 
			case 2: { finalChar = '1'; finalSymbol = false; break; } 
			case 3: { finalChar = loopChar ; finalSymbol = !loopSymbol; break; } 
		}
		left = Character.toString(finalChar).concat(left);
		char[] resFinal = finalCharOfArray(left, finalSymbol);
		return (resFinal[1] == 'k' && resFinal[0] == '+');
		
	}
	
	public boolean Check(String loopArray, int L, long C) {
		
		int lenI = reduceChar(loopArray, L, 'i');
		if (lenI == Integer.MAX_VALUE) return false;
		if (lenI >= L * C) return false;
		int n = lenI % L;
		int m = lenI / L; 
		
		String loopForJ = loopArray.substring(n).concat(loopArray.substring(0, n));
		int lenJ = reduceChar(loopForJ, L, 'j');
		if (lenJ == Integer.MAX_VALUE) return false;
		if (lenI + lenJ >= L * C) return false;
		
		System.out.println(L + "\t" + C + "\t" + lenI + "\t" + lenJ);
		
		
		
		n = (lenI + lenJ) % L;
		System.out.println(n + "\t" + (L*C - (lenI + lenJ)));
		if (n == 0) {
			return isK("", loopForJ, L, L*C - (lenI + lenJ));
		}
		else {
			System.out.println(n + "\t" + loopArray);
			String loopForK = loopArray.substring(n).concat(loopArray.substring(0, n));
			return isK(loopArray.substring(n), loopForK, L, L*C - (lenI + lenJ));
			
		}
			
		

	}
	
	
	public boolean reduceChar(StringBuilder sb, char checkChar) {

		boolean isPositive = true;
		while (sb.length() > 0) {
			if (sb.charAt(0) == checkChar && isPositive) {
				sb.delete(0, 1);
				return true;
			}
			if (sb.length() == 1) return false;
			String result = map.get(sb.substring(0, 2));
			sb.delete(0, 2);
			if (result.startsWith("-")) {
				isPositive = !isPositive;
				sb.insert(0,  result.substring(1));
			}
			else {
				sb.insert(0,  result);
			}
		}
		return false;
		
	}
	
	public boolean isK(StringBuilder sb) {
		boolean isPositive = true;
		while (sb.length() > 1) {
			
			String result = map.get(sb.substring(0, 2));
			// System.out.println(sb.substring(0, 2) + "\t" + result);
			sb.delete(0, 2);
			if (result.startsWith("-")) {
				isPositive = !isPositive;
				sb.insert(0,  result.substring(1));
			}
			else {
				sb.insert(0,  result);
			}
		}
		return isPositive && sb.toString().equals("k");
	}
	public boolean Check(StringBuilder sb) {
		if (!reduceChar(sb, 'i')) return false;
		if(!reduceChar(sb, 'j')) return false;
		return isK(sb);
	}
	public void solution(String inputFile, String outputFile) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File(inputFile)));
		PrintWriter pw = new PrintWriter(new FileWriter(new File(outputFile)));
		String line = br.readLine();
		int N = Integer.parseInt(line);
		
		int id = 1;
		for (int i = 0; i < N; ++i) {
			String[] a = br.readLine().split(" ");
			int L = Integer.parseInt(a[0]);
			long C = Long.parseLong(a[1]);
			
			line = br.readLine();
			boolean check = false;
					
			if (L * C < 0) {
				StringBuilder sb = new StringBuilder();
				// System.out.println("check this" + L + "\t" + C);
				// System.out.println("check this line " + line.substring(0,10));
				for (long j = 0; j < C; ++j) {
					sb.append(line);
				}
				check = Check(sb);
			}
			else {
				check = Check(line,  L, C);
			}
			
			// System.out.println("check this line size" + sb.length());
			if (check) 
				pw.println("Case #"+id+": "+"YES");
			else
				pw.println("Case #"+id+": "+"NO");
			id++;
		}
		br.close();
		pw.close();
	}
	
	public static void main(String[] args) throws IOException {

		String inputFile = "src/Dijkstra.in";
		String outputFile = "src/Dijkstra.out";
		Dijkstra d = new Dijkstra();
		d.solution(inputFile, outputFile);

	}
}
