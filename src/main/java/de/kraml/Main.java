package de.kraml;

import actions.listeners.commands.SlashCommandListener;
import actions.listeners.join.JoinListener;
import actions.timer.DungeonTickTask;
import actions.timer.GivePointsTask;
import actions.timer.SpawnWaifuTask;
import com.google.inject.Guice;
import com.google.inject.Injector;
import java.awt.Color;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.javacord.api.DiscordApi;


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

    Injector injector = Guice.createInjector(new GuiceModule());

    SlashCommandListener slashCommandListener = injector.getInstance(SlashCommandListener.class);
    JoinListener joinListener = injector.getInstance(JoinListener.class);

    SpawnWaifuTask spawnWaifuTask = injector.getInstance(SpawnWaifuTask.class);
    GivePointsTask givePointsTask = injector.getInstance(GivePointsTask.class);
    DungeonTickTask dungeonTickTask = injector.getInstance(DungeonTickTask.class);

    discordApi = injector.getInstance(DiscordApi.class);
    discordApi.addServerVoiceChannelMemberJoinListener(joinListener);
    discordApi.addSlashCommandCreateListener(slashCommandListener);

    ScheduledExecutorService scheduler = injector.getInstance(ScheduledExecutorService.class);
    scheduler.schedule(spawnWaifuTask, 5, TimeUnit.MINUTES);
    int timeInMinutes = givePointsTask.getTimeInMinutes();
    scheduler.scheduleAtFixedRate(givePointsTask, 5, timeInMinutes, TimeUnit.MINUTES);
    scheduler.scheduleAtFixedRate(dungeonTickTask, 5, 5, TimeUnit.MINUTES);
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
