package actions.listeners.reaction;

import embeds.DisplayableElement;

import java.util.List;

public interface Mapper<ListType> {

    List<DisplayableElement> map(List<ListType> list);

}
