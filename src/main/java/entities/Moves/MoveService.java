package entities.Moves;

import java.util.ArrayList;
import java.util.List;

import audit.Audit;

public class MoveService {
    private static final MoveDAO DAO;

    static {
        DAO = new MoveDAO(); 
    }

    public static void addMove(Move move) {
        try {
            DAO.insert(move);
            Audit.writeAudit("Add Move");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<Move> getMoves() {
        try {
            Audit.writeAudit("Get Moves");
            return DAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }    
}
