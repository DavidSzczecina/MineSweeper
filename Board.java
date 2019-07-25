import javax.swing.*;
import java.awt.*;
import java.util.Random;

/*
 * David Szczecina 1/6/2019
 * ICS4UC
 * Board Class, used to hold all the Tiles and access them
 */


public class Board {
  public int mines = 10;//amount of mines public in case we want to display it, its usually displayed to the player, so no need to protect it
  private Tile[][] tiles;

    private int width = 9;//dimension of board, keep as square
    private int height = 9;
    private int limit = 7; //variable often used for opening the tile around it, dimension-2 

    public void setBoard(){//creates the user interface and runs all of the setup functions
        JFrame frame = new JFrame();//JFrame is the thing user sees, a window
        frame.add(createButtons());//adds a panel of tiles with buttons
       
        createMines();//randomly places mines, the amount is specified in variable "mines"
        setNumMines();//if its a mine -1, else the value is the amount of mine next to it

        frame.pack();//it adjusts the size of the frame, in case you make it supper big(many tiles) 
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public JPanel createButtons(){//creates all the tiles and buttons
        JPanel panel = new JPanel(new GridLayout(width,height));
        tiles = new Tile[width][height];
        for(int i = 0; i< width; i++){
            for(int j = 0; j<height; j++){
                tiles[i][j] = new Tile(this);//creates tile
                panel.add(tiles[i][j].getButton());//adds buttons to the tile
            }
        }
        return panel;
    }

    private void createMines() {//randomly pick tiles to become mines
        Random random = new Random();//counts how many mines need to still be placed
        int counter = 0;
        while (counter != mines) {
            counter += tiles[random.nextInt(width)][random.nextInt(height)].setMine();
        }
    }
  
    public void setNumMines(){//finds out how many mines are next to each tile (number that is shown when clicked)
        for(int i = 0; i<width; i++){
            for(int j = 0; j<height; j++){
                 if(tiles[i][j].getValue() != -1){
                     if(j>=1 && tiles[i][j-1].getValue() == -1) {//left
                      tiles[i][j].incrementValue();
                     }
                     if(j<= limit && tiles[i][j+1].getValue() == -1) {//right
                      tiles[i][j].incrementValue();
                     }
                     if(i>=1 && tiles[i-1][j].getValue() == -1) {//up
                      tiles[i][j].incrementValue();
                     }
                     if(i<= limit && tiles[i+1][j].getValue() == -1) {//down
                      tiles[i][j].incrementValue();
                     }
                     if(i>=1 && j>= 1 && tiles[i-1][j-1].getValue() == -1) {//up left
                      tiles[i][j].incrementValue();
                     }
                     if(i<= limit && j<= limit && tiles[i+1][j+1].getValue() == -1) {//down right
                      tiles[i][j].incrementValue();
                     }
                     if(i>=1 && j<= limit && tiles[i-1][j+1].getValue() == -1) {//up right
                      tiles[i][j].incrementValue();
                     }
                     if(i<= limit && j>= 1 && tiles[i+1][j-1].getValue() == -1) {//down left
                      tiles[i][j].incrementValue();
                     }
                 }
            }
        }
    }
 //recursive
    public void scanForEmptyTiles(){
        for(int i = 0; i<height; i++){
            for(int j = 0; j<height; j++){//moves toward base case, j=height
                if(!tiles[i][j].isNotOpened()){//left
                    if(j>=1 && tiles[i][j-1].isEmpty()) {//calls itself
                     tiles[i][j-1].openTile();
                    }
                    if(j<= limit && tiles[i][j+1].isEmpty()) {//right
                     tiles[i][j+1].openTile();//base case, tile !empty, or at the edge of the board already
                    }
                    if(i>=1 && tiles[i-1][j].isEmpty()) {//up
                     tiles[i-1][j].openTile();
                    }
                    if(i<= limit && tiles[i+1][j].isEmpty()) {//down
                     tiles[i+1][j].openTile();
                    }
                    if(i>=1 && j>= 1 && tiles[i-1][j-1].isEmpty()) {//up left
                     tiles[i-1][j-1].openTile();
                    }
                    if(i<= limit && j<= limit && tiles[i+1][j+1].isEmpty()) {//down right
                     tiles[i+1][j+1].openTile();
                    }
                    if(i>=1 && j<= limit && tiles[i-1][j+1].isEmpty()) {//up right
                     tiles[i-1][j+1].openTile();
                    }
                    if(i<= limit && j>= 1 && tiles[i+1][j-1].isEmpty()) {//down left
                     tiles[i+1][j-1].openTile();
                    }
                }
            }
        }
    }
  
  
    public void gameOver(){//reveals all of the tiles
        for(Tile[] a : tiles){
            for(Tile b : a){
                b.reveal();
            }
        }
    }
}