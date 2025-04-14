package entities;

import java.util.ArrayList;
import java.util.List;

public class MoveService {
    private static List<Move> moves = new ArrayList<>();

    static {
        moves.add(new Move("Tackle", Type.NORMAL, 40));
        moves.add(new Move("Pound", Type.NORMAL, 60));
    }

    public static void addMove(Move move) {
        moves.add(move);
    }

    public static void deleteMove(Move move) {
        moves.remove(move);
    }

    public static List<Move> getMoves() {
        return moves;
    }    
}
