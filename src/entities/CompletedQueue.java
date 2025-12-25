package entities;

import java.time.LocalDate;
import java.util.ArrayList;

public class CompletedQueue {
    private static int counter = 0;
    final int id;
    BorrowQueue queue;
    String status;
    LocalDate date;


    public CompletedQueue (BorrowQueue queue, int i) {
        this.id = ++counter;
        this.queue = queue;
        this.status = returnStatus(i);
        this.date = LocalDate.now();
    }

    private String returnStatus (int i) {
        if (i == 1) {
            return "borrowed";
        }
        return "returned";
    }

    public void displayQueue(ArrayList<Book> books) {
        Book book = null;
        for (Book b: books) {
            if (b.getId() == getQueue().getBook()) {
                book = b;
            }
        }

        String s = getId() + ") " + getQueue().getStudent().getName() + ", " + getStatus() + " (Book: " + book.getTitle() + " - Borrowed on: " + getDate() + ")";
        System.out.println(s);
    }

    public int getId() { return id; }
    public BorrowQueue getQueue() { return queue; }
    public String getStatus() { return status; }
    public LocalDate getDate() { return date; }

    public void setStatus(int status) {
        this.status = returnStatus(status);
    }
}
