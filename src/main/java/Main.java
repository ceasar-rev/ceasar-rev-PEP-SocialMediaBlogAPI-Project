import Controller.SocialMediaController;
import DAO.AccountDAO;
import Model.Account;
import io.javalin.Javalin;

/**
 * This class is provided with a main method to allow you to manually run and test your application. This class will not
 * affect your program in any way and you may write whatever code you like here.
 */
public class Main {
    public static void main(String[] args) {
        SocialMediaController controller = new SocialMediaController();
        Javalin app = controller.startAPI();
        app.start(8080);

        // testing code below
        Account testAccount = new Account("Ceasar", "Pass");
        AccountDAO testDAO = new AccountDAO();
        Account createdAccount = testDAO.createAccount(testAccount);
        if (createdAccount == null) {
            System.out.println("Account could not be created in database.");
        }
        else {
            System.out.println("Account created successfully");
            System.out.println(createdAccount.getAccount_id());
            System.out.println(createdAccount.getUsername());
            System.out.println(createdAccount.getPassword());
        }
        
    }
}
