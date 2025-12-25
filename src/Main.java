import entities.Book;
import entities.BorrowQueue;
import entities.CompletedQueue;
import entities.Person;

import java.time.LocalTime;
import java.util.*;

public class Main {
    static ArrayList<Book> books = new ArrayList<>();
    static List<Person> people = new LinkedList<>();
    static Queue<BorrowQueue> queue = new LinkedList<>();
    static Queue<CompletedQueue> completedQueues = new LinkedList<>();

    public static void main(String[] args) {
        Person.seedPeople(people);
        Book.seedBooks(books);
        BorrowQueue.seedQueue(queue, people, books);

        Scanner scanner = new Scanner(System.in);
        process(scanner);
        scanner.close();
    }

    private static void process(Scanner scanner) {
        boolean process = true;

        System.out.println("--------------------------------");
        System.out.println("   LIBRARY MANAGEMENT SYSTEM");
        System.out.println("--------------------------------");
        while (process) {
            System.out.println("\n1) User Settings");
            System.out.println("2) Book Settings");
            System.out.println("3) Queue Settings");
            System.out.print("0) Exit \n\nSelection: ");

            String selector = scanner.nextLine();

            switch (selector) {
                case "0": {
                    System.out.println("Exiting program!");
                    process = false;
                    break;
                }
                case "1": {
                    userFeatures(scanner, people);
                    break;
                }
                case "2": {
                    bookFeatures(scanner);
                    break;
                }
                case "3": {
                    queueFeatures(scanner, books, queue);
                    break;
                }
                default: {
                    System.out.println("Invalid selection. Please choose 0, 1, 2 or 3.");
                    break;
                }
            }
        }
    }

    private static void userFeatures(Scanner scanner, List<Person> people) {
        System.out.println("----------------------------------");
        System.out.println("USER MUTATION INTERFACE");
        System.out.println("----------------------------------");
        System.out.println("1) Register User"); // Completed
        System.out.println("2) Remove User"); // Completed
        System.out.println("3) Update User"); // Completed
        System.out.println("4) View All User"); // Completed
        System.out.println("0) Exit");

        System.out.print("\nSelection: ");
        String selector = scanner.nextLine();

        switch (selector) {
            case "1": {
                people.add(createUser(scanner));
                System.out.println("[!] User registered successfully.");
                break;
            }
            case "2": {
                System.out.println("\n-----------------------------");
                System.out.println("Remove user by ID");
                System.out.println("-----------------------------");
                Person person = removeUser(people, scanner);
                if (person != null) {
                    people.remove(person);
                    System.out.println("[!] User removed successfully.");
                } else {
                    System.out.println("[!] User not found.");
                }
                System.out.println("-----------------------------");
                break;
            }
            case "3": {
                System.out.println("\n-----------------------------");
                System.out.println("Update User");
                System.out.println("-----------------------------");
                updateUser(people, scanner);
                System.out.println("[!] Reservation updated Successfully.");
                System.out.println("-----------------------------");
                break;
            }
            case "4": {
                System.out.println("\n-----------------------------");
                System.out.println("All Registered Users");
                System.out.println("-----------------------------");
                viewUsers(people);
                System.out.println("-----------------------------");
                break;
            }
            default: {
                System.out.println("Invalid selection. Please choose 0, 1, 2, 3, or 4.");
                break;
            }
        }
    }

