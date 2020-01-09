# PageReplacement
## 1. 說明
* 當使用分頁(Paging)管理記憶體時，如發生分頁錯誤(Page fault)，如何找到最佳替換分頁(Page)，取代新的內容，減少分頁發生錯誤機率為一門重要的研究課題，現今學術上亦已提出多種不同演算，以提升執行效率及降低錯誤率為目標。
* 本程式為實作LRU(Least Recently Used Page Replacement)及OPT(Optimal Page Replacement)兩種分頁替換演算。
* 使用語言 Java(JRE JavaSE-12)
* 開發及編譯工具 Eclipse
* 輸入方式:執行程式後，依系統畫面提示輸入相關變數，如下
 <br/>  1.選擇本次執行分頁替換演算:(1) LRU Page Replacement (2) Optimal Page Replacement ==> 如輸入1，選擇LRU演算
 <br/>  2.輸入頁框(Frame)數量 ==> 輸入本次執行Frame數量，如 3，代表產生3個Frame數量
 <br/>  3.輸入分頁(Page)序列(英文單字) ==> 輸入連續存取分頁序列，以英文字母代表，如輸入"abcdrgbbjavhgabc"進行運算
 <br/>  4.完成輸入後，按下Enter，系統產生「上述變數輸入確認」提示文字，隨即開始執行運算，畫面參考結果如下: <br>   
![image](1.png "執行結果")
## 2. 執行結果
* LRU Page Replacement : 依上例，設定3個 Frame，輸入16個分頁序列 (abcdrgbbjavhgabc)，執行結果如下<br>  
![image](2.png "LRU運算結果_1")
![image](3.png "LRU運算結果_2")<br>
* OPT Page Replacement : 依上例，設定3個 Frame，輸入16個分頁序列 (abcdrgbbjavhgabc)，執行結果如下<br>  
![image](4.png "OPT運算結果_1")
![image](5.png "OPT運算結果_2")
![image](6.png "OPT運算結果_3")
## 3. 程式說明
 * 程式5~10行:設定一個Fram的類別，代表Frame，內含識別號(id)、下一個參考值位置(next)、內存(value)及是否為victim(replace)。
  <pre><code>class fram {
	int id;
	int next;
	char value;
	String replace;
}</code></pre>
 * 程式12~34行:為主程式，負責設定使用者提供之初始變數，如選擇替換演算法、頁框數量等，完成後將上述變數，如果選擇LRU演算，執行程式28行，如果選擇OPT演算，執行程式30行，否則跳出 "輸入錯誤，請重新執行程式 ! !" 提示。
<pre><code>public class PageReplacement {
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
	}</code></pre>
 * ## Method I : LRU_Algorithm (36~91行) LRU演算
<pre><code>public static void LRU_Algorithm(int NumofFrm, String input)</code></pre>
(38~44行):依使用者設定frame數量，初始化frame陣列
 <pre><code>int NumofInput = input.length();
		fram frm[] = new fram[NumofFrm];
		for (int i = 0; i < NumofFrm; i++) {
			frm[i] = new fram();
			frm[i].id = i;
			frm[i].value = ' ';
		}</code></pre>
(46~49行):將frame的ID，依Linkedlist的方式排列，保持最進換值的frame ID會在最後一個節點
<pre><code>LinkedList list = new LinkedList();
		for (int i = 0; i < NumofFrm; i++)
			list.add(i);</code></pre>
(60~71行):檢查目前輸入的序列的值，使否已存在任一frame中，如果是將此frame調至Linkedlist的最後一個節點，表示最近最新剛使用過
<pre><code>for (int i = 0; i < NumofInput; i++) {
			boolean error = true;
			for (int j = 0; j < NumofFrm; j++) {
				if (input.charAt(i) == frm[j].value) {
					int index = list.indexOf(frm[j].id);
					list.remove(index);
					list.addLast(frm[j].id);
					error = false;
					break;
				}
			}</code></pre>
(73~77行):表示目前輸入的序列的值，不存在任一frame中，目前Linkedlist狀態的第一個節點，即最久未使用過的frame，將此內存進行替換，完成後，再將此frame調至Linkedlist狀態的最後一個節點，表示剛替換過，為最近最新使用
<pre><code>if (error) {
				frm[(int) list.getFirst()].value = input.charAt(i);
				list.addLast((int) list.getFirst());
				list.remove(0);
			}</code></pre>
(81~89行):將運算結果進行列印
	<pre><code>System.out.println("第" + (i + 1) + "運算(page='" + input.charAt(i) + "')");
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\tFrame " + j + "   內 存:\t " + frm[j].value + "\t" + "|");
			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\t\t排 序:\t " + list.indexOf(frm[j].id) + "\t" + "|");
			System.out.println(
					"\n-------------------------------------------------------");
		}</code></pre>
 * ## Method II : Optimal_Algorithm (93~208行) OPT演算
 <pre><code>public static void Optimal_Algorithm(int NumofFrm, String input)</code></pre>
 1.  (98~109行):依使用者設定之frame數量，進行初始化
