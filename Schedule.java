
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
         System.out.println("=================================================================\n Welcome to your Schedule App!\n Enter:\n 'add' for add a new event\n 'events' to see your events\n ");
         String answer = scan.next();

         if (answer.equals("add")) {
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

         if (answer.equals("events")) {
            if (!events.isEmpty()){
               for (Event eventt : events) {
                  System.out.println("\n Event: " + eventt.getName() + "\n Date: " + eventt.getDate() + "\n Id: " + eventt.getId());
               }

               System.out.println("\n Enter:\n 'edit' if you wanna edit an event\n 'exclude' to exclude an event");
               String answerr = scan.next();

               if (answerr.equals("edit")) {
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

               if(answerr.equals("exclude")){
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
            } else {
               System.out.println("\n There is no event :(");
            }
         }

         if (answer.equals("exit")) {
            break;
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