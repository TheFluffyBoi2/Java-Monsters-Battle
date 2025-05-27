package entities.Moves;

import java.util.ArrayList;
import java.util.List;

import audit.Audit;
import entities.Type;

public class MoveService {
    private static final MoveDAO DAO;

    static {
        DAO = new MoveDAO(); }
    //     try {
    //         if (DAO.findAll().isEmpty()) {
    //             DAO.insert(new Move("Tackle", Type.NORMAL, 40));
    //             DAO.insert(new Move("Pound", Type.NORMAL, 60));
    //         }
    //     } catch (Exception e) {
    //         System.out.println(e.getMessage());
    //     }
    // }

    public static void addMove(Move move) {
        try {
            DAO.insert(move);
            Audit.writeAudit("Add Move");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static List<Move> getMoves() {
        try {
            Audit.writeAudit("Get Moves");
            return DAO.findAll();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new ArrayList<>();
        }
    }    
}
