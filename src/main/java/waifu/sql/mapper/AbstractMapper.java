package waifu.sql.mapper;

import exceptions.MyOwnException;
import waifu.sql.entry.AbstractEntrySet;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

abstract class AbstractMapper<ObjectType, EntryType extends AbstractEntrySet.AbstractEntry> {

    private final List<ObjectType> mappedObjects;
    private final AbstractEntrySet<EntryType> entrySet;
    private boolean mapped;

    protected AbstractMapper(AbstractEntrySet<EntryType> entrySet) {
        this.mappedObjects = new ArrayList<>();
        this.entrySet = entrySet;
        this.mapped = false;

    }

    abstract ObjectType mapOneEntry(EntryType entry) throws MyOwnException;


    public List<ObjectType> getList() throws MyOwnException {
        map();
        return Collections.unmodifiableList(mappedObjects);
    }

    public Optional<ObjectType> getFirst() throws MyOwnException {
        map();
        if (this.mappedObjects.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.ofNullable(this.mappedObjects.get(0));
        }
    }

    private void map() throws MyOwnException {
        if (mapped) {
            return;
        }

        for (EntryType entry : entrySet) {
            ObjectType object = this.mapOneEntry(entry);
            if (object != null) {
                mappedObjects.add(this.mapOneEntry(entry));
            }
        }


        mapped = true;
    }
}
