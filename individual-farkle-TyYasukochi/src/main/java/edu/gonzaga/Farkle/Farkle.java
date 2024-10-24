/**
 *  Zag Farkle - Gonzaga Farkle game
 *  CPSC 224 Class Project
 * 
 *  Author:
 *  Copyright year:
 * 
 *  Summary:
 * 
 */

// Java packaging - specifies directory and Java project namespace
package edu.gonzaga.Farkle;

import java.util.Scanner;

import javax.lang.model.type.NullType;

import java.util.ArrayList;
import java.util.Collections;

/*
 *  This is the main class for the Farkle project.
 *  It really should just instantiate another class and run
 *   a method of that other class.
 */

/** Main program class for launching Farkle program. */
public class Farkle {
    // This main is where your Farkle game starts execution for general use.
    private static int savedscore = 0;
    public static void main(String[] args) {
        ArrayList<Integer> currenthand = hand();
        
        ArrayList<Integer> meld = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            meld.add(-1);
        }



        //default menu  
        System.out.println("\n*****************************************************************************\n" + 
                           "\n*                      Zag Farkle By Tyler Yasukochi                        *\n" +                                              
                           "\n*                             Copyright 2024                                *\n" +
                           "\n*****************************************************************************\n" );
        Scanner scanner = new Scanner(System.in);  
        System.out.println("Enter your name: ");
        String Username = scanner.nextLine();

        if(Username.equals("")){
            Username = "UnknownPlayer";
        }

        System.out.println("Username: "+ Username);
        System.out.println(Username + " It is your turn! Rolling dice...");                           
        System.out.println("Hand: "+ currenthand); 
        System.err.println("\n**************************| CURRENT HAND AND MELD |**************************");
        System.err.println("Die     hand    |  meld");
        System.err.println("----------------+--------");
        System.err.println("(a)      "+ currenthand.get(0)+"      |     ");
        System.err.println("(b)      "+ currenthand.get(1)+"      |     ");    
        System.err.println("(c)      "+ currenthand.get(2)+"      |     ");
        System.err.println("(d)      "+ currenthand.get(3)+"      |     ");
        System.err.println("(e)      "+ currenthand.get(4)+"      |     ");
        System.err.println("(f)      "+ currenthand.get(5)+"      |     ");
        System.err.println("----------------+--------");
        System.err.println("      Meld Score: ");
        System.out.println("\n(k) Back meld\n" +"(q) Quit Game");

        //end game when generated hand = farkle
        if (farkle(currenthand) == true) {
            System.out.println("\nFARKLE!\n" + "Game Over!");
            return;
        }

        //permamenant true bool for while loop
        boolean running = true;

        while (running) {
            //meld values get saved when edited
            meld = meld(currenthand, meld, Username);
            print(currenthand,meld,Username);
            System.out.println("\n(k) Bank meld\n" +"(q) Quit Game");
        }
    }

/*----------------------------------------------------------------------------------------- */
//function to assign die values to hand
static ArrayList<Integer> hand(){
        ArrayList<Integer> hand = new ArrayList<>();

        //generate 6 die
        Die die0 = new Die(6);
        Die die1 = new Die(6);
        Die die2 = new Die(6);
        Die die3 = new Die(6);
        Die die4 = new Die(6);
        Die die5 = new Die(6);
        
        //roll 6 die
        die0.roll();
        die1.roll();
        die2.roll();
        die3.roll();
        die4.roll();
        die5.roll();
        
        //assign die values to hand array
        hand.add(die0.getSideUp());
        hand.add(die1.getSideUp());
        hand.add(die2.getSideUp());
        hand.add(die3.getSideUp());
        hand.add(die4.getSideUp());
        hand.add(die5.getSideUp());
        
        
        //sort hand
        Collections.sort(hand);

        return hand;

    }

