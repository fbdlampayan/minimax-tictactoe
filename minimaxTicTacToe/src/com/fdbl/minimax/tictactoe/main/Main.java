/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fdbl.minimax.tictactoe.main;

import com.fbdl.minimax.tictactoe.elements.StrategyResult;
import com.fbdl.minimax.tictactoe.elements.TicTacToe;
import java.io.IOException;

/**
 * This tictactoe project attempts to use minimax algorithm for the AI.
 * 10/12/2015: Still open for few more improvements. Like adding
 * "depth" in minimax analysis, and "priority" of choice.
 * @author lampayan
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
        boolean gameOver = false;
        boolean choiceValid = false;
        int rowChoice = 0;
        int columnChoice = 0;
        
        TicTacToe game = new TicTacToe();
        
        //PRE-GAME
        do{
            choiceValid = game.initializeGame();
        }while(!choiceValid);

        choiceValid = false;
        
        //GAME START
        game.drawBoard();
        
        boolean isRowAndColumnValid = false;
        
        do{
            do{
                do{
                    try{
                        rowChoice = game.requestForInputDimensions("row");
                        choiceValid = game.isInputValid(rowChoice, "row");
                    }
                    catch(NumberFormatException nx){
                        System.err.println("Your input is invalid, please try again!");
                    }
                    catch(IOException ex){
                        System.err.println("Now this is embarassing, and IOEXCEPTION occured. Ending game. " + ex.getMessage());
                        System.exit(1);
                    }
                }while(!choiceValid);
                choiceValid = false;

                do{
                    try{
                        columnChoice = game.requestForInputDimensions("column");
                        choiceValid = game.isInputValid(columnChoice, "column");
                    }
                    catch(NumberFormatException nx){
                        System.err.println("Your input is invalid, please try again!");
                    }
                    catch(IOException ex){
                        System.err.println("Now this is embarassing, and IOEXCEPTION occured. Ending game. " + ex.getMessage());
                        System.exit(1);
                    }
                }while(!choiceValid);
                choiceValid = false;

            isRowAndColumnValid = game.inputRowAndColumn(--rowChoice, --columnChoice, game.getPlayer());
                
            }while(!isRowAndColumnValid);

            //since choice is now validated, display board with player choice
            game.drawBoard();

            //is this a winning move?
            if(game.isWinningMove(game.getPlayer()))
                System.exit(0);

            //if not print computer thinking
            System.out.println(game.getComputer().getPlayerName() + " is thinking...");

            //simulate!
            StrategyResult computerMove = game.minimaxAlgo(game.getComputer(), game.getPlayer(), game.getComputer());

            System.out.println("computer will move: " + computerMove.getxCoordinates() + computerMove.getyCoordinates());
            
            if(computerMove.isDraw()){
                System.out.println("DRAW!");
                System.exit(0);
            }
            
            //input computer move
            game.inputRowAndColumn(computerMove.getxCoordinates(), computerMove.getyCoordinates(), game.getComputer());
            
            //draw board
            game.drawBoard();

            //checker
            if(game.isWinningMove(game.getComputer())){
                System.exit(0);
            }
            
        }while(!gameOver);   
    }
}
