package entities.Moves;

import java.util.ArrayList;
import java.util.List;

import audit.Audit;

public class MoveService {
    private static final MoveCRUD CRUD;

    static {
        CRUD = new MoveCRUD(); 
    }

    public static void addMove(Move move) {
        try {
            CRUD.insert(move);
            Audit.writeAudit("Add Move");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Move> getMoves() {
        try {
            Audit.writeAudit("Get Moves");
            return CRUD.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }    
}
