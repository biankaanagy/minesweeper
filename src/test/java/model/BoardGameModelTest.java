package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardGameModelTest {

    @Test
    void winCheck() {
        BoardGameModel testModel = new BoardGameModel();
        assertFalse(testModel.winCheck());
    }

    @Test
    void setFlagged(){
        BoardGameModel testModel = new BoardGameModel();
        testModel.setFlag(0,1,true);
        assertTrue(testModel.getFlaggedCell(0,1));
    }

}