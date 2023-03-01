package de.kraml;

import actions.commands.ACommand;
import actions.commands.CommandManager;
import actions.commands.MySlashCommandListener;
import actions.commands.dungeon.CreateDungeon;
import actions.commands.dungeon.DeleteDungeons;
import actions.commands.dungeon.TeamAdd;
import actions.commands.dungeon.TeamCreate;
import actions.commands.dungeon.TeamExpand;
import actions.commands.dungeon.TeamRemove;
import actions.commands.dungeon.TeamRename;
import actions.commands.dungeon.TeamsList;
import actions.commands.gambling.DoubleOrNothing;
import actions.commands.gambling.DoubleOrNothingWithParameter;
import actions.commands.group.GroupAddWaifu;
import actions.commands.group.GroupCreate;
import actions.commands.group.GroupDelete;
import actions.commands.group.GroupRemoveWaifu;
import actions.commands.group.GroupShowList;
import actions.commands.guess.Guess;
import actions.commands.guess.Reveal;
import actions.commands.guess.StartGuessingGame;
import actions.commands.music.Play;
import actions.commands.music.ShowQueue;
import actions.commands.music.Songs;
import actions.commands.music.Stop;
import actions.commands.utility.Disconnect;
import actions.commands.utility.Stats;
import actions.commands.waifu.Claim;
import actions.commands.waifu.Daily;
import actions.commands.waifu.Delete;
import actions.commands.waifu.DeleteFromAnime;
import actions.commands.waifu.DeleteFromTo;
import actions.commands.waifu.Expand;
import actions.commands.waifu.GiveMoney;
import actions.commands.waifu.GiveWaifu;
import actions.commands.waifu.Merge;
import actions.commands.waifu.ShowWaifuList;
import actions.commands.waifu.Spawn;
import actions.listeners.join.JoinListener;
import actions.listeners.reaction.AnimeInfoReactionListenerBuilder;
import actions.listeners.reaction.AnimeOpeningEndingReactionListenerBuilder;
import actions.timer.DungeonTickTask;
import actions.timer.GivePointsTask;
import actions.timer.RevealTimerBuilder;
import actions.timer.SpawnWaifuTask;
import discord.ChannelFinder;
import discord.RoleChecker;
import discord.RoleFinder;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import messages.MessageSenderImpl;
import messages.messages.AnimeEndingsBuilder;
import messages.messages.AnimeOpeningsBuilder;
import messages.messages.GuessGameEndBuilder;
import music.GuessingGameManager;
import music.audio.MusicPlayerManager;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import routines.RoutineAddToQueueBuilder;
import routines.RoutineClaimBuilder;
import routines.RoutineDoubleOrNothingBuilder;
import routines.RoutineDoubleOrNothingWithParameterBuilder;
import routines.RoutineDungeonTickBuilder;
import routines.RoutineGiveWaifuBuilder;
import routines.RoutineGuessBuilder;
import routines.RoutineRevealBuilder;
import routines.RoutineRunner;
import routines.RoutineShowGroupListBuilder;
import routines.RoutineShowSongsBuilder;
import routines.RoutineShowWaifuListBuilder;
import routines.RoutineSpawnWaifuBuilder;
import routines.RoutineSpawnWaifuCommandBuilder;
import routines.RoutineStartGuessingGameBuilder;
import waifu.JikanFetcher;
import waifu.WaifuBuilder;
import waifu.WaifuSpawnManager;
import waifu.loader.DungeonLoader;
import waifu.loader.DungeonLoaderSql;
import waifu.loader.GroupLoader;
import waifu.loader.PlayerLoader;
import waifu.loader.PlayerLoaderCached;
import waifu.loader.TeamLoader;
import waifu.loader.TeamLoaderSql;
import waifu.loader.WaifuLoader;
import waifu.loader.WaifuLoaderSql;
import waifu.model.dungeon.DungeonTicker;
import youtube.YoutubeFetcher;


public class Main {

  public static final Color COLOR = Color.ORANGE;
  private static final int NUMBER_OF_THREADS = 16;
  private static DiscordApi discordApi;
  private static String ip;
  private static String userName;
  private static String password;