    private static void queueFeatures(Scanner scanner, ArrayList<Book> books, Queue<BorrowQueue> queue) {
        System.out.println("----------------------------------");
        System.out.println("QUEUE MUTATION INTERFACE");
        System.out.println("----------------------------------");
        System.out.println("1) Register Reservation"); // Completed
        System.out.println("2) Confirm Reservation"); // Completed
        System.out.println("3) Update Reservation"); // Completed
        System.out.println("4) Remove Reservation"); // Completed
        System.out.println("5) View All Reservations"); // Completed
        System.out.println("6) View all Borrowers"); // Completed
        System.out.println("7) Register book return"); // Completed
        System.out.println("8) View all returnee's"); // Completed
        System.out.println("0) Exit");

        System.out.print("\nSelection: ");
        String selector = scanner.nextLine();

        switch (selector) {
            case "1": {
                BorrowQueue sequence = addToQueue(scanner);
                if (sequence != null) {
                    queue.add(sequence);
                    System.out.println("[!] Successfully created Reservation.");
                } else {
                    System.out.println("[!] Couldn't create a reservation.");
                }
                break;
            }
            case "2": {
                System.out.println("\n-----------------------------");
                System.out.println("Completing Reservation");
                System.out.println("-----------------------------");
                completeBooking(scanner);
                System.out.println("-----------------------------");
                break;
            }
            case "3": {
                System.out.println("\n-----------------------------");
                System.out.println("Update Reservation");
                System.out.println("-----------------------------");
                updateSequence(scanner);
                System.out.println("[!] Successfully updated reservation");
                System.out.println("-----------------------------");
                break;
            }
            case "4": {
                System.out.println("\n-----------------------------");
                System.out.println("Remove Reservation");
                System.out.println("-----------------------------");
                removeSequence(scanner);
                System.out.println("-----------------------------");
                break;
            }
            case "5": {
                System.out.println("\n-----------------------------");
                System.out.println("Displaying all Reservations");
                System.out.println("-----------------------------");
                
                if (queue.isEmpty()) {
                    System.out.println("[!] The Reservation Queue is empty.");
                } else {
                    Queue<BorrowQueue> orderedQueue = getOrderedQueue(queue);
                    Queue<BorrowQueue> displayQueue = new LinkedList<>(orderedQueue);
                    
                    while (!displayQueue.isEmpty()) {
                        displayQueue.poll().displayQueue(books);
                    }
                }
                
                System.out.println("-----------------------------");
                break;
            }
            case "6": {
                System.out.println("\n-----------------------------");
                System.out.println("Viewing current Borrowers");
                System.out.println("-----------------------------");
                displayBorrowers();
                System.out.println("-----------------------------");
                break;
            }
            case "7": {
                System.out.println("\n-----------------------------");
                System.out.println("Register Book Return");
                System.out.println("-----------------------------");
                returnBook(scanner);
                System.out.println("[!] Book return has been registered successfully.");
                System.out.println("-----------------------------");
                break;
            }
            case "8": {
                System.out.println("\n-----------------------------");
                System.out.println("Viewing Returnee's");
                System.out.println("-----------------------------");
                if (!completedQueues.isEmpty()) {
                    for (CompletedQueue q: completedQueues) {
                        if (q.getStatus().equalsIgnoreCase("returned")) {
                            q.displayQueue(books);
                        }
                    }
                } else {
                    System.out.println("[!] There are no return records.");
                }
                System.out.println("-----------------------------");
                break;
            }
            default: {
                System.out.println("Invalid selection. Please choose 0, 1, 2, 3, 4, 5, 6, 7 or 8.");
                break;
            }
        }
    }

