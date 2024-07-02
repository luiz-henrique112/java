import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Schedule {
   public static Scanner scan = new Scanner(System.in);
   public static List<Event> events = new ArrayList<>();
   public static Event event = null;
   public static int id = 0;

   public static void main(String[] args) {

      while (true) {
         System.out.println("=================================================================");
         System.out.println(" Welcome to your Schedule App!");
         System.out.println(" Enter:");
         System.out.println(" 'add' for add a new event");
         System.out.println(" 'events' to see your events");
         System.out.println(" 'exit' to exit");
         String answer = scan.next();

         switch (answer) {
            case "add":
               addEvent();
               break;
            case "events":
               viewEvents();
               break;
            case "exit":
               return;
            default:
               System.out.println("Invalid option. Please try again.");
         }
      }
   }

   public static void viewEvents(){
      if (!events.isEmpty()){
         for (Event event : events) {
            System.out.println("\nEvent: " + event.getName());
            System.out.println("Date: " + event.getDate());
            System.out.println("Id: " + event.getId());
         }

         System.out.println("\nEnter:");
         System.out.println(" 'edit' if you want to edit an event");
         System.out.println(" 'exclude' to exclude an event");

         String answerr = scan.next();

         switch (answerr) {
            case "edit":
               edit();
               break;
               
            case "exclude":
               exclude();
               break;
            default:
               break;
            }
         } else {
            System.out.println("\n There is no event :(");
         }
   }

   public static void addEvent(){
      System.out.println("\n Enter the event name:");
      String name = scan.next();

      System.out.println("\n Enter the date of the event ('dd/MM/yyyy'):");

      try {
         String dateInput = scan.next();
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
         LocalDate date = LocalDate.parse(dateInput, formatter);
         event = new Event(name, date, id);
         events.add(event);
         id++;
         System.out.println("\n Event added!");
      } catch (DateTimeParseException e) {
         System.out.println("Invalid date format, please use the 'dd/MM/yyyy' format");
      }
   }

   public static void edit(){
      System.out.println("\n Enter the name of the event you wanna edit:");
      String name_e = scan.next();

      System.out.println("\n Enter the Id of the event you wanna edit");
      int id_e = scan.nextInt();

      for (Event eventt : events){
         if (name_e.equals(eventt.getName()) && id_e == eventt.getId()) {
            System.out.println("\n Type the new name of the event:");
            String newName = scan.next();
            System.out.println("\n Now enter the new date of this event:");
            
            try {
               String newDateInput = scan.next();
               DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
               LocalDate newDate = LocalDate.parse(newDateInput, formatter);

               eventt.setName(newName);
               eventt.setDate(newDate);
               System.out.println("\n Event edited with success!");
            } catch (DateTimeParseException e) {
               System.out.println("Invalid date format, please use the 'dd/MM/yyyy' format");
            }
         } else {
            System.out.println("\n Event not found");
         }
      }
   }

   public static void exclude(){
      System.out.println("\n Enter the name of the event you wanna exclude:");
      String name_ex = scan.next();

      System.out.println("\n Enter the Id of the event you wanna exclude:");
      int id_ex = scan.nextInt();

      for (Event eventt : events) {
         if (name_ex.equals(eventt.getName()) && id_ex == eventt.getId()){
            events.remove(eventt);
            System.out.println("\n Event excluded with success!");
         }
      }
   }
}

class Event{
   String name;
   LocalDate date;
   int id;

   public Event(String name, LocalDate date, int id){
      this.name = name;
      this.date = date;
      this.id = id;
   }

   public String getName(){
      return this.name;
   }

   public LocalDate getDate(){
      return this.date;
   }

   public int getId(){
      return this.id;
   }

   public void setDate(LocalDate newDate){
      this.date = newDate;
   }

   public void setName(String newName){
      this.name = newName;
   }
}
