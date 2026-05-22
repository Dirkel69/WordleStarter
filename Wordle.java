/*
 * File: Wordle.java
 * -----------------
 * This module is the starter file for the Wordle assignment.
 * BE SURE TO UPDATE THIS COMMENT WHEN YOU COMPLETE THE CODE.
 */

import edu.willamette.cs1.wordle.WordleDictionary;
import edu.willamette.cs1.wordle.WordleGWindow;

import java.awt.*;

public class Wordle {

    public void run() {
        used = new boolean[5];
        usedSecret = new boolean[5];
        gw = new WordleGWindow();
        gw.addEnterListener((s) -> enterAction(s));
        //choosing word
        sercretWord = selectRandomWord();

        //milestone1();
    }

/*
 * Called when the user hits the RETURN key or clicks the ENTER button,
 * passing in the string of characters on the current row.
 */

    public void enterAction(String s) {
        for(int i = 0; i < 5; i++){
            used[i] = false;
            usedSecret[i] = false;
        }
        gw.showMessage("You have to implement this method.");
        //gw.setSquareColor(1,2, new Color(0,1,1));
        milestone2();
        test();
        //to go down a row:
        //gw.setCurrentRow(gw.getCurrentRow()+1);
        milestone3();
        gw.showMessage("Word:" + sercretWord);
    }

/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
        //comment
    }

/* Private instance variables */

    private WordleGWindow gw;
    private String sercretWord;
    private boolean[] usedSecret;
    private boolean[] used;

/* Some code added by Gilad */
    void setWord(int row, String word){
        for(int col = 0; col < 5; col++){
            gw.setSquareLetter(row, col, word.substring(col, col+1));
        }
    }
    String selectRandomWord(){
        int length = WordleDictionary.FIVE_LETTER_WORDS.length;
        int index = (int)(Math.random() * length);
        return WordleDictionary.FIVE_LETTER_WORDS[index];
    }


    void milestone1(){
        setWord(0, sercretWord);
    }

    void milestone2(){
        String word = getCurrentWord();
        if(find(word, WordleDictionary.FIVE_LETTER_WORDS, 0, WordleDictionary.FIVE_LETTER_WORDS.length-1) < 0){
            gw.showMessage("Not in word list ("+word+")");
        }
        else{
            gw.showMessage("Got the milestone running");
        }
    }
    void milestone3(){
        int row = gw.getCurrentRow();
        String word = getCurrentWord();
        //make green first
        for(int col = 0; col < 5; col++){
            if(sercretWord.substring(col, col+1).equals(word.substring(col, col+1))){
                gw.setSquareColor(row, col, WordleGWindow.CORRECT_COLOR);
            }
        }
        //make yellow
        for(int col = 0; col < 5; col++){
            if(presentAndNotCorrect(col)){
                gw.setSquareColor(row, col, WordleGWindow.PRESENT_COLOR);
            }
        }
    }
    void test(){

        // CORRECT_COLOR = "#66BB66"
        // PRESENT_COLOR = "#CCBB66"
        // MISSING_COLOR = "#999999"

        gw.setSquareColor(1,1, WordleGWindow.CORRECT_COLOR);

    }
    String getCurrentWord(){
        int row = gw.getCurrentRow();
        String word = "";
        for(int col = 0; col < 5; col++){
            word += gw.getSquareLetter(row, col);
        }
        return word.toLowerCase();
    }
    int find(String target, String[] arr, int start, int end){
        int middle = (start + end) / 2;
        if(start > end) {return -1;}
        if(arr[middle].equals(target)){return middle;}
        if(arr[middle].compareTo(target) < 0){
            return find(target, arr, middle + 1, end);
        }
        else if(arr[middle].compareTo(target) > 0){
            return find(target, arr, start, middle - 1);
        }
        else{
            gw.showMessage("(In find()) ähhh, how did we get here??");
        }
        return -1;
    }
    boolean presentAndNotCorrect(int col){
        int row = gw.getCurrentRow();
        String word = getCurrentWord();
        for(int i = 0; i < 5; i++){
            if(word.substring(col,col+1).equals(sercretWord.substring(i, i+1)) && !gw.getSquareColor(row, col).equals(WordleGWindow.CORRECT_COLOR)){
                return true;
            }
        }
        return false;
    }
}
