   package lista;

   import java.time.LocalDate;

   import org.controlsfx.control.Notifications;

   import javafx.application.Application;
   import javafx.collections.FXCollections;
   import javafx.collections.ObservableList;
   import javafx.geometry.Insets;
   import javafx.scene.Scene;
   import javafx.scene.control.Alert;
   import javafx.scene.control.Button;
   import javafx.scene.control.ButtonBar;
   import javafx.scene.control.ButtonType;
   import javafx.scene.control.DatePicker;
   import javafx.scene.control.Dialog;
   import javafx.scene.control.Label;
   import javafx.scene.control.ListCell;
   import javafx.scene.control.ListView;
   import javafx.scene.control.TextField;
   import javafx.scene.control.Tooltip;
   import javafx.scene.input.KeyCode;
   import javafx.scene.layout.HBox;
   import javafx.scene.layout.Priority;
   import javafx.scene.layout.VBox;
   import javafx.stage.Stage;
   import javafx.util.Callback;


   public class Todolist extends Application {
      public static void main(String[] args) {
         launch(args);
      }

      private ListView<Task> listView;

      @Override
      public void start(Stage primaryStage) {
         primaryStage.setTitle("ToDoList");

         // Vertical alignment
         VBox root = new VBox();
         root.setPadding(new Insets(10));
         root.setSpacing(10);

         String css = this.getClass().getResource("styles.css").toExternalForm();
         root.getStylesheets().add(css);

         // Horizontal alignments
         HBox inputBox = new HBox();
         inputBox.setSpacing(10);

         HBox buttons = new HBox();
         buttons.setSpacing(15);

         // Lists
         ObservableList<Task> taskList = FXCollections.observableArrayList();
         listView = new ListView<>(taskList);

         // Customize the ListView to show tooltips
         listView.setCellFactory(new Callback<ListView<Task>, ListCell<Task>>() {
               @Override
               public ListCell<Task> call(ListView<Task> listView) {
                  return new ListCell<Task>() {
                     @Override
                     protected void updateItem(Task task, boolean empty) {
                           super.updateItem(task, empty);
                           if (task != null) {
                              setText(task.toString());
                              Tooltip tooltip = new Tooltip(task.getDescription());
                              setTooltip(tooltip);
                           } else {
                              setText(null);
                              setTooltip(null);
                           }
                     }
                  };
               }
         });
         // Buttons
         Button removeButton = new Button("Remove a task");
         removeButton.setOnAction(e -> deleteTask(listView, taskList));

         Button editTask = new Button("Edit task");
         editTask.setOnAction(e -> showDialogBoxEditing(taskList, listView));

         Button markFinish = new Button("Mark as completed");
         markFinish.setOnAction(e -> markTaskCompleted(listView));

         Button addButton = new Button("Add a new task");
         addButton.setOnAction(e -> showDialogBoxAdding(taskList));

         VBox.setVgrow(listView, Priority.ALWAYS);
         buttons.getChildren().addAll(addButton, removeButton, editTask, markFinish);
         root.getChildren().addAll(listView, buttons);

         Scene scene = new Scene(root, 400, 300);
         primaryStage.setScene(scene);
         primaryStage.show();
      }

      public Task showDialogBoxAdding(ObservableList<Task> taskList) {
         Dialog<Task> dialog = new Dialog<>();
         dialog.setTitle("Add the new task");

         Label labelTitle = new Label("Title:");
         TextField titleField = new TextField();
         Label labelDescription = new Label("Description:");
         TextField descriptionField = new TextField();
         Label dateLabel = new Label("Maximum Date:");
         DatePicker datePicker = new DatePicker();

         VBox alignment = new VBox(10, labelTitle, titleField, labelDescription, descriptionField, dateLabel, datePicker);
         alignment.setPadding(new Insets(10));

         ButtonType okButton = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);

         dialog.getDialogPane().setContent(alignment);
         dialog.getDialogPane().getButtonTypes().addAll(okButton, ButtonType.CANCEL);

         descriptionField.setOnKeyPressed(e -> { // Use setOnKeyPressed instead of setOnAction
               if (e.getCode() == KeyCode.ENTER) {
                  dialog.showAndWait().ifPresent(task -> taskList.add(task));
               }
         });

         dialog.setResultConverter(dialogButton -> {
               if (dialogButton == okButton) {
                  return new Task(titleField.getText(), descriptionField.getText(), datePicker.getValue(), false);
               }
               return null;
         });

         dialog.showAndWait().ifPresent(task -> taskList.add(task)); 
         Notifications.create()
         .title("Task created with success!")
         .text("The task '" + titleField.getText() + "' have been created with success!")
         .darkStyle()
         .show(); // Add the Task object directly to the taskList
         return null;
      }

      public void showDialogBoxEditing(ObservableList<Task> taskList, ListView<Task> listview){
         Task selectedTask = listView.getSelectionModel().getSelectedItem();
         if (selectedTask == null) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("WARNING");
               alert.setHeaderText(null);
               alert.setContentText("Please, write a valid task name.");
               alert.showAndWait();
               return;
         }

         Dialog<Task> dialog = new Dialog<>();
         dialog.setTitle("Edit a task");

         Label titleLabel = new Label("Title:");
         TextField titleField = new TextField(selectedTask.getTitle());
         Label descriptionLabel = new Label("Description:");
         TextField descriptionField = new TextField(selectedTask.getDescription());
         Label dateLabel = new Label("Date:");
         DatePicker dateField = new DatePicker(selectedTask.getDate());

         VBox alignment = new VBox(10, titleLabel, titleField, descriptionLabel, descriptionField, dateLabel, dateField);
         alignment.setPadding(new Insets(10));

         dialog.getDialogPane().setContent(alignment);

         ButtonType saveButton = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
         dialog.getDialogPane().getButtonTypes().addAll(saveButton, ButtonType.CANCEL);

         dialog.setResultConverter(dialogButton -> {
               if (dialogButton == saveButton) {
                  selectedTask.setTitle(titleField.getText());
                  selectedTask.setDescription(descriptionField.getText());
                  selectedTask.setDate(dateField.getValue());
                  return selectedTask;
               }
               
               return null;
         });

         dialog.showAndWait().ifPresent(task -> listView.refresh());

         Notifications.create()
               .title("Task edited with success!")
               .darkStyle()
               .show();
      }

      public void deleteTask(ListView<Task> listview, ObservableList<Task> taskList) {
         Task selectedTask = listview.getSelectionModel().getSelectedItem();

         if (selectedTask == null) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("WARNING");
               alert.setHeaderText(null);
               alert.setContentText("Please, write a valid task name.");
               alert.showAndWait();
         } else {
               Notifications.create()
               .title("Task excluded!")
               .text("You excluded the task '" + selectedTask.getTitle() + "'.")
               .darkStyle()
               .show();

               taskList.remove(selectedTask);
         }
      }

      public Task markTaskCompleted(ListView<Task> listview) {
         Task selectedTask = listview.getSelectionModel().getSelectedItem();

         if (selectedTask == null) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("WARNING");
               alert.setHeaderText(null);
               alert.setContentText("Please, select a task to mark as completed");
               alert.showAndWait();
         } else {
         Notifications.create()
               .title("Task completed!")
               .text("You completed the task '" + selectedTask.getTitle() + "', congratulations!")
               .darkStyle()
               .show();
               selectedTask.markAsFinished();
               listview.refresh();
               return selectedTask;
         }

         return null;
      }
   }

   class Task {
      private String title;
      private String description;
      private LocalDate date;
      private boolean finished;

      public Task(String title, String description, LocalDate date, boolean finished) {
         this.title = title;
         this.description = description;
         this.date = date;
         this.finished = finished;
      }

      public String getTitle() {
         return title;
      }

      public void setTitle(String title) {
         this.title = title;
      }

      public String getDescription() {
         return description;
      }

      public void setDescription(String description) {
         this.description = description;
      }

      public LocalDate getDate() {
         return date;
      }

      public void setDate(LocalDate date) {
         this.date = date;
      }

      public boolean isFinished() {
         return finished;
      }

      public void setFinished(boolean finished) {
         this.finished = finished;
      }

      public void markAsFinished() {
         this.finished = true;
         if (date != null) {
               this.title = title + " (Date: " + date + ") \u2705";
         } else {
               this.title = title + " \u2705";
         }
      }

      @Override
      public String toString() {
         if (date != null) {
               return title + " (Date: " + date + ")";
         } else {
               return title;
         }
      }
   }

