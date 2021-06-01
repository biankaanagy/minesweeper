package highscores;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.tinylog.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class ResultManager {
    final ObjectMapper objectMapper = new ObjectMapper(); //.enable(SerializationFeature.INDENT_OUTPUT)
    final private String URL = "target/minesweeper.json";

    public ArrayList<GameStat> fileReader() throws IOException {
        GameStat[] stats = objectMapper.readValue(new FileReader(URL), GameStat[].class);
        return new ArrayList<>(Arrays.asList(stats));
    }

    public void fileWriter(ArrayList<GameStat> stats){
        try {
            Logger.info(objectMapper.writeValueAsString(stats));
            try (FileWriter writer = new FileWriter(URL)) {
                objectMapper.writeValue(writer, stats);
            }
        }catch (IOException e){Logger.warn(e);}
    }
}
