package manageable;

public abstract class Gymequipment {
    protected String titleequipment;
    protected int weight;
    public Gymequipment(String titleequipment, int weight){
        this.titleequipment = titleequipment;
        this.weight = weight;
    }
    public abstract String getGymequipmenttype();
    @Override
    public String toString(){
        return getGymequipmenttype() + " name: " + titleequipment + " weight " + weight;
    }
    public static class Dumbell extends Gymequipment {
        public Dumbell (String titleequipment, int weight){
            super(titleequipment, weight);
        }
        @Override
        public String getGymequipmenttype() {
            return "Dumbell";
        }
    }
    public static class Shtanga extends Gymequipment {
        public Shtanga (String titleequipment, int weight){
            super(titleequipment, weight);
        }
        @Override
        public String getGymequipmenttype(){
            return "Shtanga";
        }
    }
}

