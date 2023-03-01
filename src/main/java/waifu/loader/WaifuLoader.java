package waifu.loader;

import exceptions.MyOwnException;
import java.util.List;
import java.util.Optional;
import waifu.model.Player;
import waifu.model.Waifu;

public interface WaifuLoader {

  List<Waifu> getWaifusFromPlayer(Player player) throws MyOwnException;

  Optional<Waifu> getWaifuById(String id) throws MyOwnException;

  void saveWaifu(Waifu waifu, Player player) throws MyOwnException;

  void deleteWaifu(Waifu waifu, Player player) throws MyOwnException;
}
