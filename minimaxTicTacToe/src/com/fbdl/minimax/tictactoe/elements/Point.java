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
public class Point {
    
    private int x;
    private int y;
    
    public Point(int xCoordinates, int yCoordinates){
        this.x = xCoordinates;
        this.y = yCoordinates;
    }
    
    public int getX(){
        return this.x;
    }
    
    public int getY(){
        return this.y;
    }
    
}
