package ua.delsix.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.delsix.service.impl.DatamuseServiceImpl;
import ua.delsix.utils.MessageUtils;

import java.io.IOException;
import java.util.List;

@Controller
@Log4j2
public class UpdateController {
    private TelegramBot telegramBot;
    private final DatamuseServiceImpl datamuseService;
    private final MessageUtils messageUtils;

    public UpdateController(DatamuseServiceImpl datamuseService, MessageUtils messageUtils) {
        this.datamuseService = datamuseService;
        this.messageUtils = messageUtils;
    }

    protected void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        try {
            var message = update.getMessage();
            String text = message.getText();
            String answer;

            if (text != null) {
                if (text.equals("/start")) {
                    answer = "Hello there! This bot will help you find rhymes to a word you want.\n" +
                            "Just type in the word, and the bot will find rhymes for you.\n\nNotice that it doesn't work " +
                            "with words that don't exist in an actual English dictionary.";

                    //Checking if message consists only of latin letters
                } else if (text.matches("^[a-zA-Z]+$")) {
                    answer = findRhymes(text);
                } else {
                    answer = "Your message must consist only of latin letters and nothing else.";
                }
            } else {
                answer = "Send an actual message with text.";
            }
            SendMessage sendMessage = messageUtils.generateSendMessage(update, answer);
            telegramBot.sendMessage(sendMessage);
            log.debug(String.format("UpdateController - processUpdate: message \"%s\"" +
                    " sent to MessageUtils - generateSendMessage()", answer));

        } catch (
                Exception e) {
            log.error("Error in UpdateController - processUpdate()", e);
        }

    }

    private String findRhymes(String text) throws IOException {
        // Get last word from a string, in case if user wrote multiple, to give a rhyme for the last word
        String lastWord = text.split(" ")[text.split(" ").length - 1];

        List<String> rhymes = datamuseService.getPerfectRhymes(lastWord);
        rhymes.addAll(datamuseService.getNearRhymes(lastWord));
        if (rhymes.size() > 0) {
            return String.format("Rhymes to a word \"%s\":\n\n%s",
                    text.toLowerCase(),
                    String.join(", ", rhymes));
        } else {
            return "Unfortunately, couldn't manage to find any rhymes." +
                    " Make sure the word is spelled correctly and exists in English";
        }
    }
}
