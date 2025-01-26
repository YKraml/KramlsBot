package domain.waifu.dungeon;

public class DungeonRecord {

  private final Team team;
  private final RecordTypes recordType;
  private final long value;

  public DungeonRecord(Team team, long value, RecordTypes recordType) {
    this.team = team;
    this.value = value;
    this.recordType = recordType;
  }


  public Team getTeam() {
    return team;
  }

  public long getValue() {
    return value;
  }

  public RecordTypes getRecordType() {
    return recordType;
  }

  public boolean thisRecordIsWorseThen(DungeonRecord dungeonRecord) {

    if (!this.recordType.equals(dungeonRecord.recordType)) {
      return false;
    }

    return this.value < dungeonRecord.value;
  }

  public String print() {
    return this.recordType.getRecordTitle() + ": " + this.value + " | " + this.team.getPlayer()
        .getName() + " | " + team.getName();
  }

  public enum RecordTypes {
    HIGHEST_LEVEL("Tiefstes Level"),
    MOST_MONEY("Meiste Geld"),
    MOST_STARDUST("Meistes Stardust"),
    MOST_COOKIES("Meiste Cookies");

    private final String recordTitle;

    RecordTypes(String recordName) {
      this.recordTitle = recordName;
    }

    public String getRecordTitle() {
      return recordTitle;
    }
  }
}
