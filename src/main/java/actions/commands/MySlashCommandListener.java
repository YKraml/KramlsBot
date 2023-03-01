package actions.commands;

import de.kraml.Terminal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.function.Supplier;
import org.javacord.api.event.interaction.SlashCommandCreateEvent;
import org.javacord.api.listener.interaction.SlashCommandCreateListener;

public class MySlashCommandListener implements SlashCommandCreateListener {

  private final CommandManager commandManager;

  private final ExecutorService executorService;

  public MySlashCommandListener(CommandManager commandManager, ExecutorService executorService) {
    this.commandManager = commandManager;
    this.executorService = executorService;
  }

  @Override
  public void onSlashCommandCreate(SlashCommandCreateEvent event) {

    Supplier<Answer> supplier = () -> commandManager.executeCommand(event);
    CompletableFuture<Answer> future = CompletableFuture.supplyAsync(supplier, executorService);
    future.whenComplete((answer, throwable) -> Terminal.printLine(answer.getMessage()));

  }
}
