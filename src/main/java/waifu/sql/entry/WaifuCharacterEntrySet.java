package waifu.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;
import waifu.sql.entry.WaifuCharacterEntrySet.WaifuCharacterEntry;

public class WaifuCharacterEntrySet extends AbstractEntrySet<WaifuCharacterEntry> {

  @Override
  public void addSingleResult(ResultSet resultSet) throws SQLException {

    int idMal = resultSet.getInt(1);
    String id = resultSet.getString(2);
    String rarity = resultSet.getString(3);
    int level = resultSet.getInt(4);
    int xp = resultSet.getInt(5);
    int baseHp = resultSet.getInt(6);
    int baseAtt = resultSet.getInt(7);
    int baseDef = resultSet.getInt(8);
    int baseInit = resultSet.getInt(9);
    String owner = resultSet.getString(10);
    String imageUrl = resultSet.getString(11);
    int starLevel = resultSet.getInt(12);
    String url = resultSet.getString(13);
    String imageUrlCharacter = resultSet.getString(14);
    String name = resultSet.getString(15);
    String role = resultSet.getString(16);

    WaifuCharacterEntry waifuCharacterEntry = new WaifuCharacterEntry(idMal, id, rarity, level, xp, baseHp, baseAtt, baseDef, baseInit, owner, imageUrl, starLevel,
        url, imageUrlCharacter, name, role);
    this.addEntry(waifuCharacterEntry);
  }


  public static class WaifuCharacterEntry extends AbstractEntry {

    private final String id;
    private final int idMal;
    private final String rarity;
    private final int level;
    private final int starLevel;
    private final int xp;
    private final int baseHp;
    private final int baseAtt;
    private final int baseDef;
    private final int baseInit;
    private final String owner;
    private final String imageUrl;
    private final String url;
    private final String imageUrlCharacter;
    private final String name;
    private final String animeName;

    public WaifuCharacterEntry(int idMal, String id, String rarity, int level, int xp, int baseHp, int baseAtt, int baseDef, int baseInit, String owner, String imageUrl, int starLevel,
        String url, String imageUrlCharacter, String name, String animeName) {
      this.id = id;
      this.idMal = idMal;
      this.rarity = rarity;
      this.level = level;
      this.starLevel = starLevel;
      this.xp = xp;
      this.baseHp = baseHp;
      this.baseAtt = baseAtt;
      this.baseDef = baseDef;
      this.baseInit = baseInit;
      this.owner = owner;
      this.imageUrl = imageUrl;
      this.url = url;
      this.imageUrlCharacter = imageUrlCharacter;
      this.name = name;
      this.animeName = animeName;
    }

    public String getId() {
      return id;
    }

    public int getIdMal() {
      return idMal;
    }

    public String getRarity() {
      return rarity;
    }

    public int getLevel() {
      return level;
    }

    public int getXp() {
      return xp;
    }

    public int getBaseHp() {
      return baseHp;
    }

    public int getBaseAtt() {
      return baseAtt;
    }
    public int getBaseDef() {
      return baseDef;
    }
    public int getBaseInit() {
      return baseInit;
    }

    public String getOwner() {
      return owner;
    }

    public String getImageUrl() {
      return imageUrl;
    }
    public int getStarLevel() {
      return starLevel;
    }

    public String getUrl() {
      return url;
    }

    public String getImageUrlCharacter() {
      return imageUrlCharacter;
    }

    public String getName() {
      return name;
    }

    public String getAnimeName() {
      return animeName;
    }
  }
}
