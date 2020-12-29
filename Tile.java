import java.util.*;

class Tile{
   public int x;
   public int y;
   private boolean hasMine;
   private boolean isHidden;
   private boolean isFlagged;
   private int neighboursWithMine;
   public static int numOfMines = 0;
   public Tile(int x, int y){
      hasMine = false;
      isHidden = true;
      isFlagged = false;
      neighboursWithMine = 0;
      this.x = x;
      this.y = y;
   }
   public void addRandomMine(){
      if(Math.random() <= 0.15){
         hasMine = true;
         numOfMines++;
      }
   }
   public void reveal(){
      isHidden = false;
   }
   public boolean ifHidden(){
      return isHidden;
   }
   public boolean hasMine(){
      return hasMine;
   }
   public void removeMine(){
      hasMine = false;
   }
   public void increaseMinedNeighbours(){
      neighboursWithMine++;
   }
   public int returnMinedNeighbours(){
      return neighboursWithMine;
   }
   public boolean ifFlagged(){
      return isFlagged;
   }
   public void flagTile(){
      isFlagged = true;
   }
   public static void neighbourChecker(List<List<Tile>> tiles){
      int x = tiles.get(0).size(), y = tiles.size();
      for (int i = 0; i < y; i++) {
         for (int j = 0; j < x; j++) {
            if (i - 1 >= 0) {
               if (j - 1 >= 0 && tiles.get(i - 1).get(j - 1).hasMine()) {
                  tiles.get(i).get(j).increaseMinedNeighbours();
               }
               if (j + 1 < x && tiles.get(i - 1).get(j + 1).hasMine()) {
                  tiles.get(i).get(j).increaseMinedNeighbours();
               }        
               if(tiles.get(i - 1).get(j).hasMine()){
                  tiles.get(i).get(j).increaseMinedNeighbours();
               } 
            }
            if (i + 1 < y) {
               if (j - 1 >= 0 && tiles.get(i + 1).get(j - 1).hasMine()) {
                  tiles.get(i).get(j).increaseMinedNeighbours();
               }
               if (j + 1 < x && tiles.get(i + 1).get(j + 1).hasMine()) {
                  tiles.get(i).get(j).increaseMinedNeighbours();
               }     
               if(tiles.get(i + 1).get(j).hasMine()){
                  tiles.get(i).get(j).increaseMinedNeighbours();
               }    
            }
            if (j - 1 >= 0 && tiles.get(i).get(j - 1).hasMine()) {
               tiles.get(i).get(j).increaseMinedNeighbours();
            }
            if (j + 1 < x && tiles.get(i).get(j + 1).hasMine()) {
               tiles.get(i).get(j).increaseMinedNeighbours();
            }
         } 
      }
   }
}