<pre><code>LinkedList list = new LinkedList();
		for (int i = 0; i < NumofFrm; i++)
			list.add(i);

		fram frm[] = new fram[NumofFrm];
		for (int i = 0; i < NumofFrm; i++) {
			frm[i] = new fram();
			frm[i].id = i;
			frm[i].value = ' ';
			frm[i].next = 0;
			frm[i].replace = " ";
		}</code></pre>
 (111~133行):依分頁序列輸入順序，檢查任一frame是否存在相同內存，如果有，該frame為vitcim，分頁錯誤為flase，並計算未來分頁序列是否有下一個同樣的值，如果有寫入該frame的next，如果沒有，該frame的next為0(即未來沒有參考值)
<pre><code>for (int i = 0; i < NumofLength; i++) {
			System.out.println("\n第" + (i + 1) + "次執行，序列為 " + input + "，目前輸入 " + input.charAt(i));
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
			}</code></pre>
(135~151行):如果沒有任一frame內存與目前輸入分頁序列的值相同，即發生分頁錯誤(pageerror為true)，開始檢查各frame是否已有內存或是完全沒有，如果已有內存，計算各frame的下個參考值的距離，最遠的即為victim
<pre><code>if (pageerror) {
				int isHasNext = -1; // 檢查各frame目前的值有沒有下一個參考值，預設值-1
				int whoisBigNext = 0; // 檢查各frame，如果有參考值，誰的參考值最遠
				int count = 0; // 計算有幾個frame的參考值為0(即沒有任何參考，可立即列入替換)

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
				}</code></pre>
(155~183行):依上述取得的資料，計算下一個vitcim會是哪一個frame，有3種可能需判斷
1.  每個frame都有下一個參考值:victim由每個frame的參考值距離來決定，最遠即victim
2.  只有一個frame無下一個參考值:該frame即為victim
3.  有兩個以上frame無參考值:frame存在最久的即為victim，由Linkedlist狀態決定
<pre><code>if ((isHasNext > 0) && count == 0) { // 表示每個frame都有下一個參考值
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next > whoisBigNext) {
							whoisBigNext = frm[j].next;
							nextVictim = frm[j].id;
						}
					}
					frm[nextVictim].replace = "Victim";
				} else if ((isHasNext > 0) && count == 1) { // 表示只有一個frame無參考值
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next == 0) {
							nextVictim = frm[j].id;
							break;
						}
					}
					frm[nextVictim].replace = "Victim";
				}
				else if (count > 1) { // 表示有二個以上frame無參考值
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next == 0) {
							if (candidateVictim > list.indexOf(frm[j].id))
								candidateVictim = list.indexOf(frm[j].id);
						}
					}
					nextVictim = (int) list.get(candidateVictim);
					frm[nextVictim].replace = "Victim";
				}</code></pre>
(185~193行):確定vitcim位置後，開始替換演算，並更新各frame暫存資料的時間排序，及是否存在下一個參考值
<pre><code>frm[nextVictim].value = input.charAt(i); // 確定vitcim位置後，開始替換演算
				list.remove(list.indexOf(frm[nextVictim].id)); // 並更新各frame暫存資料的時間排序
				list.addLast(frm[nextVictim].id);
				input = input.replaceFirst(Character.toString(input.charAt(i)), " ");
				if (input.contains(Character.toString(frm[nextVictim].value))) {
					frm[nextVictim].next = input.indexOf(frm[nextVictim].value) + 1;
				} else
					frm[nextVictim].next = 0;</code></pre>
(195~206行):完成替換演算後，進行列印
		<pre><code>System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\t" + frm[j].replace + "\t\t\t|");
			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\tFrame " + frm[j].id + "  內存:\t " + frm[j].value + "\t|");
			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\t下一個參考值位置:   " + frm[j].next + "\t|");
			for (int j = 0; j < NumofFrm; j++)
				frm[j].replace = " ";
			System.out.println();</code></pre>
