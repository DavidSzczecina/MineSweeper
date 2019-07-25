import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
 * David Szczecina 1/6/2019
 * ICS4UC
 * Tile Class, used to store info for every tile
 */

public class Tile implements ActionListener{
    private JButton button;
    private Board board;
    private int value;//-1 is mine, 0-8 is the number mines around it
    private boolean notOpened;//Whether or not tile is clicked or cleared(for 0's)

    
    
    public Tile(Board board){
        button = new JButton();//sense if clicked
        button.addActionListener(this);//sense if clicked
        button.setPreferredSize(new Dimension(50,50));//setting size of Tiles
       // button.setMargin(new Insets(0,0,0,0));//space between buttons border and label, saw it on the swt guide in eclipse and 
        this.board = board;
        notOpened = true;//starts at no tile is clicked or checked
    }

    
    
    public JButton getButton() {//getter for button, just returns a pointer to the tile's button
        return button;
    }

    public int getValue() {//returns -1 if bomb, or 0-8 depending on number of mines around tile
        return value;
    }
  
    int setMine() {
        if (!isMine()) {//if its not a mine, it makes it a mine, returns 0 if it was a mine, 1 if it wasn't a mine 
            setValue(-1);
            return 1;//added up in board class, so if it created a new mine, it moves up an iteration
        }
        return 0;//if it didn't add a mine(already was a mine) it doesn't move up an iteration
    }
    
    boolean isMine() {// used to check if tile is a mine, value of -1
        return value == -1;
    }
    
    public void setValue(int value) {//sets value of tile
        this.value = value;
    }
    
    public void displayValue(){//when a tile is clicked decides what to display
        if(value==-1){
            button.setText("\u0058");// i found unicode for X 
            button.setBackground(Color.RED);//highlights mine background in red
        }else if(value!=0){// if no mines nearby leaves it blank
            button.setText(String.valueOf(value));//displays number of mines around tile, i found .valueOf it just returns the string for an int
        }
    }

    public void openTile(){
        button.setEnabled(false);//disable button, you can't click tile anymore
        displayValue();//shows value
        notOpened = false;
        if(value == 0) board.scanForEmptyTiles();
        if(value == -1) board.gameOver();
    }

    public void incrementValue(){//increases value, one time for every mine next to it
        value++;
    }

    public boolean isNotOpened(){// returns true if not checked or clicked yet
        return notOpened;
    }

    public boolean isEmpty(){//if clicked and no mines around it, return true
        return isNotOpened() && value==0;
    }

    public void reveal(){//disables button and shows number(or mine)
        displayValue();
        button.setEnabled(false);
    }

    @Override
    public void actionPerformed(ActionEvent click) {//when a tile is clicked it runs openTile()
        openTile();
    }

}