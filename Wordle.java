/*
 * File: Wordle.java
 * -----------------
 * This module is the starter file for the Wordle assignment.
 * BE SURE TO UPDATE THIS COMMENT WHEN YOU COMPLETE THE CODE.
 */

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import edu.willamette.cs1.wordle.WordleDictionary;
import edu.willamette.cs1.wordle.WordleGWindow;

public class Wordle {
	private String solution;
	private List<String> searchable = Arrays.asList(WordleDictionary.FIVE_LETTER_WORDS);
	private boolean winner;
	private boolean gameover;
	private int[] turnGuesses = new int[26];
	private boolean[] overallGuesses = new boolean[26];
	
    public void run() {
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
        var r = new Random();
        solution = WordleDictionary.FIVE_LETTER_WORDS[r.nextInt(WordleDictionary.FIVE_LETTER_WORDS.length)];
        solution = "stump";
        System.out.printf("%s\n", solution);
    }

/*
 * Called when the user hits the RETURN key or clicks the ENTER button,
 * passing in the string of characters on the current row.
 */

    public void enterAction(String s) {
    	s = s.toLowerCase();
    	var correct = 0;
        if (searchable.contains(s)) {
        	for (int i = 0; i < 5; i++) {
        		var sChar = s.charAt(i);
        		var alphaPos = (int)sChar - 96;        		
        		turnGuesses[alphaPos]++; // convert ASCII char -> alpha pos
        		if(sChar == solution.charAt(i)) {
        			gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.CORRECT_COLOR);
        			correct++;
        		} else if (solution.indexOf(sChar) >= 0) {
        			var numCharInSolution = 0;
        			for (char j : solution.toCharArray()) {
        				if (sChar == j) {
        					numCharInSolution++;
        				}
        			}
        			if (turnGuesses[alphaPos] <= numCharInSolution) {
        				gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.PRESENT_COLOR);
        			} else {
        				gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.MISSING_COLOR);
        			}
        		} else {
        			gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.MISSING_COLOR);
        		}
        	}
        	
        	
        } else {
        	gw.showMessage("Invalid guess.");
        }
        
        if (correct == 5) {
    		winner = true;
    		gw.showMessage("YOU WIN");
    		gw.setCurrentRow(-1);
    	} else if (gw.getCurrentRow() == 6) {
    		gameover = true;
    	} else {
        	gw.setCurrentRow(gw.getCurrentRow() + 1);
        	turnGuesses = new int[26];
    	}
    }

/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
    }

/* Private instance variables */

    private WordleGWindow gw;

}
