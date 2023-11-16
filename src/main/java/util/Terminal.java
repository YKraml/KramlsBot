package util;

import domain.exceptions.MyOwnException;

import java.text.SimpleDateFormat;
import java.util.Date;


public final class Terminal {


    private Terminal() {
        throw new IllegalStateException("Utility-class constructor.");
    }


    public static void printError(final String message) {
        Terminal.printLine("Exception happened:\n" + message);
    }


    public static void printLine(final Object object) {
        String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String text = date + "\n" + object + "\n";
        System.out.println(text);
        String consoleId = "858051641176752218";
        Main.getDiscordApi().getTextChannelById(consoleId)
                .ifPresent(channel -> channel.sendMessage(text));
    }

    public static void printError(MyOwnException e) {

        e.printStackTrace();
        e.getInnerException().ifPresent(Throwable::printStackTrace);
        StringBuilder exceptionMessage;

        MyOwnException currentException = e;
        int counter = 1;
        exceptionMessage = new StringBuilder();
        exceptionMessage.append(formatMyException(currentException, counter));

        while (currentException.getInnerException().isPresent() && currentException.getInnerException()
                .get() instanceof MyOwnException) {
            counter++;
            currentException = (MyOwnException) currentException.getInnerException().get();
            exceptionMessage.append('\n');
            exceptionMessage.append(formatMyException(currentException, counter));
        }

        if (currentException.getInnerException().isPresent()) {
            counter++;
            Exception exception = currentException.getInnerException().get();
            exceptionMessage.append("\n");
            exceptionMessage.append(formatNormalException(counter, exception));
        }

        Terminal.printError(exceptionMessage.toString());
    }

    private static String formatNormalException(int counter, Exception exception) {
        return counter + " | " + exception.getClass().getSimpleName() + " | " + exception.getMessage();
    }

    private static String formatMyException(MyOwnException currentException, int counter) {
        return counter + " | " + currentException.getExceptionMessage().getClassName() + " | "
                + currentException.getExceptionMessage().getContent();
    }

}