/*----------------------------------------------------------------------------------------- */
//function to detect farkle
static boolean farkle(ArrayList<Integer> hand){

    //initialize integers to keep track of number of die value
    int countone = 0;
    int counttwo = 0;
    int countthree = 0;
    int countfour = 0;
    int countfive = 0;
    int countsix = 0;
    int pair = 0;

    //iterate through hand and add to corresponding int per value occurence
    for (Integer number : hand) {
        switch (number) {
            case 1: 
                countone++; 
                break;

            case 2: 
                counttwo++; 
                break;

            case 3: 
                countthree++; 
                break;
                
            case 4: 
                countfour++; 
                break;

            case 5: 
                countfive++; 
                break;

            case 6: 
                countsix++;
                break;
            }
        }

        if (countone == 2){pair++;}
        if (counttwo == 2){pair++;}
        if (countthree == 2){pair++;}
        if (countfour == 2){pair++;}
        if (countfive == 2){pair++;}
        if (countsix == 2){pair++;}

    //if there are possible combinations there is no farkle
    if (countone > 0 || countfive > 0 || counttwo >= 3 || countthree >= 3 || countfour >= 3 || countsix >= 3 || countone >= 3 || countfive >= 3 || pair == 3){
            return false;
        }
    if (hand.get(0) == -1&&hand.get(1) == -1&&hand.get(2) == -1&&hand.get(3) == -1&&hand.get(4) == -1&&hand.get(5) == -1){
        return false;
    }
        return true;
    }

/*----------------------------------------------------------------------------------------- */
//function for adding die value to meld
static ArrayList<Integer> meld(ArrayList<Integer> hand, ArrayList<Integer> meld, String Username) {
        Scanner scanner = new Scanner(System.in);   
    
        System.out.print("\nEnter a letter for your choice :");
        //read user input
        String choice = scanner.nextLine();
    
        if(choice.equals("k")||choice.equals("K")){
        System.out.println("Would you like to reroll your remaining die? |y/n|");
        String rrchoice = scanner.nextLine();
            if (rrchoice.equals("y") || rrchoice.equals("Y")) {
            savedscore = score(meld);
            reroll(hand, meld, Username);
                for (int i = 0; i < meld.size(); i++) {
                meld.set(i, -1); // Clear meld
                }
        }
        if(rrchoice.equals("n")||rrchoice.equals("N")){
                System.out.println("\nRound Over! " + "Score banked: " + score(meld));
                System.exit(0);
        }
        if (hothand(meld)) {
            System.out.println("*******************************");
            System.out.println("*          HOT HAND!          *");
            System.out.println("*******************************");
            System.out.println("Would you like to roll 6 new dice, or bank and end your turn? |y/n|");
            String hhrrchoice = scanner.nextLine();
        
            if (hhrrchoice.equals("y") || rrchoice.equals("Y")) {
                savedscore = score(meld);
                reroll(hand, meld, Username);
    
                // Clear the meld but keep the score
                for (int i = 0; i < meld.size(); i++) {
                    meld.set(i, -1); // Clear meld
                }
            if (hhrrchoice.equals("n")||rrchoice.equals("N")){
                    System.out.println("\nRound Over! " + "Score banked: " + score(meld));
                    System.exit(0);
            }
            }
            }
        } else {
                switch (choice) {
                    case "a":
                    case "A":
                        toggleDie(hand, meld, 0);
                        break;
                    case "b":
                    case "B":
                        toggleDie(hand, meld, 1);
                        break;
                    case "c":
                    case "C":
                        toggleDie(hand, meld, 2);
                        break;
                    case "d":
                    case "D":
                        toggleDie(hand, meld, 3);
                        break;
                    case "e":
                    case "E":
                        toggleDie(hand, meld, 4);
                        break;
                    case "f":
                    case "F":
                        toggleDie(hand, meld, 5);
                        break;
        
                    case "q":
                    case "Q":
                        System.out.println("\nGame Over! You Ended With " + score(meld) + " points!" );
                        System.exit(0);
                    default:
                        System.out.println("INVALID CHOICE");
                        break;
                }
            }
        return meld;
    }

/*----------------------------------------------------------------------------------------- */
//function to select and deselect die
static void toggleDie(ArrayList<Integer> hand, ArrayList<Integer> meld, int index) {
        
        //when die is in meld
        if (meld.get(index) != -1) { 
            //move back to hand when toggled
            hand.set(index, meld.get(index));
            //remove from meld
            meld.set(index, -1);

        //when die is in hand
        } else if (hand.get(index) != -1) {
            //move back to meld when toggled
            meld.set(index, hand.get(index));
            //remove from hand
            hand.set(index, -1);
        }
    }

