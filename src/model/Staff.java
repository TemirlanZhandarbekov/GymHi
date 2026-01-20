package model;
public abstract class Staff {
    protected String name;
    protected String id;
    public Staff(String name, String id) {
        this.name = name;
        this.id = id;
    }
    public abstract String getRoleDescription();
    public String getName() {
        return name;
    }
    public String getId() {
        return id;
    }
    @Override
    public String toString() {
        return getRoleDescription() + " ID: " + id + " Name: " + name;
    }
}
