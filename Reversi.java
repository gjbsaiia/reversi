import javax.swing.*;
import java.awt.*;
import java.awt.Color;
import java.awt.event.*;

/**
 * A window in which you can play reversi - done through a paneled array of linklist-like JButtons. Contians sub-class RButton.
 * @author Griffin Saiia, Gjs64
 */
public class Reversi extends JFrame implements ActionListener{
  
  //holds current player
  private static boolean player;
  
  //Player True's score
  private static int redScore;
  
  //Player False's score
  private static int blackScore;

  //the player's scores in JTextAreas
  private static JTextArea scoreRed;
  private static JTextArea scoreBlack;
    
  //the clickable tiles
  private   RButton[][] buttons;
  
  //format for the tiles
  private   JPanel panel;
  
  //holds x-dimension (columns)
  private static double x;
  
  //holds y dimension (rows)
  private static double y;
  
  //holds default background color for JButton
  private   Color og = (new JButton()).getBackground();
  
  //holds reset JButton;
  private   JButton newGame;
  
  //holds pass JButton;
  private   JButton pass;
  
  //format for extra buttons and text
  private   JPanel scoreStuff;

  /**
   *default constructor for Reversi - sets board dimensions to 8x8, x and y = 8
   */
  public Reversi() {
    //intializes fields
    redScore = 2;
    blackScore = 2;
    x = 8.0;
    y = 8.0;
    player = true;
    scoreRed = new JTextArea("Red: "+redScore+'\n'+"You're up!");
    scoreBlack = new JTextArea("Black: "+blackScore);
    newGame = new JButton("New Game");
    pass = new JButton("Pass");
    //Set up board
    panel = new JPanel(new GridLayout(8,8));
    initializeButtons(8, 8);
    //Set up score and buttons
    scoreStuff = new JPanel(new GridLayout(2,2));
    scoreStuff.add(scoreRed);
    scoreStuff.add(scoreBlack);
    scoreStuff.add(pass);
    scoreStuff.add(newGame);
    //adds actionlisteners to pass and reset buttons
    newGame.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        new DimensionWindow();
      }
    });
    pass.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e){
        //changes player
        Reversi.changePlayer();
      }
    });
    //finishes setting up scoreboard
    scoreRed.setEditable(false);
    scoreBlack.setEditable(false);
    // Add the buttons, the text area, and extra credit buttons to this window.
    Container c = this.getContentPane();
    c.add(panel, "Center");
    c.add(scoreStuff, "South");
    setVisible(true);
  }
  
  /**
   * Reversi constructor with one input- this makes a square board with dimensions input x input, x y respectively.
   * @param d - square board dimensions
   */
  public Reversi(int d){
    //ensures a board that fits starting pieces
    if(d < 2)
      throw new ArithmeticException("Values must be greater than 1.");
    else{
      //intializes fields
      x = (double)d;
      y = (double)d;
      redScore = 2;
      blackScore = 2;
      scoreRed = new JTextArea("Red: "+redScore+'\n'+"You're up!");
      scoreBlack = new JTextArea("Black: "+blackScore);
      newGame = new JButton("New Game");
      pass = new JButton("Pass");
      //Initializes player
      player = true;
      //sets up board
      panel = new JPanel(new GridLayout(d, d));
      initializeButtons(d, d);
      //Set up score and buttons
      scoreStuff = new JPanel(new GridLayout(2,2));
      scoreStuff.add(scoreRed);
      scoreStuff.add(scoreBlack);
      scoreStuff.add(pass);
      scoreStuff.add(newGame);
      //adds actionlisteners to additional buttons
      newGame.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          new DimensionWindow();
        }
      });
      pass.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          if(player)
            player = false;
          else
            player = true;
        }
      });
      //sets up scoreboard
      scoreRed.setEditable(false);
      scoreBlack.setEditable(false);
      // Add the buttons and the text area to this window.
      Container c = this.getContentPane();
      c.add(panel, "Center");
      c.add(scoreStuff, "South");
      if(d == 2)
        updateScore();
      setVisible(true);
    }
  }
  
  /**
   * Reversi constructor with two inputs a, b - this detemines a rectangular boards dimensions, a x b, x respectively
   * @param a - x dimension, # of columns
   * @param b - y dimension, # of rows
   */
  public Reversi(int a, int b){
    //ensures a board that fits starting pieces
    if(a < 2 || b <  2)
      throw new ArithmeticException("Values must be greater than 1.");
    else{
      //intializes fields
      x = (double)a;
      y = (double)b;
      redScore = 2;
      blackScore = 2;
      scoreRed = new JTextArea("Red: "+redScore+'\n'+"You're up!");
      scoreBlack = new JTextArea("Black: "+blackScore);
      newGame = new JButton("New Game");
      pass = new JButton("Pass");
      //Initializes player
      player = true;
      //sets up board
      panel = new JPanel(new GridLayout(a, b));
      initializeButtons(a, b);
      //Set up score and buttons
      scoreStuff = new JPanel(new GridLayout(2,2));
      scoreStuff.add(scoreRed);
      scoreStuff.add(scoreBlack);
      scoreStuff.add(pass);
      scoreStuff.add(newGame);
      //adds actionlisteners to additional buttons
      newGame.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent e){
          new DimensionWindow();
        }
      });
      //sets up scoreboard
      scoreRed.setEditable(false);
      scoreBlack.setEditable(false);
      // Add the buttons and the text area to this window.
      Container c = this.getContentPane();
      c.add(panel, "Center");
      c.add(scoreStuff, "South");
      //makes sure that if 2 x 2 dimensions, automatically displays 'Tie!'
      if(x == 2 && y == 2)
        updateScore();
      setVisible(true);
    }
  }
  
  /**
   * Main method - allows reversi to be played directly from the command line
   * @param args - String[] that takes list of arguments for the method
   */
  public static void main(String[] args){
    try{
      //boolean that indicates whether or not the method should continue
      boolean cont = true;
      //checks that there are only two arguments
      if(args.length > 2){
        //defaults, sends message to interactions
        new Reversi();
        System.out.println("Error, too many inputs.");
        cont = false;
      }
      //if no arguments calls default constructor
      else if(args.length == 0){
        if(cont){
          new Reversi();
          cont = false;
        }
      }
      //if there are only two
      else{
        if(cont){
          //array to store each character of the input's number
          Integer[] f = new Integer[2];
          //array to store each input number
          Integer[] d = new Integer[2];
          //turns strings to numbers!
          for(int i = 0; i < args.length; i++){
            //array holds characters of string input
            char[] c = args[i].toCharArray();
            //checks to see if these are actually numbers
            for(int j = 0; j < c.length; j++){
              if(cont){
                if(Character.isAlphabetic((int)c[j])){
                  new Reversi();
                  System.out.println("Error, inputs are not numbers.");
                  cont = false;
                }
              }
            }
            //checks that the dimensions don't get too big
            if(c.length > 2){
              if(cont){
                new Reversi();
                System.out.println("Error, dimensions are too large.");
                cont = false;
              }
            }
            if(cont){
              //changes characters to numbers
              for(int j = 0; j < c.length; j++){
                d[j] = (int)c[j] - 48;
              }
              //changes the two char turned digits into a number, and assigns to f
              if(c.length == 2)
                f[i] = d[0]*10 + d[1];
              //if only one number, assgins it to f
              else{
                f[i] = d[0];
              }
            }
          }
          //makes sure we have two inputs for our constructor
          if(args.length == 1)
            f[1] = f[0];
          //creates our instance!
          if(cont)
            new Reversi(f[0], f[1]);
        }
      }
    }
    catch(Exception NullPointerException){
      new Reversi();
    }
  }
  
  /**
   * Helper method that creates a number of RButtons and assigns each RButton their 8 adjacent RButtons.
   * @param a - x dimension, # of columns
   * @param b - y dimensio, # of rows
   */
  private void initializeButtons(int a, int b){
    //sets the appropraite window size - 83 is an arbitrary number
    setSize(83 * a, 83 * b);
    //Creates an array of RButtons
    buttons = new RButton[a][b];
    //initializes JButton array, and adds the JButtons to panel
    for(int i = 0; i < a; i++){
      for(int j = 0; j < b; j++){
        buttons[i][j] = new RButton();
        buttons[i][j].addActionListener(this);
        panel.add(buttons[i][j]);
      }
    }
    //places starting pieces on the board
    buttons[(a/2)-1][(b/2)-1].setBackground(Color.RED);
    buttons[(a/2)-1][(b/2)].setBackground(Color.BLACK);
    buttons[(a/2)][(b/2)-1].setBackground(Color.BLACK);
    buttons[(a/2)][(b/2)].setBackground(Color.RED);
    //alots each RButton its 3-8 adjacent RButtons
    for(int i = 0; i < a; i++){
      for(int j = 0; j < b; j++){
        if(i == 0){
          if(j == 0){
            buttons[i][j].setEast(buttons[i][j+1]); 
            buttons[i][j].setSouthEast(buttons[i+1][j+1]);
            buttons[i][j].setSouth(buttons[i+1][j]); 
          }
          else if(j == b - 1){
            buttons[i][j].setWest(buttons[i][j-1]); 
            buttons[i][j].setSouthWest(buttons[i+1][j-1]);
            buttons[i][j].setSouth(buttons[i+1][j]);
          }
          else{
            buttons[i][j].setEast(buttons[i][j+1]); 
            buttons[i][j].setSouthEast(buttons[i+1][j+1]); 
            buttons[i][j].setSouth(buttons[i+1][j]); 
            buttons[i][j].setWest(buttons[i][j-1]); 
            buttons[i][j].setSouthWest(buttons[i+1][j-1]);
          }
        }
        else if(i == a - 1){
          if(j == 0){
            buttons[i][j].setNorth(buttons[i-1][j]); 
            buttons[i][j].setNorthEast(buttons[i-1][j+1]);
            buttons[i][j].setEast(buttons[i][j+1]);
          }
          else if(j == b - 1){
            buttons[i][j].setNorth(buttons[i-1][j]); 
            buttons[i][j].setNorthWest(buttons[i-1][j-1]);
            buttons[i][j].setWest(buttons[i][j-1]);  
          }
          else{
            buttons[i][j].setNorth(buttons[i-1][j]);
            buttons[i][j].setNorthEast(buttons[i-1][j+1]);
            buttons[i][j].setEast(buttons[i][j+1]); 
            buttons[i][j].setNorthWest(buttons[i-1][j-1]); 
            buttons[i][j].setWest(buttons[i][j-1]);
          }
        }
        else{
          if(j == 0){
            buttons[i][j].setNorth(buttons[i-1][j]); 
            buttons[i][j].setNorthEast(buttons[i-1][j+1]);
            buttons[i][j].setEast(buttons[i][j+1]); 
            buttons[i][j].setSouthEast(buttons[i+1][j+1]);
            buttons[i][j].setSouth(buttons[i+1][j]); 
          }
          else if(j == b - 1){
            buttons[i][j].setNorth(buttons[i-1][j]); 
            buttons[i][j].setNorthWest(buttons[i-1][j-1]);
            buttons[i][j].setWest(buttons[i][j-1]);
            buttons[i][j].setSouthWest(buttons[i+1][j-1]);
            buttons[i][j].setSouth(buttons[i+1][j]);
          }
          
          else{
            buttons[i][j].setNorth(buttons[i-1][j]); 
            buttons[i][j].setNorthEast(buttons[i-1][j+1]);
            buttons[i][j].setEast(buttons[i][j+1]); 
            buttons[i][j].setNorthWest(buttons[i-1][j-1]); 
            buttons[i][j].setWest(buttons[i][j-1]);
            buttons[i][j].setSouthEast(buttons[i+1][j+1]); 
            buttons[i][j].setSouth(buttons[i+1][j]);
            buttons[i][j].setSouthWest(buttons[i+1][j-1]);
          }
        }
      }
    }
  }
  
  /**
   * this what happens when an RButton gets clicked. Mostly just a house for the helper methods. 
   * @param e - the flag that indicates the user clicked, containing the location of the cursor where the user clicked
   */
  public void actionPerformed(ActionEvent e){
    //stores the address of e in RButton variable
    RButton clicked = (RButton) e.getSource();
    //checks if the button has already been clicked
    if(clicked.getBackground() != Color.RED && clicked.getBackground() != Color.BLACK){
      //houses the directions in which tiles need flipped returned by validMove
      boolean[] direction = validMove(clicked);
      //checks that at least one direction has flippable tiles before it allows the playing of the tile
      if(direction[0] == true || direction[1] == true || direction[2] == true || direction[3] == true || direction[4] == true || direction[5] == true || direction[6] == true || direction[7] == true){
        //calls helper method to do the flipping
        flipTiles(clicked, direction);
        if(((double)redScore + (double)blackScore) == (x * y)){
        //updates scoreboard
        updateScore();
        }
        else{
        //updates scoreboard
        updateScore();
        //changes player
        changePlayer();
        }
      }
    }
  }
  
  /**
   * helper method that changes player, and the You're up! signifier
   */
  private static void changePlayer(){
    if(player){
      player = false;
      scoreBlack.setText("Black: "+blackScore+'\n'+"You're up!");
      scoreRed.setText("Red: "+redScore);
    }
    else{
      player = true;
      scoreRed.setText("Red: "+redScore+'\n'+"You're up!");
      scoreBlack.setText("Black: "+blackScore);
    }
  }
  
  /**
    * helper method that updates scoreboard
    */
  private static void updateScore(){
    if(((double)redScore + (double)blackScore) == (x * y)){
      if(redScore > blackScore){
        scoreRed.setEditable(true);
        scoreRed.setText("Red: "+redScore+'\n'+"RED WINS!!");
        scoreRed.setEditable(false);
        scoreBlack.setEditable(true);
        scoreBlack.setText("Black: "+blackScore);
        scoreBlack.setEditable(false);
      }
      else if(redScore < blackScore){
        scoreRed.setEditable(true);
        scoreRed.setText("Red: "+redScore);
        scoreRed.setEditable(false);
        scoreBlack.setEditable(true);
        scoreBlack.setText("Black: "+blackScore+'\n'+"BLACK WINS!!");
        scoreBlack.setEditable(false);
      }
      else if(redScore == blackScore){
        scoreRed.setEditable(true);
        scoreRed.setText("Red: "+redScore+'\n'+"We tied.");
        scoreRed.setEditable(false);
        scoreBlack.setEditable(true);
        scoreBlack.setText("Black: "+blackScore+'\n'+"We tied.");
        scoreBlack.setEditable(false);
      }
    }
    else{
      scoreRed.setEditable(true);
      scoreRed.setText("Red: "+redScore);
      scoreRed.setEditable(false);
      scoreBlack.setEditable(true);
      scoreBlack.setText("Black: "+blackScore);
      scoreBlack.setEditable(false);
    }
  }
  
  /**
   * helper method that checks the 8 directions in which the move is potentially valid
   * @param pressed - the RButton dat got clicked
   * @return boolean[] - houses the yay or nay in all 8 directions
   */
  private   boolean[] validMove(RButton pressed){
    //creates boolean array direction
    boolean[] direction = new boolean[8];
    //intializes direction to all falses
    for(int i = 0; i < direction.length; i++){
      direction[i] = false;
    }
    //checks who's playing
    if(player){
      //checks that the button to the north exists (that we're not on an north edge)
      if(pressed.getNorth() != null){
        //checks if button is opposing color
        if(pressed.getNorth().getBackground() == Color.black){
          //stores button in a pointer
          RButton temp = pressed.getNorth();
          //intializes boolean flag
          boolean isBlack = true;
          //creates another pointer to make the exception catcher work
          RButton temp2 = new RButton();
          //loops till we aren't black anymore
          while(isBlack == true){ 
            //stores next button in temporary
            temp = temp.getNorth();
            //catches if this north doesn't exist
            try{
              isBlack = (temp.getBackground() == Color.black);
              temp2 = temp;
            }
            //if it doesn't assist exits loop, and sets temp2 to skirt the if statement
            catch(Exception NullPointerException){
              isBlack = false;
              temp2 = new RButton();
            }
          }
          //checks if the non-black button is red or not
          if(temp2.getBackground() == Color.red)
            //if it is, gives the go ahead for ol flipper in the north direction
            direction[0] = true;
        }
      }
      //these are all the exact same method with switched directions - numbering from 0-7 as North,Northeast...West, Northwest
      if(pressed.getNorthEast() != null){
        if(pressed.getNorthEast().getBackground() == Color.black){
          RButton temp = pressed.getNorthEast();
          boolean isBlack = true;
          RButton temp2 = new RButton();
          while(isBlack == true){ 
            temp = temp.getNorthEast();
            try{
              isBlack = (temp.getBackground() == Color.black);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isBlack = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.red)
            direction[1] = true;
        }
      }
      if(pressed.getEast() != null){
        if(pressed.getEast().getBackground() == Color.black){
          RButton temp = pressed.getEast();
          boolean isBlack = true;
          RButton temp2 = new RButton();
          while(isBlack == true){ 
            temp = temp.getEast();
            try{
              isBlack = (temp.getBackground() == Color.black);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
               isBlack = false;
               temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.red)
            direction[2] = true;
        }
      }
      if(pressed.getSouthEast() != null){
        if(pressed.getSouthEast().getBackground() == Color.black){
          RButton temp = pressed.getSouthEast();
          boolean isBlack = true;
          RButton temp2 = new RButton();
          while(isBlack == true){ 
            temp = temp.getSouthEast();
            try{
              isBlack = (temp.getBackground() == Color.black);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isBlack = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.red)
            direction[3] = true;
        }
      }
      if(pressed.getSouth() != null){
        if(pressed.getSouth().getBackground() == Color.black){
         RButton temp = pressed.getSouth();
          boolean isBlack = true;
          RButton temp2 = new RButton();
          while(isBlack == true){ 
            temp = temp.getSouth();
            try{
              isBlack = (temp.getBackground() == Color.black);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isBlack = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.red)
            direction[4] = true;
        }
      }
      if(pressed.getSouthWest() != null){
        if(pressed.getSouthWest().getBackground() == Color.black){
          RButton temp = pressed.getSouthWest();
          boolean isBlack = true;
          RButton temp2 = new RButton();
          while(isBlack == true){ 
            temp = temp.getSouthWest();
            try{
              isBlack = (temp.getBackground() == Color.black);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isBlack = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.red)
            direction[5] = true;
        }
      }
      if(pressed.getWest() != null){
        if(pressed.getWest().getBackground() == Color.black){
          RButton temp = pressed.getWest();
          boolean isBlack = true;
          RButton temp2 = new RButton();
          while(isBlack == true){ 
            temp = temp.getWest();
            try{
              isBlack = (temp.getBackground() == Color.black);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isBlack = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.red)
            direction[6] = true;
        }
      }
      if(pressed.getNorthWest() != null){
        if(pressed.getNorthWest().getBackground() == Color.black){
          RButton temp = pressed.getNorthWest();
          boolean isBlack = true;
          RButton temp2 = new RButton();
          while(isBlack == true){ 
            temp = temp.getNorthWest();
            try{
              isBlack = (temp.getBackground() == Color.black);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isBlack = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.red)
            direction[7] = true;
        }
      }
    }
    //this side of the method just reverses the color for the black player
    else{
      if(pressed.getNorth() != null){
        if(pressed.getNorth().getBackground() == Color.red){
          RButton temp = pressed.getNorth();
          boolean isRed = true;
          RButton temp2 = new RButton();
          while(isRed == true){ 
            temp = temp.getNorth();
            try{
              isRed = (temp.getBackground() == Color.red);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isRed = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.black)
            direction[0] = true;
        }
      }
      if(pressed.getNorthEast() != null){
        if(pressed.getNorthEast().getBackground() == Color.red){
          RButton temp = pressed.getNorthEast();
          boolean isRed = true;
          RButton temp2 = new RButton();
          while(isRed == true){ 
            temp = temp.getNorthEast();
            try{
              isRed = (temp.getBackground() == Color.red);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isRed = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.black)
            direction[1] = true;
        }
      }
      if(pressed.getEast() != null){
        if(pressed.getEast().getBackground() == Color.red){
          RButton temp = pressed.getEast();
          boolean isRed = true;
          RButton temp2 = new RButton();
          while(isRed == true){ 
            temp = temp.getEast();
            try{
              isRed = (temp.getBackground() == Color.red);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isRed = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.black)
            direction[2] = true;
        }
      }
      if(pressed.getSouthEast() != null){
        if(pressed.getSouthEast().getBackground() == Color.red){
          RButton temp = pressed.getSouthEast();
          boolean isRed = true;
          RButton temp2 = new RButton();
          while(isRed == true){ 
            temp = temp.getSouthEast();
            try{
              isRed = (temp.getBackground() == Color.red);
              temp2 = temp;
            }
             catch(Exception NullPointerException){
               isRed = false;
               temp2 = new RButton();
             }
          }
          if(temp2.getBackground() == Color.black)
            direction[3] = true;
          }
      }
      if(pressed.getSouth() != null){
        if(pressed.getSouth().getBackground() == Color.red){
         RButton temp = pressed.getSouth();
          boolean isRed = true;
          RButton temp2 = new RButton();
          while(isRed == true){ 
            temp = temp.getSouth();
            try{
              isRed = (temp.getBackground() == Color.red);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isRed = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.black)
            direction[4] = true;
         }
      }
      if(pressed.getSouthWest() != null){
        if(pressed.getSouthWest().getBackground() == Color.red){
          RButton temp = pressed.getSouthWest();
          boolean isRed = true;
          RButton temp2 = new RButton();
          while(isRed == true){ 
            temp = temp.getSouthWest();
            try{
              isRed = (temp.getBackground() == Color.red);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isRed = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.black)
            direction[5] = true;
        }
      }
      if(pressed.getWest() != null){
        if(pressed.getWest().getBackground() == Color.red){
          RButton temp = pressed.getWest();
          boolean isRed = true;
          RButton temp2 = new RButton();
          while(isRed == true){ 
            temp = temp.getWest();
            try{
              isRed = (temp.getBackground() == Color.red);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isRed = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.black)
            direction[6] = true;
        }
      }
      if(pressed.getNorthWest() != null){
        if(pressed.getNorthWest().getBackground() == Color.red){
          RButton temp = pressed.getNorthWest();
          boolean isRed = true;
          RButton temp2 = new RButton();
          while(isRed == true){ 
            temp = temp.getNorthWest();
            try{
              isRed = (temp.getBackground() == Color.red);
              temp2 = temp;
            }
            catch(Exception NullPointerException){
              isRed = false;
              temp2 = new RButton();
            }
          }
          if(temp2.getBackground() == Color.black)
            direction[7] = true;
        }
      }
    }
    //returns array direction....finally
    return direction;
  }
  
  /**
   * checks 0-7 for trues, if true it changes the tiles to the players color
   * @param pressed - the chosen RButton.
   * @param direction - array containing valid directions
   */
  private   void flipTiles(RButton pressed, boolean[] direction){
    //checks who's playing
    if(player){
      //systematically checks each direction for valid paths
      
      //updates score field
      redScore = redScore + 1;
      //for example: this if statement checks north
      if(direction[0]){
        //stores clicked button in pointer
        RButton temp = pressed;
        //defines RButton array to the largest possible streak of buttons (given the coordinates)
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        //intializes counter
        int i = 0;
        //intializes boolean flag
        boolean isBlack = true;
        //loops storing each consecutive black tile in this row into array change
        while(isBlack == true){
          if(i > 0)
            blackScore = blackScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getNorth();
          isBlack = (temp.getBackground() == Color.black);
        }
        //checks if the tile that broke the streak is red or not - this should always be true
        if(temp.getBackground() == Color.red){
          //loops through and changes them to the player's color
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.red);
              //updates score fields
              if(i > 0)
                redScore = redScore + 1;
            }
          }
        }
      }
      if(direction[1]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isBlack = true;
        while(isBlack == true){
          if(i > 0)
            blackScore = blackScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getNorthEast();
          isBlack = (temp.getBackground() == Color.black);
        }
        if(temp.getBackground() == Color.red){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.red);
              if(i > 0)
                redScore = redScore + 1;
            }
          }
        }
      }
      if(direction[2]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isBlack = true;
        while(isBlack == true){
          if(i > 0)
            blackScore = blackScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getEast();
          isBlack = (temp.getBackground() == Color.black);
        }
        if(temp.getBackground() == Color.red){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.red);
              if(i > 0)
                redScore = redScore + 1;
            }
          }
        }
      }
      if(direction[3]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isBlack = true;
        while(isBlack == true){
          if(i > 0)
            blackScore = blackScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getSouthEast();
          isBlack = (temp.getBackground() == Color.black);
        }
        if(temp.getBackground() == Color.red){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.red);
              if(i > 0)
                redScore = redScore + 1;
            }
          }
        }
      }
      if(direction[4]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isBlack = true;
        while(isBlack == true){
          if(i > 0)
            blackScore = blackScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getSouth();
          isBlack = (temp.getBackground() == Color.black);
        }
        if(temp.getBackground() == Color.red){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.red);
              if(i > 0)
                redScore = redScore + 1;
            }
          }
        }
      }
      if(direction[5]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isBlack = true;
        while(isBlack == true){
          if(i > 0)
            blackScore = blackScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getSouthWest();
          isBlack = (temp.getBackground() == Color.black);
        }
        if(temp.getBackground() == Color.red){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.red);
              if(i > 0)
                redScore = redScore + 1;
            }
          }
        }
      }
      if(direction[6]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isBlack = true;
        while(isBlack == true){
          if(i > 0)
            blackScore = blackScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getWest();
          isBlack = (temp.getBackground() == Color.black);
        }
        if(temp.getBackground() == Color.red){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.red);
              if(i > 0)
                redScore = redScore + 1;
            }
          }
        }
      }
      if(direction[7]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isBlack = true;
        while(isBlack == true){
          if(i > 0)
            blackScore = blackScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getNorthWest();
          isBlack = (temp.getBackground() == Color.black);
        }
        if(temp.getBackground() == Color.red){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.red);
              if(i > 0)
                redScore = redScore + 1;
            }
          }
        }
      }
    }
    else{
      //updates score field
      blackScore = blackScore + 1;
      if(direction[0]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isRed = true;
        while(isRed == true){
          if(i > 0)
            redScore = redScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getNorth();
          isRed = (temp.getBackground() == Color.red);
        }
        if(temp.getBackground() == Color.black){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.black);
              if(i > 0)
                blackScore = blackScore + 1;
            } 
          }
        }
      }
      if(direction[1]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isRed = true;
        while(isRed == true){
          if(i > 0)
            redScore = redScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getNorthEast();
          isRed = (temp.getBackground() == Color.red);
        }
        if(temp.getBackground() == Color.black){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.black);
              if(i > 0)
                blackScore = blackScore + 1;
            }
          }
        }
      }
      if(direction[2]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isRed = true;
        while(isRed == true){
          if(i > 0)
            redScore = redScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getEast();
          isRed = (temp.getBackground() == Color.red);
        }
        if(temp.getBackground() == Color.black){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.black);
              if(i > 0)
                blackScore = blackScore + 1;
            }
          }
        }
      }
      if(direction[3]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isRed = true;
        while(isRed == true){
          if(i > 0)
            redScore = redScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getSouthEast();
          isRed = (temp.getBackground() == Color.red);
        }
        if(temp.getBackground() == Color.black){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.black);
              if(i > 0)
                blackScore = blackScore + 1;
            }
          }
        }
      }
      if(direction[4]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isRed = true;
        while(isRed == true){
          if(i > 0)
            redScore = redScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getSouth();
          isRed = (temp.getBackground() == Color.red);
        }
        if(temp.getBackground() == Color.black){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.black);
              if(i > 0)
                blackScore = blackScore + 1;
            }
          }
        }
      }
      if(direction[5]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isRed = true;
        while(isRed == true){
          if(i > 0)
            redScore = redScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getSouthWest();
          isRed = (temp.getBackground() == Color.red);
        }
        if(temp.getBackground() == Color.black){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.black);
              if(i > 0)
                blackScore = blackScore + 1;
            }
          }
        }
      }
      if(direction[6]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isRed = true;
        while(isRed == true){
          if(i > 0)
            redScore = redScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getWest();
          isRed = (temp.getBackground() == Color.red);
        }
        if(temp.getBackground() == Color.black){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.black);
              if(i > 0)
                blackScore = blackScore + 1;
            }
          }
        }
      }
      if(direction[7]){
        RButton temp = pressed;
        RButton[] change = new RButton[(int)Math.hypot(x, y)];
        int i = 0;
        boolean isRed = true;
        while(isRed == true){
          if(i > 0)
            redScore = redScore - 1;
          change[i] = temp;
          i = i + 1;
          temp = temp.getNorthWest();
          isRed = (temp.getBackground() == Color.red);
        }
        if(temp.getBackground() == Color.black){
          for(i = 0; i < change.length; i++){
            if(change[i] != null){
              change[i].setBackground(Color.black);
              if(i > 0)
                blackScore = blackScore + 1;
            }
          }
        }
      }
    }
  }
  
  
  /**
   * private sub class RButton that gives JButton linklist ability
   */
  private class RButton extends JButton{
    
    //holds RButton adjacent to the NorthWest
    private   RButton northwest;
    
    //holds RButton adjacent to the North
    private   RButton north;
    
    //holds RButton adjacent to the NorthEast
    private   RButton northeast;
    
    //holds RButton adjacent to the east
    private   RButton east;
    
    //holds RButton adjacent to the southeast
    private   RButton southeast;
    
    //holds RButton adjacent to the south
    private   RButton south;
    
    //holds RButton adjacent to the southwest
    private   RButton southwest;
    
    //holds RButton adjacent to the west
    private   RButton west;
    
    /**
     * default RButton constructor - intializes all fields to null
     */
    private RButton(){
      northwest = null;
      north = null;
      northeast = null;
      east = null;
      southeast = null;
      south = null;
      southwest = null;
      west = null;
    }
    
    /**
     * a mess of getters that return the appropriate field
     * @return RButton - returns the RButtpn in a given direction relative to this RButton
     */
    private   RButton getNorthWest(){
      return northwest;
    }
    
    private   RButton getNorth(){
      return north;
    }
    
    private   RButton getNorthEast(){
      return northeast;
    }
    
    private   RButton getEast(){
      return east;
    }
    
    private   RButton getSouthEast(){
      return southeast;
    }
    
    private   RButton getSouth(){
      return south;
    }
    
    private   RButton getSouthWest(){
      return southwest;
    }
    
    private   RButton getWest(){
      return west;
    }
    
    /**
     * a whole mess of setters for all the appropriate fields
     * @param nw - Assigins RButton the RButton in whatever direction in respect to this RButton
     */
    private   void setNorthWest(RButton nw){
      northwest = nw;
    }
    
    private   void setNorth(RButton n){
      north = n;
    }
    
    private   void setNorthEast(RButton ne){
      northeast = ne;
    }
    
    private   void setEast(RButton e){
      east = e;
    }
    
    private   void setSouthEast(RButton se){
      southeast = se;
    }
    
    private   void setSouth(RButton s){
      south = s;
    }
    
    private   void setSouthWest(RButton sw){
      southwest = sw;
    }
    
    private   void setWest(RButton w){
      west = w;
    }
  }
  
  /**
   * Subclass that creates a pop up window when reset is pressed
   * it offers the choice to set the dimensions of the next game
   */
  private class DimensionWindow extends JFrame{
    
    //text area for user input
    private JTextArea a;
    
    //text area for user input
    private JTextArea b;
    
    //easy way to label the text areas
    private JButton labelX;
    
    private JButton labelY;
    
    //only JButton here with an action listener
    private JButton enter;
    
    //easy way to title
    private JButton title;
    
    //nice little formatter
    private JPanel structure;
    
    /**
     * default constructor
     */
    private DimensionWindow(){
      //intializes fiels
      a = new JTextArea();
      b = new JTextArea();
      labelX = new JButton("Enter number of columns:");
      labelY = new JButton("Enter number of rows:");
      title = new JButton("Enter the board dimensions for the next game!");
      enter = new JButton("New Game");
      //formats the window
      structure = new JPanel(new GridLayout(2, 2));
      structure.add(labelX);
      structure.add(a);
      structure.add(labelY);
      structure.add(b);
      //assigns action listener and color
      enter.addActionListener(new ActionListener(){
        
        /**
         * this is what happens when enter gets clicked!
         * @param e - the address of the clicked button, I don't use this value
         */
        public void actionPerformed(ActionEvent e){
          //stores the text from both areas into an array
          String[] args = new String[2];
          args[0] = a.getText();
          args[1] = b.getText();
          //sets old windows invisible
          Reversi.this.setVisible(false);
          setVisible(false);
          Reversi.main(args);
          Reversi.this.dispose();
        }
        
      });
      enter.setBackground(Color.blue);
      //creates the window
      Container c = this.getContentPane();
      c.add(title, "North");
      c.add(structure, "Center");
      c.add(enter, "South");
      setSize(436,106);
      setVisible(true);
    }
  }
}