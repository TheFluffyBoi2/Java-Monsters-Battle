package entities.Player;

import java.util.List;

import audit.Audit;
import entities.Monsters.Monster;

public class TeamService {
    private static final TeamDAO DAO;

    static {
        DAO = new TeamDAO();
    }

    public static List<Monster> getTeam() {
        try {
            Audit.writeAudit("Get Team");
            return DAO.findAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static void updateTeam(List<Monster> monsters) {
        try {
            DAO.updateTeam(monsters);
            Audit.writeAudit("Update Team");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
