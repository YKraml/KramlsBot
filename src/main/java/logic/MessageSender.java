package logic;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;
import domain.waifu.dungeon.Inventory;
import domain.waifu.dungeon.Team;
import logic.routines.RoutineRevealBuilder;
import logic.routines.RoutineRunner;
import logic.waifu.JikanFetcher;
import logic.waifu.PlayerLoader;
import logic.waifu.WaifuLoader;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.user.User;

public interface MessageSender {

    Message send(MyMessage myMessage, TextChannel textChannel) throws MyOwnException;

    void sendSafe(MyMessage myMessage, TextChannel textChannel);

    void sendMerged(TextChannel channel, Player player, Waifu waifu) throws MyOwnException;

    void sendWaifusAreDifferent(TextChannel channel, Player player) throws MyOwnException;

    void sendGroupList(TextChannel channel, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) throws MyOwnException;

    void sendGuessGameStarted(TextChannel textChannel, RoutineRunner routineRunner, RoutineRevealBuilder routineRevealBuilder) throws MyOwnException;

    void sendSafeNoMatchForSongMessage(TextChannel channel);

    void sendSafeCouldNotLoadSongMessage(TextChannel channel);

    void sendWaifuSpawned(TextChannel channel, Player player) throws MyOwnException;

    void sendWaifuStats(TextChannel channel, Waifu waifu, Player player, PlayerLoader playerLoader, WaifuLoader waifuLoader, JikanFetcher jikanFetcher, MessageSender messageSender) throws MyOwnException;

    void sendTeamKilled(TextChannel textChannel, Team team, int level, Inventory inventory) throws MyOwnException;

    void sendTeamIsLow(TextChannel textChannel, Team team) throws MyOwnException;

    void sendDailyAlreadyUsed(TextChannel channel, Player player, String newDate) throws MyOwnException;

    void sendDailyUsed(TextChannel channel, Player player) throws MyOwnException;

    Message sendFightRequest(TextChannel textChannel, User user, User userEnemy, long money, long stardust, long morphStones) throws MyOwnException;
}