    private static void bookFeatures(Scanner scanner) {
        System.out.println("----------------------------------");
        System.out.println("BOOK MUTATION INTERFACE");
        System.out.println("----------------------------------");
        System.out.println("1) Register Book");
        System.out.println("2) Update Book");
        System.out.println("3) Delete Book");
        System.out.println("4) View all Books");
        System.out.println("5) Create Book Shallow Copy");
        System.out.println("6) Search book by ID");
        System.out.println("7) View book in insertion sort of Title");
        System.out.println("8) Create a Deep Copy");
        System.out.println("0) Exit");

        System.out.print("\nSelection: ");
        String selector = scanner.nextLine();

        switch (selector) {
            case "1": {
                System.out.println("\n-----------------------------");
                System.out.println("Book registration started");
                System.out.println("-----------------------------");
                Book book = createBook(scanner);
                if (book != null) {
                    books.add(book);
                    System.out.println("[!] Successfully registered book.");
                } else {
                    System.out.println("[!] Failed to register book.");
                }
                System.out.println("-----------------------------");
                break;
            }
            case "2": {
                System.out.println("\n-----------------------------");
                System.out.println("Book update process started");
                System.out.println("-----------------------------");
                Book book = updateBook(scanner);
                if (book != null) {
                    book.displayBook();
                    System.out.println("[!] Successfully updated book.");
                } else {
                    System.out.println("[!] Failed to update book.");
                }
                System.out.println("-----------------------------");
                break;
            }
            case "3": {
                System.out.println("\n-----------------------------");
                System.out.println("Book removal process started");
                System.out.println("-----------------------------");
                Book book = removeBook(scanner);
                if (book != null) {
                    books.remove(book);
                    System.out.println("[!] Successfully removed book.");
                } else {
                    System.out.println("[!] Failed to remove book.");
                }
                System.out.println("-----------------------------");
                break;
            }
            case "4": {
                System.out.println("\n-----------------------------");
                System.out.println("Displaying all registered Books");
                System.out.println("-----------------------------");
                viewBooks(books);
                System.out.println("-----------------------------");
                break;
            }
            case "5": {
                System.out.println("\n-----------------------------");
                System.out.println("Creating Shallow copy for book");
                System.out.println("-----------------------------");
                shallowCopy(scanner);
                System.out.println("-----------------------------");
                break;
            }
            case "6": {
                System.out.println("\n-----------------------------");
                System.out.println("Search book by ID");
                System.out.println("-----------------------------");
                System.out.print("Enter book ID: ");
                int bookID;
                try {
                    bookID = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("[!] Book ID should be an integer.");
                    break;
                }
                HashMap<Integer, Book> result = bookSearchBinary(bookID);

                int index = result.keySet().stream().findFirst().get();
                System.out.println("Book with ID of " + bookID + " was found at index: " + index);
                result.get(index).displayBook();
                System.out.println("-----------------------------");
                break;
            }
            case "7": {
                System.out.println("\n-----------------------------");
                System.out.println("View books in title order");
                System.out.println("-----------------------------");
                viewBooksInsertionSort();
                break;
            }
            case "8": {
                System.out.println("\n-----------------------------");
                System.out.println("Create A Deep Copy");
                System.out.println("-----------------------------");
                Book deepcopy = deepCopy(scanner);
                if (deepcopy != null) {
                    books.add(deepcopy);
                }
                break;
            }
            default: {
                System.out.println("Invalid selection. Please choose 0, 1, 2, 3, 4, 5, 6, 7 or 8.");
                break;
            }
        }
    }

    private static Person createUser(Scanner scanner) {
        System.out.println("\n-----------------------------");
        System.out.println("Creating new User:");
        System.out.println("-----------------------------");
        System.out.print("Person Name: ");
        String name = scanner.nextLine();

        System.out.print("Person Priority (1/2/3): ");
        int priority;
        try {
            priority = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid priority. Defaulting to 1.");
            priority = 1;
        }

        Person person = new Person(name, priority);
        System.out.println("-----------------------------");
        return person;
    }

    private static Person updateUser(List<Person> people, Scanner scanner) {
        Person person = null;
        viewUsers(people);

        System.out.print("Enter user ID to update user: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return null;
        }

        for (Person p: people) {
            if (id == p.getId()) {
                person = p;
            }
        }

        System.out.print("Enter new username (Current - " + person.getName() + "): ");
        String name = scanner.nextLine();

        System.out.print("Enter new priority (Current - " + person.priority + "): ");
        int priority;
        try {
            priority = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Priority should be of Integer type!");
            return null;
        }

        person.setName(name);
        person.setPriority(priority);

        return person;
    }

    private static void viewUsers(List<Person> people) {
        if (!people.isEmpty()) {
            for (Person p : people) {
                p.displayPerson();
            }
        } else {
            System.out.println("There are no users in the program!");
        }
    }

    private static Person removeUser(List<Person> people, Scanner scanner) {
        viewUsers(people);

        System.out.print("Enter user ID to remove user: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
            return null;
        }

        Person person = null;
        for (Person p : people) {
            if (id == p.getId()) {
                person = p;
                break;
            }
        }

        return person;
    }

