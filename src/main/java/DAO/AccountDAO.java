package DAO;

import java.sql.*;

import Model.Account;
import Util.ConnectionUtil;

public class AccountDAO {
  public Account insertAccount(Account account) {
    Connection connection = ConnectionUtil.getConnection();
    try {
      String sql = "INSERT INTO account (username, password) VALUES (?, ?)" ;
      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      
      preparedStatement.setString(1, account.getUsername());
      preparedStatement.setString(2, account.getPassword());
            
      preparedStatement.executeUpdate();
      ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
      if(pkeyResultSet.next()){
          int generated_account_id = (int) pkeyResultSet.getLong(1);
          return new Account(generated_account_id, account.getUsername(), account.getPassword());
      }
    }catch(SQLException e){
      System.out.println(e.getMessage());
    }
    return null;
  }

  public Account loginUser(Account account) {
    Connection connection = ConnectionUtil.getConnection();
    try{
      String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setString(1, account.getUsername());
      preparedStatement.setString(2, account.getPassword());
      
      ResultSet rs = preparedStatement.executeQuery();
      while(rs.next()){
        Account acc = new Account(rs.getInt("account_id"),
          rs.getString("username"),
          rs.getString("password"));
        return acc;
      }
    }catch(SQLException e){
      System.out.println(e.getMessage());
    }
    return null;
  }

  public boolean doesUsernameExist(String username){
    Connection connection = ConnectionUtil.getConnection();
    try{
      String sql = "SELECT * FROM account WHERE username = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      
      preparedStatement.setString(1, username);

      ResultSet rs = preparedStatement.executeQuery();
      if(rs.next()){
        return true;
      }
    }catch(SQLException e){
      System.out.println(e.getMessage());
    }
    return false;
  }
}
