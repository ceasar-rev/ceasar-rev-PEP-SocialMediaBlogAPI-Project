package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
  private AccountDAO accountDAO;

  public AccountService(){
    accountDAO = new AccountDAO();
  }

  public Account createAccount(Account account){
    if (account.getUsername() == "" || account.getPassword().length() < 4){
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
}
