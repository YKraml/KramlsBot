package util;


import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import database.sql.ConnectionPool;
import domain.DungeonLoader;
import domain.GroupLoader;
import domain.PlayerLoader;
import domain.TeamLoader;
import domain.WaifuLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Singleton;
import loader.DungeonLoaderSql;
import loader.GroupLoaderSql;
import loader.PlayerLoaderCached;
import loader.PlayerLoaderOffline;
import loader.TeamLoaderSql;
import loader.WaifuLoaderCached;
import logic.AnimeInfoReactionListenerBuilder;
import logic.MusicPlayerManager;
import logic.messages.MessageSender;
import logic.routines.RoutineRunner;
import logic.waifu.GuessingGameManager;
import logic.waifu.GuessingGameManagerImpl;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import ui.audio.MusicPlayerManagerImpl;
import ui.commands.ACommand;
import ui.commands.dungeon.CreateDungeon;
import ui.commands.dungeon.DeleteDungeons;
import ui.commands.gambling.DoubleOrNothing;
import ui.commands.gambling.DoubleOrNothingWithParameter;
import ui.commands.group.GroupAddWaifu;
import ui.commands.group.GroupCreate;
import ui.commands.group.GroupDelete;
import ui.commands.group.GroupRemoveWaifu;
import ui.commands.group.GroupShowList;
import ui.commands.guess.Guess;
import ui.commands.guess.Reveal;
import ui.commands.guess.StartGuessingGame;
import ui.commands.music.Play;
import ui.commands.music.ShowQueue;
import ui.commands.music.Songs;
import ui.commands.music.Stop;
import ui.commands.teams.Fight;
import ui.commands.teams.TeamAdd;
import ui.commands.teams.TeamCreate;
import ui.commands.teams.TeamExpand;
import ui.commands.teams.TeamRemove;
import ui.commands.teams.TeamRename;
import ui.commands.teams.TeamsList;
import ui.commands.utility.Disconnect;
import ui.commands.utility.IpAddress;
import ui.commands.utility.Stats;
import ui.commands.waifu.Claim;
import ui.commands.waifu.Daily;
import ui.commands.waifu.Delete;
import ui.commands.waifu.DeleteFromAnime;
import ui.commands.waifu.DeleteFromTo;
import ui.commands.waifu.Expand;
import ui.commands.waifu.GiveMoney;
import ui.commands.waifu.GiveWaifu;
import ui.commands.waifu.Merge;
import ui.commands.waifu.ShowWaifuList;
import ui.commands.waifu.Spawn;
import ui.messages.MessageSenderImpl;
import ui.reaction.AnimeInfoReactionListenerBuilderImpl;

public class GuiceModule extends AbstractModule {

  private final String userName;
  private final String password;
  private final String ip;
  private final String token;

  public GuiceModule(String userName, String password, String ip, String token) {
    this.userName = userName;
    this.password = password;
    this.ip = ip;
    this.token = token;
  }

  @Override
  protected void configure() {
    bind(PlayerLoader.class).to(PlayerLoaderCached.class);
    //bind(PlayerLoader.class).to(PlayerLoaderOffline.class);
    bind(WaifuLoader.class).to(WaifuLoaderCached.class);
    bind(DungeonLoader.class).to(DungeonLoaderSql.class);
    bind(TeamLoader.class).to(TeamLoaderSql.class);
    bind(MessageSender.class).to(MessageSenderImpl.class);
    bind(GuessingGameManager.class).to(GuessingGameManagerImpl.class);
    bind(GroupLoader.class).to(GroupLoaderSql.class);
    bind(MusicPlayerManager.class).to(MusicPlayerManagerImpl.class);
    bind(AnimeInfoReactionListenerBuilder.class).to(AnimeInfoReactionListenerBuilderImpl.class);

  }

  @Provides
  @Singleton
  DiscordApi provideDiscordApi() {
    DiscordApi discordApi = new DiscordApiBuilder().setToken(token).login().join();
    discordApi.updateActivity("Jetzt mit Slash-commands!");
    return discordApi;
  }

  @Provides
  @Singleton
  ExecutorService provideExecutorService() {
    return Executors.newCachedThreadPool();
  }

  @Provides
  @Singleton
  ScheduledExecutorService provideScheduledExecutorService() {
    return Executors.newScheduledThreadPool(4);
  }

  @Provides
  @Singleton
  ConnectionPool provideConnectionPool(Terminal terminal) {
    String url = "jdbc:mysql://%s/KRAMLSBOT?serverTimezone=Europe/Berlin&useSSL=false&allowPublicKeyRetrieval=true".formatted(
        ip);
    return new ConnectionPool(url, userName, password, terminal);
  }

  @Provides
  @Singleton
  List<ACommand> provideACommands(RoutineRunner routineRunner, Disconnect disconnect,
      DoubleOrNothing doubleOrNothing, DoubleOrNothingWithParameter doubleOrNothingWithParameter,
      StartGuessingGame startGuessingGame, Guess guess, Reveal reveal, Stats stats, Daily daily,
      Spawn spawn, ShowWaifuList showWaifuList, Claim claim, GiveWaifu giveWaifu, Merge merge,
      Expand expand, DeleteFromAnime deleteFromAnime, DeleteFromTo deleteFromTo,
      GiveMoney giveMoney, Delete delete, GroupCreate groupCreate, GroupAddWaifu groupAddWaifu,
      GroupShowList groupShowList, GroupRemoveWaifu groupRemoveWaifu, GroupDelete groupDelete,
      TeamAdd teamAdd, TeamRemove teamRemove, TeamCreate teamCreate, TeamExpand teamExpand,
      TeamsList teamsList, TeamRename teamRename, CreateDungeon createDungeon,
      DeleteDungeons deleteDungeons, Play play, Stop stop, ShowQueue showQueue, Songs songs,
      Fight fight, IpAddress ip) {

    List<ACommand> commands = new ArrayList<>(
        List.of(disconnect, doubleOrNothing, doubleOrNothingWithParameter, startGuessingGame, guess,
            reveal, stats, daily, spawn, showWaifuList, claim, giveWaifu, merge, expand,
            deleteFromAnime, deleteFromTo, giveMoney, delete, groupCreate, groupAddWaifu,
            groupShowList, groupRemoveWaifu, groupDelete, teamAdd, teamRemove, teamCreate,
            teamExpand, teamsList, teamRename, createDungeon, deleteDungeons, play, stop, showQueue,
            songs, fight, ip));

    commands.forEach(abstractCommand -> abstractCommand.setRoutineRunner(routineRunner));

    return commands;
  }
}
