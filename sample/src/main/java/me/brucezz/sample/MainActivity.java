package me.brucezz.sample;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mTextView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextView = $(R.id.tv);

        mButton = $(R.id.btn);

        mButton.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                Call<Result> hello = HttpClient.getInstance().create(APIService.class).hello("mock");
                mTextView.setText("Loading...");
                hello.enqueue(new Callback<Result>() {
                    @Override
                    public void onResponse(Call<Result> call, Response<Result> response) {
                        mTextView.setText(response.body().toString());
                    }

                    @Override
                    public void onFailure(Call<Result> call, Throwable t) {
                        mTextView.setText(t.getMessage());
                    }
                });
            }
        });
    }

    private <T extends View> T $(int id) {
        return ((T) findViewById(id));
    }
}
