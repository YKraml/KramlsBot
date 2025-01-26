package ui.messages;

import domain.GuessingGame;
import domain.PlayerLoader;
import domain.WaifuLoader;
import domain.exceptions.MyOwnException;
import domain.exceptions.messages.CouldNotGetContent;
import domain.exceptions.messages.CouldNotSendMessage;
import domain.exceptions.messages.CouldNotStartRoutine;
import domain.queue.QueueElement;
import domain.waifu.Group;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.waifu.dungeon.Dungeon;
import domain.waifu.dungeon.Inventory;
import domain.waifu.dungeon.Team;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import logic.AnimeInfoReactionListenerBuilder;
import logic.MusicPlayerManager;
import logic.messages.MessageSender;
import logic.messages.MyMessage;
import logic.routines.RoutineRevealBuilder;
import logic.routines.RoutineRunner;
import logic.waifu.JikanFetcher;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.server.Server;
import org.javacord.api.entity.user.User;
import ui.messages.messages.CouldNotLoadSongMessage;
import ui.messages.messages.DailyAlreadyUsed;
import ui.messages.messages.DailyUsed;
import ui.messages.messages.DungeonCreatedMessage;
import ui.messages.messages.DungeonMessage;
import ui.messages.messages.ExceptionHappenedMessage;
import ui.messages.messages.FightRequest;
import ui.messages.messages.GaveMoney;
import ui.messages.messages.GaveWaifu;
import ui.messages.messages.GroupCreated;
import ui.messages.messages.GroupList;
import ui.messages.messages.GroupOverview;
import ui.messages.messages.GuessGameEnd;
import ui.messages.messages.GuessGameStarted;
import ui.messages.messages.GuessedRight;
import ui.messages.messages.GuessedWrong;
import ui.messages.messages.LikedSongs;
import ui.messages.messages.LostMoney;
import ui.messages.messages.Merged;
import ui.messages.messages.NoMatchForSongMessage;
import ui.messages.messages.SongAdded;
import ui.messages.messages.TeamIsLow;
import ui.messages.messages.TeamKilled;
import ui.messages.messages.TeamRenamedMessage;
import ui.messages.messages.TimeIsUpMessage;
import ui.messages.messages.WaifuList;
import ui.messages.messages.WaifuNotFound;
import ui.messages.messages.WaifuSpawn;
import ui.messages.messages.WaifuSpawned;
import ui.messages.messages.WaifuStats;
import ui.messages.messages.WaifuToClaimWas;
import ui.messages.messages.WaifusAreDifferent;
import ui.messages.messages.WaifusDeleted;
import ui.messages.messages.WonMoney;
import util.Terminal;

public class MessageSenderImpl implements MessageSender {


  @Override
  public Message send(MyMessage myMessage, TextChannel textChannel) throws MyOwnException {

    EmbedBuilder content;
    try {
      content = myMessage.getContent();
    } catch (MyOwnException e) {
      throw new MyOwnException(new CouldNotGetContent(), e);
    }

    if (!textChannel.canYouWrite()) {
      throw new MyOwnException(new CouldNotSendMessage(myMessage.getClass().getSimpleName()), null);
    }

    CompletableFuture<Message> sentMessageFuture = textChannel.sendMessage(content);
    Message sentMessage = sentMessageFuture.join();

    try {
      myMessage.startRoutine(sentMessage);
    } catch (MyOwnException e) {
      throw new MyOwnException(new CouldNotStartRoutine(), e);
    }

    return sentMessage;
  }

  @Override
  public void sendSafe(MyMessage myMessage, TextChannel textChannel) {

    try {
      CompletableFuture<Message> future = textChannel.sendMessage(myMessage.getContent());
      myMessage.startRoutine(future.join());
    } catch (MyOwnException ignored) {
      //Ignore
    }


  }

  @Override
  public void sendMerged(TextChannel channel, Player player, Waifu waifu) throws MyOwnException {
    send(new Merged(player, waifu), channel);
  }

  @Override
  public void sendWaifusAreDifferent(TextChannel channel, Player player) throws MyOwnException {
    send(new WaifusAreDifferent(player), channel);
  }

  @Override
  public void sendGroupList(TextChannel channel, Player player, PlayerLoader playerLoader,
      WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender,
      Terminal terminal) throws MyOwnException {
    send(new GroupList(player, playerLoader, waifuLoader, jikanFetcher, messageSender, terminal),
        channel);
  }

  @Override
  public void sendGuessGameStarted(TextChannel textChannel, RoutineRunner routineRunner,
      RoutineRevealBuilder routineRevealBuilder) throws MyOwnException {
    send(new GuessGameStarted(routineRunner, routineRevealBuilder), textChannel);
  }

  @Override
  public void sendSafeNoMatchForSongMessage(TextChannel channel) {
    sendSafe(new NoMatchForSongMessage(), channel);
  }

  @Override
  public void sendSafeCouldNotLoadSongMessage(TextChannel channel) {
    sendSafe(new CouldNotLoadSongMessage(), channel);
  }

  @Override
  public void sendWaifuSpawned(TextChannel channel, Player player) throws MyOwnException {
    send(new WaifuSpawned(player), channel);
  }

  @Override
  public void sendWaifuStats(TextChannel channel, Waifu waifu, Player player,
      PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher,
      MessageSender messageSender, Terminal terminal) throws MyOwnException {
    send(new WaifuStats(waifu, player, playerLoader, waifuLoader, jikanFetcher, messageSender,
        terminal), channel);
  }

