package database.sql.commands.waifu;

import database.sql.commands.SQLCommandWithResult;
import database.sql.entry.WaifuEntrySet;

public class SelectWaifuById extends SQLCommandWithResult<WaifuEntrySet> {

    private final String waifuId;

    public SelectWaifuById(String waifuId) {
        super(new WaifuEntrySet());
        this.waifuId = waifuId;
    }

    @Override
    protected String getCommand() {
        return "select * from "
                + ("KRAMLSBOT" + "." + "WAIFU")
                + " where id like "
                + "'" + waifuId + "'"
                + ";";
    }
}
