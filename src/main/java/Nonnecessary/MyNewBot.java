package Nonnecessary;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

public class MyNewBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        try {

            Message message = update.getMessage();
            System.out.println(message);
            User user =  message.getFrom();
            if (message.hasText()){

                System.out.println(user);
                System.out.println(user.getUserName() + " " + message.getText());
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(message.getChatId());
                sendMessage.setText("Assalomu  <tg-spoiler>spoiler</tg-spoiler>  " +user.getUserName());
                sendMessage.setParseMode("HTML");
                senMSG(sendMessage);
            }else if (message.hasPhoto()){
                List<PhotoSize> photoList = message.getPhoto();
                StringBuilder builder = new StringBuilder();

                photoList.forEach(photoSize -> {
                    builder.append(photoSize.getFileId());
                    builder.append("   size = ");
                    builder.append(photoSize.getFileSize());
                    builder.append("\n");
                });

                PhotoSize photoSize = photoList.get(photoList.size() - 1);

                SendPhoto sendPhoto = new SendPhoto();

                sendPhoto.setChatId(user.getId());
//                sendPhoto.setPhoto(new InputFile(new File("images.jpg")));
                sendPhoto.setCaption(builder.toString());
                senMSG(sendPhoto);
            }

            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());
            sendMessage.setText("Qayta  ");
            senMSG(sendMessage);

        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }

    public  void senMSG(SendPhoto sendPhoto){
        try {
            execute(sendPhoto);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

    public  void senMSG(SendMessage sendMessage){
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }





























    @Override
    public String getBotUsername() {
        return "ashnaqa_mashnaqa_bot";
    }

    @Override
    public String getBotToken() {
        return "6131486580:AAE9C464EDvo7-MQWIsumKvv5kQnBySghes";
    }
}
