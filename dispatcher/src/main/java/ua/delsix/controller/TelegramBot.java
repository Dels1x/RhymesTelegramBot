package ua.delsix.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import javax.annotation.PostConstruct;

@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {
    private final UpdateController updateController;
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;

    public TelegramBot(UpdateController updateController) {
        this.updateController = updateController;
    }

    // Registers TelegramBot in UpdateController
    @PostConstruct
    private void registerBotInUpdateController() {
        updateController.registerBot(this);
    }


    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println(66);
        log.debug("Update received. Message: "+update);
        updateController.processUpdate(update);
    }

    public void sendMessage(SendMessage message) {
        if (message == null) {
            log.warn("Attempted to send null message");
            return;
        }

        try {
            execute(message);
            log.debug("Message sent: "+ message.getText());
        } catch (TelegramApiException e) {
            log.error("Error sending message: "+ e.getMessage());
        }
    }
}
