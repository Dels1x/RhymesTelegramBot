package ua.delsix.controller;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Log4j
public class TelegramBot extends TelegramLongPollingBot {
    @Value("${bot.name}")
    private String botName;

    @Value("${bot.token}")
    private String botToken;


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
        var message = update.getMessage();
        String text = message.getText();
        Long chatId = message.getChatId();

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(text);
        sendMessage.setChatId(chatId);

        sendMessage(sendMessage);
    }

    public void sendMessage(SendMessage message) {
        if (message != null) {
            try {
                execute(message);
                log.debug(message.getText());
            } catch (TelegramApiException e) {
                log.error(e);
            }

            // test
        }
    }
}