    private static BorrowQueue addToQueue(Scanner scanner) {
        System.out.println("\n-----------------------------");
        System.out.println("Registering Reservation");
        System.out.println("-----------------------------");
        BorrowQueue newSequence = null;
        viewUsers(people);
        System.out.print("Enter borrower ID: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("The ID should be an integer!" + e.getMessage());
            return null;
        }

        Person person = null;
        try {

            for (Person p: people) {
                if (p.getId() == id) {
                    person = p;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("[!] User not found.");
        }

        viewBooks(books);
        System.out.print("Enter book ID to borrow: ");
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("The ID should be an integer!" + e.getMessage());
            return null;
        }

        int bookID = id;
        Book book = null;
        try {
            for (Book b: books) {
                if (b.getId() == id) {
                    book = b;
                }
            }
        } catch (NullPointerException e) {
            System.out.println("[!] Book not found.");
        }

        if (!book.displayAvailable()) {
            System.out.println("[!] The book \"" + book.getTitle() + "\" is not available.");
            return null;
        } else {
            int available = (int) book.getStatus().getFirst().get("available");
            if (available > 0) {
                book.calculateStatus(2);
            } else {
                System.out.println("[!] Book is not available.");
                return null;
            }
        }

        newSequence = new BorrowQueue(person, bookID);
        System.out.println("-----------------------------");

        return newSequence;
    }

    private static void viewBooksInsertionSort() {
        if (books.isEmpty()) {
            System.out.println("[!] There are no books to display.");
            return;
        }

        ArrayList<Book> sortedBooks = new ArrayList<>(books);

        for (int i = 1; i < sortedBooks.size(); i++) {
            Book current = sortedBooks.get(i);
            String currentTitle = current.getTitle();

            int j = i - 1;
            while (j >= 0 && sortedBooks.get(j).getTitle().compareToIgnoreCase(currentTitle) > 0) {
                sortedBooks.set(j + 1, sortedBooks.get(j));
                j--;
            }
            sortedBooks.set(j + 1, current);
        }

        for (int i = 0; i < sortedBooks.size(); i++) {
            System.out.print((i + 1) + ". ");
            sortedBooks.get(i).displayBook();
        }
    }

    private static void viewBooks(ArrayList<Book> books) {
        if (books.isEmpty()) {
            System.out.println("[!] The are no books to display.");
            return;
        }

        int i = 1;
        for (Book b: books) {
            System.out.print(i);
            b.displayBook();
            i++;
        }
    }

    private static Queue<BorrowQueue> getOrderedQueue(Queue<BorrowQueue> queue) {
        LocalTime peakStart = LocalTime.of(14, 0);
        LocalTime peakEnd = LocalTime.of(19, 0);
        LocalTime now = LocalTime.of(15, 0);
        boolean peak;
        Queue<BorrowQueue> newQueue = null;

        if (now.isAfter(peakStart) && now.isBefore(peakEnd)) {
            peak = true;
        } else {
            peak = false;
        }

        if (peak) {
            newQueue = new PriorityQueue<>(
                    (a, b) -> {
                        int comparePriority = Integer.compare(a.getStudent().getPriority(), b.getStudent().getPriority());
                        if (comparePriority == 0) { return Integer.compare(a.getSequence(), b.getSequence()); }
                        return comparePriority;
                    }
            );
        } else {
            newQueue = new PriorityQueue<>((a, b) -> Integer.compare(a.getSequence(), b.getSequence()));
        }

        newQueue.addAll(queue);
        return newQueue;
    }

    private static BorrowQueue getNextReservation(Queue<BorrowQueue> queue) {
        if (queue.isEmpty()) {
            return null;
        }
        
        Queue<BorrowQueue> orderedQueue = getOrderedQueue(queue);
        return orderedQueue.peek(); // Get the next reservation without removing it
    }

    private static Queue<BorrowQueue> viewQueue(Queue<BorrowQueue> queue) {
        if (queue.isEmpty()) {
            System.out.println("[!] The Reservation Queue is empty.");
            return null;
        }

        return getOrderedQueue(queue);
    }

    private static BorrowQueue updateSequence(Scanner scanner) {
        BorrowQueue sequence = null;

        // Display current reservations in proper order
        System.out.println("\n=== CURRENT RESERVATIONS ===");
        Queue<BorrowQueue> orderedQueue = getOrderedQueue(queue);
        Queue<BorrowQueue> displayQueue = new LinkedList<>(orderedQueue);
        
        if (displayQueue.isEmpty()) {
            System.out.println("[!] No reservations to update.");
            return null;
        }
        
        while (!displayQueue.isEmpty()) {
            displayQueue.poll().displayQueue(books);
        }
        
        System.out.print("Enter Reservation ID to Update: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[!] Reservation ID should be an Integer!");
            return null;
        }

        for (BorrowQueue q: queue) {
            if (q.getSequence() == id) {
                sequence = q;
            }
        }

        if (sequence == null) {
            System.out.println("[!] Reservation not found.");
            return null;
        }

        boolean selector = true;
        int selection;
        while (selector) {

            System.out.println("1) Update user in reservation");
            System.out.println("2) Update book in reservation");
            System.out.println("0) Exit");
            selection = Integer.parseInt(scanner.nextLine());

            if (selection == 1) {
                viewUsers(people);
                System.out.print("Input user ID to update: ");
                int uid;

                try {
                    uid = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("[!] Invalid selection, escaping operation.");
                    return null;
                }

                Person person = null;
                for (Person p: people) {
                    if (uid == p.getId()) {
                        person = p;
                    }
                }

                if (person != null) {
                    sequence.setPerson(person);
                    System.out.println("[!] Reservation user updated successfully. (New User - " + person.getName() +")");
                } else {
                    System.out.println("[!] User not found.");
                }
            }
            else if (selection == 2) {
                viewBooks(books);
                System.out.print("Input book ID to update: ");


                int bid;

                try {
                    bid = Integer.parseInt(scanner.nextLine());
                } catch (NumberFormatException e) {
                    System.out.println("[!] Invalid selection, escaping operation.");
                    return null;
                }

                Book book = null;
                for (Book b: books) {
                    if (b.getId() == bid) {
                        book = b;
                    }
                }

                if (book != null) {
                    int available = (int) book.getStatus().getFirst().get("available");
                    if (available > 0) {
                        for (Book b: books) {
                            if (b.getId() == sequence.getBook()) {
                                b.calculateStatus(3);
                            }
                        }
                        sequence.setBook(bid);

                        book.calculateStatus(2);
                        System.out.println("[!] Book updated successfully. (New Book - " + book.getTitle() + ")");
                    } else {
                        System.out.println("[!] The book you selected is un-available.");
                        return null;
                    }
                } else {
                    System.out.println("[!] Book not found.");
                }

            } else if (selection == 0) {
                System.out.println("[!] Exiting Operation.");
                selector = false;
            }
        }

        return sequence;
    }

