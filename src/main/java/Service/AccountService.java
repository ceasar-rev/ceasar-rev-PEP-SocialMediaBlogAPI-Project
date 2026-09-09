package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
  private AccountDAO accountDAO;

  public AccountService(){
    accountDAO = new AccountDAO();
  }

  public Account createAccount(Account account){
    // 1. Check if username is not blank
    // 2. Check if password is longer than 4 characters
    // 3. Check if username already exists
    if (account.getUsername() == "" || account.getPassword().length() < 4){
      // could throw exception here
      return null;
    }
    if (accountDAO.doesUsernameExist(account.getUsername())){
      // could throw exception here
      return null;
    }
    return accountDAO.insertAccount(account);
  }

  public Account processLogin(Account account){
    return accountDAO.loginUser(account);
  }
}
