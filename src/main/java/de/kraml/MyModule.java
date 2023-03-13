package de.kraml;


import actions.listeners.commands.ACommand;
import actions.listeners.commands.dungeon.CreateDungeon;
import actions.listeners.commands.dungeon.DeleteDungeons;
import actions.listeners.commands.teams.Fight;
import actions.listeners.commands.teams.TeamAdd;
import actions.listeners.commands.teams.TeamCreate;
import actions.listeners.commands.teams.TeamExpand;
import actions.listeners.commands.teams.TeamRemove;
import actions.listeners.commands.teams.TeamRename;
import actions.listeners.commands.teams.TeamsList;
import actions.listeners.commands.gambling.DoubleOrNothing;
import actions.listeners.commands.gambling.DoubleOrNothingWithParameter;
import actions.listeners.commands.group.GroupAddWaifu;
import actions.listeners.commands.group.GroupCreate;
import actions.listeners.commands.group.GroupDelete;
import actions.listeners.commands.group.GroupRemoveWaifu;
import actions.listeners.commands.group.GroupShowList;
import actions.listeners.commands.guess.Guess;
import actions.listeners.commands.guess.Reveal;
import actions.listeners.commands.guess.StartGuessingGame;
import actions.listeners.commands.music.Play;
import actions.listeners.commands.music.ShowQueue;
import actions.listeners.commands.music.Songs;
import actions.listeners.commands.music.Stop;
import actions.listeners.commands.utility.Disconnect;
import actions.listeners.commands.utility.Stats;
import actions.listeners.commands.waifu.Claim;
import actions.listeners.commands.waifu.Daily;
import actions.listeners.commands.waifu.Delete;
import actions.listeners.commands.waifu.DeleteFromAnime;
import actions.listeners.commands.waifu.DeleteFromTo;
import actions.listeners.commands.waifu.Expand;
import actions.listeners.commands.waifu.GiveMoney;
import actions.listeners.commands.waifu.GiveWaifu;
import actions.listeners.commands.waifu.Merge;
import actions.listeners.commands.waifu.ShowWaifuList;
import actions.listeners.commands.waifu.Spawn;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Singleton;
import messages.MessageSender;
import messages.MessageSenderImpl;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import routines.RoutineRunner;
import waifu.loader.DungeonLoader;
import waifu.loader.DungeonLoaderSql;
import waifu.loader.PlayerLoader;
import waifu.loader.PlayerLoaderCached;
import waifu.loader.TeamLoader;
import waifu.loader.TeamLoaderSql;
import waifu.loader.WaifuLoader;
import waifu.loader.WaifuLoaderSql;
import waifu.sql.ConnectionPool;

public class MyModule extends AbstractModule {

  @Override
  protected void configure() {
    bind(PlayerLoader.class).to(PlayerLoaderCached.class);
    bind(WaifuLoader.class).to(WaifuLoaderSql.class);
    bind(DungeonLoader.class).to(DungeonLoaderSql.class);
    bind(TeamLoader.class).to(TeamLoaderSql.class);
    bind(MessageSender.class).to(MessageSenderImpl.class);
  }

  @Provides
  @Singleton
  DiscordApi provideDiscordApi() {
    DiscordApi discordApi = new DiscordApiBuilder().setToken(Main.getToken()).login().join();
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
  ConnectionPool provideConnectionPool() {
    String url = "jdbc:mysql://%s/KRAMLSBOT?serverTimezone=Europe/Berlin&useSSL=false&allowPublicKeyRetrieval=true".formatted(
        Main.getIp());
    String userName = Main.getUserName();
    String password = Main.getPassword();
    return new ConnectionPool(url, userName, password);
  }

  @Provides
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
      Fight fight) {

    List<ACommand> commands = new ArrayList<>(
        List.of(disconnect, doubleOrNothing, doubleOrNothingWithParameter, startGuessingGame, guess,
            reveal, stats, daily, spawn, showWaifuList, claim, giveWaifu, merge, expand,
            deleteFromAnime, deleteFromTo, giveMoney, delete, groupCreate, groupAddWaifu,
            groupShowList, groupRemoveWaifu, groupDelete, teamAdd, teamRemove, teamCreate,
            teamExpand, teamsList, teamRename, createDungeon, deleteDungeons, play, stop, showQueue,
            songs, fight));

    commands.forEach(abstractCommand -> abstractCommand.setRoutineRunner(routineRunner));

    return commands;
  }
}
