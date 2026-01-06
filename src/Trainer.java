public class Trainer {
    private String name;
    private String specialty;
    public Trainer(String name, String specialty){
        this.name = name;
        this.specialty = specialty;
    }
    @Override
    public String toString(){
        return name + specialty;
    }
}