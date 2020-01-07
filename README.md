# PageReplacement
## 1.說明
* 當使用分頁(Paging)管理記憶體時，如發生分頁錯誤(Page fault)，如何找到最佳替換分頁(Page)，取代新的內容，減少分頁發生錯誤機率為一門重要的研究課題，現今學術上亦已提出多種不同演算，以提升執行效率及降低錯誤率為目標。
* 本程式為實作LRU(Least Recently Used Page Replacement)及OPT(Optimal Page Replacement)兩種分頁替換演算。
* 開發及編譯工具 Eclipse
* 輸入方式:執行程式後，依系統畫面提示輸入相關變數，如下
 <br/>  1.選擇本次執行分頁替換演算:(1) LRU Page Replacement (2) Optimal Page Replacement ==> 如輸入1，選擇LRU演算
 <br/>  2.輸入頁框(Frame)數量 ==> 輸入本次執行Frame數量，如 3，代表產生3個Frame數量
 <br/>  3.輸入分頁(Page)序列(英文單字) ==> 輸入連續存取分頁序列，以英文字母代表，如輸入"abcdrgbbjavhgabc"進行運算
 <br/>  4.完成輸入後，按下Enter，系統產生「上述變數輸入確認」提示文字，隨即開始執行運算，畫面參考結果如下: <br>   
![image](1.png "執行結果")
