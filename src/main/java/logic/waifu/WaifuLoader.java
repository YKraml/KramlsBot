package logic.waifu;

import domain.exceptions.MyOwnException;
import domain.waifu.Player;
import domain.waifu.Waifu;

import java.util.List;
import java.util.Optional;

public interface WaifuLoader {

    List<Waifu> getWaifusFromPlayer(Player player) throws MyOwnException;

    Optional<Waifu> getWaifuById(String id) throws MyOwnException;

    void saveWaifu(Waifu waifu, Player player) throws MyOwnException;

    void deleteWaifu(Waifu waifu, Player player) throws MyOwnException;
}
