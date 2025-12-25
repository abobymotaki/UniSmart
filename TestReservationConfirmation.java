import entities.Book;
import entities.BorrowQueue;
import entities.Person;
import java.time.LocalTime;
import java.util.*;

public class TestReservationConfirmation {
    
    // Copy the relevant methods from Main for testing
    static Queue<BorrowQueue> getOrderedQueue(Queue<BorrowQueue> queue) {
        LocalTime peakStart = LocalTime.of(14, 0);
        LocalTime peakEnd = LocalTime.of(19, 0);
        LocalTime now = LocalTime.now();
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
    
    static BorrowQueue getNextReservation(Queue<BorrowQueue> queue) {
        if (queue.isEmpty()) {
            return null;
        }
        
        Queue<BorrowQueue> orderedQueue = getOrderedQueue(queue);
        return orderedQueue.peek(); // Get the next reservation without removing it
    }
    
    public static void main(String[] args) {
        // Test the reservation confirmation logic
        ArrayList<Book> books = new ArrayList<>();
        List<Person> people = new LinkedList<>();
        Queue<BorrowQueue> queue = new LinkedList<>();
        
        // Seed test data
        Person.seedPeople(people);
        Book.seedBooks(books);
        BorrowQueue.seedQueue(queue, people, books);
        
        System.out.println("=== TESTING RESERVATION CONFIRMATION LOGIC ===");
        System.out.println("Original queue size: " + queue.size());
        
        // Test 1: Get next reservation based on constraints
        System.out.println("\nTest 1: Getting next reservation based on constraints");
        BorrowQueue next = getNextReservation(queue);
        if (next != null) {
            System.out.println("Next reservation should be: S-ID-" + next.getSequence() + 
                             " by " + next.getStudent().getName() + 
                             " (Priority: " + next.getStudent().getPriority() + ")");
        } else {
            System.out.println("No reservations found!");
        }
        
        // Test 2: Verify ordering during peak hours (simulate)
        System.out.println("\nTest 2: Checking reservation ordering");
        Queue<BorrowQueue> orderedQueue = getOrderedQueue(queue);
        Queue<BorrowQueue> displayQueue = new LinkedList<>(orderedQueue);
        
        System.out.println("Reservations in order:");
        int count = 0;
        while (!displayQueue.isEmpty() && count < 5) {
            BorrowQueue q = displayQueue.poll();
            System.out.println((count + 1) + ". S-ID-" + q.getSequence() + 
                             " by " + q.getStudent().getName() + 
                             " (Priority: " + q.getStudent().getPriority() + ")");
            count++;
        }
        
        // Test 3: Verify that removing the correct reservation works
        System.out.println("\nTest 3: Testing reservation removal logic");
        BorrowQueue toRemove = getNextReservation(queue);
        if (toRemove != null) {
            System.out.println("Attempting to remove: S-ID-" + toRemove.getSequence());
            boolean removed = queue.remove(toRemove);
            System.out.println("Removal successful: " + removed);
            System.out.println("Queue size after removal: " + queue.size());
            
            // Get the next reservation after removal
            BorrowQueue newNext = getNextReservation(queue);
            if (newNext != null) {
                System.out.println("New next reservation: S-ID-" + newNext.getSequence() + 
                                 " by " + newNext.getStudent().getName());
            }
        }
        
        System.out.println("\n=== TEST COMPLETED ===");
    }
}