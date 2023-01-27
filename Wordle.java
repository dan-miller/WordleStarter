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
	private boolean gameOver;
	private int[] turnGuesses = new int[26];
	private boolean[] overallGuesses = new boolean[26];
	
    public void run() {
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
        var r = new Random();
        solution = WordleDictionary.FIVE_LETTER_WORDS[r.nextInt(WordleDictionary.FIVE_LETTER_WORDS.length)];
        solution = "class";
        System.out.printf("%s\n", solution);
    }

/*
 * Called when the user hits the RETURN key or clicks the ENTER button,
 * passing in the string of characters on the current row.
 */

    public void enterAction(String guess) {
		if (gameOver) {
			for (int i = 0; i < 6; i++) {
                for (int j = 0; j < 5; j++) {
                    gw.setSquareLetter(i, j, "");
                    gw.setSquareColor(i, j, java.awt.Color.WHITE);
                }
            }
			for (int i = 65; i <= 90; i++) {
				gw.setKeyColor(Character.toString((char)i), new java.awt.Color(0xDDDDDD));
			}
            gameOver = false;
            gw.setCurrentRow(0);
		} else {
			guess = guess.toLowerCase();
			var correct = 0;
			if (searchable.contains(guess)) {
				for (int i = 0; i < 5; i++) {
					var guessChar = guess.charAt(i);
					var alphaPos = (int)guessChar - 96;
					var numCharRemainingInSolution = 0;
					for (char j : solution.toCharArray()) {
						if (guessChar == j) {
							numCharRemainingInSolution++;
						}
					}       		
					turnGuesses[alphaPos]++; // convert ASCII char -> alpha pos
					if(guessChar == solution.charAt(i)) {
						gw.setKeyColor(Character.toString(guessChar).toUpperCase(), WordleGWindow.CORRECT_COLOR);
						gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.CORRECT_COLOR);
						numCharRemainingInSolution--;

						if (turnGuesses[alphaPos] > numCharRemainingInSolution) {
							for (int k = 0; k < i; k++) {
								if (gw.getSquareLetter(gw.getCurrentRow(), k).equalsIgnoreCase(Character.toString(guessChar))) {
									gw.setSquareColor(gw.getCurrentRow(), k, WordleGWindow.MISSING_COLOR);
								}
							}
						}
						correct++;
					} else if (solution.indexOf(guessChar) >= 0) {
						if (turnGuesses[alphaPos] > 0 && turnGuesses[alphaPos] <= numCharRemainingInSolution) {
							if (solution.indexOf(guessChar, i+1) != -1 || solution.indexOf(guessChar) < i) {
								gw.setKeyColor(Character.toString(guessChar).toUpperCase(), WordleGWindow.PRESENT_COLOR);
								gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.PRESENT_COLOR);
								numCharRemainingInSolution--;
							} else {
								gw.setKeyColor(Character.toString(guessChar).toUpperCase(), WordleGWindow.MISSING_COLOR);
								gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.MISSING_COLOR);
							}

						} else {
							gw.setKeyColor(Character.toString(guessChar).toUpperCase(), WordleGWindow.MISSING_COLOR);
							gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.MISSING_COLOR);
						}
					} else {
						gw.setKeyColor(Character.toString(guessChar).toUpperCase(), WordleGWindow.MISSING_COLOR);
						gw.setSquareColor(gw.getCurrentRow(), i, WordleGWindow.MISSING_COLOR);
					}
				}
				
				
			} else {
				gw.showMessage("Invalid guess.");
			}
			
			if (correct == 5) {
				gw.showMessage("YOU WIN. Press enter to play again.");
				gameOver = true;
			} else if (gw.getCurrentRow() == 6) {
				gw.showMessage("YOU LOSE. Press enter to play again.");
				gameOver = true;
			} else {
				gw.setCurrentRow(gw.getCurrentRow() + 1);
				turnGuesses = new int[26];
			}
		}
    }

/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
    }

/* Private instance variables */

    private WordleGWindow gw;

}
