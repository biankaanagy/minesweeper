package model;

import org.tinylog.Logger;

/**
 * The class that represents the table of the Game.
 */
public class BoardGameModel {
    /**
     * Board size.
     */
    final private static int BOARD_SIZE = 9;
    /**
     * Bomb mark.
     */
    final private int bomb = -1;
    /**
     * Bombs on the game board.
     */
    final private int sumBomb = 15;

    /**
     * The array representing the state of the table.
     */
    private Cell[][] boardmodel = new Cell[BOARD_SIZE][BOARD_SIZE];


    /**
     * The {@code BoardGameModel} creating the state of the table.
     * Put down the bombs and numbers.
     */
    public BoardGameModel() {
        for (int i = 0; i < boardmodel.length; i++) {
            for (int j = 0; j < boardmodel.length; j++) {
                boardmodel[i][j] = new Cell(0,false, false);
            }
        }

        int oszlop;
        int sor;
        for (int i = 0; i < sumBomb; i++) {
            do {
                oszlop = (int) (Math.random() * 9);
                sor = (int) (Math.random() * 9);
            } while (boardmodel[sor][oszlop].value == bomb);
            boardmodel[sor][oszlop].value = bomb;
        }
        int db;
        for (int i = 0; i < boardmodel.length; i++) {
            for (int j = 0; j < boardmodel.length; j++) {
                if (boardmodel[i][j].value != bomb) {
                    db = 0;
                    if (i > 0 && j > 0 && boardmodel[i - 1][j - 1].value == bomb) {
                        db++;
                    }
                    if (i > 0 && boardmodel[i - 1][j].value == bomb) {
                        db++;
                    }
                    if (i > 0 && j < 8 && boardmodel[i - 1][j + 1].value == bomb) {
                        db++;
                    }
                    if (j < 8 && boardmodel[i][j + 1].value == bomb) {
                        db++;
                    }
                    if (i < 8 && j < 8 && boardmodel[i + 1][j + 1].value == bomb) {
                        db++;
                    }
                    if (i < 8 && boardmodel[i + 1][j].value == bomb) {
                        db++;
                    }
                    if (i < 8 && j > 0 && boardmodel[i + 1][j - 1].value == bomb) {
                        db++;
                    }
                    if (j > 0 && boardmodel[i][j - 1].value == bomb) {
                        db++;
                    }
                    boardmodel[i][j].value = db;
                }
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        for (int i = 0; i < boardmodel.length; i++) {
            for (int j = 0; j < boardmodel.length; j++) {
                sb.append(boardmodel[i][j].value+" ");
            }
            sb.append("\n");
        }
        Logger.info(sb);
    }

    /**
     * {@code setOpened} set the opened field in the state array.
     */
    public void setOpened(int row, int col,boolean opened){
        boardmodel[row][col].opened = opened;
    }

    /**
     * {@code setFlag} set the flag marker in the state array.
     */
    public void setFlag(int row, int col,boolean flag){
        boardmodel[row][col].flag = flag;
    }

    /**
     * {@code getBoardModel}.
     * @return the board actual value.
     */
    public String getBoardModel(int row, int col){
        if (boardmodel[row][col].value == -1){
            return "X";
        }
        else {
            return boardmodel[row][col].value + "";
        }
    }

    /**
     * {@code getFlaggedCell}.
     * @return that the field is flagged or not.
     */
    public boolean getFlaggedCell(int row, int col){
        return boardmodel[row][col].flag;
    }

    /**
     * {@code winCheck}.
     * @return true if opened ((BOARD_SIZE * BOARD_SIZE) - sumBomb) square.
     */
    public boolean winCheck(){
        int openedCells = 0;
        for (int i = 0; i < boardmodel.length; i++) {
            for (int j = 0; j < boardmodel.length; j++) {
                if(boardmodel[i][j].opened){
                    openedCells++;
                }
            }
        }
        return openedCells == ((BOARD_SIZE * BOARD_SIZE) - sumBomb); //igazzal tér vissza, ha minden nem bombas cella nyitott
    }
}
