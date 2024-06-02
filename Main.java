import java.util.Optional;

public class Main extends Application {
    private static BookManager bookManager = new BookManager();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Book Manager");

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        
        ListView<Book> bookListView = new ListView<>();
        bookListView.getItems().addAll(bookManager.getBooks());

        
        Button addButton = new Button("Add Book");
        Button removeButton = new Button("Remove Book");
        Button updateButton = new Button("Update Book");

        
        addButton.setOnAction(e -> {
            Book book = createBookFromUserInput();
            if (book != null) {
                bookManager.addBook(book);
                bookListView.getItems().setAll(bookManager.getBooks());
            }
        });

        
        removeButton.setOnAction(e -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                bookManager.removeBook(selectedBook);
                bookListView.getItems().setAll(bookManager.getBooks());
            }
        });

        
        updateButton.setOnAction(e -> {
            Book selectedBook = bookListView.getSelectionModel().getSelectedItem();
            if (selectedBook != null) {
                Book updatedBook = createBookFromUserInput();
                if (updatedBook != null) {
                    bookManager.updateBook(selectedBook, updatedBook);
                    bookListView.getItems().setAll(bookManager.getBooks());
                }
            }
        });

        HBox buttons = new HBox(10, addButton, removeButton, updateButton);
        root.getChildren().addAll(bookListView, buttons);

        Scene scene = new Scene(root, 400, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Book createBookFromUserInput() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("New Book");
        dialog.setHeaderText("Enter book details:");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);

        TextField titleField = new TextField();
        TextField authorField = new TextField();
        TextField isbnField = new TextField();
        TextField yearField = new TextField();

        grid.add(new Label("Title:"), 0, 0);
        grid.add(titleField, 1, 0);
        grid.add(new Label("Author:"), 0, 1);
        grid.add(authorField, 1, 1);
        grid.add(new Label("ISBN:"), 0, 2);
        grid.add(isbnField, 1, 2);
        grid.add(new Label("Year:"), 0, 3);
        grid.add(yearField, 1, 3);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                int year;
                try {
                    year = Integer.parseInt(yearField.getText());
                } catch (NumberFormatException e) {
                    showAlert("Invalid year", "Please enter a valid year.");
                    return null;
                }
                return new Book(title, author, isbn, year);
            }
            return null;
        });

        Optional<Book> result = dialog.showAndWait();
        return result.orElse(null);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
