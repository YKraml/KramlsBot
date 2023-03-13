package waifu.sql.entry;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

public abstract class AbstractEntrySet<EntryType extends AbstractEntrySet.AbstractEntry> implements
    Collection<EntryType> {

  private final List<EntryType> entryList;

  public AbstractEntrySet() {
    this.entryList = new ArrayList<>();
  }

  public void addEntry(EntryType specificEntry) {
    this.entryList.add(specificEntry);
  }

  public Optional<EntryType> getFirst() {
    if (this.entryList.isEmpty()) {
      return Optional.empty();
    }
    return Optional.ofNullable(this.entryList.get(0));
  }

  @Override
  public int size() {
    return entryList.size();
  }

  @Override
  public boolean isEmpty() {
    return entryList.isEmpty();
  }

  @Override
  public boolean contains(Object o) {
    return entryList.contains(o);
  }


  @Override
  public Iterator<EntryType> iterator() {
    return entryList.iterator();
  }


  @Override
  public Object[] toArray() {
    return entryList.toArray();
  }


  @Override
  public <T> T[] toArray(T[] a) {
    return entryList.toArray(a);
  }

  @Override
  public boolean add(EntryType entry) {
    return entryList.add(entry);
  }

  @Override
  public boolean remove(Object o) {
    return entryList.remove(o);
  }

  @Override
  public boolean containsAll(Collection<?> c) {
    return new HashSet<>(entryList).containsAll(c);
  }

  @Override
  public boolean addAll(Collection<? extends EntryType> c) {
    return entryList.addAll(c);
  }

  @Override
  public boolean removeAll(Collection<?> c) {
    return entryList.removeAll(c);
  }

  @Override
  public boolean retainAll(Collection<?> c) {
    return entryList.retainAll(c);
  }

  @Override
  public void clear() {
    entryList.clear();
  }

  public abstract void addSingleResult(ResultSet resultSet) throws SQLException;

  public static class AbstractEntry {

    protected AbstractEntry() {
      //This class can only be initiated by an EntrySet
    }

  }
}
