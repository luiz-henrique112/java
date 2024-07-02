import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class BankSystem {

   private static final Map<Integer, Account> accounts = new HashMap<>();
   private static final Scanner scan = new Scanner(System.in);

   public static void main(String[] args) {
      int id = 0;

      while (true) {
         System.out.println("===========================================================================================");
         System.out.println(" Nexus Bank");
         System.out.println(" Hi!");
         System.out.println(" Enter");
         System.out.println(" 'create' to create a new account for you!");
         System.out.println(" 'login' to sign in to your account");
         System.out.println(" 'exit' to exit");
         String answer = scan.next();
      
         switch (answer) {
            case "create":
               System.out.println("\n Hello! Welcome to the Nexus Bank!");
               System.out.println(" Enter your name:");
               String name = scan.next();
      
               System.out.println(" Enter your CPF:");
               String cpf = scan.next();
               id++;
      
               Account account = new Account(name, cpf, id);
               accounts.put(id, account);
               System.out.println("Welcome " + account.getUserName() + ", your account was created successfully!");
               break;
      
            case "login":
               System.out.println("\n Hi! To sign in, enter your name:");
               String name_u = scan.next();
      
               System.out.println(" Enter your CPF:");
               String cpf_u = scan.next();
      
               Account accountt = null;
      
               for (Map.Entry<Integer, Account> entry : accounts.entrySet()) {
                  Account accountEntry = entry.getValue();
      
                  if (accountEntry.getUserName().equals(name_u) && accountEntry.getCpf().equals(cpf_u)) {
                     accountt = accountEntry;
                     break;
                  }
               }
      
               if (accountt != null) {
                  System.out.println(accountt.message());
                  String action = scan.next();
      
                  switch (action) {
                     case "pix":
                        pix(accountt, accounts);
                        break;
                     case "withdraw":
                        withdraw(accountt);
                        break;
                     case "deposit":
                        deposit(accountt);
                        break;
                     default:
                        System.err.println("Invalid action");
                        break;
                  }
      
               } else {
                  System.err.println("User not found");
               }
               break;
      
            case "exit":
               return;
      
            default:
               System.err.println("Invalid option. Please try again.");
               break;
         }
      }
   }

   public static void pix(Account accountt, Map<Integer, Account> accounts) {
      System.out.println("Enter the name of the person:\n");
      String name = scan.next();

      System.out.println("Now, enter the Pix key of this person:\n");
      String cpf = scan.next();

      System.out.println("Enter the value you wanna transfer:\n");
      double value = scan.nextDouble();

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

   public static void deposit(Account accountt){
      System.out.println("How many do you wanna deposit?\n");
      double value = scan.nextDouble();
      accountt.receive(value);
   }

   public static void withdraw(Account accountt){
      System.out.println("How many do you wanna withdraw?\n");
      double value = scan.nextDouble();

      if (value < accountt.getUserBalance()) {
         accountt.setBalance_lose(value);
      } else {
         System.out.println("Insufficient balance");
      }
   }
}

class Account {

   int id;
   double balance;
   String name;
   String cpf;
   String notify = null;
   Account account_n = null;

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
      String notification = "Received " + value + " from " + account_n.getUserName();

      Map<Integer, String> notifications = new HashMap<>();
      notifications.put(getId(), notification);

      for (Map.Entry<Integer, String> entry : notifications.entrySet()) {
         notify = "\n Notifications: " + entry.getValue();
      }
   }

   public String message() {
      String msgLogin = "\n Hi " + getUserName() + "\n Welcome back to the Nexus Bank!\n Enter\n 'pix' for you make a pix for someone\n 'withdraw' for withdraw money\n 'deposit' for deposit money in your Nexus Account  \n Your balance: " + getUserBalance();

      if (notify == null) {
         return msgLogin;
      } else {
         return msgLogin + notify;
      }
   }
}
