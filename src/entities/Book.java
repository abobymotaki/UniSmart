package entities;

import java.util.*;

public class Book {
    private static int counter = 0;

    final int id;
    String title;
    String author;
    String genre;
    List<Map<String, Object>> status = new ArrayList<>();
    int totalCopies;

    public Book(String title, String author, String genre, int totalCopies) {
        this.id = ++counter;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.totalCopies = totalCopies;

        updateStatus(0, 0, totalCopies);
    }

    public void calculateStatus(int choice) {
        int borrowed = (int) getStatus().getFirst().get("borrowed");
        int reserved = (int) getStatus().getFirst().get("reserved");

        switch (choice) {
            case 1: {
                borrowed++;
                reserved--;
                break;
            }
            case 2: {
                reserved++;
                break;
            }
            case 3: {
                reserved--;
                break;
            }
            case 4: {
                borrowed--;
                break;
            }
            default: {
                break;
            }
        }

        int availableNow = totalCopies - borrowed - reserved;

        updateStatus(borrowed, reserved, availableNow);
    }

    private void updateStatus(int borrowed, int reserved, int availableNow) {
        status.clear();

        Map<String, Object> json = new HashMap<>();
        json.put("borrowed", borrowed);
        json.put("reserved", reserved);
        json.put("available", availableNow);

        status.add(json);
    }

    public boolean displayAvailable() {
        if ((int) status.getFirst().get("borrowed") < totalCopies) {
            return true;
        }

        return false;
    }

    public void displayBook() {
        System.out.print(") Book: " + getTitle() + " by " + getAuthor());
        System.out.print(", Genre: " + getGenre() + ", \nBID: " + getId());
        System.out.print(", Status: " + status);
        System.out.println("\n-----------------------------");
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getGenre() { return genre; }
    public int getTotalCopies() { return totalCopies; }
    public List<Map<String, Object>> getStatus() { return status; }


    public static void setCounter(int counter) {
        Book.counter = counter;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setStatus(List<Map<String, Object>> status) {
        this.status = status;
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies = totalCopies;
        calculateStatus(0);
    }

    public static List<Book> seedBooks(List<Book> books) {
        books.add(new Book("Harry Potter", "J.K. Rowling", "Fantasy, Adventure, Magic", 10));
        books.add(new Book("The Hobbit", "J.R.R. Tolkien", "Fantasy, Adventure", 6));
        books.add(new Book("1984", "George Orwell", "Dystopian, Political", 12));
        books.add(new Book("To Kill a Mockingbird", "Harper Lee", "Classic, Drama", 4));
        books.add(new Book("The Great Gatsby", "F. Scott Fitzgerald", "Classic, Tragedy", 7));
        books.add(new Book("The Catcher in the Rye", "J.D. Salinger", "Classic, Coming-of-Age", 5));
        books.add(new Book("The Lord of the Rings", "J.R.R. Tolkien", "Fantasy, Epic", 15));
        books.add(new Book("The Chronicles of Narnia", "C.S. Lewis", "Fantasy, Adventure", 9));
        books.add(new Book("The Hunger Games", "Suzanne Collins", "Dystopian, Action", 8));
        books.add(new Book("Dune", "Frank Herbert", "Science Fiction, Adventure", 11));
        books.add(new Book("Moby Dick", "Herman Melville", "Adventure, Classic", 3));
        books.add(new Book("Pride and Prejudice", "Jane Austen", "Romance, Classic", 14));
        books.add(new Book("The Da Vinci Code", "Dan Brown", "Mystery, Thriller", 6));
        books.add(new Book("The Alchemist", "Paulo Coelho", "Philosophical, Adventure", 10));
        books.add(new Book("The Girl with the Dragon Tattoo", "Stieg Larsson", "Mystery, Crime", 7));
        books.add(new Book("Brave New World", "Aldous Huxley", "Dystopian, Sci-Fi", 9));
        books.add(new Book("Fahrenheit 451", "Ray Bradbury", "Dystopian, Sci-Fi", 4));
        books.add(new Book("The Maze Runner", "James Dashner", "Dystopian, Adventure", 13));
        books.add(new Book("The Shining", "Stephen King", "Horror, Thriller", 16));
        books.add(new Book("The Witcher: Blood of Elves", "Andrzej Sapkowski", "Fantasy, Action", 5));

        System.out.println("[ ! ] All Books Seeded.");

        return books;
    }
}
