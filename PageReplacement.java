package test;

import java.util.*;

class fram {
	int id;
	int next;
	char value;
	String replace;
}

public class PageReplacement {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Scanner sc = new Scanner(System.in);
		System.out.print("請選擇執行分頁替換演算:(1) LRU Page Replacement (2) Optimal Page Replacement ");
		int choose = sc.nextInt();
		System.out.println();
		System.out.print("請輸入頁框(Frame)數量:");
		int NumofFrame = sc.nextInt();
		System.out.println();
		System.out.print("請輸入分頁(Page)序列(英文單字):");
		String Input = sc.next().toLowerCase();
		System.out.println();
		if (choose == 1)
			LRU_Algorithm(NumofFrame, Input);
		else if (choose == 2)
			Optimal_Algorithm(NumofFrame, Input);
		else
			System.out.println("輸入錯誤，請重新執行程式 ! !");

	}

	public static void LRU_Algorithm(int NumofFrm, String input) {
		System.out.println("執行LRU分頁替換演算-->頁框(Frame)數量  " + NumofFrm + " ; 分頁(Page)序列  " + input + "\n");
		int NumofInput = input.length();
		fram frm[] = new fram[NumofFrm];
		for (int i = 0; i < NumofFrm; i++) {
			frm[i] = new fram();
			frm[i].id = i;
			frm[i].value = ' ';
		}

		LinkedList list = new LinkedList();
		for (int i = 0; i < NumofFrm; i++)
			list.add(i);
		System.out.println("設定初始狀態:");
		for (int i = 0; i < NumofFrm; i++)
			System.out.print("\tFrame " + i + "   內 存:\t  " + frm[i].value + "\t" + "|");
		System.out.println();
		for (int i = 0; i < NumofFrm; i++)
			System.out.print("\t\t排 序:\t " + list.indexOf(frm[i].id) + "\t" + "|");
		System.out.println(
				"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		// 本段開始執行分頁替換演算

		for (int i = 0; i < NumofInput; i++) {
			boolean error = true;
			for (int j = 0; j < NumofFrm; j++) {
				if (input.charAt(i) == frm[j].value) {
					int index = list.indexOf(frm[j].id);
					list.remove(index);
					list.addLast(frm[j].id);
					error = false;
					break;
				}

			}

			if (error) {
				frm[(int) list.getFirst()].value = input.charAt(i);
				list.addLast((int) list.getFirst());
				list.remove(0);
			}

			// 每執行完一次，進行狀態列印

			System.out.println("第" + (i + 1) + "運算(page='" + input.charAt(i) + "')");
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\tFrame " + j + "   內 存:\t " + frm[j].value + "\t" + "|");
			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\t\t排 序:\t " + list.indexOf(frm[j].id) + "\t" + "|");
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		}

	}
	
	public static void Optimal_Algorithm(int NumofFrm, String input) {
		System.out.println("執行Optimal分頁替換演算-->頁框(Frame)數量  " + NumofFrm + " ; 分頁(Page)序列  " + input + "\n");
		System.out.println("開始執行:");
		int NumofLength = input.length();

		LinkedList list = new LinkedList();
		for (int i = 0; i < NumofFrm; i++)
			list.add(i);

		fram frm[] = new fram[NumofFrm];
		for (int i = 0; i < NumofFrm; i++) {
			frm[i] = new fram();
			frm[i].id = i;
			frm[i].value = ' ';
			frm[i].next = 0;
			frm[i].replace = " ";
		}

		for (int i = 0; i < NumofLength; i++) {
			System.out.println("\n第" + (i + 1) + "次執行，序列為 "+input+"，目前輸入 " + input.charAt(i));
			int nextVictim = -1;
			boolean pageerror = true;
			for (int j = 0; j < NumofFrm; j++) {
				if (input.charAt(i) == frm[j].value) { // 輸入的值與目前frame的值相同時
					nextVictim = frm[j].id;
					frm[nextVictim].replace = "Victim";
					int index = list.indexOf(frm[j].id);
					list.remove(index);
					list.addLast(frm[j].id);

					input = input.replaceFirst(Character.toString(input.charAt(i)), " ");
					if (input.contains(Character.toString((frm[j].value)))) {
						frm[j].next = input.indexOf(frm[j].value) + 1;
					} else
						frm[j].next = 0;
					pageerror = false;
					break;

				}

			}

			if (pageerror) {
				int isHasNext = -1; 	// 檢查各frame目前的值有沒有下一個參考值，預設值-1
				int whoisBigNext = 0;	//檢查各frame，如果有參考值，誰的參考值最遠
				int count = 0; 			// 計算有幾個frame的參考值為0(即沒有任何參考，可立即列入替換)
				
				int candidateVictim = 9999;
				for (int j = 0; j < NumofFrm; j++) { // 檢查各frame有沒有值
					if (frm[j].value != ' ') {
						break;
					}
				}
				for (int j = 0; j < NumofFrm; j++) { // 檢查isHasNext的值大小
					if (frm[j].next > isHasNext)
						isHasNext = frm[j].next;
					if (frm[j].next == 0)
						count++;
				}
				
				// 判斷下一個victim為哪一個frame，有3個狀況

				if ((isHasNext > 0) && count == 0) { 				// 表示每個frame都有下一個參考值
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next > whoisBigNext) {
							whoisBigNext = frm[j].next;
						nextVictim = frm[j].id;
						}
					}
					frm[nextVictim].replace = "Victim";
				} else if ((isHasNext > 0) && count == 1) { 		// 表示只有一個frame無參考值
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next == 0) {
							nextVictim = frm[j].id;
							break;
						}
					}
					frm[nextVictim].replace = "Victim";
				}

				else if (count > 1) { 								// 表示有二個以上frame無參考值
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next == 0) {
							if (candidateVictim > list.indexOf(frm[j].id))
								candidateVictim = list.indexOf(frm[j].id);
						}
					}

					nextVictim = (int) list.get(candidateVictim);
					frm[nextVictim].replace = "Victim";
				}

				frm[nextVictim].value = input.charAt(i);             //確定vitcim位置後，開始替換演算
				list.remove(list.indexOf(frm[nextVictim].id));		 //並更新各frame暫存資料的時間排序
				list.addLast(frm[nextVictim].id);
				input = input.replaceFirst(Character.toString(input.charAt(i)), " ");
				if (input.contains(Character.toString(frm[nextVictim].value))) {
					frm[nextVictim].next = input.indexOf(frm[nextVictim].value) + 1;
				} else
					frm[nextVictim].next = 0;
			}

			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\t"+frm[j].replace+"\t\t\t|");
			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\tFrame " + frm[j].id + "  內存:\t " + frm[j].value + "\t|");
			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\t下一個參考值位置:   " + frm[j].next + "\t|");
			for (int j = 0; j < NumofFrm; j++)
				frm[j].replace=" ";
			System.out.println();
		}
	}

}
