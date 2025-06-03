package entities.Items;

import java.util.List;

import audit.Audit;

public class ItemService {
    private static final ItemCRUD CRUD;

    static {
        CRUD = new ItemCRUD();
    }

    public static void addItem(Item item) {
        try {
            CRUD.insert(item);
            Audit.writeAudit("Add Item");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getItems() {
        try {
            Audit.writeAudit("Get Items");
            return CRUD.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
