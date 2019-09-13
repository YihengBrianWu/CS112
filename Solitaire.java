package solitaire;

import java.io.IOException;
import java.util.Scanner;
import java.util.Random;

/**
 * This class implements a simplified version of Bruce Schneier's Solitaire Encryption algorithm.
 * 
 * @author RU NB CS112
 */
public class Solitaire {
	
	/**
	 * Circular linked list that is the deck of cards for encryption
	 */
	CardNode deckRear;
	
	/**
	 * Makes a shuffled deck of cards for encryption. The deck is stored in a circular
	 * linked list, whose last node is pointed to by the field deckRear
	 */
	public void makeDeck() {
		// start with an array of 1..28 for easy shuffling
		int[] cardValues = new int[28];
		// assign values from 1 to 28
		for (int i=0; i < cardValues.length; i++) {
			cardValues[i] = i+1;
		}
		
		// shuffle the cards
		Random randgen = new Random();
 	        for (int i = 0; i < cardValues.length; i++) {
	            int other = randgen.nextInt(28);
	            int temp = cardValues[i];
	            cardValues[i] = cardValues[other];
	            cardValues[other] = temp;
	        }
	     
	    // create a circular linked list from this deck and make deckRear point to its last node
	    CardNode cn = new CardNode();
	    cn.cardValue = cardValues[0];
	    cn.next = cn;
	    deckRear = cn;
	    for (int i=1; i < cardValues.length; i++) {
	    	cn = new CardNode();
	    	cn.cardValue = cardValues[i];
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
	    }
	}
	
	/**
	 * Makes a circular linked list deck out of values read from scanner.
	 */
	public void makeDeck(Scanner scanner) 
	throws IOException {
		CardNode cn = null;
		if (scanner.hasNextInt()) {
			cn = new CardNode();
		    cn.cardValue = scanner.nextInt();
		    cn.next = cn;
		    deckRear = cn;
		}
		while (scanner.hasNextInt()) {
			cn = new CardNode();
	    	cn.cardValue = scanner.nextInt();
	    	cn.next = deckRear.next;
	    	deckRear.next = cn;
	    	deckRear = cn;
		}
	}
	
	/**
	 * Implements Step 1 - Joker A - on the deck.
	 */
	void jokerA() {
		// COMPLETE THIS METHOD
		CardNode prev = null;
		CardNode n = deckRear;
		if(deckRear.cardValue == 27){
			CardNode after = deckRear.next;
			while(n.next.cardValue!=27){
				n=n.next;
			}
			n.next = after;
			deckRear.next = after.next;
			after.next = deckRear;
			deckRear = after;
		}else{
			while(n.cardValue!=27){
				prev = n;
				n=n.next;
			}
			CardNode after = n.next;
			if(after.cardValue == deckRear.cardValue){
				prev.next = after;
				n.next = after.next;
				after.next = n;
				deckRear = n;
			}else{
			prev.next = after;
			n.next = after.next;
			after.next = n;
			}
		}
	}
	
	/**
	 * Implements Step 2 - Joker B - on the deck.
	 */
	void jokerB() {
	    // COMPLETE THIS METHOD
		CardNode n =deckRear;
		CardNode prev = null;
		if(deckRear.cardValue == 28){
			CardNode after = deckRear.next;
			while(n.next.cardValue!=28){
				n = n.next;
			}
			n.next = after;
			deckRear.next = after.next.next;
			after.next.next = deckRear;
			deckRear = after;
		}else{
			while(n.cardValue!=28){
				prev = n;
				n = n.next;
			}
			CardNode after = n.next;
			if(after.cardValue == deckRear.cardValue){
				prev.next = after;
				n.next = after.next.next;
				after.next.next = n;
				deckRear = after.next;
			}else if(after.next.cardValue == deckRear.cardValue){
				prev.next = after;
				n.next = after.next.next;
				after.next.next = n;
				deckRear = n;
			}else{
			prev.next = after;
			n.next = after.next.next;
			after.next.next = n;
			}
		}
	}
	
