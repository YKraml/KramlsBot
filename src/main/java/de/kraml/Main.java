package de.kraml;

import actions.listeners.commands.ACommand;
import actions.listeners.commands.SlashCommandListener;
import actions.listeners.join.JoinListener;
import actions.timer.DungeonTickTask;
import actions.timer.GivePointsTask;
import actions.timer.SpawnWaifuTask;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.javacord.api.DiscordApi;
import org.javacord.api.interaction.SlashCommand;
import org.javacord.api.interaction.SlashCommandBuilder;
import org.javacord.api.interaction.SlashCommandOption;


public class Main {

  public static final Color COLOR = Color.ORANGE;
  private static DiscordApi discordApi;
  private static String ip;
  private static String userName;
  private static String password;
  private static String token;

  public static void main(String[] args) {

    token = args[0];
    ip = args[1];
    userName = args[2];
    password = args[3];

    //slashCommands();

    Injector injector = Guice.createInjector(new MyModule());

    discordApi = injector.getInstance(DiscordApi.class);

    SlashCommandListener slashCommandListener = injector.getInstance(SlashCommandListener.class);
    JoinListener joinListener = injector.getInstance(JoinListener.class);

    SpawnWaifuTask spawnWaifuTask = injector.getInstance(SpawnWaifuTask.class);
    GivePointsTask givePointsTask = injector.getInstance(GivePointsTask.class);
    DungeonTickTask dungeonTickTask = injector.getInstance(DungeonTickTask.class);

    discordApi.addServerVoiceChannelMemberJoinListener(joinListener);
    discordApi.addSlashCommandCreateListener(slashCommandListener);

    ScheduledExecutorService scheduler = injector.getInstance(ScheduledExecutorService.class);
    scheduler.schedule(spawnWaifuTask, 1, TimeUnit.MINUTES);
    scheduler.scheduleAtFixedRate(givePointsTask, 1, 5, TimeUnit.MINUTES);
    scheduler.scheduleAtFixedRate(dungeonTickTask, 1, 5, TimeUnit.MINUTES);
  }

  private static void initSlashCommands(List<ACommand> commands) {

    List<SlashCommand> slashCommands = discordApi.getGlobalSlashCommands().join();
    for (SlashCommand slashCommand : slashCommands) {
      slashCommand.deleteGlobal().whenComplete(
          (unused, throwable) -> System.out.println("Deleted " + slashCommand.getName()));
    }

    List<SlashCommandBuilder> builders = new ArrayList<>();
    for (ACommand abstractCommand : commands) {
      if (slashCommands.stream()
          .noneMatch(slashCommand -> abstractCommand.getName().equals(slashCommand.getName()))) {
        String name = abstractCommand.getName();
        String description = abstractCommand.getDescription();
        List<SlashCommandOption> slashCommandOptions = abstractCommand.getSlashCommandOptions();
        builders.add(SlashCommand.with(name, description, slashCommandOptions));
        System.out.println("Added to List: " + name);
      }
    }
    builders.forEach(slashCommandBuilder -> slashCommandBuilder.createGlobal(discordApi)
        .whenComplete((slashCommand, throwable) -> {
          if (throwable != null) {
            System.out.println("Could not built. " + throwable.getMessage());
          }
          System.out.println("Built: " + slashCommand.getName());
        }));
  }

  public static DiscordApi getDiscordApi() {
    return discordApi;
  }

  public static String getIp() {
    return ip;
  }

  public static String getUserName() {
    return userName;
  }

  public static String getPassword() {
    return password;
  }

  public static String getToken() {
    return token;
  }
}
