package domain.waifu.dungeon;

public class DungeonInformation {

    private boolean isOwnerMessaged;
    private Dungeon currentDungeon;
    private int level;
    private boolean isInDungeon;

    public DungeonInformation() {
        this.isOwnerMessaged = false;
        this.isInDungeon = false;
        this.currentDungeon = Dungeon.getHOME();
        this.level = 0;
    }

    public void entersDungeon(Dungeon dungeon, int level) {
        this.level = level;
        currentDungeon = dungeon;
        isOwnerMessaged = false;
        isInDungeon = true;
    }

    public void leavesDungeon() {
        this.level = 0;
        isOwnerMessaged = false;
        currentDungeon = Dungeon.getHOME();
        isInDungeon = false;
    }

    public void raiseLevel() {
        level = level + 1;
    }

    public boolean isOwnerMessaged() {
        return isOwnerMessaged;
    }

    public void setOwnerMessaged(boolean ownerMessaged) {
        this.isOwnerMessaged = ownerMessaged;
    }

    public Dungeon getCurrentDungeon() {
        return currentDungeon;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isInDungeon() {
        return isInDungeon;
    }
}