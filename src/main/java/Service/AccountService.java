package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
  private AccountDAO accountDAO;

  public AccountService(){
    accountDAO = new AccountDAO();
  }

  public Account createAccount(Account account){
    if (!isUsernameValid(account.getUsername())){
      return null;
    }
    else if(accountDAO.doesUsernameExist(account.getUsername())){
      return null;
    }
    else{
      return accountDAO.insertAccount(account);
    }
  }

  public Account processLogin(Account account){
    return accountDAO.loginUser(account);
  }

  // Helper function below to make sure username satisfies valid constraints
  public boolean isUsernameValid(String username){
    if(username == "" || username.length() < 4){
      return false;
    }
    return true;
  }
}
