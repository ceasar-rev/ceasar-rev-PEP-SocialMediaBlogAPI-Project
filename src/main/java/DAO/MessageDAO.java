package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
  public Message insertMessage(Message message){
    Connection connection = ConnectionUtil.getConnection();
    try{
      String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)" ;
      PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
      
      preparedStatement.setInt(1, message.getPosted_by());
      preparedStatement.setString(2, message.getMessage_text());
      preparedStatement.setLong(3, message.getTime_posted_epoch());
            
      preparedStatement.executeUpdate();
      ResultSet pkeyResultSet = preparedStatement.getGeneratedKeys();
      if(pkeyResultSet.next()){
          int generated_message_id = (int) pkeyResultSet.getLong(1);
          return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());
      }
    }catch(SQLException e){
      System.out.println(e.getMessage());
    }
    return null;
  }

  public List<Message> getAllMessages(){
    Connection connection = ConnectionUtil.getConnection();
        List<Message> messages = new ArrayList<>();
        try {
            String sql = "SELECT * FROM message";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Message message = new Message(rs.getInt("message_id"),
                        rs.getInt("posted_by"),
                        rs.getString("message_text"),
                        rs.getLong("time_posted_epoch"));
                messages.add(message);
            }
        }catch(SQLException e){
          System.out.println(e.getMessage());
        }
        return messages;
  }

  public Message getMessageById(int messageId){
    Connection connection = ConnectionUtil.getConnection();
    try{
      String sql = "SELECT * FROM message WHERE message_id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);

      preparedStatement.setInt(1, messageId);

      ResultSet rs = preparedStatement.executeQuery();
      while(rs.next()){
        Message message = new Message(rs.getInt("message_id"),
        rs.getInt("posted_by"),
        rs.getString("message_text"),
        rs.getLong("time_posted_epoch"));
        return message;
      }
    }catch(SQLException e){
      System.out.println(e.getMessage());
    }
    return null;
  }

  public Message deleteMessageById(int messageId){
    Message message = getMessageById(messageId);
    if(message != null){
      Connection connection = ConnectionUtil.getConnection();
      try{
        String sql = "DELETE * FROM message WHERE message_id = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, messageId);
        preparedStatement.executeUpdate();
        return message;
      }catch(SQLException e){
        System.out.println(e.getMessage());
      }
    }
    return null;
    // Connection connection = ConnectionUtil.getConnection();
    // try{
    //   String sqlSelect = "SELECT * FROM message WHERE message_id = ?";
    //   PreparedStatement preparedStatementSelect = connection.prepareStatement(sqlSelect);

    //   preparedStatementSelect.setInt(1, messageId);
      
    //   ResultSet rs = preparedStatementSelect.executeQuery();
    //   if(rs.next()){
    //     Message message = new Message(rs.getInt("message_id"),
    //     rs.getInt("posted_by"),
    //     rs.getString("message_text"),
    //     rs.getLong("time_posted_epoch"));

    //     String sqlDelete = "DELETE * FROM message WHERE message_id = ?";
    //     PreparedStatement preparedStatementDelete = connection.prepareStatement(sqlDelete);
    //     preparedStatementDelete.setInt(1, messageId);
    //     preparedStatementDelete.executeUpdate();
    //     return message;
    //   }
    // }catch(SQLException e){
    //   System.out.println(e.getMessage());
    // }
    // return message;
  }

  public Message updateMessageById(int messageId){
    return null;
  }

  public List<Message> getAllMessagesByUser(int accountId){
    return null;
  }

  public boolean doesPostedByIdExist(int accountId){
    Connection connection = ConnectionUtil.getConnection();
    try{
      String sql = "SELECT * FROM account WHERE account_id = ?";
      PreparedStatement preparedStatement = connection.prepareStatement(sql);
      preparedStatement.setInt(1, accountId);
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
