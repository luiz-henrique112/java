package lista;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class StoreSystem extends Application {
   private static BooleanProperty validEmail = new SimpleBooleanProperty(false);
   private static BooleanProperty validPassword = new SimpleBooleanProperty(false);
   private static BooleanProperty validUser = new SimpleBooleanProperty(false);
   private static Stage primaryStage;
   public static List<User> users = new ArrayList<>();
   public static List<String> currentUser = new ArrayList<>();
   public static List<Product> productss = new ArrayList<>();
   
   public static String screen = "login";
   public static int id = 1;;
   public static int productId = 0;

   @Override
   public void start(Stage primaryStage) {
      StoreSystem.primaryStage = primaryStage;

      // LOGIN SCREEN
      VBox login = new VBox();
      Label labelemail = new Label("Email:\n");
      TextField emailInput = new TextField();
      Label labelPassword = new Label("Password:\n");
      TextField passwordInput = new TextField();
      Label labelUser = new Label("User Name:\n");
      TextField userInput = new TextField();
      Button loginBtn = new Button("Login");
      Button signIn = new Button("Sign In");
      
      VBox inputs = new VBox();
      inputs.getChildren().addAll(labelemail,
                                 emailInput,
                                 labelPassword, 
                                 passwordInput, 
                                 labelUser,
                                 userInput);
      inputs.setSpacing(7);

      HBox buttons = new HBox();
      buttons.getChildren().addAll(loginBtn, signIn);
      buttons.setSpacing(200);

      for (int i = 0; i <= 3; i++) {
         currentUser.add("");
      }

      login.getChildren().addAll(inputs, buttons);
      login.setPadding(new Insets(25));
      login.setSpacing(7);
      
      FlowPane productPane = new FlowPane();
      productPane.setPadding(new Insets(10));
      productPane.setHgap(10);
      productPane.setVgap(10);
      productPane.setStyle("-fx-background-color: #f0f0f0;");

      VBox dashboard = new VBox();
      HBox navigation = new HBox();
      TextField search = new TextField();
      Button addProduct = new Button("Add a product");
      addProduct.setOnAction(event -> showDialogBoxProducts(productPane));
      addProduct.setPrefSize(100, 30);
      Button storeCartBtn = new Button("Cart");
      storeCartBtn.setPrefSize(60, 30);
      Button logout = new Button("Logout");
      logout.setPrefSize(60, 30);
      Button settingsBtn = new Button("Settings");
      settingsBtn.setPrefSize(60, 30);

      HBox buttons_n = new HBox(storeCartBtn, addProduct, logout, settingsBtn);
      buttons_n.setSpacing(15);

      VBox products = new VBox(productPane);
      
      navigation.setSpacing(60);
      navigation.getChildren().addAll(search, buttons_n);

      dashboard.getChildren().addAll(navigation, products);
      dashboard.setPadding(new Insets(10));

      VBox settings = new VBox();
      Label label_email = new Label("E-MAIL:");
      Text textEmail = new Text();
      Label label_password = new Label("PASSWORD:");
      Text textPassword = new Text();
      Label label_user = new Label("USER NAME:");
      Text textUser = new Text();
      Button change_password_btn = new Button("Change password");
      change_password_btn.setOnAction(event -> showDialogBoxEdit("password", textEmail, textPassword, textUser));
      Button change_user_btn = new Button("Change user name");
      change_user_btn.setOnAction(event -> showDialogBoxEdit("user", textEmail, textPassword, textUser));
      Button home = new Button("Home");
      
      label_email.setFont(new Font(15));
      label_password.setFont(new Font(15));
      label_user.setFont(new Font(15));
      textEmail.setFont(new Font(12));
      textPassword.setFont(new Font(12));
      textUser.setFont(new Font(12));

      VBox text_email = new VBox(label_email, textEmail);
      text_email.setSpacing(2);

      VBox text_password = new VBox(label_password, textPassword);
      text_password.setSpacing(2);

      VBox text_user = new VBox(label_user, textUser);
      text_user.setSpacing(2);

      VBox texts = new VBox(text_email,
                           text_password,
                           text_user);
      texts.setSpacing(14);

      HBox buttons_s = new HBox();
      buttons_s.setSpacing(20);
      buttons_s.getChildren().addAll(change_password_btn, change_user_btn, home);

      
      FlowPane productcart = new FlowPane();
      productcart.setPadding(new Insets(10));
      productcart.setHgap(10);
      productcart.setVgap(10);
      productcart.setStyle("-fx-background-color: #f0f0f0;");
      VBox cart = new VBox(home, productcart);
      
      settings.getChildren().addAll(texts, buttons_s);
      settings.setSpacing(25);
      settings.setPadding(new Insets(15));

      Scene scene_login = new Scene(login, 350, 250);
      Scene scene_dashboard = new Scene(dashboard, 550, 500);
      Scene scene_settings = new Scene(settings, 350, 245);
      Scene scene_cart = new Scene(cart, 400, 400);

      loginBtn.setDisable(true);
      loginBtn.setOnAction(event -> {
         login(scene_dashboard, scene_dashboard, scene_settings, scene_cart, true, userInput, emailInput, passwordInput);
      });

      signIn.setDisable(true);
      signIn.setOnAction(event -> {
         login(scene_dashboard, scene_dashboard, scene_settings, scene_cart, false, userInput, emailInput, passwordInput);
      });

      logout.setOnAction(event -> {
         emailInput.clear();
         passwordInput.clear();
         userInput.clear();
         switchScene(scene_login, scene_login, scene_settings, scene_cart, "login");
      });

      settingsBtn.setOnAction(event -> {
         setUserData(textEmail, textPassword, textUser);
         switchScene(scene_dashboard, scene_login, scene_settings, scene_cart, "settings");
      });
      
      home.setOnAction(event -> switchScene(scene_dashboard, scene_login, scene_settings, scene_cart, "dashboard"));

      storeCartBtn.setOnAction(event -> {
         for (User user : users) {
            if (user.getEmail().equals(currentUser.get(0)) &&
               user.getPassword().equals(currentUser.get(1)) &&
               user.getUserName().equals(currentUser.get(2))){

               updateProductPane(productcart, user.getCart());
            }
         }
         switchScene(scene_dashboard, scene_login, scene_settings, scene_cart, "cart");
      });

      emailInput.textProperty().addListener((observable, oldValue, newValue) -> {
         validEmail.set(newValue.matches("[^@\\s]+@[^@\\s]+\\.[^@\\s]+"));
         emailInput.setStyle(validEmail.get() ? "-fx-border-color: green;" : "-fx-border-color: red;");

         if (newValue.isEmpty()) {
            validEmail.set(false);
         }
      });

      passwordInput.textProperty().addListener((observable, oldValue, newValue) -> {
         validPassword.set(newValue.length() >= 8);
         passwordInput.setStyle(validPassword.get() ? "-fx-border-color: green;" : "-fx-border-color: red;");

         if (newValue.isEmpty()) {
            validPassword.set(false);
            loginBtn.setDisable(true);
            signIn.setDisable(true);
         }
      });

      userInput.textProperty().addListener((observable, oldValue, newValue) -> {
         boolean isValid = true;

         if (newValue.isEmpty() || newValue.length() < 4) {
            isValid = false;
            userInput.setStyle("-fx-border-color: red;");
         }

         if (isValid) {
            userInput.setStyle("-fx-border-color: green;");
            validUser.set(isValid);
         } else if (!isValid && !newValue.isEmpty()) {
            userInput.setStyle("-fx-border-color: red;");
            validUser.set(isValid);
         }
      });

      validEmail.addListener((obs, oldVal, newVal) -> setButtonAble(loginBtn, signIn));
      validPassword.addListener((obs, oldVal, newVal) -> setButtonAble(loginBtn, signIn));
      validUser.addListener((obs, oldVal, newVal) -> setButtonAble(loginBtn, signIn));

      switch (screen) {
         case "login" -> {
            primaryStage.setScene(scene_login);
         }
         case "dashboard" -> {
            primaryStage.setScene(scene_dashboard);
         }
         case "settings" -> {
            primaryStage.setScene(scene_settings);
         }
         case "cart" -> {
            primaryStage.setScene(scene_cart);
         }
      }
      
      primaryStage.setTitle("Stock Management System");
      primaryStage.show();
   }

   public static void login(Scene scene_dashboard, Scene scene_login, Scene scene_settings, Scene scene_cart, boolean isLogin, TextField userInput, TextField emailInput, TextField passwordInput) {
      String email = emailInput.getText().trim();
      String password = passwordInput.getText().trim();
      String userName = userInput.getText().trim();

      if (!isLogin) {
         handleSignUp(email, password, userName, scene_dashboard, scene_login, scene_settings, scene_cart, userInput);
      } else if (isLogin) {
         handleLogin(email, password, userName, scene_dashboard, scene_login, scene_settings, scene_cart);
      }
   }

   private static void handleSignUp(String email, String password, String userName, Scene scene_dashboard, Scene scene_login, Scene scene_settings, Scene scene_cart, TextField userInput) {
      if (users.isEmpty()) {
         User user = new User(email, password, userName, "0");
         users.add(user);
         setCurrentUser(user);
         switchScene(scene_dashboard, scene_login, scene_settings, scene_cart, "dashboard");
      } else {
         for (User user : users) {
            if (user.getUserName().equals(userName) || user.getEmail().equals(email)) {
               showAlert(userInput);
               break;
            } else {
               User newUser = new User(email, password, userName, Integer.toString(id));
               id++;
               users.add(newUser);
               setCurrentUser(newUser);
               switchScene(scene_dashboard, scene_login, scene_settings, scene_cart, "dashboard");
               break;
            }
         }
      }
   }
   
   private static void handleLogin(String email, String password, String userName, Scene scene_dashboard, Scene scene_login, Scene scene_settings, Scene scene_cart){
      for (User user : users) {
         if (user.getEmail().equals(email) &&
            user.getPassword().equals(password) &&
            user.getUserName().equals(userName)) {

            setCurrentUser(user);
            switchScene(scene_dashboard, scene_login, scene_settings, scene_cart, "dashboard");
         }
      }
   }

   private static void setCurrentUser(User user){
      currentUser.set(0, user.getEmail());
      currentUser.set(1, user.getPassword());
      currentUser.set(2, user.getUserName());
      currentUser.set(3, user.getId());
   }

   private static void showAlert(TextField userInput) {
      userInput.setStyle("-fx-border-color: red;");
      validUser.set(false);
   
      Alert alert = new Alert(Alert.AlertType.WARNING);
      alert.setTitle("WARNING");
      alert.setHeaderText(null);
      alert.setContentText("This e-mail or user name is already used by another user, please try another one.");
      alert.showAndWait();
   }

   public static void showDialogBoxEdit(String passwordOrUser, Text textEmail, Text textPassword, Text textUser){
      switch (passwordOrUser) {
         case "user" -> {
            Dialog<User> dialog = new Dialog<>();
            dialog.setTitle("Edit user");

            Label label_user_s = new Label("USER NAME:");
            TextField textUser_s = new TextField(currentUser.get(2));
            ButtonType save_s = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            
            VBox alignment = new VBox(label_user_s, textUser_s);
            alignment.setPadding(new Insets(10));
            dialog.getDialogPane().setContent(alignment);
            dialog.getDialogPane().getButtonTypes().addAll(save_s, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
               if (dialogButton == save_s) {
                  for (User user  : users) {
                     if (textUser_s.getText().equals(user.getUserName())) {
                        Alert alert = new Alert(Alert.AlertType.WARNING);
                        alert.setTitle("WARNING");
                        alert.setHeaderText(null);
                        alert.setContentText("This user name is already used by another user, please try another one.");
                        alert.showAndWait();
                     } else {
                        user.setUser(textUser_s.getText().trim());
                        setCurrentUser(user);
                        setUserData(textEmail, textPassword, textUser);
                     }
                  }
               }
               return null;
            });

            dialog.showAndWait();
         }

         case "password" -> {
            Dialog<User> dialog = new Dialog<>();
            dialog.setTitle("Edit password");

            Label label_user_s = new Label("PASSWORD:");
            TextField textPassword_s = new TextField(currentUser.get(1));
            ButtonType save_s = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            
            VBox alignment = new VBox(label_user_s, textPassword_s);
            alignment.setPadding(new Insets(10));
            dialog.getDialogPane().setContent(alignment);
            dialog.getDialogPane().getButtonTypes().addAll(save_s, ButtonType.CANCEL);

            dialog.setResultConverter(dialogButton -> {
               if (dialogButton == save_s){
                  if (textPassword_s.getText().length() >= 8) {
                     for (User user : users) {
                        if(user.getEmail().equals(currentUser.get(0))){
                           user.setPassword(textPassword_s.getText().trim());
                           setCurrentUser(user);
                           setUserData(textEmail, textPassword, textUser);
                        }
                     }
                  } else {
                     Alert alert = new Alert(Alert.AlertType.WARNING);
                     alert.setTitle("WARNING");
                     alert.setHeaderText(null);
                     alert.setContentText("The password must have more than 8 characters");
                     alert.showAndWait();
                  }
               }
               return null;
            });
            dialog.showAndWait();
         }
      }
   }

   public static VBox createProductcart(Product product){
      VBox productBox = new VBox(5);
      productBox.setId(Integer.toString(product.getId()));
      productBox.setPadding(new Insets(10));
      productBox.setStyle("-fx-border-color: #ddd; -fx-border-radius: 5px; -fx-background-color: #fff;");

      Label nameLabel = new Label(product.getName());
      nameLabel.setStyle("-fx-font-size: 1.2em;");
      Label descriptionLabel = new Label("Description: " + product.getDescription());
      descriptionLabel.setStyle("-fx-font-size: 1.2em;");
      Label priceLabel = new Label(product.getPrice());
      priceLabel.setStyle("-fx-font-size: 1em; -fx-text-fill: #888;");
      Button addToCart = new Button("Add to the cart");
      Button buyBtn = new Button("Buy");
      
      for (User user : users) {
         if (user.getEmail().equals(currentUser.get(0)) &&
            user.getPassword().equals(currentUser.get(1)) &&
            user.getUserName().equals(currentUser.get(2))) {

            int productId = Integer.parseInt(productBox.getId());
            addToCart.setOnAction(event -> {
               addToCart(user.getCart(), productId);
            });
         }
      }

      productBox.getChildren().addAll(nameLabel, descriptionLabel, priceLabel, addToCart, buyBtn);
      return productBox;
   }

   public static void showDialogBoxProducts(FlowPane productPane){
      Dialog<User> dialog = new Dialog<>();
      dialog.setTitle("Add a product");

      Label label_name_p = new Label("Name of the product:");
      TextField name_input_p = new TextField();
      Label label_price_p = new Label("Price of the product:");
      TextField price_input_p = new TextField();
      Label label_description_p = new Label("Description of the product:");
      TextField description_input_p = new TextField();
      ButtonType save_p = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
      
      VBox alignment = new VBox(label_name_p,
                                 name_input_p,
                                 label_price_p,
                                 price_input_p,
                                 label_description_p,
                                 description_input_p);
      
      alignment.setPadding(new Insets(10));
      dialog.getDialogPane().setContent(alignment);
      dialog.getDialogPane().getButtonTypes().addAll(save_p, ButtonType.CANCEL);

      dialog.setResultConverter(dialogButton -> {
         if (dialogButton == save_p){
            if (price_input_p.getText().matches("\\d+")) {
               Product product = new Product(name_input_p.getText(), description_input_p.getText(), Integer.parseInt(currentUser.get(3)), productId, price_input_p.getText());
               productss.add(product);
               productId++;
               updateProductPane(productPane, productss);
            } else {
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("WARNING");
               alert.setHeaderText(null);
               alert.setContentText("The price can't have letters");
               alert.showAndWait();
            }
         }
         return null;
      });
      dialog.showAndWait();
   }

   public static void updateProductPane(FlowPane productPane, List<Product> list) {
      productPane.getChildren().clear();
      for (Product pro : list) {
         productPane.getChildren().add(createProductcart(pro));
      }
   }

   private static void addToCart(List<Product> cart, int productId){
      for (Product product : productss) {
         if (product.getId() == productId) {
            cart.add(product);
         }
      }
   }

   private static void setUserData(Text textEmail, Text textPassword, Text textUser) {
      textEmail.setText(currentUser.get(0));
      textPassword.setText(currentUser.get(1));
      textUser.setText(currentUser.get(2));
   }
   
   private static void switchScene(Scene scene_dashboard, Scene scene_login, Scene scene_settings, Scene scene_cart, String newScreen) {
      screen = newScreen;

      switch (screen) {
         case "login" -> {
            primaryStage.setScene(scene_login);
         }
         case "dashboard" -> {
            primaryStage.setScene(scene_dashboard);
         }
         case "settings" -> {
            primaryStage.setScene(scene_settings);
         }
         case "cart" -> {
            primaryStage.setScene(scene_cart);
         }
         default -> throw new AssertionError();
      }
   }

   public static void setButtonAble(Button loginBtn, Button signInBtn) {
      if (validEmail.get() && validPassword.get() && validUser.get()) {
         loginBtn.setDisable(false);
         signInBtn.setDisable(false);
      } else {
         loginBtn.setDisable(true);
         signInBtn.setDisable(true);
      }
   }

   public static void main(String[] args) {
      launch(args);


      for(User user : users){
         System.out.println(user.getUserName());
         for (Product pro : user.getCart()){
            System.out.println(pro.getName());
         }
         
      }
   }
}

