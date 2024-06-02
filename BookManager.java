import java.util.ArrayList;
import java.util.List;

public class BookManager implements BookOperations {
    private List<Book> books;

    public BookManager() {
        books = new ArrayList<>();
        
        books.add(new Book("Book 1", "Author 1", "ISBN001", 2001));
        books.add(new Book("Book 2", "Author 2", "ISBN002", 2002));
        books.add(new Book("Book 3", "Author 3", "ISBN003", 2003));
        books.add(new Book("Book 4", "Author 4", "ISBN004", 2004));
        books.add(new Book("Book 5", "Author 5", "ISBN005", 2005));
    }

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public void removeBook(Book book) {
        books.remove(book);
    }

    @Override
    public void updateBook(Book oldBook, Book newBook) {
        int index = books.indexOf(oldBook);
        if (index != -1) {
            books.set(index, newBook);
        }
    }

    @Override
    public List<Book> getBooks() {
        return new ArrayList<>(books);
    }
}
