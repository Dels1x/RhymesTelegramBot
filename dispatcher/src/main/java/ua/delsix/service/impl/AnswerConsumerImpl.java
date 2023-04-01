package ua.delsix.service.impl;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ua.delsix.controller.UpdateController;
import ua.delsix.service.AnswerConsumer;

import static ua.delsix.RabbitQueue.ANSWER_UPDATE;

@Service
public class AnswerConsumerImpl implements AnswerConsumer {
    private final UpdateController updateController;

    public AnswerConsumerImpl(UpdateController updateController) {
        this.updateController = updateController;
    }

    @Override
    @RabbitListener(queues = ANSWER_UPDATE)
    public void consume(SendMessage sendMessage) {
        updateController.setView(sendMessage);
    }
}
