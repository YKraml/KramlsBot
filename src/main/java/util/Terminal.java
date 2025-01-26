package util;

import domain.exceptions.MyOwnException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.inject.Inject;
import logic.discord.ChannelFinder;


public final class Terminal {


  private final ChannelFinder discordApi;

  @Inject
  public Terminal(ChannelFinder channelFinder) {
    this.discordApi = channelFinder;
  }

  public void printError(final String message) {
    printLine("Exception happened:\n" + message);
  }


  public void printLine(final Object object) {
    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
    String text = date + "\n" + object + "\n";
    System.out.println(text);
    String consoleId = "858051641176752218";
    discordApi.getTextChannelById(consoleId).ifPresent(channel -> channel.sendMessage(text));
  }

  public void printError(MyOwnException e) {
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

    printError(exceptionMessage.toString());
  }

  private String formatNormalException(int counter, Exception exception) {
    return counter + " | " + exception.getClass().getSimpleName() + " | " + exception.getMessage();
  }

  private String formatMyException(MyOwnException currentException, int counter) {
    return counter + " | " + currentException.getExceptionMessage().getClassName() + " | "
        + currentException.getExceptionMessage().getContent();
  }

}
