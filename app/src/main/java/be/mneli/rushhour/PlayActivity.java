package be.mneli.rushhour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import be.mneli.rushhour.model.Board;
import be.mneli.rushhour.model.Car;
import be.mneli.rushhour.model.Direction;
import be.mneli.rushhour.model.Position;
import be.mneli.rushhour.model.RushHourGame;
import be.mneli.rushhour.model.helper.json.ParseJson;

public class PlayActivity extends AppCompatActivity {
    private static final String EASY_LEVELS = "easy";
    private static final String MEDIUM_LEVELS = "medium";
    private static final String HARD_LEVELS = "hard";
    private HashMap<String, List<RushHourGame>> gameHashMap;
    private GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        gameHashMap = ParseJson.parseLevels(this.getResources().openRawResource(R.raw.levels));
        initView();
    }

    private void initView() {

        gridView = (GridView) findViewById(R.id.gridView_play_board);

        final RushHourGame game = getSingleLevel();
        String[] board = getBoard(game.getBoard());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, board);
        gridView.setAdapter(adapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                Log.e("POSITION", String.valueOf(position));
                Log.e("ID", String.valueOf(id));
                Log.e("View", (String) ((TextView) v).getText());
                Car selectedCar = game.getBoard().getCar((((TextView) v).getText()).charAt(0));
                Log.e("Selected car", selectedCar.toString());
                game.getBoard().canMove(selectedCar, Direction.LEFT);
            }
        });
    }

    private String[] getBoard(Board board) {
        String[] boardAsStringArray = new String[36];
        int index = 0;
        for (int row = 0; row < board.getHeight(); row++) {

            for (int column = 0; column < board.getWidth(); column++) {
                Car car = board.getCarAt(new Position(row, column));
                boardAsStringArray[index] = "";
                if (car != null)
                    boardAsStringArray[index] += car.getId();
                index++;
            }

        }
        return boardAsStringArray;
    }

    private RushHourGame getSingleLevel() {
        List<RushHourGame> easyLevels = gameHashMap.get(EASY_LEVELS);
        return easyLevels.get(0);
    }

}
