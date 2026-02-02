import database.MemberDAO;
import model.Staff;
import model.Trainer;
import model.Member;

public class TestInsert {
    public static void main(String[] args) {
        Member newMember = new Member.PremiumMember("Bro", "151");
        MemberDAO dao = new MemberDAO();
        dao.insertMember(newMember);
    }
}
