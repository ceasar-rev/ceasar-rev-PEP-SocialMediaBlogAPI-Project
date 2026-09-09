package Controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Model.Message;
import Service.AccountService;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

public class SocialMediaController {
    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(){
        this.accountService = new AccountService();
        this.messageService = new MessageService();
    }

    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postAccountLoginHandler);
        app.post("/messages", this::postMessageHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageByIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageByIdHandler);
        app.patch("/messages/{message_id}", this::patchMessageTextByIdHandler);

        return app;
    }

    private void postAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account addedAccount = accountService.createAccount(account);
        if(addedAccount != null){
            context.json(mapper.writeValueAsString(addedAccount));
        }
        else{
            context.status(400);
        }
    }

    private void postAccountLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(context.body(), Account.class);
        Account loginAccount = accountService.processLogin(account);
        if(loginAccount != null){
            context.json(mapper.writeValueAsString(loginAccount));
        }
        else{
            context.status(401);
        }
    }

    private void postMessageHandler(Context context) throws JsonProcessingException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(context.body(), Message.class);
        Message addedMessage = messageService.createMessage(message);
        if(addedMessage != null){
            context.json(mapper.writeValueAsString(addedMessage));
        }
        else{
            context.status(400);
        }
    }

    private void getAllMessagesHandler(Context context){
        List<Message> messages = messageService.retrieveAllMessages();
        context.json(messages);
    }
    
    private void getMessageByIdHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.retrieveMessageById(messageId);
        if(message != null){
            context.json(message);
        }
    }

    private void deleteMessageByIdHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.deleteMessageById(messageId);
        if(message != null){
            context.json(message);
        }
    }

    private void patchMessageTextByIdHandler(Context context){
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        String messageText = context.attribute("message_text");
        Message message = messageService.updateMessageTextById(messageId, messageText);
        if(message != null){
            context.json(message);
        }
        else{
            context.status(400);
        }
    }
}