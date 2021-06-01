package highscores;

@lombok.Data
public class GameStat {
    private String playername;
    private String win;
    private String date;

    public GameStat(String playername, String win, String date) {
        this.playername = playername;
        this.win = win;
        this.date = date;
    }

    public GameStat(){

    }

    @Override
    public String toString() {
        return "GameStat{" +
                "playername='" + playername + '\'' +
                ", win='" + win + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
