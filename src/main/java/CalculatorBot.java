import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;

import static java.lang.Math.toIntExact;
import static java.lang.Thread.sleep;

public class CalculatorBot extends TelegramLongPollingBot {

    StringBuilder nums;


    StringBuilder signs;
    @Override
    public void onUpdateReceived(Update update) {

        try {


            if(update.hasMessage()){
                Message message = update.getMessage();
                System.out.println(message);
                User user = message.getFrom();

                if(message.hasText()){
                    if(message.getText().equals("/start"))
                    {
                        nums = new StringBuilder();
                        signs = new StringBuilder("#");
                        System.out.println(user);
                        System.out.println(user.getUserName() + " " + message.getText());
                        SendMessage sendMessage = new SendMessage();
                        sendMessage.setChatId(message.getChatId());
                        sendMessage.setText("Assalomu  <tg-spoiler>Alaykum</tg-spoiler>  " + user.getFirstName()+" let's calculate");
                        sendMessage.setParseMode("HTML");
                        sendMessage.setChatId(user.getId());
                        sendMessage.setReplyMarkup(CalculatorUtil.getmenu());
                        senMSG(sendMessage);

                    }

                }

            } else if(update.hasCallbackQuery()){
                CallbackQuery callbackQuery = update.getCallbackQuery();
                String callback = callbackQuery.getData();
                User user = callbackQuery.getFrom();






                switch (callback) {
                    case "zero" -> {
                        nums.append("0");
                        splitSignList(callback);
                    }
                    case "one" -> {
                        nums.append("1");
                        splitSignList(callback);
                    }
                    case "two" -> {
                        nums.append("2");
                        splitSignList(callback);
                    }
                    case "three" -> {
                        nums.append("3");
                        splitSignList(callback);
                    }
                    case "four" -> {
                        nums.append("4");
                        splitSignList(callback);
                    }
                    case "five" -> {
                        nums.append("5");
                        splitSignList(callback);
                    }
                    case "six" -> {
                        nums.append("6");
                        splitSignList(callback);
                    }
                    case "seven" -> {
                        nums.append("7");
                        splitSignList(callback);
                    }
                    case "eight" -> {
                        nums.append("8");
                        splitSignList(callback);
                    }
                    case "nine" -> {
                        nums.append("9");
                        splitSignList(callback);
                    }

                }


                System.out.println(nums);


                if(callback.equals("plus") || callback.equals("minus")|| callback.equals("star")|| callback.equals("division")){
                    if(!nums.substring(nums.length()-1).equals("#")){
                        nums.append("#");
                    }
                    signs.append(" "+ callback);
                }
                System.out.println(signs);



                if(callback.equals("equal")){
//                    calculate();
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(user.getId());
                    sendMessage.setText(calculate());
                    senMSG(sendMessage);
                }




            }




        }catch (RuntimeException e){
            e.printStackTrace();
        }


    }

    private String calculate() {
        double result = 0;
        ArrayList<Integer> numbersList = new ArrayList<>();
        ArrayList<String> signsList = new ArrayList<>();
        String num[] = nums.toString().split("#");
        for (String s : num) {
            numbersList.add(Integer.parseInt(s));
        }
        System.out.println(numbersList);


        String sig[] = signs.toString().split("#");
        for (int i = 0; i < sig.length; i++) {
            String[] item = sig[i].split(" ");
            signsList.add(item[item.length-1]);

        }
        System.out.println(signsList);
        result += numbersList.get(0);


        for (int i = 0; i < signsList.size(); i++) {
            if(signsList.get(i).equals("plus")){
                result += numbersList.get(i);

            }
            else if(signsList.get(i).equals("minus")){
                result -= numbersList.get(i);
            }
            else if(signsList.get(i).equals("star")){
                result *= numbersList.get(i);
            }
            else if(signsList.get(i).equals("division")){
                result /= numbersList.get(i);
            }
        }
        System.out.println(result);
        return print(numbersList, signsList, result);

    }

    public String print(ArrayList<Integer> nums, ArrayList<String> signs, double result){

        ArrayList<String> fSings = new ArrayList<>();


        for (String sign : signs) {
            if (sign.equals("plus")) fSings.add(" + ");
            if (sign.equals("minus")) fSings.add(" - ");
            if (sign.equals("star")) fSings.add(" * ");
            if (sign.equals("division")) fSings.add(" / ");
        }

        String output = "";
        for (int i = 0; i < fSings.size(); i++) {
            output+=nums.get(i)+fSings.get(i);

        }

        output+=nums.get(nums.size()-1)+ " "+"= "+result;
        return output;
    }





    public void splitSignList(String data) {
        if (data.equals("zero") ||data.equals("one") || data.equals("two") || data.equals("three") || data.equals("four") || data.equals("five") || data.equals("six") || data.equals("seven") || data.equals("eight") || data.equals("nine")) {
            if (!signs.substring(signs.length() - 1).equals("#")) {
                signs.append("#");
            }
        }
    }

    private void senMSG(SendMessage sendMessage) {
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public String getBotUsername() {
        return "Cal_Cul_bot";
    }

    @Override
    public String getBotToken() {
        return "5878135478:AAFGGo2qp7n5XiSQmCKSuY6Zdf5UbAZrSbQ";
    }
}