  /**
   * Main method.
   *
   * @param args Arguments. Should be empty.
   */
  public static void main(String[] args) {

    String token = args[0];
    ip = args[1];
    userName = args[2];
    password = args[3];

    discordApi = new DiscordApiBuilder().setToken(token).login().join();
    discordApi.updateActivity("Jetzt mit Slash-commands!");

    JikanFetcher jikanFetcher = new JikanFetcher();
    YoutubeFetcher youtubeFetcher = new YoutubeFetcher();
    MessageSenderImpl messageSender = new MessageSenderImpl();
    WaifuLoader waifuLoader = new WaifuLoaderSql();
    DungeonLoader dungeonLoader = new DungeonLoaderSql();
    RoleFinder roleFinder = new RoleFinder();
    MusicPlayerManager musicPlayerManager = new MusicPlayerManager();
    ChannelFinder channelFinder = new ChannelFinder(discordApi);
    GuessingGameManager guessingGameManager = new GuessingGameManager(jikanFetcher);
    WaifuSpawnManager waifuSpawnManager = new WaifuSpawnManager(jikanFetcher);
    WaifuBuilder waifuBuilder = new WaifuBuilder(jikanFetcher);
    TeamLoader teamLoader = new TeamLoaderSql(dungeonLoader);
    GroupLoader groupLoader = new GroupLoader(waifuLoader);
    PlayerLoader playerLoader = new PlayerLoaderCached(teamLoader, waifuLoader, groupLoader);
    RoleChecker roleChecker = new RoleChecker(playerLoader, roleFinder);
    DungeonTicker dungeonTicker = new DungeonTicker(playerLoader, channelFinder, messageSender, teamLoader, waifuBuilder);

    ExecutorService executorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();

    AnimeOpeningsBuilder animeOpeningsBuilder = new AnimeOpeningsBuilder(playerLoader, jikanFetcher, channelFinder, youtubeFetcher, musicPlayerManager, messageSender);
    AnimeEndingsBuilder animeEndingsBuilder = new AnimeEndingsBuilder(playerLoader, jikanFetcher, channelFinder, youtubeFetcher, musicPlayerManager, messageSender);
    AnimeOpeningEndingReactionListenerBuilder animeOpeningEndingReactionListenerBuilder = new AnimeOpeningEndingReactionListenerBuilder(jikanFetcher, messageSender, animeOpeningsBuilder, animeEndingsBuilder);
    AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder = new AnimeInfoReactionListenerBuilder(jikanFetcher, messageSender, animeOpeningEndingReactionListenerBuilder);
    GuessGameEndBuilder guessGameEndBuilder = new GuessGameEndBuilder(animeInfoReactionListenerBuilder);

    RevealTimerBuilder revealTimerBuilder = new RevealTimerBuilder(guessingGameManager, messageSender, guessGameEndBuilder, scheduledExecutorService);

    RoutineRevealBuilder routineRevealBuilder = new RoutineRevealBuilder(guessingGameManager, messageSender, guessGameEndBuilder);
    RoutineAddToQueueBuilder routineAddToQueueBuilder = new RoutineAddToQueueBuilder(musicPlayerManager, youtubeFetcher, messageSender, channelFinder, playerLoader);
    RoutineStartGuessingGameBuilder routineStartGuessingGameBuilder = new RoutineStartGuessingGameBuilder(guessingGameManager, jikanFetcher, youtubeFetcher, channelFinder, musicPlayerManager, messageSender, playerLoader, routineRevealBuilder);
    RoutineGuessBuilder routineGuessBuilder = new RoutineGuessBuilder(guessingGameManager, playerLoader, messageSender, guessGameEndBuilder);
    RoutineSpawnWaifuCommandBuilder routineSpawnWaifuCommandBuilder = new RoutineSpawnWaifuCommandBuilder(waifuBuilder, playerLoader, waifuLoader, jikanFetcher, messageSender);
    RoutineClaimBuilder routineClaimBuilder = new RoutineClaimBuilder(waifuSpawnManager, playerLoader, waifuLoader, jikanFetcher, messageSender);
    RoutineShowWaifuListBuilder routineShowWaifuListBuilder = new RoutineShowWaifuListBuilder(messageSender, playerLoader, waifuLoader, jikanFetcher);
    RoutineShowGroupListBuilder routineShowGroupListBuilder = new RoutineShowGroupListBuilder(messageSender, playerLoader, waifuLoader, jikanFetcher);
    RoutineDungeonTickBuilder routineDungeonTickBuilder = new RoutineDungeonTickBuilder(dungeonTicker, channelFinder, teamLoader, playerLoader, dungeonLoader);
    RoutineSpawnWaifuBuilder routineSpawnWaifuBuilder = new RoutineSpawnWaifuBuilder(scheduledExecutorService, waifuSpawnManager, waifuBuilder, discordApi);
    RoutineShowSongsBuilder routineShowSongsBuilder = new RoutineShowSongsBuilder(messageSender, playerLoader, musicPlayerManager);
    RoutineDoubleOrNothingBuilder routineDoubleOrNothingBuilder = new RoutineDoubleOrNothingBuilder(playerLoader, messageSender);
    RoutineDoubleOrNothingWithParameterBuilder routineDoubleOrNothingWithParameterBuilder = new RoutineDoubleOrNothingWithParameterBuilder(playerLoader);
    RoutineGiveWaifuBuilder routineGiveWaifuBuilder = new RoutineGiveWaifuBuilder(playerLoader, messageSender);


    List<ACommand> commands = new ArrayList<>();
    commands.add(new Disconnect());
    commands.add(new DoubleOrNothing(routineDoubleOrNothingBuilder));
    commands.add(new DoubleOrNothingWithParameter(routineDoubleOrNothingWithParameterBuilder));
    commands.add(new StartGuessingGame(routineStartGuessingGameBuilder, revealTimerBuilder));
    commands.add(new Guess(routineGuessBuilder));
    commands.add(new Reveal(routineRevealBuilder));
    commands.add(new Stats(messageSender));
    commands.add(new Daily(playerLoader));
    commands.add(new Spawn(routineSpawnWaifuCommandBuilder));
    commands.add(new ShowWaifuList(routineShowWaifuListBuilder));
    commands.add(new Claim(routineClaimBuilder));
    commands.add(new GiveWaifu(routineGiveWaifuBuilder));
    commands.add(new Merge(waifuLoader, playerLoader));
    commands.add(new Expand(playerLoader, messageSender));
    commands.add(new DeleteFromAnime(waifuLoader, playerLoader, messageSender));
    commands.add(new DeleteFromTo(waifuLoader, playerLoader, messageSender));
    commands.add(new GiveMoney(playerLoader, messageSender));
    commands.add(new Delete(waifuLoader, messageSender));
    commands.add(new GroupCreate(playerLoader, messageSender));
    commands.add(new GroupAddWaifu(playerLoader));
    commands.add(new GroupShowList(routineShowGroupListBuilder));
    commands.add(new GroupRemoveWaifu(groupLoader, messageSender));
    commands.add(new GroupDelete(groupLoader));
    commands.add(new TeamAdd(playerLoader));
    commands.add(new TeamRemove(playerLoader, teamLoader));
    commands.add(new TeamCreate(playerLoader));
    commands.add(new TeamExpand(playerLoader));
    commands.add(new TeamsList(dungeonLoader, playerLoader, messageSender));
    commands.add(new TeamRename(playerLoader));
    commands.add(new CreateDungeon(dungeonLoader));
    commands.add(new DeleteDungeons(dungeonLoader, messageSender));
    commands.add(new Play(routineAddToQueueBuilder));
    commands.add(new Stop(musicPlayerManager, guessingGameManager));
    commands.add(new ShowQueue(musicPlayerManager, playerLoader, messageSender));
    commands.add(new Songs(routineShowSongsBuilder));

    slashCommands();

    RoutineRunner routineRunner = new RoutineRunner();
    commands.forEach(abstractCommand -> abstractCommand.setRoutineRunner(routineRunner));
    CommandManager commandManager = new CommandManager(playerLoader, commands);

    Runnable spawnWaifuTask = new SpawnWaifuTask(scheduledExecutorService, routineRunner, routineSpawnWaifuBuilder);
    Runnable pointsTask = new GivePointsTask(discordApi, routineRunner, playerLoader);
    Runnable dungeonTickTask = new DungeonTickTask(routineRunner, routineDungeonTickBuilder);






    discordApi.addSlashCommandCreateListener(new MySlashCommandListener(commandManager, executorService));
    discordApi.addServerVoiceChannelMemberJoinListener(new JoinListener(playerLoader, roleChecker));

    scheduledExecutorService.scheduleAtFixedRate(pointsTask, 1, 5, TimeUnit.MINUTES);
    scheduledExecutorService.schedule(spawnWaifuTask, 1, TimeUnit.MINUTES);
    scheduledExecutorService.scheduleAtFixedRate(dungeonTickTask, 1, 5, TimeUnit.MINUTES);
  }

  private static void slashCommands() {
         /*
    List<SlashCommand> slashCommands = discordApi.getGlobalSlashCommands().join();
    for (SlashCommand slashCommand : slashCommands) {
      slashCommand.deleteGlobal().whenComplete(
          (unused, throwable) -> System.out.println("Deleted " + slashCommand.getName()));
    }

    List<SlashCommandBuilder> builders = new ArrayList<>();
    for (AbstractCommand abstractCommand : commands) {
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

     */
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
}
