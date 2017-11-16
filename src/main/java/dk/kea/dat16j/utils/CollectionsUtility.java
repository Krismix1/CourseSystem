package dk.kea.dat16j.utils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Chris on 16-Nov-17.
 */
public class CollectionsUtility {
    // code from https://stackoverflow.com/questions/6416706/easy-way-to-change-iterable-into-collection
    public static <E> Collection<E> makeCollection(Iterable<E> iter) {
        Collection<E> list = new ArrayList<>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }
}
