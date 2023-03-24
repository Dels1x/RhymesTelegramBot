package ua.delsix.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Controller;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Controller
@Log4j
public class UpdateController {
    private TelegramBot telegramBot;

    protected void registerBot(TelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }

    public void processUpdate(Update update) {
        try {
            var message = update.getMessage();
            if (message != null) {
                if (message.getText().matches("^[a-zA-Z]+$")) {
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.setChatId(message.getChatId());
                    sendMessage.setText(
                            String.format("Rhymes to a word \"%s\":\n\n", message.getText()));
                    //TODO add list of rhymes, once I implement Datamuse API
                    telegramBot.sendMessage(sendMessage);
                    log.debug(String.format("UpdateController - processUpdate: message \"%s\"" +
                            " sent to TelegramBot - sendMessage()", sendMessage.getText()));
                }
            }

        } catch (Exception e) {
            log.error("Error in UpdateController - processUpdate()", e);
        }
    }
}
