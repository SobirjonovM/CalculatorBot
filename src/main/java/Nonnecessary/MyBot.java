package Nonnecessary;

import org.apache.commons.io.FileUtils;
import Nonnecessary.Interfaces.CallbackData;
import Nonnecessary.Interfaces.UserStep;
import Nonnecessary.Interfaces.TgUser;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.*;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MyBot extends TelegramLongPollingBot {
    List<TgUser> tgUsers = new ArrayList<>();
    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()){
            String chatId = update.getMessage().getChatId().toString();
            TgUser user = saveUser(chatId);
            // If user sends message... this code executes
            Message message = update.getMessage();

            if (message.hasText()){
                String text = message.getText();
                if (text.equals("/list")) {
                    System.out.println(tgUsers);
                }
                else if (text.equals("/start")){
                    if (user.getFullName() != null){
                        try {
                            setLang(chatId, user);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        /* New Intro*/
                        sendText(chatId, """
                            Assalamu alaykum
                            Iltimos Ism-Familiyangizni kiriting \uD83C\uDDFA\uD83C\uDDFF
                            Пожалуйста введите свое имя \uD83C\uDDF7\uD83C\uDDFA""");
                        user.setStep(UserStep.WRITE_NAME);
                    }

                    // WRITE FULLNAME step
                } else if (user.getStep().equals(UserStep.WRITE_NAME)) {
                    if (text.contains(" ")){
                        user.setFullName(text);
                        try {
                            setLang(chatId, user);
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }else {
                        sendText(chatId, "\uD83C\uDDFA\uD83C\uDDFF Ismingizni to'g'ri kiriting " +
                                "\n\uD83C\uDDF7\uD83C\uDDFA Пожалуйста введите свое имя правильно ");
                    }
                }
                //SEND MESSAGE step
                else if (user.getStep().equals(UserStep.WRITE_MSG)) {
                    user.setMsg(text);
                    sendText(chatId, user.getSelectedLang().equals(CallbackData.UZBEK)
                            ? "Adminstrator tez orada siz bilan bog'lanadi )" :
                            "Админстратор скоро с вами свяжеться )");
                    user.setStep(UserStep.STOP);
                }
            }
        }else if(update.hasCallbackQuery()){
            String chatId = update.getCallbackQuery().getFrom().getId().toString();
            TgUser user = saveUser(chatId);
            String data = update.getCallbackQuery().getData();
            TgUser tgUser = saveUser(chatId);
            if (user.getStep().equals(UserStep.SELECT_LANG)) {
                if (data.equals(CallbackData.UZBEK)) {
                    user.setSelectedLang(CallbackData.UZBEK);
                    sendText(chatId, "Xabaringizni qoldiring. ");
                } else if (data.equals(CallbackData.RUSSIAN)) {
                    user.setSelectedLang(CallbackData.RUSSIAN);
                    sendText(chatId, "Оставьте свое сообщение");
                }
                user.setStep(UserStep.WRITE_MSG);
            }
        }
    }

    private void setLang(String chatId, TgUser user) throws TelegramApiException {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Hurmatli, " + user.getFullName().split(" ")[0].toString() + " \uD83C\uDDFA\uD83C\uDDFF Iltimos tilni tanlang \n" +
                "Дорогой, " + user.getFullName().split(" ")[0].toString() + "\uD83C\uDDF7\uD83C\uDDFA Пожалуйста выберите язык ");
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<InlineKeyboardButton> columns = new ArrayList<>();
        // Inline keys
        InlineKeyboardButton inlineKeyboardButtonUz = new InlineKeyboardButton();
        inlineKeyboardButtonUz.setText("\uD83C\uDDFA\uD83C\uDDFF Uz");
        inlineKeyboardButtonUz.setCallbackData(CallbackData.UZBEK);

        InlineKeyboardButton inlineKeyboardButtonRu = new InlineKeyboardButton();
        inlineKeyboardButtonRu.setText("\uD83C\uDDF7\uD83C\uDDFA Ru");
        inlineKeyboardButtonRu.setCallbackData(CallbackData.RUSSIAN);

        columns.add(inlineKeyboardButtonUz);
        columns.add(inlineKeyboardButtonRu);

        List<List<InlineKeyboardButton>> rows = new ArrayList<>();
        rows.add(columns);
        inlineKeyboardMarkup.setKeyboard(rows);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        execute(sendMessage);
        user.setStep(UserStep.SELECT_LANG);
    }


    private void saveFileToFolder(String fileId, String fileName) throws Exception {
        GetFile getFile = new GetFile(fileId);
        File execute = execute(getFile);
        String fileUrl = execute.getFileUrl(getBotToken());
        URL url = new URL(fileUrl);
        InputStream inputStream = url.openStream();
        FileUtils.copyInputStreamToFile(inputStream, new java.io.File(fileName));
    }
    private void sendText(String chatId, String text){
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
    private TgUser saveUser(String chatId){
        for (TgUser user : tgUsers) {
            if (user.getChatId().equals(chatId)) return user;
        } TgUser tgUser = new TgUser();
            tgUser.setChatId(chatId);
            tgUsers.add(tgUser);
            return tgUser;
    }
    @Override
    public String getBotToken() {
        return "5363436340:AAFUhQT7mCCZYZHKO20RssqSiHpS8gLCLzk";
    }

    @Override
    public String getBotUsername() {
        return "pleaseusernameformybot";
    }
    }



// Overridden methods here...

