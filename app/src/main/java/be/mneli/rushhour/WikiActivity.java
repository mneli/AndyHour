package be.mneli.rushhour;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class WikiActivity extends AppCompatActivity {
    private LinearLayout wikiFooter;
    private final String wikiUrl = "http://www.thinkfun.com/products/rush-hour/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wiki);
        initView();
    }

    private void initView() {
        wikiFooter = (LinearLayout) findViewById(R.id.layout_wiki_footer);

        wikiFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openWebPage(wikiUrl);
            }
        });
    }

    private void openWebPage(String url) {
        Uri webpage = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}
