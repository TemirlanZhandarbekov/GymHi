class Hi {
    protected String name;
    protected int age;
    protected String membershipType;
}

class Member extends Hi {

    public Member(String name, int age, String membershipType) {
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
    }
    @Override
    public String toString(){
        return "Name: " + name + age + membershipType;
    }
}
