class Member {
    protected String name;
    protected String age;
    protected String membershipType;
    public Member(String name, String age, String membershipType) {
        this.name = name;
        this.age = age;
        this.membershipType = membershipType;
    }
    @Override
    public String toString(){
        return "Name: " + name + age + membershipType;
    }
}
