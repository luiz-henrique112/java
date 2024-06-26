package lista;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TicTacToeGame extends Application {
      
   public static Map<Integer, String> board = new HashMap<>(); 
   public static boolean isFull = false;
   public static boolean isPlayerX = new Random().nextBoolean();
   public static boolean winner = false;
   public static Text textt = new Text();
   public static GridPane buttons = new GridPane();
   public static String winnerText = null;
      

   @Override
   public void start(Stage primaryStage) {
      
      HBox game = new HBox(buttons);
      game.setPrefSize(200, 200);
      HBox text = new HBox();
      text.setPrefSize(200, 50);
      text.getChildren().add(textt);

      textt.setFont(new Font(30));
      

      VBox root = new VBox(game,text);
      
      int row = 1;
      int col = 0;
         
      for (int i = 0; i <= 8; i++) {
         board.put(i,"");
      }

      for (Map.Entry<Integer, String> entry : board.entrySet()) {
         String space = entry.getValue();
         int key = entry.getKey();
         
         String id = Integer.toString(key);
         Button btn = new Button(space);
         btn.setId(id);
         buttons.add(btn, col, row);
         btn.setPrefSize(70,70);
         btn.setOnAction(event -> btnClicked(btn, buttons));

         col++;
         if (col == 3) {
            col = 0;
            row++;
         }
      }
         //String css = this.getClass().getResource("styles.css").toExternalForm();
      Scene scene = new Scene(root, 200, 200);
         //scene.getStylesheets().add(css);

      primaryStage.setTitle("Tic Tac Toe");
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   public void btnClicked(Button btn, GridPane buttons){
      int idBtn = Integer.parseInt(btn.getId());
      
      

      if (board.get(idBtn).equals("") && isFull == false && winner == false) {
         String playerSymbol = isPlayerX ? "X" : "O";
         board.put(idBtn, playerSymbol); 
         btn.setText(playerSymbol); 
         
         
         isPlayerX = !isPlayerX;
         

         String result = checkWinner();
         if (!winnerText.equals(null) && winner == true ) {
            textt.setText(result);
            disableButtons();
         }
      } 
   }

   public static String checkWinner(){
      winnerText = "Winner: none";
      

      if (!isFull && !winner) {
         for (int i = 0; i < board.size(); i++) {
            
            //horizontals
            if (!board.get(0).equals("") &&
               board.get(0).equals(board.get(1)) &&
               board.get(1).equals(board.get(2))) {

               winnerText = "Winner: " + board.get(0);
               winner = true;
               disableButtons();
            } 
            else if (!board.get(3).equals("") &&
               board.get(3).equals(board.get(4)) &&
               board.get(4).equals(board.get(5))) {

               winnerText = "Winner: " + board.get(3);
               winner = true;
               disableButtons();
            } 
            else if (!board.get(6).equals("") &&
               board.get(6).equals(board.get(7)) &&
               board.get(7).equals(board.get(8))) {

               winnerText = "Winner: " + board.get(6);
               winner = true;
               disableButtons();
            }
           
           // Verticais
            else if (!board.get(0).equals("") &&
               board.get(0).equals(board.get(3)) &&
               board.get(3).equals(board.get(6))) {

               winnerText = "Winner: " + board.get(0);
               winner = true;
               disableButtons();
            } 
            else if (!board.get(1).equals("") &&
               board.get(1).equals(board.get(4)) &&
               board.get(4).equals(board.get(7))) {

               winnerText = "Winner: " + board.get(1);
               winner = true;
            } 
            else if (!board.get(2).equals("") &&
               board.get(2).equals(board.get(5)) && 
               board.get(5).equals(board.get(8))) {

               winnerText = "Winner: " + board.get(2);
               winner = true;
               disableButtons();
            }
   
           // Diagonais
            else if (!board.get(0).equals("") && 
               board.get(0).equals(board.get(4)) && 
               board.get(4).equals(board.get(8))) {

               winnerText = "Winner: " + board.get(0);
               winner = true;
               disableButtons();
            } 
            else if (!board.get(2).equals("") && 
               board.get(2).equals(board.get(4)) && 
               board.get(4).equals(board.get(6))) {

               winnerText = "Winner: " + board.get(2);
               winner = true;
               disableButtons();
            } else {
               
            }
            if (board.values().stream().noneMatch(v -> v.equals("")) && winnerText.equals("Winner: none")) {
               disableButtons();
               winnerText = "Winner: none";
            }
         }
      } 

      
      
      if (isFull){
         disableButtons();
      }
   
   return winnerText;
   }

   public static void disableButtons() {
      for (Node node : buttons.getChildren()) {
         if (node instanceof Button) {
            ((Button) node).setDisable(true);
         }
      }
  }


   public static void main(String[] args) {
         launch(args);
               System.out.println(board);
               System.out.println(checkWinner());
   }}

   