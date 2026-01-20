package model;
import exception.InvalidInputException;
public abstract class Member extends Staff {
    public Member(String name, String memberId) {
        super(name, memberId);
    }
    public abstract String getMembershipType();
    @Override
    public String getRoleDescription() {
        return getMembershipType() + " Member";
    }
    public void setName(String name) throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidInputException("Name cannot be empty");
        }
        this.name = name.trim();
    }
    public void setMemberId(String memberId) throws InvalidInputException {
        if (memberId == null || memberId.trim().isEmpty()) {
            throw new InvalidInputException("Member ID cannot be empty");
        }
        this.id = memberId.trim();
    }
    public static class StudentMember extends Member {
        public StudentMember(String name, String memberId) {
            super(name, memberId);
        }
        @Override
        public String getMembershipType() {
            return "Student";
        }
    }
    public static class PremiumMember extends Member {
        public PremiumMember(String name, String memberId) {
            super(name, memberId);
        }
        @Override
        public String getMembershipType() {
            return "Premium";
        }
    }
    public static class SeniorMember extends Member {
        public SeniorMember(String name, String memberId) {
            super(name, memberId);
        }
        @Override
        public String getMembershipType() {
            return "Senior";
        }
    }
}
