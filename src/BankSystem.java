import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


public class BankSystem {

   private static Map<Integer, Account> accounts = new HashMap<>();
   private static Scanner scan = new Scanner(System.in); // Scanner movido para fora do loop

   public static void main(String[] args) {
      int id = 0;

      while (true) {
         System.out.println("===========================================================================================\n Nexus Bank\n Hi!\n Enter\n 'create' for create a new account for you!\n 'login' for you sign in in your account");
         String answer = scan.next();

         if (answer.equals("create")) {
            System.out.println("\n Hello! Welcome to the Nexus Bank!\n Enter your name:");
            String name = scan.next();

            System.out.println("\n Enter your CPF:");
            String cpf = scan.next();
            id++;

            Account account = new Account(name, cpf, id);
            accounts.put(id, account);
            System.out.println("Welcome " + account.getUserName() + ", your account was created with success!");

         } else if (answer.equals("login")) {
            System.out.println("\n Hi! \n For you can sign in, enter your name:");
            String name_u = scan.next();

            System.out.println("\n Enter your CPF:");
            String cpf_u = scan.next();

            Account accountt = null;

            for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
               Account account = entry.getValue();

               if (account.getUserName().equals(name_u) && account.getCpf().equals(cpf_u)) {
                  accountt = account;
                  break;
               }
            }

            if (accountt != null) {
               System.out.println("\n Hi " + accountt.getUserName() + "\n Welcome back to the Nexus Bank!\n Enter\n 'pix' for you make a pix for someone\n 'withdraw' for withdraw money\n 'deposit' for deposit money in your Nexus Account\n Your balance: " + accountt.getUserBalance());
               String action = scan.next();

               if (action.equals("pix")) {
                  System.out.println("Enter the name of the person:\n");
                  String name = scan.next();

                  System.out.println("Now, enter the Pix key of this person:\n");
                  String cpf = scan.next();

                  System.out.println("Enter the value you wanna transfer:\n");
                  double value = scan.nextDouble();

                  pix(accountt, accounts, name, cpf, value);
               }

               if (action.equals("withdraw")) {
                  System.out.println("How many do you wanna withdraw?\n");
                  double value = scan.nextDouble();

                  if (value < accountt.getUserBalance()) {
                     accountt.setBalance_lose(value);
                  } else {
                     System.out.println("Insufficient balance");
                  }
               }

               if (action.equals("deposit")) {
                  System.out.println("How many do you wanna deposit?\n");
                  double value = scan.nextDouble();

                  accountt.receive(value);
               }

            } else {
               System.err.println("User not found");
            }
         }

         if (answer.equals("exit")) {
            break;
         }
      }
   }

   public static void pix(Account accountt, Map<Integer, Account> accounts, String name, String cpf, double value) {

      if (accountt.getUserBalance() >= value) {
         for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
            Account receiver = entry.getValue();

            if (receiver.getUserName().equals(name) && receiver.getCpf().equals(cpf)) {
               receiver.receive(value);
               accountt.setBalance_lose(value);
               System.out.println("Transfer successful.");
               return;
            }
         }
         System.err.println("User not found");
      }
   }

   
}


class Account {

      int id;
      double balance;
      String name;
      String cpf;

      public Account(String name, String cpf, int id) {
         this.id = id;
         this.balance = 50.0;
         this.name = name;
         this.cpf = cpf;
      }

      public String getUserName() {
         return this.name;
      }

      public double getUserBalance() {
         return this.balance;
      }

      public String getCpf() {
         return this.cpf;
      }

      public int getId() {
         return this.id;
      }

      public void setBalance_lose(double value) {
         this.balance -= value;
      }

      public void receive(double value) {
         this.balance += value;
      }
   }