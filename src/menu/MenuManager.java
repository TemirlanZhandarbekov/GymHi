package menu;
import model.*;
import exception.InvalidInputException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
public class MenuManager implements Menu {
    private final ArrayList<Member> members = new ArrayList<>();
    private final ArrayList<Trainer> trainers = new ArrayList<>();
    private final ArrayList<Workout> workouts = new ArrayList<>();
    private final Scanner scanner = new Scanner(System.in);
    @Override
    public void displayMenu() {
        System.out.println("\nGYM MANAGEMENT SYSTEM");
        System.out.println("1. Add Member");
        System.out.println("2. View All Members");
        System.out.println("3. Add Trainer");
        System.out.println("4. View All Trainers");
        System.out.println("5. Add Workout");
        System.out.println("6. View All Workouts");
        System.out.println("0. Exit");
        System.out.print("Enter choice: ");
    }
    @Override
    public void run() {
        int choice;
        do {
            displayMenu();
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
                switch (choice) {
                    case 1 -> addMember();
                    case 2 -> viewAllMembers();
                    case 3 -> addTrainer();
                    case 4 -> viewAllTrainers();
                    case 5 -> addWorkout();
                    case 6 -> viewAllWorkouts();
                    case 0 -> System.out.println("Thank you! Goodbye!");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine();
                choice = -1;
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
                choice = -1;
            }
        } while (choice != 0);
        scanner.close();
    }
    private void addMember() {
        try {
            System.out.println("\nAdd New Member");
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                throw new InvalidInputException("Name cannot be empty");
            }
            System.out.print("Member ID: ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                throw new InvalidInputException("Member ID cannot be empty");
            }
            System.out.println("Type:");
            System.out.println("  1. Student");
            System.out.println("  2. Premium");
            System.out.println("  3. Senior");
            System.out.print("Choice: ");
            int type = scanner.nextInt();
            scanner.nextLine();
            Member member = switch (type) {
                case 1 -> new Member.StudentMember(name, id);
                case 2 -> new Member.PremiumMember(name, id);
                case 3 -> new Member.SeniorMember(name, id);
                default -> throw new InvalidInputException("Invalid member type selected");
            };
            members.add(member);
            System.out.println("Member added successfully!");
        }
        catch (InvalidInputException e) {
            System.out.println("Input error: " + e.getMessage());
        }
        catch (InputMismatchException e) {
            System.out.println("Error: Invalid number format for type.");
            scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    private void viewAllMembers() {
        if (members.isEmpty()) {
            System.out.println("\nNo members registered yet.");
            return;
        }
        System.out.println("\nAll Members");
        for (Member m : members) {
            System.out.println(m);
        }
    }
    private void addTrainer() {
        try {
            System.out.println("\nAdd New Trainer");
            System.out.print("Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                throw new InvalidInputException("Trainer name cannot be empty");
            }
            System.out.print("Trainer ID: ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                throw new InvalidInputException("Trainer ID cannot be empty");
            }
            System.out.println("Type:");
            System.out.println("  1. Personal Trainer");
            System.out.println("  2. Group Trainer");
            System.out.print("Choice: ");
            int type = scanner.nextInt();
            scanner.nextLine();
            Trainer trainer = switch (type) {
                case 1 -> new Trainer.PersonalTrainer(name, id);
                case 2 -> new Trainer.GroupTrainer(name, id);
                default -> throw new InvalidInputException("Invalid trainer type selected");
            };
            trainers.add(trainer);
            System.out.println("Trainer added successfully!");
        }
        catch (InvalidInputException e) {
            System.out.println("Input error: " + e.getMessage());
        }
        catch (InputMismatchException e) {
            System.out.println("Error: Invalid number format for type.");
            scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    private void viewAllTrainers() {
        if (trainers.isEmpty()) {
            System.out.println("\nNo trainers registered yet.");
            return;
        }
        System.out.println("\n=== All Trainers ===");
        for (Trainer t : trainers) {
            System.out.println(t);
        }
    }
    private void addWorkout() {
        try {
            System.out.println("\nAdd New Workout");
            System.out.print("Workout ID: ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) {
                throw new InvalidInputException("Workout ID cannot be empty");
            }
            System.out.print("Workout Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) {
                throw new InvalidInputException("Workout name cannot be empty");
            }
            System.out.print("Duration (minutes): ");
            int duration = scanner.nextInt();
            scanner.nextLine();
            if (duration <= 0) {
                throw new InvalidInputException("Duration must be a positive number");
            }
            System.out.println("Type:");
            System.out.println("  1. Cardio");
            System.out.println("  2. Strength");
            System.out.println("  3. Yoga");
            System.out.print("Choice: ");
            int type = scanner.nextInt();
            scanner.nextLine();
            Workout workout = switch (type) {
                case 1 -> new Workout.CardioWorkout(id, name, duration);
                case 2 -> new Workout.StrengthWorkout(id, name, duration);
                case 3 -> new Workout.YogaWorkout(id, name, duration);
                default -> throw new InvalidInputException("Invalid workout type selected");
            };
            workouts.add(workout);
            System.out.println("Workout added successfully!");
        }
        catch (InvalidInputException e) {
            System.out.println("Input error: " + e.getMessage());
        }
        catch (InputMismatchException e) {
            System.out.println("Error: Invalid number format.");
            scanner.nextLine();
        }
        catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    private void viewAllWorkouts() {
        if (workouts.isEmpty()) {
            System.out.println("\nNo workouts registered yet.");
            return;
        }
        System.out.println("\nAll Workouts");
        for (Workout w : workouts) {
            System.out.println(w);
        }
    }
}