    private static void removeSequence(Scanner scanner) {
        // Display current reservations in proper order
        System.out.println("\n=== CURRENT RESERVATIONS ===");
        Queue<BorrowQueue> orderedQueue = getOrderedQueue(queue);
        Queue<BorrowQueue> displayQueue = new LinkedList<>(orderedQueue);
        
        if (displayQueue.isEmpty()) {
            System.out.println("[!] No reservations to remove.");
            return;
        }
        
        while (!displayQueue.isEmpty()) {
            displayQueue.poll().displayQueue(books);
        }

        BorrowQueue sequence = null;
        System.out.print("Enter Sequence ID to remove: ");
        int id;

        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[!] ID should be an integer.");
            return;
        }

        for (BorrowQueue q: queue) {
            if (q.getSequence() == id) {
                sequence = q;
            }
        }

        if (sequence != null) {
            queue.remove(sequence);
            System.out.println("[!] Successfully removed reservation");
        } else {
            System.out.println("[!] Reservation not found.");
        }
    }

    private static void completeBooking(Scanner scanner) {
        if (queue.isEmpty()) {
            System.out.println("[!] No reservations to complete.");
            return;
        }

        boolean running = true;
        while (running) {
            BorrowQueue next = getNextReservation(queue);  // Get the next reservation based on constraints
            if (next == null) {
                System.out.println("[!] No reservations available.");
                break;
            }

            System.out.println("\n=== NEXT IN LINE ===");
            next.displayQueue(books);  // Display the next reservation details

            System.out.print("\nPress 1 to serve this user, or 0 to exit: ");
            String input = scanner.nextLine();

            if ("1".equals(input)) {
                Book book = null;
                for (Book b : books) {
                    if (b.getId() == next.getBook()) {
                        book = b;
                        break;
                    }
                }

                if (book != null) {
                    book.calculateStatus(1);  // Mark as returned/borrowed
                }

                CompletedQueue completed = new CompletedQueue(next, 1);
                completedQueues.add(completed);
                queue.remove(next);  // Remove the specific reservation that was processed

                System.out.println("[SUCCESS] Reservation completed and moved to history!");
            } else {
                System.out.println("Exiting completion mode.");
                running = false;
            }
        }
    }

    private static Book createBook(Scanner scanner) {
        Book book;

        System.out.print("Enter book title: ");
        String title = scanner.nextLine();

        System.out.print("Enter book author: ");
        String author = scanner.nextLine();

        System.out.print("Enter book genre: ");
        String genre = scanner.nextLine();

        System.out.print("Enter number of copies available: ");
        int totalCopies = Integer.parseInt(scanner.nextLine());

        book = new Book(title, author, genre, totalCopies);

        return book;
    }

    private static Book updateBook(Scanner scanner) {
        Book book = null;
        boolean selector = true;

        viewBooks(books);
        System.out.print("\nEnter book ID to update Book: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[!] Book ID must be an integer.");
            return null;
        }

        for (Book b: books) {
            if (b.getId() == id) {
                book = b;
            }
        }

        while (selector) {
            System.out.println("1) Update book Title");
            System.out.println("2) Update book Author");
            System.out.println("3) Update book Genre");
            System.out.println("4) Update book Availability\n0) Exit\n");
            System.out.print("\nSelection: ");
            int selection;
            try {
                selection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println();
                return null;
            }

            switch (selection) {
                case 0: {
                    System.out.print("Exiting Process");
                    selector = false;
                    break;
                }
                case 1: {
                    System.out.print("Enter new book title: ");
                    String name = scanner.nextLine();

                    if (!name.isEmpty()) {
                        book.setTitle(name);
                    } else {
                        System.out.println("[!] Title cannot be empty.");
                    }
                    break;
                }
                case 2: {
                    System.out.print("Enter new author(s) name: ");
                    String author = scanner.nextLine();

                    if (!author.isEmpty()) {
                        book.setAuthor(author);
                    } else {
                        System.out.println("[!] Author cannot be empty.");
                    }
                    break;
                }
                case 3: {
                    System.out.print("Enter new Genre: ");
                    String genre = scanner.nextLine();

                    if (!genre.isEmpty()) {
                        book.setGenre(genre);
                    } else {
                        System.out.println("[!] Genre cannot be empty.");
                    }
                    break;
                }
                case 4: {
                    System.out.print("Enter copies available: ");
                    int totalCopies;

                    try {
                        totalCopies = Integer.parseInt(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Genre cannot be empty.");
                        return null;
                    }

                    book.setTotalCopies(totalCopies);
                    break;
                }
                default: {
                    System.out.println("Invalid selection, try again.");
                    break;
                }
            }
        }

        return book;
    }

    private static Book removeBook(Scanner scanner) {
        Book book = null;

        viewBooks(books);
        System.out.print("\nEnter book ID to delete Book: ");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[!] Book ID must be an integer.");
            return null;
        }

        for (Book b: books) {
            if (b.getId() == id) {
                book = b;
            }
        }

        return book;
    }

    private static HashMap<Integer, Book> bookSearchBinary(int targetId) {
        HashMap<Integer, Book> result = new HashMap<>();

        if (books.isEmpty()) {
            System.out.println("[!] No books in the library.");
            return result;
        }

        List<Map.Entry<Integer, Book>> indexedBooks = new ArrayList<>();
        for (int i = 0; i < books.size(); i++) {
            indexedBooks.add(new AbstractMap.SimpleEntry<>(i, books.get(i)));
        }

        indexedBooks.sort(Comparator.comparingInt(entry -> entry.getValue().getId()));

        int left = 0;
        int right = indexedBooks.size() - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            Book currentBook = indexedBooks.get(mid).getValue();
            int originalIndex = indexedBooks.get(mid).getKey();

            if (currentBook.getId() == targetId) {
                result.put(originalIndex, currentBook);
                return result;
            } else if (currentBook.getId() < targetId) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }

    private static void displayBorrowers() {
        if (completedQueues.isEmpty()) {
            System.out.println("[!] No one has borrowed any books currently.");
            return;
        }

        for (CompletedQueue q: completedQueues) {
            q.displayQueue(books);
        }
    }

    private static void returnBook(Scanner scanner) {
        if (completedQueues.isEmpty()) {
            System.out.println("[!] There are no active borrows.");
            return;
        }

        boolean selector = true;

        while (selector) {
            System.out.println("Enter ID to update status: ");
            for (CompletedQueue q: completedQueues) {
                if (q.getStatus().equalsIgnoreCase("borrowed")) {
                    q.displayQueue(books);
                }
            }
            System.out.print("\nSelection: ");

            int i = 0;

            try {
                i = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[!] Selection must be an integer.");
            }

            if (i == 0) {
                System.out.println("[!] Exiting Process.");
                selector = false;
            }

            CompletedQueue tempQueue = null;
            for (CompletedQueue q: completedQueues) {
                if (i == q.getId() && q.getStatus().equalsIgnoreCase("borrowed")) {
                    tempQueue = q;
                }
            }

            if (tempQueue == null) {
                System.out.println("[!] There is no borrow registration with ID: " + i);
            } else {
                tempQueue.setStatus(2);
                Book book = null;
                for (Book b: books) {
                    if (b.getId() == tempQueue.getQueue().getBook()) {
                        book = b;
                    }
                }

                book.calculateStatus(4);
            }
        }
    }

    private static Book shallowCopy(Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("[!] There are no books in the list.");
            return null;
        }

        viewBooks(books);
        System.out.println("Enter book ID to create shallow copy!");
        System.out.print("Selection: ");

        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[!] Book ID must be an integer");
            return null;
        }

        Book originalBook = null;
        for (Book b : books) {
            if (b.getId() == option) {
                originalBook = b;
                break;
            }
        }

        if (originalBook == null) {
            System.out.println("[!] Book with that ID does not exist.");
            return null;
        }

        // Shallow copy: both references point to the SAME object
        Book shallowCopy = originalBook;

        System.out.println("\n[!] Shallow copy created for \"" + shallowCopy.getTitle() + "\" (ID: " + shallowCopy.getId() + ")");
        System.out.println("[i] Since this is a shallow copy, changes will affect the original book in the library.\n");

        // Now allow immediate modification (same logic as updateBook)
        boolean editing = true;
        while (editing) {
            System.out.println("=== Edit Shallow Copy ===");
            System.out.println("1) Update book Title");
            System.out.println("2) Update book Author");
            System.out.println("3) Update book Genre");
            System.out.println("4) Update book Availability");
            System.out.println("0) Finish editing (changes are already applied)\n");
            System.out.print("\nSelection: ");

            int selection;
            try {
                selection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid number.");
                continue;
            }

            switch (selection) {
                case 0:
                    System.out.println("[i] Editing complete. Changes have been applied to the original book.");
                    editing = false;
                    break;

                case 1:
                    System.out.print("Enter new book title: ");
                    String title = scanner.nextLine();
                    if (!title.trim().isEmpty()) {
                        shallowCopy.setTitle(title);
                        System.out.println("[i] Title updated.");
                    } else {
                        System.out.println("[!] Title cannot be empty.");
                    }
                    break;

                case 2:
                    System.out.print("Enter new author(s): ");
                    String author = scanner.nextLine();
                    if (!author.trim().isEmpty()) {
                        shallowCopy.setAuthor(author);
                        System.out.println("[i] Author updated.");
                    } else {
                        System.out.println("[!] Author cannot be empty.");
                    }
                    break;

                case 3:
                    System.out.print("Enter new genre: ");
                    String genre = scanner.nextLine();
                    if (!genre.trim().isEmpty()) {
                        shallowCopy.setGenre(genre);
                        System.out.println("[i] Genre updated.");
                    } else {
                        System.out.println("[!] Genre cannot be empty.");
                    }
                    break;

                case 4:
                    System.out.print("Enter new number of available copies: ");
                    try {
                        int copies = Integer.parseInt(scanner.nextLine());
                        if (copies >= 0) {
                            shallowCopy.setTotalCopies(copies);
                            System.out.println("[i] Availability updated to " + copies + ".");
                        } else {
                            System.out.println("[!] Number of copies cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Please enter a valid number.");
                    }
                    break;

                default:
                    System.out.println("[!] Invalid selection, try again.");
                    break;
            }
            System.out.println();
        }

        return shallowCopy;
    }

    private static Book deepCopy(Scanner scanner) {
        if (books.isEmpty()) {
            System.out.println("[!] There are no books in the list.");
            return null;
        }

        viewBooks(books);
        System.out.println("Enter book ID to create a DEEP copy!");
        System.out.print("Selection: ");

        int option;
        try {
            option = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("[!] Book ID must be an integer.");
            return null;
        }

        Book originalBook = null;
        for (Book b : books) {
            if (b.getId() == option) {
                originalBook = b;
                break;
            }
        }

        if (originalBook == null) {
            System.out.println("[!] Book with that ID does not exist.");
            return null;
        }

        Book deepCopyBook = new Book(
                originalBook.getTitle(),
                originalBook.getAuthor(),
                originalBook.getGenre(),
                originalBook.getTotalCopies()
        );

        System.out.println("\n[!] Deep copy created for \"" + deepCopyBook.getTitle() + "\" (ID: " + deepCopyBook.getId() + ")");
        System.out.println("[i] This is an independent copy — changes will NOT affect the original book!\n");

        boolean editing = true;
        while (editing) {
            System.out.println("=== Edit Deep Copy (Safe - Original Unchanged) ===");
            System.out.println("1) Update book Title");
            System.out.println("2) Update book Author");
            System.out.println("3) Update book Genre");
            System.out.println("4) Update book Availability");

            System.out.print("\nSelection: ");

            int selection;
            try {
                selection = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("[!] Please enter a valid number.");
                continue;
            }

            switch (selection) {
                case 1:
                    System.out.print("Enter new book title: ");
                    String title = scanner.nextLine().trim();
                    if (!title.isEmpty()) {
                        deepCopyBook.setTitle(title);
                        System.out.println("[i] Title updated.");
                    } else {
                        System.out.println("[!] Title cannot be empty.");
                    }
                    break;
                case 2:
                    System.out.print("Enter new author(s): ");
                    String author = scanner.nextLine().trim();
                    if (!author.isEmpty()) {
                        deepCopyBook.setAuthor(author);
                        System.out.println("[i] Author updated.");
                    } else {
                        System.out.println("[!] Author cannot be empty.");
                    }
                    break;
                case 3:
                    System.out.print("Enter new genre: ");
                    String genre = scanner.nextLine().trim();
                    if (!genre.isEmpty()) {
                        deepCopyBook.setGenre(genre);
                        System.out.println("[i] Genre updated.");
                    } else {
                        System.out.println("[!] Genre cannot be empty.");
                    }
                    break;
                case 4:
                    System.out.print("Enter new number of available copies: ");
                    try {
                        int copies = Integer.parseInt(scanner.nextLine());
                        if (copies >= 0) {
                            deepCopyBook.setTotalCopies(copies);
                            System.out.println("[i] Availability updated to " + copies + ".");
                        } else {
                            System.out.println("[!] Cannot be negative.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("[!] Invalid number.");
                    }
                    break;
                default:
                    System.out.println("[!] Invalid option.");
                    editing = false;
                    break;
            }
            System.out.println();
        }

        return deepCopyBook;
    }
}