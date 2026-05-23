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
        secretWord = selectRandomWord();

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
        gw.showMessage("Word:" + secretWord);
    }

/* Startup code */

    public static void main(String[] args) {
        new Wordle().run();
        //comment
    }

/* Private instance variables */

    private WordleGWindow gw;
    private String secretWord;
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
        setWord(0, secretWord);
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
        //make grey
        for(int col = 0; col < 5; col++){
            gw.setSquareColor(row, col, WordleGWindow.MISSING_COLOR);
        }
        //make green
        for(int col = 0; col < 5; col++){
            if(secretWord.substring(col, col+1).equals(word.substring(col, col+1))){
                gw.setSquareColor(row, col, WordleGWindow.CORRECT_COLOR);
            }
        }
        //make yellow
        for(int col = 0; col < 5; col++){
            if(toBeYellow(col)){
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
    /*boolean toBeYellow(int col){
        int row = gw.getCurrentRow();
        String word = getCurrentWord();
        for(int i = 0; i < 5; i++){
            //System.out.println("Got to here");
            //if(word.substring(col,col+1).equals(secretWord.substring(i, i+1)) && !gw.getSquareColor(row, col).equals(WordleGWindow.CORRECT_COLOR)){
            if(word.substring(i,i+1).equals(secretWord.substring(col, col+1)) && countYellow(word.substring(i,i+1)) + countGreen(word.substring(i,i+1)) < countCharacter(word.substring(i, i+1))){
                System.out.println("Got here");
                return true;

            }
            //String debug = "CHAR: "+word.charAt(i)+", Yellow:"+countYellow(word.substring(i,i+1))+", Green:"+countGreen(word.substring(i,i+1))+", CharCount:"+countCharacter(word.substring(i,i+1));
            //System.out.println("DEBUG: " + debug);
        }
        return false;
    }*/
    boolean toBeYellow(int column){
        int row = gw.getCurrentRow();
        if(gw.getSquareColor(row, column).equals(WordleGWindow.PRESENT_COLOR)){return false;}
        String currentWord = getCurrentWord();
        for(int i = 0; i < 5; i++){
            System.out.println("For character: "+currentWord.charAt(column)+"(COL: "+column+")");
            if(currentWord.charAt(column) == secretWord.charAt(i)){
                //appears in both
                System.out.println("DEBUG 1  :"+currentWord.charAt(column)+"=="+secretWord.charAt(i));
                if(i != column){
                    //not in same place
                    System.out.println("DEBUG 2:  "+i+"!="+column);
                    if(countYellowInInput(currentWord.substring(column, column+1)) < countInSecretWord(currentWord.substring(column,column+1))){
                        //yellow of this letter < this letter in secretWord
                        //TODO: change to yellow&green of this character??
                        System.out.println("DEBUG 3:  "+countYellowInInput(currentWord.substring(column, column+1))+"<"+countInSecretWord(currentWord.substring(column,column+1)));
                        return true;
                    }
                    /*
                    what also needed?

                     */
                }
            }
        }
        return false;
    }
    int countYellowInInput(String letter){
        int n = 0;
        int row = gw.getCurrentRow();
        for(int col = 0; col < 5; col++){
            if(gw.getSquareColor(row, col).equals(WordleGWindow.PRESENT_COLOR) && gw.getSquareLetter(row, col).equals(letter))
                n++;
        }
        return n;
    }
    int countInSecretWord(String letter){
        int n = 0;
        for(int i = 0; i < 5; i++){
            if(letter.equals(secretWord.substring(i, i+1))){
                n++;
            }
        }
        return n;
    }
    int countGreen(String letter){
        int n = 0;
        int row = gw.getCurrentRow();
        for(int col = 0; col < 5; col++){
            if(gw.getSquareColor(row, col).equals(WordleGWindow.CORRECT_COLOR) && gw.getSquareLetter(row, col).equals(letter))
                n++;
        }
        return n;
    }
    int countInInput(String letter){
        int n = 0;
        String word = getCurrentWord();
        for(int i = 0; i < 5; i++){
            if(letter.equals(word.substring(i, i+1))){
                n++;
            }
        }
        return n;
    }
}
