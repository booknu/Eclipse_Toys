package 백트래킹2_줄다리기문제;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.StringTokenizer;

/************
 * <주소>		: 
 * **********
 * <해결방안>	: 
 * 
 * **********
 * <오답노트>	: 
 * 
 * 1 3 1 2 3
 * 1 3 1 1 2
 * 1 3 1 2 1
 * 과 같이 몸무게의 합이 같은게 있는 경우 오류 발생
 * 
 * 1 3 2 1 1 
 * 1 3 3 1 1
 * 오류 발생
 * 
 * 1 7   3 2 1 1 1 1 1
 * 넣으면 w=5일 때 n=4인 경우를 포함하지 않음
 * 
 * ★★★★★
 * 1 15   1 1 1 1 1 1 1 1 1 1 2 3 4 5 6
 * 넣으면 답: (2 3 5 1(5개), 4 6 1(5개)) = 15, 15가 나와야하는데 오류
 * 
 * **********
 * @author LENOVO
 *
 */
public class __DP사용시도 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FastScanner sc = new FastScanner();
		int T = sc.nextInt();
		for (int testCase = 0; testCase < T; testCase++) {
			int N = sc.nextInt();
			int half = N / 2;
			int[] W = new int[N + 1];
			int sumW = 0;
			for(int i = 1; i <= N; i++) {
				W[i] = sc.nextInt();
				sumW += W[i];
			}
			
			int[][] dp = new int[N + 1][sumW + 1];  // i+1명 선택해서 j무게를 만들 때
												// 마지막으로 선택한 사람 번호 (순서대로 선택함) (1~N)
			
			for(int i = 1; i <= N; i++) {
				// 이미 이전 번호에서 같은 무게에 대해 계산 되었을 때
				// 이전 번호를 유지
				if(dp[1][W[i]] == 0) {
					dp[1][W[i]] = i;
				}
			}
			for(int i = 2; i <= half; i++) {
				for(int j = 0; j <= sumW; j++) {
					int last = dp[i-1][j];
					// 이전 i-1명으로 만든 무게 j가 있을 때
					if(last != 0) {
						// j에 그 후의 무게를 더해봄
						for(int k = last + 1; k <= N; k++) {
							int next = j + W[k];
							if(next <= sumW) {
								// dp[i][w]가 이미 계산 되었으면, 
								// 현재 번호가 더 작으면 갱신 (현재 번호가 더 크면 무시)
								// dp[i][w]가 계산 안 됐으면 k로 갱신
								if(dp[i][next] == 0 || dp[i][next] > k) {
									dp[i][next] = k;
								}
							}
						}
					}
				}
			}
			
			// 원본 코드
//			ArrayList<Integer> list = new ArrayList<Integer>();
//			for(int i = 0; i < sumW; i++) {
//				if(dp[half][i] != 0) {
//					list.add(i);
//				}
//			}
//			
//			int minSub = Integer.MAX_VALUE;
//			int minA = 0, minB = 0;
//			for(int i = 0; i < list.size(); i++) {
//				int a = list.get(i);
//				int b = sumW - a;
//				int sub = Math.abs(a - b);
//				if(sub < minSub) {
//					minA = a;
//					minB = b;
//					minSub = sub;
//				}
//			}
			
			int minSub = Integer.MAX_VALUE;
			int minA = 0, minB = 0;
			for(int i = 0; i < sumW; i++) {
				if(dp[half][i] != 0) {
					int a = i;
					int b = sumW - a;
					int sub = Math.abs(a - b);
					if(sub < minSub) {
						minA = a;
						minB = b;
						minSub = sub;
					}
				}
			}
			
			if(minA > minB) {
				int temp = minA;
				minA = minB;
				minB = temp;
			}
			System.out.println(minA + " " + minB);
		}
	}
	
	static class Pair{
		int w, n; // 무게, 사람 수
		public Pair(int w, int n) {
			this.w = w;
			this.n = n;
		}
	}
	
	static class FastScanner {
		BufferedReader br;
		StringTokenizer st;

		public FastScanner(String s) {
			try {
				br = new BufferedReader(new FileReader(s));
			} catch (FileNotFoundException e) {
				throw new RuntimeException(e);
			}
		}

		public FastScanner() {
			br = new BufferedReader(new InputStreamReader(System.in));
		}

		String nextToken() {
			while (st == null || !st.hasMoreElements()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
			return st.nextToken();
		}

		int nextInt() {
			return Integer.parseInt(nextToken());
		}

		long nextLong() {
			return Long.parseLong(nextToken());
		}

		double nextDouble() {
			return Double.parseDouble(nextToken());
		}

		String nextLine() {
			String str = "";
			try {
				str = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
