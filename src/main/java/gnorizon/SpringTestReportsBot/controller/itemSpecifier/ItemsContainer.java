package gnorizon.SpringTestReportsBot.controller.itemSpecifier;

import com.google.common.collect.ImmutableMap;
import gnorizon.SpringTestReportsBot.controller.itemSpecifier.items.*;


import static gnorizon.SpringTestReportsBot.controller.itemSpecifier.ItemsName.*;

public class ItemsContainer {
    private final ImmutableMap<Character, Item> itemMap;
    private final Item noItem;

    public ItemsContainer() {
        itemMap = ImmutableMap.<Character,Item>builder()
                .put(ITEM_1.step,new Item1())
                .put(ITEM_2.step,new Item2())
                .put(ITEM_3.step,new Item3())
                .put(ITEM_4.step,new Item4())
                .put(ITEM_5.step,new Item5())
                .put(ITEM_6.step,new Item6())
                .put(ITEM_7.step,new Item7())
                .put(ITEM_8.step,new Item8())
                .put(ITEM_9.step,new Item9())
                .put(ITEM_10.step,new Item10())
                .build();
        noItem = new NoItem();
    }
    public Item retrieveItem(Character itemIdentifier){
        return itemMap.getOrDefault(itemIdentifier,noItem);
    }
}
