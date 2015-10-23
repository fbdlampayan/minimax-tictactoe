/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fbdl.minimax.tictactoe.elements;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author lampayan
 */
public class TicTacToe {
    
    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_BLUE = "\u001B[34m";
    
    private Player player;
    private Player computer;
    private final int ROWS = 3;
    private final int COLUMN = 3;
    private final int X_PIECE = 1;
    private final int O_PIECE = -1;
    private int gameBoard[][];
    BufferedReader inputReader;
    
    public TicTacToe(){
        this.gameBoard = new int[ROWS][COLUMN];
        this.inputReader =  new BufferedReader(new InputStreamReader(System.in));
    }
    
    public boolean initializeGame(){
        boolean isGameInitialized = false;
        System.out.println("Tic-Tac-Toe! \nChoose your piece X or O: ");
        String userInput = "";
        
        try {
            userInput = inputReader.readLine().toUpperCase();
        } catch (IOException ex) {
            System.err.println("Now this is embarassing, and IOEXCEPTION occured. Ending game. " + ex.getMessage());
            System.exit(1);
        }

        if(!((userInput.contentEquals("X")) || (userInput.contentEquals("O")))){
            System.out.println("your choice is invalid, please try again");
        }
        else{
            System.out.println("Let's get Started!");
            switch(userInput){
                case "X":
                    setPlayer(new Player(X_PIECE, "You"));
                    setComputer(new Player(O_PIECE, "computer"));
                    break;
                case "O":
                    setComputer(new Player(X_PIECE, "computer"));
                    setPlayer(new Player(O_PIECE, "You"));
                    break;
            }
            isGameInitialized = true;
        }
        return isGameInitialized;
    }
    
    public void drawBoard(){
        for(int rowIndex = 0; rowIndex < ROWS; rowIndex++){
            for(int columnIndex = 0; columnIndex < COLUMN; columnIndex++){
                checkContents(gameBoard[rowIndex][columnIndex]);
                System.out.print("|");
            }
            System.out.println();
            System.out.println("------------");
        }
    }
    
    private void checkContents(int value){
        switch(value){
            case 0:
                System.out.print("   ");
                break;
            case X_PIECE:
                System.out.print(" X ");
                break;
            case O_PIECE:
                System.out.print(" O ");
                break;
        }
    }
    
    public int requestForInputDimensions(String cellDimension) throws IOException{
        int input = 0;
        System.out.println("Input " + cellDimension + " [1-3] :");
        input = Integer.parseInt(inputReader.readLine());
        System.out.println(cellDimension + " of choice is: " + input);
        return input;
    }
    
    public boolean isInputValid(int choice, String cellDimension){
        int choiceToValidate = choice - 1;
        boolean result = true;
        
        if(!(choiceToValidate >= 0 && choiceToValidate < 3)){
            System.out.println( ANSI_RED + "Your input is invalid, please choose a valid value for " + cellDimension + ANSI_RESET);
            result = false;
        }
        return result;
    }
    
    public boolean inputRowAndColumn(int row, int column, Player currentPlayer){
        boolean isRowAndColumnValid = false;
        if(gameBoard[row][column] == 0){
            gameBoard[row][column] = currentPlayer.getPlayerToken();
            isRowAndColumnValid = true;
        }
        return isRowAndColumnValid;
    }
    
    public boolean isWinningMove(Player currentPlayer){
        boolean isWon = false;
        if((gameBoard[0][0] == getPlayer().getPlayerToken() && gameBoard[0][1] == getPlayer().getPlayerToken() && gameBoard[0][2] == getPlayer().getPlayerToken()) ||
            (gameBoard[1][0] == getPlayer().getPlayerToken() && gameBoard[1][1] == getPlayer().getPlayerToken() && gameBoard[1][2] == getPlayer().getPlayerToken()) ||
                (gameBoard[2][0] == getPlayer().getPlayerToken() && gameBoard[2][1] == getPlayer().getPlayerToken() && gameBoard[2][2] == getPlayer().getPlayerToken()) ||
                    (gameBoard[0][0] == getPlayer().getPlayerToken() && gameBoard[1][0] == getPlayer().getPlayerToken() && gameBoard[2][0] == getPlayer().getPlayerToken()) ||
                        (gameBoard[0][1] == getPlayer().getPlayerToken() && gameBoard[1][1] == getPlayer().getPlayerToken() && gameBoard[2][1] == getPlayer().getPlayerToken()) ||
                            (gameBoard[0][2] == getPlayer().getPlayerToken() && gameBoard[1][2] == getPlayer().getPlayerToken() && gameBoard[2][2] == getPlayer().getPlayerToken()) || 
                                (gameBoard[0][0] == getPlayer().getPlayerToken() && gameBoard[1][1] == getPlayer().getPlayerToken() && gameBoard[2][2] == getPlayer().getPlayerToken()) ||
                                    (gameBoard[0][2] == getPlayer().getPlayerToken() && gameBoard[1][1] == getPlayer().getPlayerToken() && gameBoard[2][0] == getPlayer().getPlayerToken())){
        System.out.println(getPlayer().getPlayerName() + " won!");
        isWon = true;
        }
        return isWon;
    }

