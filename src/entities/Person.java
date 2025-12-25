package entities;

import java.util.List;

public class Person {
    private static int counter = 0;
    final int id;
    String name;
    public int priority; // 1 = Faculty || 2 = Postgraduate || 3 = Undergraduate

    public Person(String name, int priority) {
        this.id = ++counter;
        this.name = name;
        this.priority = priority;
    }

    public void displayPerson() {
        System.out.println(
                "ID: " + id
                + ") Name: " + getName()
                + ", Status: " + getType(getPriority())
        );
    }

    public String getType(int priority) {
        String entityType = null;

        switch (priority) {
            case 1 -> entityType = "Faculty";
            case 2 -> entityType = "PostGraduate";
            case 3 -> entityType = "Undergraduate";
            default -> entityType = "'undefined'";
        }

        return entityType;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public static List<Person> seedPeople(List<Person> people) {
        people.add(new Person("Taki Ahmed", 1));
        people.add(new Person("Ayan Ahmed", 2));
        people.add(new Person("Ahsabb Ahmed", 3));
        people.add(new Person("Shafakat Ahmed", 2));
        people.add(new Person("Inaya Ahmed", 3));
        people.add(new Person("Hafiz Ahmed", 3));
        people.add(new Person("Ayub Ahmed", 2));
        people.add(new Person("Ronn Ahmed", 1));
        people.add(new Person("Aksa Ahmed", 3));
        people.add(new Person("Aaya Ahmed", 2));
        people.add(new Person("Saad Ahmed", 2));
        people.add(new Person("Zara Khan", 3));
        people.add(new Person("Omar Farooq", 2));
        people.add(new Person("Fatima Zahra", 3));
        people.add(new Person("Yusuf Ali", 3));
        people.add(new Person("Maryam Siddiqui", 2));
        people.add(new Person("Ibrahim Hassan", 3));
        people.add(new Person("Aisha Rahman", 1));
        people.add(new Person("Hamza Malik", 2));
        people.add(new Person("Noor Fatima", 3));
        people.add(new Person("Bilal Ahmed", 3));
        people.add(new Person("Sofia Khan", 2));
        people.add(new Person("Arham Sheikh", 3));
        people.add(new Person("Zainab Iqbal", 3));
        people.add(new Person("Danish Raza", 2));
        people.add(new Person("Hoorain Aslam", 3));
        people.add(new Person("Talha Qureshi", 1));
        people.add(new Person("Amna Javed", 2));
        people.add(new Person("Rayyan Butt", 3));
        people.add(new Person("Eshaal Nawaz", 3));
        people.add(new Person("Fahad Mehmood", 2));
        people.add(new Person("Laiba Akram", 3));
        people.add(new Person("Haroon Shah", 1));
        people.add(new Person("Maira Yousaf", 2));
        people.add(new Person("Usman Ghani", 3));
        people.add(new Person("Anabiya Tariq", 1));
        people.add(new Person("Shahzain Rajput", 2));
        people.add(new Person("Rabia Basheer", 3));
        people.add(new Person("Abdullah Chohan", 3));
        people.add(new Person("Hafsa Naeem", 2));
        people.add(new Person("Zeeshan Arain", 3));
        people.add(new Person("Alisha Farooq", 3));
        people.add(new Person("Waleed Sarwar", 2));
        people.add(new Person("Sana Gull", 3));
        people.add(new Person("Mikaeel Ahmed", 1));
        people.add(new Person("Zeyan Shahid", 1));

        System.out.println("[ ! ] All People Seeded.");

        return people;
    }
}
