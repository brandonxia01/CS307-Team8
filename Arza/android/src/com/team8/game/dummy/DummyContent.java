package com.team8.game.dummy;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static  List<DummyItem> ITEMS = new ArrayList<DummyItem>();
    public static  List<DummyItem> ITEMS2 = new ArrayList<DummyItem>();



    /**
     * A map of sample (dummy) items, by ID.
     */
    //public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();
    //public static final Map<String, DummyItem> ITEM_MAP2 = new HashMap<String, DummyItem>();

    private static final int COUNT = 25;

    static {
        // Add some sample items.

        for (int i = 1; i <= COUNT; i++) {
            addItem(createDummyItem(i,"Achievements"),ITEMS);
            addItem(createDummyItem(i,"Players"),ITEMS2);
        }
    }

    public static void addItem(DummyItem item, List<DummyItem> ITEMS) {
        ITEMS.add(item);
        //ITEM_MAP.put(item.id, item);
    }

    public static DummyItem createDummyItem(int position, String str) {
        return new DummyItem(String.valueOf(position), str + position, makeDetails(position));
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        public final String content;
        public final String details;

        public DummyItem(String id, String content, String details) {
            this.id = id;
            this.content = content;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
