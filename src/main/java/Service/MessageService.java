package Service;

import java.util.List;

import DAO.MessageDAO;
import Model.Message;

public class MessageService {
 private MessageDAO messageDAO;
 
 public MessageService(){
  messageDAO = new MessageDAO();
 }

 public Message createMessage(Message message){
  // 1. Check if message_text is not blank
  // 2. Check if message_text is not over 255 characters
  // 3. Check if posted_by refers to an existing user
  if(message.getMessage_text() == "" || message.getMessage_text().length() > 255){
    // could throw exception here
    return null;
  }
  if(!messageDAO.doesPostedByIdExist(message.getPosted_by())){
    // could throw exception here
    return null;
  }
  return messageDAO.insertMessage(message);
 }

 public List<Message> retrieveAllMessages(){
  return messageDAO.getAllMessages();
 }
}
