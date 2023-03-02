package actions.listeners.reaction;

import embeds.DisplayableElement;

import java.util.List;

public interface Mapper<ListType2> {

    List<DisplayableElement> map(List<ListType2> list);

}
