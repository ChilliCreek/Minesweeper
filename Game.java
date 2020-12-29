import java.util.*;

class Game{
   public static void main(String[] args){
      Scanner scnr = new Scanner(System.in);
      int width, height, startX, startY, totalNumOfTiles;
      System.out.println("Welcome to Minesweeper by Chinguun");
      System.out.println("Hidden tiles are represented by the \"?\" symbol");
      System.out.println("Please choose the tile you wish to reveal by its X and Y coordinates like 5 7 R, and entering F for flagging and R for revealing");
      System.out.println("But first, please enter the width and height of the rectangle on which you will play, then the coordinates you would like to start at");
      System.out.println("Enter X to quit");
      width = scnr.nextInt();
      height = scnr.nextInt();
      totalNumOfTiles = width * height - 1;
      List<List<Tile>> tiles = new ArrayList<List<Tile>>();
      for (int i = 0; i < height; i++) {
         Tile[] tempTiles = new Tile[width];
         for (int j = 0; j < width; j++) {
            tempTiles[j] = new Tile(i, j);
         }
         tiles.add(Arrays.asList(tempTiles));
      }
      for (List<Tile> row : tiles) {
         for (Tile tile : row) {
             tile.addRandomMine();
         }
      }
      startX = scnr.nextInt();
      startY = scnr.nextInt();
      tiles.get(startX).get(startY).removeMine();
      Tile.neighbourChecker(tiles);
      forestFire(tiles, startX, startY);
      displayTiles(tiles);
      totalNumOfTiles -= Tile.numOfMines;
      while(true){
         String inp = scnr.next();
         if (inp.equals("X")) {
            System.out.println("Goodbye");
            break;
         }
         else {
            int col = scnr.nextInt();
            char flag = scnr.next().charAt(0);
            if (flag == 'F' || flag == 'f'){
               tiles.get(Integer.parseInt(inp)).get(col).flagTile();
            }
            else if (flag == 'R' || flag == 'r') {
               if (tiles.get(Integer.parseInt(inp)).get(col).ifHidden()){
                  if (tiles.get(Integer.parseInt(inp)).get(col).hasMine() == true){
                     System.out.println("You hit a mine. You Lost!");
                     break;
                  }
                  totalNumOfTiles = totalNumOfTiles - forestFire(tiles, Integer.parseInt(inp), col);
               }
               else {
                   System.out.println("The tile is already revealed, please try again");
               }
            }
         }
         if (totalNumOfTiles <= 0) {
            System.out.println("You have won!. Goodbye.");
            break;
         }
         displayTiles(tiles);
      }
   }
   public static void displayTiles(List<List<Tile>> tiles){
      int x = tiles.get(0).size(), y = tiles.size();
      int spacesInFrontForTheLastRow = (int)(Math.log10(y) + 1);
      int spacesInFrontOfThisRow;
      System.out.print(" ");
      spacesInFrontOfThisRow = spacesInFrontForTheLastRow - 1;
      for (int l = 0; l < spacesInFrontOfThisRow; l++){
         System.out.print(" ");
      }
      for (int j = 0; j < x; j++) {
         System.out.print(" " + (j));
      }
      System.out.println(" X");
      for (int i = 0; i < y; i++) {
         if (i != 0) {
            spacesInFrontOfThisRow = spacesInFrontForTheLastRow - (int)(Math.log10(i) + 1);
         }
         else {
            spacesInFrontOfThisRow = spacesInFrontForTheLastRow - (int)(Math.log10(i + 1) + 1);
         }
         for (int l = 0; l < spacesInFrontOfThisRow; l++){
            System.out.print(" ");
         }
         System.out.print("" + i + " ");
         for (int j = 0; j < x; j++) {
            if (j != 0) {
               int spacesBetweenColumns = (int)(Math.log10(j) + 1) - 1;
               for (int l = 0; l < spacesBetweenColumns; l++){
                  System.out.print(" ");
               }
            }
            if (tiles.get(i).get(j).ifFlagged()) {
               //System.out.print("" + tiles.get(i).get(j).returnMinedNeighbours() + " ");
               System.out.print("F ");
            }
            else if (tiles.get(i).get(j).ifHidden()) {
               System.out.print("? ");
            }
            else {
               System.out.print("" + tiles.get(i).get(j).returnMinedNeighbours() + " ");
            }
         }
         System.out.println();
      }
      System.out.println("Y");
   }
   public static int forestFire(List<List<Tile>> tiles, int i, int j) {
      Queue<Tile> q = new LinkedList<>();
      q.add(tiles.get(i).get(j));
      int result = 0;
      while(q.size() > 0){
         if(q.peek().ifHidden()){
            int currX = q.peek().x, currY = q.peek().y;
            if (q.peek().returnMinedNeighbours() == 0) {
               if (currX - 1 >= 0) {
                  q.add(tiles.get(currX - 1).get(currY));
               } 
               if (currX + 1 < tiles.size()) {
                  q.add(tiles.get(currX + 1).get(currY));
               } 
               if (currY - 1 >= 0) {
                  q.add(tiles.get(currX).get(currY - 1));
               } 
               if (currY + 1 < tiles.get(0).size()) {
                  q.add(tiles.get(currX).get(currY + 1));
               } 
            }
            q.peek().reveal();
            result++;
         }
         q.remove();
      }
      return result;
   }
}