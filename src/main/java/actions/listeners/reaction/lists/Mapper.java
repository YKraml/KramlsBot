package actions.listeners.reaction.lists;

import embeds.DisplayableElement;

import java.util.List;

public interface Mapper<ListType2> {

    List<DisplayableElement> map(List<ListType2> list);

}
