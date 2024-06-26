   package lista;
   import javafx.application.Application;
   import javafx.geometry.Insets;
   import javafx.geometry.Pos;
   import javafx.scene.Scene;
   import javafx.scene.control.Button;
   import javafx.scene.control.TextField;
   import javafx.scene.layout.GridPane;
   import javafx.stage.Stage;

   public class Calculator extends Application{
      private final TextField display = new TextField();
      public double num1 = 0;
      private boolean start = true;
      private String operator = "";

      @Override

      public void start(Stage primaryStage){
         display.setEditable(false);
         display.setAlignment(Pos.CENTER_RIGHT);

         GridPane raiz = new GridPane();
         raiz.setPadding(new Insets(10));
         raiz.setVgap(5);
         raiz.setHgap(5);
         raiz.add(display, 0,0,4,1);

         String[] botoes = {
               "7", "8", "9", "/",
               "4", "5", "6", "*",
               "1", "2", "3", "-",
               "0", "C", "=", "+"
         };

      int row  = 1;
      int col  = 0;

      for (String label : botoes) {
         Button button = new Button(label);
         button.setPrefSize(50, 50); 
         button.setOnAction(e -> botaoClicado(label)); 
         raiz.add(button, col, row); 

         col++;
         if (col == 4) { 
               col = 0;
               row++;
         }
      }

      Scene scene = new Scene(raiz, 220, 250);
      primaryStage.setScene(scene);
      primaryStage.setTitle("Calculadora JavaFX");
      primaryStage.show();

      }

      private void botaoClicado(String label) {
         if (label.matches("[0-9]")) { 
               if (start) {
                  display.clear();
                  start = false;
               }
               display.appendText(label);

         } else if (label.matches("[+\\-*/]")) {
               num1 = Double.parseDouble(display.getText()); 
               operator = label; 
               start = true; 

         } else if (label.equals("=")) {
               double secondNumber = Double.parseDouble(display.getText()); 
               double result = calculate(num1, secondNumber, operator); 
               display.setText(String.valueOf(result)); 
               start = true; 

         } else if (label.equals("C")) { 
               display.clear(); 
               num1 = 0; 
               operator = ""; 
               start = true; 
         }
      }
      
      private double calculate(double num1, double secondNumber, String operator){

         switch (operator) {
               case "+":
                  return num1  + secondNumber;
               case "-":
                  return num1  - secondNumber;
               case "*":
                  return num1  * secondNumber;
               case "/":
                  if (secondNumber != 0) {
                     return num1 / secondNumber;
                  } else {
                     return 0;
                  }
               default:
                  return 0;
         }

      }

      public static void main(String[] args) {
               launch(args);
      }
         
   }
      
