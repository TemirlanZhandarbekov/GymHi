import java.util.ArrayList;
import java.util.Scanner;
public class Main {
    private static ArrayList<Member> members = new ArrayList<>();
    private static ArrayList<Trainer> trainers = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            System.out.println("Gym");
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice){
                case 1:
                    addMember();
                    break;
                case 2:
                    viewAllMembers();
                case 3:
                    addTrainer();
                    break;
                case 4:
                    viewAllTrainers();
                    break;
                case 5:
                    running = false;
                    break;
            }
        }
        scanner.close();
    }
    private static void addMember() {
        String name = scanner.nextLine();
        int age = scanner.nextInt();
        scanner.nextLine();
        String membershipType = scanner.nextLine();
        Member member = new Member(name, age, membershipType);
        members.add(member);
    }
    private static void viewAllMembers(){
        if (members.isEmpty()){
            System.out.println("no");
        } else {
            for (int i = 0; i <members.size(); i++){
                System.out.println((i + 1) +". " + members.get(i));
            }
        }
    }
    private static void addTrainer(){
        String name = scanner.nextLine();
        String specialty = scanner.nextLine();
        Trainer trainer = new Trainer(name, specialty);
        trainers.add(trainer);
    }
    private static void viewAllTrainers(){
        if (trainers.isEmpty()){
            System.out.println("no");
        } else {
            for (int i = 0; i <trainers.size(); i++){
                System.out.println((i + 1) +". " + trainers.get(i));
            }
        }
    }
}