  @Override
  public void sendTeamKilled(TextChannel channel, Team team, int level, Inventory inventory)
      throws MyOwnException {
    send(new TeamKilled(team, level, inventory), channel);
  }

  @Override
  public void sendTeamIsLow(TextChannel textChannel, Team team) throws MyOwnException {
    send(new TeamIsLow(team), textChannel);
  }

  @Override
  public void sendDailyAlreadyUsed(TextChannel channel, Player player, String newDate)
      throws MyOwnException {
    send(new DailyAlreadyUsed(player, newDate), channel);
  }

  @Override
  public void sendDailyUsed(TextChannel channel, Player player) throws MyOwnException {
    send(new DailyUsed(player), channel);
  }

  @Override
  public Message sendFightRequest(TextChannel textChannel, User user, User userEnemy, long money,
      long stardust, long morphStones) throws MyOwnException {
    return send(new FightRequest(user, userEnemy, money, stardust, morphStones), textChannel);
  }

  @Override
  public void sendGaveWaifu(TextChannel channel, Player senderPlayer, Player receiverPlayer,
      Waifu waifu) throws MyOwnException {
    send(new GaveWaifu(senderPlayer, receiverPlayer, waifu), channel);
  }

  @Override
  public void sendWaifuNotFound(TextChannel channel, int waifuNumber) throws MyOwnException {
    send(new WaifuNotFound(waifuNumber), channel);
  }

  @Override
  public void sendTimeIsUpMessage(TextChannel channel) throws MyOwnException {
    send(new TimeIsUpMessage(), channel);
  }

  @Override
  public void sendWaifuToClaimWas(TextChannel textChannel, Waifu waifu) throws MyOwnException {
    send(new WaifuToClaimWas(waifu), textChannel);
  }

  @Override
  public void sendWaifuSpawn(TextChannel textChannel, Waifu newWaifu) throws MyOwnException {
    send(new WaifuSpawn(newWaifu), textChannel);
  }

  @Override
  public void sendSafeExceptionHappenedMessage(TextChannel textChannel, MyOwnException e) {
    sendSafe(new ExceptionHappenedMessage(e), textChannel);
  }

  @Override
  public void sendWaifusDeleted(TextChannel channel, Player player, int deletedWaifus, int stardust,
      int cookies) throws MyOwnException {
    send(new WaifusDeleted(player, deletedWaifus, stardust, cookies), channel);
  }

  @Override
  public void sendTeamRenamedMessage(TextChannel channel, Player player, String oldName,
      String newName) throws MyOwnException {
    send(new TeamRenamedMessage(player, oldName, newName), channel);
  }

  @Override
  public void sendGuessedRight(TextChannel channel, Player player) throws MyOwnException {
    send(new GuessedRight(player), channel);
  }

  @Override
  public void sendWonMoney(TextChannel channel, Player player, long wonMoney)
      throws MyOwnException {
    send(new WonMoney(player, wonMoney), channel);
  }

  @Override
  public void sendSafeDungeonMessage(TextChannel channel, Dungeon dungeon, List<Team> teams) {
    sendSafe(new DungeonMessage(dungeon, teams), channel);
  }

  @Override
  public void sendLostMoney(TextChannel channel, Player player, long bettedMoney)
      throws MyOwnException {
    send(new LostMoney(player, bettedMoney), channel);
  }

  @Override
  public void sendGaveMoney(TextChannel channel, Player giverPlayer, Player receiverPlayer,
      int money) throws MyOwnException {
    send(new GaveMoney(giverPlayer, receiverPlayer, money), channel);
  }

  @Override
  public void sendSongAdded(TextChannel channel, QueueElement queueElement,
      MusicPlayerManager musicPlayerManager, PlayerLoader playerLoader) throws MyOwnException {
    send(new SongAdded(queueElement, musicPlayerManager, playerLoader, this), channel);
  }

  @Override
  public void sendGuessedWrong(TextChannel channel, Player player) throws MyOwnException {
    send(new GuessedWrong(player), channel);
  }

  @Override
  public void sendGroupCreated(TextChannel channel, String groupName) throws MyOwnException {
    send(new GroupCreated(groupName), channel);
  }

  @Override
  public void sendLikedSongs(TextChannel channel, Player player, Server server, User user,
      PlayerLoader playerLoader, MessageSender messageSender, MusicPlayerManager musicPlayerManager)
      throws MyOwnException {
    send(new LikedSongs(player, server, user, playerLoader, messageSender, musicPlayerManager),
        channel);
  }

  @Override
  public void sendDungeonCreatedMessage(TextChannel channel, Dungeon dungeon)
      throws MyOwnException {
    send(new DungeonCreatedMessage(dungeon), channel);
  }

  @Override
  public void sendWaifuList(TextChannel channel, Player player, MessageSender messageSender,
      PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher,
      Terminal terminal) throws MyOwnException {
    send(new WaifuList(player, messageSender, playerLoader, waifuLoader, jikanFetcher, terminal),
        channel);
  }

  @Override
  public void sendGuessGameEnd(TextChannel channel, GuessingGame guessingGame,
      AnimeInfoReactionListenerBuilder animeInfoReactionListenerBuilder) throws MyOwnException {
    send(new GuessGameEnd(guessingGame, animeInfoReactionListenerBuilder), channel);
  }

  @Override
  public void sendGroupOverview(TextChannel channel, Group group, Player player,
      PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher,
      MessageSender messageSender, Terminal terminal) throws MyOwnException {
    send(new GroupOverview(group, player, playerLoader, waifuLoader, jikanFetcher, messageSender,
        terminal), channel);
  }


}
