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
  if(!isMessageTextValid(message.getMessage_text())){
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

 public Message retrieveMessageById(int messageId){
  return messageDAO.getMessageById(messageId);
 }

 public Message deleteMessageById(int messageId){
  Message message = retrieveMessageById(messageId);
  if (message != null){
    messageDAO.deleteMessageById(messageId);
  }
  return message;
 }

 public Message updateMessageTextById(int messageId, String messageText){
  Message oldMessage = retrieveMessageById(messageId);
  if (oldMessage == null || !isMessageTextValid(messageText)){
    return null;
  }
  messageDAO.updateMessageById(messageId, messageText);
  Message updatedMessage = retrieveMessageById(messageId);
  return updatedMessage;
 }

 public boolean isMessageTextValid(String messageText){
  if(messageText == "" || messageText.length() > 255){
    return false;
  }
  return true;
 }
}
