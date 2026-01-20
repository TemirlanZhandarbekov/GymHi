package model;
import exception.InvalidInputException;
public abstract class Trainer extends Staff {
    public Trainer(String name, String trainerId) {
        super(name, trainerId);
    }
    public abstract String getTrainerType();
    @Override
    public String getRoleDescription() {
        return getTrainerType() + " Trainer";
    }
    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Trainer name cannot be empty");
        }
        this.name = name.trim();
    }
    public static class PersonalTrainer extends Trainer {
        public PersonalTrainer(String name, String trainerId) {
            super(name, trainerId);
        }
        @Override
        public String getTrainerType() {
            return "Personal Trainer";
        }
    }
    public static class GroupTrainer extends Trainer {
        public GroupTrainer(String name, String trainerId) {
            super(name, trainerId);
        }
        @Override
        public String getTrainerType() {
            return "Group Trainer";
        }
    }
}