class User {
   public static List<Product> cart = new ArrayList<>();
   String email;
   String password;
   String userName;
   String id;
   
   public User(String email, String password, String userName, String id) {
      this.email = email;
      this.password = password;
      this.userName = userName;
      this.id = id;
   }

   public List<Product> getCart(){
      return cart;
   }

   public String getEmail() {
      return this.email;
   }

   public String getPassword() {
      return this.password;
   }

   public String getUserName() {
      return this.userName;
   }

   public String getId() {
      return this.id;
   }

   public void setUser(String newUserName){
      this.userName = newUserName;
   }

   public void setPassword(String newPassword){
      this.password = newPassword;
   }
}

class Product {
   String name;
   String description;
   String price;
   int userId;
   int id;
   

   public Product(String name, String description, int userId, int id, String price) {
      this.name = name;
      this.description = description;
      this.userId = userId;
      this.id = id;
      this.price = price;
   }

   public String getName(){
      return this.name;
   }

   public int getUserId(){
      return this.userId;
   }

   public int getId(){
      return this.id;
   }

   public String getPrice(){
      return this.price;
   }

   public String getDescription(){
      return this.description;
   }

   public void setName(String newName){
      this.name = newName;
   }

   public void setPrice(String newPrice){
      this.price = newPrice;
   }
}


