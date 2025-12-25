package entities;

import java.util.*;

public class BorrowQueue {
    private static int counter = 0;
    int sequence;
    Person person;
    int book;

    public BorrowQueue(Person person, int book) {
        this.sequence = ++counter;
        this.person = person;
        this.book = book;
    }

    public void displayQueue(ArrayList<Book> books) {
        Book getBook = null;
        for (Book b: books) {
            if (book == b.id) {
                getBook = b;
            }
        }

        System.out.println("S-ID-" + sequence
            + ", Individual: " + person.name + " (ID: " + person.id + " || " + person.getType(person.priority)
            + "), [Book] " + getBook.getTitle() + " by " + getBook.getAuthor() + " (" + getBook.getId() + ")"
        );
    }

    public Person getStudent() {
        return this.person;
    }
    public int getBook() { return book; }
    public int getSequence() {
        return sequence;
    }

    public void setBook(int book) { this.book = book; }
    public void setPerson(Person person) { this.person = person; }

    private static BorrowQueue newSequence(Person person, int book, ArrayList<Book> books) {
        BorrowQueue sequence = null;

        Book book1 = null;
        for (Book b: books) {
            if (b.id == book) {
                book1 = b;
            }
        }

        if (book1 == null) {
            return null;
        } else {
            int available = (int) book1.getStatus().getFirst().get("available");
            if (available > 0) {
                book1.calculateStatus(2);
            } else {
                System.out.println("[!] Book is unavailable.");
                return null;
            }
        }

        sequence = new BorrowQueue(person, book);

        return sequence;
    }

    public static Queue<BorrowQueue> seedQueue(Queue<BorrowQueue> queue, List<Person> people, ArrayList<Book> books) {
        Random random = new Random();
        int randomNum;
        Person person = null;
        int book;

        for (int i = 0; i < 100; i++) {
            randomNum = random.nextInt(1, people.size() + 1);
            for (Person p: people) {
                if (p.getId() == randomNum) {
                    person = p;
                }
            }

            boolean allow = true;
            for (BorrowQueue q: queue) {
                if (q.getStudent() == person) {
                    allow = false;
                }
            }

            if (!allow) {
//                System.out.println("[!] " + person.getName() + " already has a reservation.");
            } else if (person != null) {
                book = random.nextInt(1, books.size() + 1);
                BorrowQueue sequence = newSequence(person, book, books);

                if (sequence != null) {
                    queue.offer(sequence);
                } else {
                    System.out.println("[!] Sequence couldn't be created.");
                }
            } else {
                System.out.println("[!] Could not find user." + randomNum);
            }
            System.out.println("[ ! ] All Reservations Seeded.");
        }

        return queue;
    }
}