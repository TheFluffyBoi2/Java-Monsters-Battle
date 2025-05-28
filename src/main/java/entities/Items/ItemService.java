package entities.Items;

import java.util.List;

import audit.Audit;

public class ItemService {
    private static final ItemDAO DAO;

    static {
        DAO = new ItemDAO();
    }

    public static void addItem(Item item) {
        try {
            DAO.insert(item);
            Audit.writeAudit("Add Item");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Item> getItems() {
        try {
            Audit.writeAudit("Get Items");
            return DAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
