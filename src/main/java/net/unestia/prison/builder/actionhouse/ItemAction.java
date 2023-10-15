package net.unestia.prison.builder.actionhouse;

public enum ItemAction {

    ITEM_LISTED,
    ITEM_SOLD,
    ITEM_CANCELLED,
    ITEM_RETURNED,
    ITEM_PURGED;

    private static ItemAction[] getItemActions() {
        return new ItemAction[]{ITEM_LISTED, ITEM_SOLD, ITEM_CANCELLED, ITEM_RETURNED, ITEM_PURGED};
    }

}
