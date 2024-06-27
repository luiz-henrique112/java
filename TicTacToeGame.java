package lista;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Insets;
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

   public static boolean isFull = false;
   public static boolean winner = false;
   public static boolean isPlayerX = new Random().nextBoolean();
   public static String winnerText = null;
   public static Text textt = new Text();
   public static Button resetBtn = new Button("restart");
   public static GridPane buttons = new GridPane();
   public static Map<Integer, String> board = new HashMap<>();

   @Override
   public void start(Stage primaryStage) {
      HBox game = new HBox(buttons);
      game.setPrefSize(120, 120);

      HBox text = new HBox();
      text.setPrefSize(120, 20);
      text.getChildren().addAll(resetBtn, textt);
      text.setSpacing(7);

      textt.setFont(new Font(14));
      textt.setText("Winner:");
      resetBtn.setPadding(new Insets(3));
      resetBtn.setOnAction(event -> reset());

      VBox root = new VBox(game, text);
      root.setSpacing(7);
      root.setPadding(new Insets(7));

      int row = 1;
      int col = 0;

      for (int i = 0; i <= 8; i++) {
         board.put(i, "");
      }

      for (Map.Entry<Integer, String> entry : board.entrySet()) {
         String space = entry.getValue();
         int key = entry.getKey();

         String id = Integer.toString(key);
         Button btn = new Button(space);
         btn.setId(id);
         buttons.add(btn, col, row);
         btn.setPrefSize(40, 40);
         btn.setOnAction(event -> btnClicked(btn, buttons));

         col++;
         if (col == 3) {
            col = 0;
            row++;
         }
      }

      Scene scene = new Scene(root, 130, 170);

      primaryStage.setTitle("Tic Tac Toe");
      primaryStage.setScene(scene);
      primaryStage.show();
   }

   public void btnClicked(Button btn, GridPane buttons) {
      int idBtn = Integer.parseInt(btn.getId());

      if (board.get(idBtn).equals("") && isFull == false && winner == false) {
         String playerSymbol = isPlayerX ? "X" : "O";
         board.put(idBtn, playerSymbol);
         btn.setText(playerSymbol);

         isPlayerX = !isPlayerX;

         String result = checkWinner();
         textt.setText(result);
         if (!winnerText.equals(null) && winner == true) {
            disableButtons();
         }

         if (winnerText.equals("Winner: none")) {
            disableButtons();
         }
      }
   }

   public static String checkWinner() {
      if (!isFull && !winner) {
         for (int i = 0; i < board.size(); i++) {
            winnerText = "Winner:";
            if (!board.get(0).equals("") &&
               board.get(0).equals(board.get(1)) &&
               board.get(1).equals(board.get(2))) {
               winnerText = "Winner: " + board.get(0);
               winner = true;
               disableButtons();
            } else if (!board.get(3).equals("") &&
               board.get(3).equals(board.get(4)) &&
               board.get(4).equals(board.get(5))) {
               winnerText = "Winner: " + board.get(3);
               winner = true;
               disableButtons();
            } else if (!board.get(6).equals("") &&
               board.get(6).equals(board.get(7)) &&
               board.get(7).equals(board.get(8))) {
               winnerText = "Winner: " + board.get(6);
               winner = true;
               disableButtons();
            } else if (!board.get(0).equals("") &&
               board.get(0).equals(board.get(3)) &&
               board.get(3).equals(board.get(6))) {
               winnerText = "Winner: " + board.get(0);
               winner = true;
               disableButtons();
            } else if (!board.get(1).equals("") &&
               board.get(1).equals(board.get(4)) &&
               board.get(4).equals(board.get(7))) {
               winnerText = "Winner: " + board.get(1);
               winner = true;
            } else if (!board.get(2).equals("") &&
               board.get(2).equals(board.get(5)) &&
               board.get(5).equals(board.get(8))) {
               winnerText = "Winner: " + board.get(2);
               winner = true;
               disableButtons();
            } else if (!board.get(0).equals("") &&
               board.get(0).equals(board.get(4)) &&
               board.get(4).equals(board.get(8))) {
               winnerText = "Winner: " + board.get(0);
               winner = true;
               disableButtons();
            } else if (!board.get(2).equals("") &&
               board.get(2).equals(board.get(4)) &&
               board.get(4).equals(board.get(6))) {
               winnerText = "Winner: " + board.get(2);
               winner = true;
               disableButtons();
            } 
            if (board.values().stream().noneMatch(v -> v.equals("")) && winner == false) {
               winnerText = "It's a draw!";
               disableButtons();
            }
         }
      }
      if (isFull) {
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

   public void reset() {
      winner = false;
      isFull = false;
      textt.setText("Winner:");

      for (int i = 0; i <= 8; i++) {
         board.put(i, "");
      }

      for (Node node : buttons.getChildren()) {
         if (node instanceof Button) {
            ((Button) node).setDisable(false);
            ((Button) node).setText("");
         }
      }
   }

   public static void main(String[] args) {
      launch(args);
   }
}