/*----------------------------------------------------------------------------------------- */

static void print(ArrayList<Integer> currenthand, ArrayList<Integer> meld, String Username) {
        
    System.out.println("\nUsername: "+ Username);
    System.err.println("\n*****************************| CURRENT HAND AND MELD |*****************************");
    System.err.println("Die     hand    |  meld");
    System.err.println("----------------+--------");
    
    //iterate through meld
    for (int i = 0; i < meld.size(); i++) {

        //convert hand to string and replace placeholder -1 with " "
        String handnum = (currenthand.get(i) == -1) ? " " : Integer.toString(currenthand.get(i));
        //same for meld
        String meldnum = (meld.get(i) == -1) ? " " : Integer.toString(meld.get(i));

        System.err.println("(" + (char) ('a' + i) + ")      " + handnum + "      |    " + meldnum);

        }
        System.err.println("----------------+--------");
        System.err.println("      Meld Score: " + score(meld));
    }   

/*----------------------------------------------------------------------------------------- */
//function to calculate score
static Integer score(ArrayList<Integer> meld){
        Integer score = 0;

        //same ints to track die value occurences
        int countone = 0;
        int counttwo = 0;
        int countthree = 0;
        int countfour = 0;
        int countfive = 0;
        int countsix = 0;
        int triple = 0;
        int pair = 0;


    //iterate through and count
    for (Integer number : meld) {
        switch (number) {
            case 1: 
                countone++; 
                break;

            case 2: 
                counttwo++; 
                break;

            case 3: 
                countthree++; 
                break;
                
            case 4: 
                countfour++; 
                break;

            case 5: 
                countfive++; 
                break;

            case 6: 
                countsix++; 
                break;
            }
        }
    
        //pairs

        if (countone == 2){pair++;}
        if (counttwo == 2){pair++;}
        if (countthree == 2){pair++;}
        if (countfour == 2){pair++;}
        if (countfive == 2){pair++;}
        if (countsix == 2){pair++;}



    //combinations
    if (countone == 1){score=score+100;}
    if(countone == 2){score = score + 200;}
    if (countfive == 1){score=score+50;} 
    if (countfive ==2){score = score+100;}
    if (countone == 3){score=1000; triple++;} 
    if (counttwo == 3){score=score+200; triple++;} 
    if (countthree == 3){score=score+300; triple++;} 
    if (countfour == 3){score=score+400; triple++;}  
    if (countfive == 3){score=score+500; triple++;}
    if (countsix == 3){score=score+600; triple++;}
    if (countsix == 5){score = score + 1800;}
    if (countone == 1&&counttwo == 1&&countthree == 1&&countfour == 1&&countfive == 1&&countsix == 1){score=1000;} 
    if (pair == 3){score = score + 750;}    

    return (score+savedscore);
    }

    static ArrayList<Integer> reroll(ArrayList<Integer> currenthand, ArrayList<Integer> meld, String Username){
        for(int i=0; i< currenthand.size(); i++){
            if (currenthand.get(0) == -1&&currenthand.get(1) == -1&&currenthand.get(2) == -1&&currenthand.get(3) == -1&&
            currenthand.get(4) == -1&&currenthand.get(5) == -1){
                for (int j = 0; j < currenthand.size(); j++) {
                    Die die = new Die(6);
                    die.roll();
                    currenthand.set(j, die.getSideUp()); 
                }
                break;
            } else if (currenthand.get(i) != -1){
                Die die = new Die(6);
                die.roll();
                currenthand.set(i, die.getSideUp());
            }
        }
        if (farkle(currenthand) == true) {
            print(currenthand, meld, Username);
            System.out.println("\nFARKLE!\n" + "Game Over!");
            System.exit(0);
        }
        return currenthand;
    }

    static boolean hothand(ArrayList<Integer> meld) {

        for (int die : meld) {
            if (die == -1) {
                return false; 
            }
        }
      
        return score(meld) > 0;
    }
}