    /**
     * @return the player
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * @return the computer
     */
    public Player getComputer() {
        return computer;
    }

    /**
     * @param computer the computer to set
     */
    public void setComputer(Player computer) {
        this.computer = computer;
    }
    
    private ArrayList<Point> getAvailableSpaces(){
        ArrayList<Point> availableSpaces = new ArrayList<>();
        availableSpaces.clear();
        for(int rowIndex = 0; rowIndex < ROWS; rowIndex++){
            for(int columnIndex = 0; columnIndex < COLUMN; columnIndex++){
                if(gameBoard[rowIndex][columnIndex] == 0)
                availableSpaces.add(new Point(rowIndex, columnIndex));
            }
        }
        return availableSpaces;
    }
    
    private ArrayList<Integer> evaluateBoard() {
        //evaluate who won, return 10 or -10
        ArrayList<Integer> sumOfResults = new ArrayList<>();
        int rowSum = 0;
        int columnSum = 0;
        
        //1)check if winning pattern exists
        //diagonals
        sumOfResults.add(gameBoard[0][0] + gameBoard[1][1] + gameBoard[2][2]);
        sumOfResults.add(gameBoard[0][2] + gameBoard[1][1] + gameBoard[2][0]);
        
        //rows
        for(int rowIndex = 0; rowIndex < ROWS; rowIndex++){
            rowSum = 0;
            for(int columnIndex = 0; columnIndex < COLUMN; columnIndex++){
                rowSum += gameBoard[rowIndex][columnIndex];
            }
            sumOfResults.add(rowSum);
        }
        
        //columns
        for(int columnIndex = 0; columnIndex < COLUMN; columnIndex++){
            columnSum = 0;
            for(int rowIndex = 0; rowIndex < ROWS; rowIndex++){
                columnSum += gameBoard[rowIndex][columnIndex];
            }
            sumOfResults.add(columnSum);
        }
        
        return sumOfResults;
    }
    
    private boolean hasWinner(){
        boolean hasWinner = false;
        ArrayList<Integer> sumOfResults = new ArrayList<>();
        
        sumOfResults = evaluateBoard();
        
        for(Iterator<Integer> iter = sumOfResults.iterator(); iter.hasNext();){
            int currentEntry = iter.next();
            if(currentEntry == 3 || currentEntry == -3){
                hasWinner = true;
            }
        }
        
        return hasWinner;
    }
    
    public StrategyResult minimaxAlgo(Player currentPlayer, Player userPlayer, Player computer){
        
        StrategyResult minimaxResult = new StrategyResult();
        int bestResult = -11;
        
        
        ArrayList<Point> availableSpaces = this.getAvailableSpaces();
        if(hasWinner())
        {
            ArrayList<Integer> boardResults = evaluateBoard();
            for(Iterator<Integer> iter = boardResults.iterator(); iter.hasNext();){
                int currentEvent = iter.next();
                if(currentEvent == 3){
                    //X = 1 won!
                    minimaxResult.setSimulationResult(10);
                    return minimaxResult;
                }
                else if(currentEvent == -3){
                    //O = -1 won
                    minimaxResult.setSimulationResult(-10);
                    return minimaxResult;
                }
            }
        }
        else if(availableSpaces.isEmpty()){
            //it's a draw
            minimaxResult.setSimulationResult(0);
            minimaxResult.setIsDraw(true);
            return minimaxResult;
        }
        
        //simulate
        //there are possible moves... so do simulation
        for(Iterator<Point> iter = availableSpaces.iterator(); iter.hasNext();)
        {
            Point currentSimulationPoint = iter.next();
            //temporary place
            gameBoard[currentSimulationPoint.getX()][currentSimulationPoint.getY()] = currentPlayer.getPlayerToken();
            Player nextPlayer = (currentPlayer.getPlayerToken() == computer.getPlayerToken()) ? userPlayer : computer;
            StrategyResult currentResult = minimaxAlgo(nextPlayer, userPlayer, computer);
            
            if (currentPlayer.getPlayerToken() == computer.getPlayerToken()){
                if(bestResult < currentResult.getSimulationResult() || bestResult == -11){
                    bestResult = currentResult.getSimulationResult();
                    minimaxResult.setSimulationResult(currentResult.getSimulationResult());
                    minimaxResult.setxCoordinates(currentSimulationPoint.getX());
                    minimaxResult.setyCoordinates(currentSimulationPoint.getY());
                }
            }
            else{
                if(bestResult > currentResult.getSimulationResult() || bestResult == -11){
                    bestResult = currentResult.getSimulationResult();
                    minimaxResult.setSimulationResult(currentResult.getSimulationResult());
                    minimaxResult.setxCoordinates(currentSimulationPoint.getX());
                    minimaxResult.setyCoordinates(currentSimulationPoint.getY());
                }
            }
            gameBoard[currentSimulationPoint.getX()][currentSimulationPoint.getY()] = 0;
            
        }
        
        return minimaxResult;
    }
}
