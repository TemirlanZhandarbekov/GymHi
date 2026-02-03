package menu;
import database.*;
import manageable.Gymequipment;
import model.*;
import exception.InvalidInputException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.List;
public class MenuManager implements Menu {
    private final Scanner scanner = new Scanner(System.in);
    private final MemberDAO memberDAO = new MemberDAO();
    private final ArrayList<Trainer> trainers = new ArrayList<>();
    private final ArrayList<Workout> workouts = new ArrayList<>();
    private final ArrayList<Gymequipment> gymequipments = new ArrayList();
    @Override
    public void displayMenu() {
        System.out.println("\nGYM MANAGEMENT SYSTEM");
        System.out.println("1. Add Member");
        System.out.println("2. View All Members");
        System.out.println("3. Update Member Name");
        System.out.println("4. Delete Member");
        System.out.println("5. Search Member by Name");
        System.out.println("6. Search by Fee Range");
        System.out.println("7. Search by Minimum Fee");
        System.out.println("8. Add Trainer");
        System.out.println("9. View All Trainers");
        System.out.println("10. Add Workout");
        System.out.println("11. View All Workouts");
        System.out.println("12. Add Gym Equipment");
        System.out.println("13. View Gym Equipment");
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
                    case 3 -> updateMember();
                    case 4 -> deleteMember();
                    case 5 -> searchMemberByName();
                    case 6 -> searchMemberByFeeRange();
                    case 7 -> searchMemberByMinFee();
                    case 8 -> addTrainer();
                    case 9 -> viewAllTrainers();
                    case 10 -> addWorkout();
                    case 11 -> viewAllWorkouts();
                    case 12 -> addGymequipment();
                    case 13 -> viewAllGymequipment();
                    case 0 -> System.out.println("Thank you! Goodbye!");
                    default -> System.out.println("Invalid choice. Please try again.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Error: Please enter a valid number.");
                scanner.nextLine(); // Clear buffer
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
            if (name.isEmpty()) throw new InvalidInputException("Name cannot be empty");
            String id = "0";
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
            memberDAO.insertMember(member);
            System.out.println("Member processed successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    private void viewAllMembers(){
        memberDAO.getAllMembers();
    }
    private void searchMemberByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.nextLine();
        List<Member> results = memberDAO.searchByName(name);
        for(Member m : results) {
            System.out.println("Found: " + m.getName() + " (" + m.getRoleDescription() + ")");
        }
    }
    private void updateMember() {
        System.out.print("Enter Member ID to update: ");
        int memberId = scanner.nextInt();
        scanner.nextLine();
        Member existingMember = memberDAO.getMemberById(memberId);
        if (existingMember == null){
            System.out.println("No member found with ID: " + memberId);
            return;
        }
        System.out.println("Current Name: " + existingMember.getName());
        System.out.print("Enter New Name (press Enter to keep current): ");
        String newName = scanner.nextLine().trim();
        if (!newName.isEmpty()) {
            try {
                existingMember.setName(newName);
                if(memberDAO.updateMember(existingMember)) {
                    System.out.println("✅ Member updated successfully.");
                } else {
                    System.out.println("❌ Update failed.");
                }
            } catch (InvalidInputException e) {
                System.out.println("Invalid name: " + e.getMessage());
            }
        } else {
            System.out.println("Name unchanged.");
        }
    }
    private void deleteMember() {
        System.out.print("Enter Member ID to delete: ");
        int memberId = scanner.nextInt(); // Fixed variable name (was staffId)
        scanner.nextLine();
        Member member = memberDAO.getMemberById(memberId);
        if (member == null) {
            System.out.println("No member found with ID: " + memberId);
            return;
        }
        System.out.println("Member to delete: " + member.getName());
        System.out.print("Are you sure? (yes/no): ");
        String confirmation = scanner.nextLine();
        if (confirmation.equalsIgnoreCase("yes")) {
            memberDAO.deleteMember(memberId);
        } else {
            System.out.println("Deletion cancelled.");
        }
    }
    private void searchMemberByFeeRange() {
        try {
            System.out.print("Enter Minimum Fee: ");
            double min = scanner.nextDouble();
            System.out.print("Enter Maximum Fee: ");
            double max = scanner.nextDouble();
            scanner.nextLine();
            List<Member> results = memberDAO.searchByFeeRange(min, max);
            System.out.println("\n--- Members with fee between " + min + " and " + max + " ---");
            if (results.isEmpty()) {
                System.out.println("No members found in this price range.");
            } else {
                for (Member m : results) {
                    System.out.println(m.getName() + " (" + m.getRoleDescription() + ")");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter valid numbers for the fee.");
            scanner.nextLine();
        }
    }
    private void searchMemberByMinFee() {
        try {
            System.out.print("Enter Minimum Fee to search: ");
            double min = scanner.nextDouble();
            scanner.nextLine();
            List<Member> results = memberDAO.searchByMinFee(min);
            System.out.println("\n--- Members paying at least " + min + " ---");
            if (results.isEmpty()) {
                System.out.println("No members found paying that much.");
            } else {
                for (Member m : results) {
                    System.out.println(m.getName() + " (" + m.getRoleDescription() + ")");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Error: Please enter a valid number.");
            scanner.nextLine();
        }
    }
    private void addTrainer() {
        try {
            System.out.print("Add trainer name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) throw new InvalidInputException("Name cannot be empty");
            System.out.print("His id: ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) throw new InvalidInputException("ID cannot be empty");
            System.out.println("Choose Type:");
            System.out.println("1. Personal Trainer");
            System.out.println("2. Group Trainer");
            int type = scanner.nextInt();
            scanner.nextLine();
            Trainer trainer = switch (type) {
                case 1 -> new Trainer.PersonalTrainer(name, id);
                case 2 -> new Trainer.GroupTrainer(name, id);
                default -> throw new InvalidInputException("Invalid trainer type");
            };
            trainers.add(trainer);
            System.out.println("Trainer is added successfully!");
        } catch (InvalidInputException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid number format.");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
    private void viewAllTrainers() {
        if (trainers.isEmpty()) {
            System.out.println("\nNo trainers registered yet.");
            return;
        }
        System.out.println("\nAll Trainers:");
        for (Trainer t : trainers) {
            System.out.println(t);
        }
    }
    private void addWorkout() {
        try {
            System.out.println("\nAdd New Workout");
            System.out.print("Workout ID: ");
            String id = scanner.nextLine().trim();
            if (id.isEmpty()) throw new InvalidInputException("Workout ID cannot be empty");
            System.out.print("Workout Name: ");
            String name = scanner.nextLine().trim();
            if (name.isEmpty()) throw new InvalidInputException("Workout name cannot be empty");
            System.out.print("Duration (minutes): ");
            int duration = scanner.nextInt();
            scanner.nextLine();
            if (duration <= 0) throw new InvalidInputException("Duration must be a positive number");
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
        } catch (InvalidInputException e) {
            System.out.println("Input error: " + e.getMessage());
        } catch (InputMismatchException e) {
            System.out.println("Error: Invalid number format.");
            scanner.nextLine();
        } catch (Exception e) {
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
    private void addGymequipment() {
        try {
            System.out.println("\nAdd Gymequipment");
            System.out.println("\nEnter name: ");
            String titleequipment = scanner.nextLine().trim();
            if (titleequipment.isEmpty()) throw new InvalidInputException("there is no such thing");
            System.out.println("\nWhat's weight? ");
            int weight = scanner.nextInt();
            scanner.nextLine();
            if (weight <= 0) throw new InvalidInputException("Nah");
            System.out.println("Type: ");
            System.out.println("1. Dumbell");
            System.out.println("2. Shtanga");
            int type = scanner.nextInt();
            scanner.nextLine();
            Gymequipment gymequipment = switch (type) {
                case 1 -> new Gymequipment.Dumbell(titleequipment, weight);
                case 2 -> new Gymequipment.Shtanga(titleequipment, weight);
                default -> throw new InvalidInputException("Invalid input");
            };
            gymequipments.add(gymequipment);
            System.out.println("Gymequipment added successfully");
        } catch (InvalidInputException e){
            System.out.println("invalid input: " + e.getMessage());
        } catch (InputMismatchException e){
            System.out.println("No brainrot");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("no such thing " + e.getMessage());
        }
    }
    private void viewAllGymequipment() {
        if (gymequipments.isEmpty()) {
            System.out.println("\nNo gymequipment registered yet.");
            return;
        }
        System.out.println("\nAll gymequipment");
        for (Gymequipment b : gymequipments) {
            System.out.println(b);
        }
    }
}