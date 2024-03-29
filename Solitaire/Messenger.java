package solitaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Messenger {
	public static void main(String[] args) 
	throws IOException {
		
		Solitaire ss = new Solitaire();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Enter deck file name => ");
		
		Scanner sc = new Scanner(new File(br.readLine()));
		ss.makeDeck(sc);
		//Solitaire.printList(ss.deckRear);
		//ss.jokerA();
		//Solitaire.printList(ss.deckRear);
		//ss.jokerB();
		//Solitaire.printList(ss.deckRear);
		//ss.tripleCut();
		//Solitaire.printList(ss.deckRear);
		//ss.countCut();
		//Solitaire.printList(ss.deckRear);
		//ss.getKey();
		//System.out.println(ss.getKey());
		System.out.print("Encrypt or decrypt? (e/d), press return to quit => ");
		String inp = br.readLine();
		if (inp.length() == 0) {
			System.exit(0);
		}
		char ed = inp.charAt(0);
		System.out.print("Enter message => ");
		String message = br.readLine();
		if (ed == 'e') {
			System.out.println("Encrypted message: " + ss.encrypt(message));
		} else {
			System.out.println("Decrypted message: " + ss.decrypt(message));
		}
	}
}
