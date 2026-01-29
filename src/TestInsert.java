import database.StaffDAO;
import model.Staff;
import model.Trainer;

public class TestInsert {
    public static void main(String[] args) {
        Staff staff = new Trainer.GroupTrainer("Bro", "Aibek");
        StaffDAO dao = new StaffDAO();
        dao.insertStaff(staff);
    }
}
