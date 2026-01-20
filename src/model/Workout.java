package model;
import exception.InvalidInputException;
public abstract class Workout {
    protected String workoutId;
    protected String name;
    protected int durationMinutes;
    public Workout(String workoutId, String name, int durationMinutes) {
        this.workoutId = workoutId;
        this.name = name;
        this.durationMinutes = durationMinutes;
    }
    public abstract String getWorkoutType();
    public void setDurationMinutes(int duration) throws InvalidInputException {
        if (duration <= 0) {
            throw new InvalidInputException("Duration must be positive");
        }
        this.durationMinutes = duration;
    }
    @Override
    public String toString() {
        return getWorkoutType() + " ID: " + workoutId +
                " Name: " + name + " Duration: " + durationMinutes + " min";
    }
    public static class CardioWorkout extends Workout {
        public CardioWorkout(String workoutId, String name, int durationMinutes) {
            super(workoutId, name, durationMinutes);
        }
        @Override
        public String getWorkoutType() {
            return "Cardio Workout";
        }
    }
    public static class StrengthWorkout extends Workout {
        public StrengthWorkout(String workoutId, String name, int durationMinutes) {
            super(workoutId, name, durationMinutes);
        }
        @Override
        public String getWorkoutType() {
            return "Strength Workout";
        }
    }
    public static class YogaWorkout extends Workout {
        public YogaWorkout(String workoutId, String name, int durationMinutes) {
            super(workoutId, name, durationMinutes);
        }
        @Override
        public String getWorkoutType() {
            return "Yoga Workout";
        }
    }
}
