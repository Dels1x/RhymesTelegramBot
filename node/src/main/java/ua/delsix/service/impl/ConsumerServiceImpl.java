package ua.delsix.service.impl;

import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;
import ua.delsix.service.ConsumerService;
import ua.delsix.service.DatamuseService;
import ua.delsix.service.ProducerService;
import ua.delsix.utils.MessageUtils;

import java.io.IOException;
import java.util.List;

import static ua.delsix.RabbitQueue.MESSAGE_UPDATE;

@Service
@Log4j
public class ConsumerServiceImpl implements ConsumerService {
    private final ProducerService producerService;
    private final DatamuseService datamuseService;
    private final MessageUtils messageUtils;

    public ConsumerServiceImpl(ProducerService producerService, DatamuseService datamuseService, MessageUtils messageUtils) {
        this.producerService = producerService;
        this.datamuseService = datamuseService;
        this.messageUtils = messageUtils;
    }

    @Override
    @RabbitListener(queues = MESSAGE_UPDATE)
    public void consumeMessageUpdate(Update update) {
        log.debug("NODE: message received");
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
                } else if (text.matches("^[a-zA-Z ]+$")) {
                    answer = findRhymes(text);
                } else {
                    answer = "Your message must consist only of latin letters and nothing else.";
                }
            } else {
                answer = "Send an actual message with text.";
            }

            producerService.produceAnswer(messageUtils.generateSendMessage(update, answer));

        } catch (
                Exception e) {
            log.error("Error in UpdateController - processUpdate(): "+ e.getMessage());
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
