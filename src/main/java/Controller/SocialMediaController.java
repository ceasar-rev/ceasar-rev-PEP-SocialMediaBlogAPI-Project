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
    
    private void getMessageByIdHandler(Context context) throws JsonProcessingException{
        int messageId = Integer.parseInt(context.pathParam("message_id"));
        Message message = messageService.retrieveMessageById(messageId);
        if(message == null){
            context.status(200);
        }
        context.json(message);
    }
}