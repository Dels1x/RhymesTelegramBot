package ua.delsix.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.delsix.service.impl.DatamuseServiceImpl;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@Log4j2
public class UpdateController {
    private TelegramBot telegramBot;
    private DatamuseServiceImpl datamuseService;

    public UpdateController(DatamuseServiceImpl datamuseService) {
        this.datamuseService = datamuseService;
    }

    protected void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        try {
            var message = update.getMessage();
            String text = message.getText();
            SendMessage sendMessage = new SendMessage();
            sendMessage.setChatId(message.getChatId());

            if (message.getText() != null) {
                if(text.equals("/start")) {
                    sendMessage.setText("Hello there! This bot will help you find rhymes to a word you want.\n" +
                            "Just type in the word, and the bot will find rhymes for you.\n\nNotice that it doesn't work " +
                            "with words that don't exist in an actual English dictionary.");

                    //Checking if message consists only of latin letters
                } else if (text.matches("^[a-zA-Z]+$")) {
                    // Get last word from a string, in case if user wrote multiple, to give a rhyme for the last word
                    String lastWord = text.split(" ")[text.split(" ").length - 1];

                    List<String> rhymes = datamuseService.getPerfectRhymes(lastWord);
                    rhymes.addAll(datamuseService.getNearRhymes(lastWord));
                    if(rhymes.size() > 0) {
                        sendMessage.setText(
                                String.format("Rhymes to a word \"%s\":\n\n%s",
                                        message.getText().toLowerCase(),
                                        rhymes.stream().collect(Collectors.joining(", "))));
                    } else {
                        sendMessage.setText("Unfortunately, couldn't manage to find any rhymes." +
                                " Make sure the word is spelled correctly and exists in English");
                    }
                } else {
                    sendMessage.setText("Your message must consist only of latin letters and nothing else.");
                }
            } else {
                sendMessage.setText("Send an actual message with text.");
            }
            telegramBot.sendMessage(sendMessage);
            log.debug(String.format("UpdateController - processUpdate: message \"%s\"" +
                    " sent to TelegramBot - sendMessage()", sendMessage.getText()));

        } catch (Exception e) {
            log.error("Error in UpdateController - processUpdate()", e);
        }
    }
}
