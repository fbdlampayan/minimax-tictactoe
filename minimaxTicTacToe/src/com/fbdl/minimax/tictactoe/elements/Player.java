/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fbdl.minimax.tictactoe.elements;

/**
 *
 * @author lampayan
 */
public class Player {
    
    private int token;
    private String name;
    
    public Player(int playerToken, String playerName){
        this.token = playerToken;
        this.name = playerName;
    }
    
    public String getPlayerName(){
        return this.name;
    }
    
    public int getPlayerToken(){
        return this.token;
    }
    
}
