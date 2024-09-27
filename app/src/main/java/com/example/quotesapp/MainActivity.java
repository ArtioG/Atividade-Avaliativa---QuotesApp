package com.example.quotesapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private TextView quoteTextView;
    private ImageView authorImageView;
    private Button newQuoteButton;
    private List<Quote> quotes; // Correto: ponto e vírgula no final

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referências para os componentes da UI
        quoteTextView = findViewById(R.id.quoteTextView);
        authorImageView = findViewById(R.id.authorImageView);
        newQuoteButton = findViewById(R.id.newQuoteButton);

        // Carregar as citações do arquivo JSON
        loadQuotes(); // Certifique-se de que o método loadQuotes é chamado corretamente com ;

        // Exibir uma citação aleatória
        showRandomQuote(); // Certifique-se que este método termina com ;

        // Definir o clique do botão para exibir uma nova citação
        newQuoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRandomQuote();
            }
        });
    }

    private void loadQuotes() {
        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("quotes.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();

            // Converter JSON para objetos Java
            String json = new String(buffer, "UTF-8");
            Gson gson = new Gson();
            Type listType = new TypeToken<List<Quote>>() {}.getType();
            quotes = gson.fromJson(json, listType);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showRandomQuote() {
        Random random = new Random();
        Quote randomQuote = quotes.get(random.nextInt(quotes.size()));
        quoteTextView.setText(randomQuote.getQuote());
        int imageResId = getResources().getIdentifier(randomQuote.getImage(), "drawable", getPackageName());
        authorImageView.setImageResource(imageResId);
    }
}