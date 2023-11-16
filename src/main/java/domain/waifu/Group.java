package domain.waifu;

import domain.exceptions.MyOwnException;
import domain.DisplayableElement;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

public class Group implements DisplayableElement {

    private final String id;
    private final String name;
    private final List<Waifu> waifus;

    public Group(String id, String name) {
        this.id = id;
        this.name = name;
        this.waifus = new LinkedList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Waifu> getWaifuList() {
        return Collections.unmodifiableList(waifus);
    }

    public void addWaifu(Waifu waifu) throws MyOwnException {
        if (waifus.contains(waifu)) {
            throw new MyOwnException(() -> "Waifu schon in der Gruppe.", null);
        }
        this.waifus.add(waifu);
    }

    @Override
    public String getDisplayTitle() {
        return this.name;
    }

    @Override
    public String getDisplayBody() {
        return "Waifus: " + this.waifus.size() + " Stueck.";
    }

    @Override
    public String getDisplayImageUrl() {
        try {
            return waifus.iterator().next().getDisplayImageUrl();
        } catch (NoSuchElementException e) {
            return null;
        }
    }

    public boolean removeWaifu(Waifu waifu) {
        return this.waifus.remove(waifu);
    }
}