	/**
	 * Implements Step 3 - Triple Cut - on the deck.
	 */
	void tripleCut() {
		// COMPLETE THIS METHOD
		CardNode n = deckRear;
		CardNode prev = null;
		CardNode head = deckRear.next;
		CardNode tmp =null;
		if(n.cardValue == 27){
			while(n.next.cardValue !=28){
				n= n.next;
			}
			deckRear = n;
		}else if(n.cardValue == 28){
			while(n.next.cardValue !=27){
				n= n.next;
			}
			deckRear = n;
		}else if(head.cardValue == 27){
			while(head.cardValue!=28){
				head = head.next;
			}
			deckRear = head;
		}else if(head.cardValue == 28){
			while(head.cardValue!=27){
				head = head.next;
			}
			deckRear = head;
		}else{
			while(head.cardValue!=27 && head.cardValue!=28){
				prev = head;
				head = head.next;
			}
			tmp = head;
			if(head.cardValue == 27){
				while(tmp.cardValue!=28){
					tmp = tmp.next;
				}
				CardNode a = tmp.next;
				tmp.next = n.next;
				prev.next = a;
				n.next = head;
				deckRear = prev;
			}else{
				while(tmp.cardValue!=27){
					tmp = tmp.next;
				}
				CardNode a = tmp.next;
				tmp.next = n.next;
				prev.next = a;
				n.next = head;
				deckRear = prev;
			}
		}
	}
	
	/**
	 * Implements Step 4 - Count Cut - on the deck.
	 */
	void countCut() {		
		// COMPLETE THIS METHOD
		int x = deckRear.cardValue;
		int n = 1;
		CardNode head = deckRear.next;
		CardNode tail = deckRear.next;
		CardNode prev =deckRear;
		if(x==28){
			x=27;
		}
		while(prev.next != deckRear){
			prev = prev.next;
		}
		while(n!=x){
			tail = tail.next;
			n++;
		}
		prev.next = head;
		deckRear.next = tail.next;
		tail.next = deckRear;
	}
	
	/**
	 * Gets a key. Calls the four steps - Joker A, Joker B, Triple Cut, Count Cut, then
	 * counts down based on the value of the first card and extracts the next card value 
	 * as key. But if that value is 27 or 28, repeats the whole process (Joker A through Count Cut)
	 * on the latest (current) deck, until a value less than or equal to 26 is found, which is then returned.
	 * 
	 * @return Key between 1 and 26
	 */
	int getKey() {
		// COMPLETE THIS METHOD
		// THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		jokerA();
		jokerB();
		tripleCut();
		countCut();
		CardNode head = deckRear.next;
		CardNode n = deckRear.next;
		int x = head.cardValue;
		int y = 1;
		if(x==28){
			x=27;
		}
		while(y!=x){
			n = n.next;
			y++;
		}
		//while(n.next.cardValue == 27 || n.next.cardValue == 28){
			//jokerA();
			//jokerB();
			//tripleCut();
			//countCut();
		//}
		if(n.next.cardValue != 27 && n.next.cardValue != 28){
			return n.next.cardValue;
		}else{
			return getKey();
		}
	}
	
	/**
	 * Utility method that prints a circular linked list, given its rear pointer
	 * 
	 * @param rear Rear pointer
	 */
	private static void printList(CardNode rear) {
		if (rear == null) { 
			return;
		}
		System.out.print(rear.next.cardValue);
		CardNode ptr = rear.next;
		do {
			ptr = ptr.next;
			System.out.print("," + ptr.cardValue);
		} while (ptr != rear);
		System.out.println("\n");
	}

	/**
	 * Encrypts a message, ignores all characters except upper case letters
	 * 
	 * @param message Message to be encrypted
	 * @return Encrypted message, a sequence of upper case letters only
	 */
	public String encrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		String result = "";
		for(int i = 0; i<message.length(); i++){
			if(Character.isLetter(message.charAt(i))&&Character.isUpperCase(message.charAt(i))){
				char a = message.charAt(i);
				int c = a-'A'+1;
				int b = getKey();
				int x = c+b;
				if(x>26){
					x = x-26;
				}
				a = (char)(x-1+'A');
				result = result + a;
			}else{
				continue;
			}
		}
	    return result;
	}
	
	/**
	 * Decrypts a message, which consists of upper case letters only
	 * 
	 * @param message Message to be decrypted
	 * @return Decrypted message, a sequence of upper case letters only
	 */
	public String decrypt(String message) {	
		// COMPLETE THIS METHOD
	    // THE FOLLOWING LINE HAS BEEN ADDED TO MAKE THE METHOD COMPILE
		String result = "";
		for(int i = 0; i<message.length(); i++){
			if(Character.isLetter(message.charAt(i))){
				char a = message.charAt(i);
				int c = a-'A'+1;
				int b = getKey();
				int x = c-b;
				if(x<=0){
					x = x+26;
				}
				a = (char)(x-1+'A');
				result = result + a;
			}else{
				continue;
			}
		}
	    return result;
	}
}
