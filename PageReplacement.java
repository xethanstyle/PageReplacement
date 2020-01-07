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
		System.out.print("�п�ܰ�����������t��:(1) LRU Page Replacement (2) Optimal Page Replacement ");
		int choose = sc.nextInt();
		System.out.println();
		System.out.print("�п�J����(Frame)�ƶq:");
		int NumofFrame = sc.nextInt();
		System.out.println();
		System.out.print("�п�J����(Page)�ǦC(�^���r):");
		String Input = sc.next().toLowerCase();
		System.out.println();
		if (choose == 1)
			LRU_Algorithm(NumofFrame, Input);
		else if (choose == 2)
			Optimal_Algorithm(NumofFrame, Input);
		else
			System.out.println("��J���~�A�Э��s����{�� ! !");

	}

	public static void LRU_Algorithm(int NumofFrm, String input) {
		System.out.println("����LRU���������t��-->����(Frame)�ƶq  " + NumofFrm + " ; ����(Page)�ǦC  " + input + "\n");
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
		System.out.println("�]�w��l���A:");
		for (int i = 0; i < NumofFrm; i++)
			System.out.print("\tFrame " + i + "   �� �s:\t  " + frm[i].value + "\t" + "|");
		System.out.println();
		for (int i = 0; i < NumofFrm; i++)
			System.out.print("\t\t�� ��:\t " + list.indexOf(frm[i].id) + "\t" + "|");
		System.out.println(
				"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");

		// ���q�}�l������������t��

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

			// �C���槹�@���A�i�檬�A�C�L

			System.out.println("��" + (i + 1) + "�B��(page='" + input.charAt(i) + "')");
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\tFrame " + j + "   �� �s:\t " + frm[j].value + "\t" + "|");
			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\t\t�� ��:\t " + list.indexOf(frm[j].id) + "\t" + "|");
			System.out.println(
					"\n-----------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		}

	}
	
	public static void Optimal_Algorithm(int NumofFrm, String input) {
		System.out.println("����Optimal���������t��-->����(Frame)�ƶq  " + NumofFrm + " ; ����(Page)�ǦC  " + input + "\n");
		System.out.println("�}�l����:");
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
			System.out.println("\n��" + (i + 1) + "������A�ǦC�� "+input+"�A�ثe��J " + input.charAt(i));
			int nextVictim = -1;
			boolean pageerror = true;
			for (int j = 0; j < NumofFrm; j++) {
				if (input.charAt(i) == frm[j].value) { // ��J���ȻP�ثeframe���ȬۦP��
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
				int isHasNext = -1; 	// �ˬd�Uframe�ثe���Ȧ��S���U�@�ӰѦҭȡA�w�]��-1
				int whoisBigNext = 0;	//�ˬd�Uframe�A�p�G���ѦҭȡA�֪��Ѧҭȳ̻�
				int count = 0; 			// �p�⦳�X��frame���ѦҭȬ�0(�Y�S������ѦҡA�i�ߧY�C�J����)
				
				int candidateVictim = 9999;
				for (int j = 0; j < NumofFrm; j++) { // �ˬd�Uframe���S����
					if (frm[j].value != ' ') {
						break;
					}
				}
				for (int j = 0; j < NumofFrm; j++) { // �ˬdisHasNext���Ȥj�p
					if (frm[j].next > isHasNext)
						isHasNext = frm[j].next;
					if (frm[j].next == 0)
						count++;
				}
				
				// �P�_�U�@��victim�����@��frame�A��3�Ӫ��p

				if ((isHasNext > 0) && count == 0) { 				// ��ܨC��frame�����U�@�ӰѦҭ�
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next > whoisBigNext) {
							whoisBigNext = frm[j].next;
						nextVictim = frm[j].id;
						}
					}
					frm[nextVictim].replace = "Victim";
				} else if ((isHasNext > 0) && count == 1) { 		// ��ܥu���@��frame�L�Ѧҭ�
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next == 0) {
							nextVictim = frm[j].id;
							break;
						}
					}
					frm[nextVictim].replace = "Victim";
				}

				else if (count > 1) { 								// ��ܦ��G�ӥH�Wframe�L�Ѧҭ�
					for (int j = 0; j < NumofFrm; j++) {
						if (frm[j].next == 0) {
							if (candidateVictim > list.indexOf(frm[j].id))
								candidateVictim = list.indexOf(frm[j].id);
						}
					}

					nextVictim = (int) list.get(candidateVictim);
					frm[nextVictim].replace = "Victim";
				}

				frm[nextVictim].value = input.charAt(i);             //�T�wvitcim��m��A�}�l�����t��
				list.remove(list.indexOf(frm[nextVictim].id));		 //�ç�s�Uframe�Ȧs��ƪ��ɶ��Ƨ�
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
				System.out.print("\tFrame " + frm[j].id + "  ���s:\t " + frm[j].value + "\t|");
			System.out.println();
			for (int j = 0; j < NumofFrm; j++)
				System.out.print("\t�U�@�ӰѦҭȦ�m:   " + frm[j].next + "\t|");
			for (int j = 0; j < NumofFrm; j++)
				frm[j].replace=" ";
			System.out.println();
		}
	}

}
