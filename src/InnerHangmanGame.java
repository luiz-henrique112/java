
   import java.util.ArrayList;
   import java.util.List;
   import java.util.Random;
   import java.util.Scanner;


   public class InnerHangmanGame {
      public static void main(String[] args) {
         HangmanGame game = new HangmanGame();
         Scanner scanner = new Scanner(System.in);

         System.out.println("Welcome to the Hangman Game!");

         while (!game.isGameOver() && !game.isGameWon()) {
               System.out.println("Word:" + game.getGuessedWord());
               System.out.println("Remaining Attempts " + game.getRemainingAttempts());
               System.out.println("Type a letter:");
               char guess = scanner.next().charAt(0);


               if (game.guessLetter(guess)) {
                  System.out.println("Correct!");
               } else {
                  System.out.println("Wrong!");
               }
         }

         if (game.isGameWon()){
               System.out.println("Congratulations! The word was:" + game.getGuessedWord());
         } else {
               System.out.println("You lost! The word was:" + game.getGuessedWord());
         }
         
         scanner.close();
      }
   }

   class HangmanGame{
      static String[] words = {
         "abjures",
         "bigorna",
         "cabelos",
         "domique",
         "figuero",
         "galheiro",
         "julgados",
         "lazares",
         "meditros",
         "orquides",
         "pedrais",
         "quebramos",
         "retalho",
         "sabidura",
         "tombares",
         "umbrela",
         "ventral",
         "xantros"
      };

      private String selectedWord;
      private char[] guessedWord;
      private List<Character> incorrectGuesses;
      private int remainingAttempts;

      public HangmanGame() {
         Random random = new Random();
         this.selectedWord = words[random.nextInt(words.length)];
         this.guessedWord = new char[selectedWord.length()];
         for (int i = 0; i < guessedWord.length; i++) {
               guessedWord[i] = '_';
         }
         this.incorrectGuesses = new ArrayList<>();
         this.remainingAttempts = 6; // Número típico de tentativas na forca
      }

      public String getGuessedWord(){
         return new String(guessedWord);
      }

      public List<Character> getIncorrectGuesses(){
         return incorrectGuesses;
      }

      public int getRemainingAttempts(){
         return remainingAttempts;
      }

      public boolean isGameWon(){
         return new String(guessedWord).equals(selectedWord);
      }

      public boolean isGameOver() {
         return remainingAttempts <= 0;
      }

      public boolean guessLetter(char guess){
         boolean correctGuess = false;

         for (int i = 0; i < selectedWord.length(); i++) {
               if (selectedWord.charAt(i) == guess) {
                  guessedWord[i] = guess;
                  correctGuess = true;
               }
         }
         
         if (!correctGuess) {
               incorrectGuesses.add(guess);
               remainingAttempts--;
         }
         
         return correctGuess;
      }
   }


      

