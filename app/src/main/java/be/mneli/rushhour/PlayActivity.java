package be.mneli.rushhour;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.HashMap;
import java.util.List;

import be.mneli.rushhour.model.RushHourGame;
import be.mneli.rushhour.model.helper.ParseJson;

public class PlayActivity extends AppCompatActivity {
    private static final String EASY_LEVELS = "easy";
    private static final String MEDIUM_LEVELS = "medium";
    private static final String HARD_LEVELS = "hard";
    private HashMap<String, List<RushHourGame>> gameHashMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);

        gameHashMap = new ParseJson().parseLevels(this.getResources().openRawResource(R.raw.levels));
        initView();
    }

    private void initView() {

    }

}
