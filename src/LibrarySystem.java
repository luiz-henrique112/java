import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LibrarySystem {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        List<Post> postsBdD = new ArrayList<>();

        while (true) {
            System.out.print("======================\nCommands:\n'add' to add a new post\n'book' to see if your book is available\n'posts' to see all the posts made\n'exit' to exit the program:\n");
            String command = scan.next();

            if (command.equals("add")) {
                System.out.print("Enter the name of the book:\n");
                String book = scan.next();

                System.out.print("Enter the name of your user:\n");
                String user = scan.next();

                Post post = new Post(book, user);
                postsBdD.add(post);
                System.out.println("Post added successfully.");

            } else if (command.equals("book")) {
                System.out.print("Enter the name of the book you want:\n");
                String book2 = scan.next();

                boolean found = false;
                for (Post post : postsBdD) {
                    if (post.getBookName().equalsIgnoreCase(book2)) {
                        System.out.println("The book " + book2 + " is borrowed by " + post.getUserName());
                        System.out.println("The book isn't available\nEnter 'set' to set this book available\nEnter 'back' to go back");

                        String answer = scan.next();
                        if (answer.equals("set")) {
                            postsBdD.remove(post);
                            System.out.println("Book set as available.");
                        }
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    System.out.println("The book " + book2 + " is available.");
                }

            } else if (command.equals("posts")) {
                for (Post post : postsBdD) {
                    System.out.println("Book: " + post.getBookName() + ", User: " + post.getUserName());
                }

            } else if (command.equals("exit")) {
                break;
            } else {
                System.out.println("Invalid command. Please try again.");
            }
        }
        scan.close();
    }
}

class Post {
    private String book;
    private String user;

    public Post(String book, String user) {
        this.book = book;
        this.user = user;
    }

    public String getBookName() {
        return this.book;
    }

    public String getUserName() {
        return this.user;
    }
}