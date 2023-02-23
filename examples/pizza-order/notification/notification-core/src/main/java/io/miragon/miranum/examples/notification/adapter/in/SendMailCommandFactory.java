package io.miragon.miranum.examples.notification.adapter.in;

import io.miragon.miranum.integrations.mail.application.port.in.SendMailCommand;

public class SendMailCommandFactory {

    public static SendMailCommand create(NotifyGuestCommand notifyGuestCommand) {
        final String recipient = notifyGuestCommand.getEmail();
        final String subject = "Your order confirmation";
        final String text = generateText(notifyGuestCommand);
        return new SendMailCommand(
                recipient,
                subject,
                text
        );
    }

    private static String generateText(NotifyGuestCommand notifyGuestCommand) {
        var food = notifyGuestCommand.getFood()
                .stream().map(item -> String.format("\t- %s\n", item))
                .reduce("food: \n", String::concat);
        var drinks = notifyGuestCommand.getDrinks()
                .stream().map(item -> String.format("\t- %s\n", item))
                .reduce("drinks: \n", String::concat);
        return String.format(
                "Hi %s, \n" +
                        "Thank you for your order! \n" +
                        "We'll work on it asap. \n" +
                        "Your order: \n %s %s \n Bon appetit!",
                notifyGuestCommand.getName(),
                food,
                drinks
        );
    }
}