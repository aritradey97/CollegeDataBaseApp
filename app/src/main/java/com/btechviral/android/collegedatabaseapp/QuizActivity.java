package com.btechviral.android.collegedatabaseapp;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class QuizActivity extends AppCompatActivity {

    TextView question;
    TextView score;
    Button option1, option2, option3, option4,reset,menu;
    QuestionBank q = new QuestionBank();
    int qno = 0, sc = 0, c = 0,status = 0, count = 0;
    ArrayList<Integer> arr = new ArrayList<>();
    String ans;
    ProgressBar mprogress;
    Handler h = new Handler();
    Random r = new Random();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        Intent i = getIntent();

        while(arr.size() < 5)
        {
            int a = r.nextInt(5);
            if(!arr.contains(a))
                arr.add(a);
        }
        setUpUIViews();
        updateQuestion();
        update();
        option1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option1.getText().toString().equalsIgnoreCase(ans))
                {
                    sc += 10;
                    updateScore(sc);
                    //option1.setBackgroundColor(136);
                    Toast.makeText(QuizActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //option1.setBackgroundColor(100);
                    Toast.makeText(QuizActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                }
                status = 0;
                updateQuestion();
            }
        });
        option2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option2.getText().toString().equalsIgnoreCase(ans))
                {
                    sc += 10;
                    updateScore(sc);
                    //option1.setBackgroundColor(136);
                    Toast.makeText(QuizActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //option1.setBackgroundColor(100);
                    Toast.makeText(QuizActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                }
                status = 0;
                updateQuestion();
            }
        });
        option3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option3.getText().toString().equalsIgnoreCase(ans))
                {
                    sc += 10;
                    updateScore(sc);
                    //option1.setBackgroundColor(136);
                    Toast.makeText(QuizActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //option1.setBackgroundColor(100);
                    Toast.makeText(QuizActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                }
                status = 0;
                updateQuestion();
            }
        });
        option4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(option4.getText().toString().equalsIgnoreCase(ans))
                {
                    sc += 10;
                    updateScore(sc);
                    //option1.setBackgroundColor(136);
                    Toast.makeText(QuizActivity.this,"Correct",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //option1.setBackgroundColor(100);
                    Toast.makeText(QuizActivity.this,"Wrong",Toast.LENGTH_SHORT).show();
                }
                updateQuestion();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateQuestion();
                updateScore(0);
                reset.setVisibility(View.GONE);
                menu.setVisibility(View.GONE);
                option1.setVisibility(View.VISIBLE);
                option2.setVisibility(View.VISIBLE);
                option3.setVisibility(View.VISIBLE);
                option4.setVisibility(View.VISIBLE);
                mprogress.setVisibility(View.VISIBLE);
            }
        });
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(QuizActivity.this, MainActivity.class);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
            }
        });
    }
    private void setUpUIViews()
    {
        question = (TextView)findViewById(R.id.question);
        score = (TextView)findViewById(R.id.score);
        option1 = (Button)findViewById(R.id.option1);
        option2 = (Button)findViewById(R.id.option2);
        option3 = (Button)findViewById(R.id.option3);
        option4 = (Button)findViewById(R.id.option4);
        reset = (Button)findViewById(R.id.reset);
        menu = (Button)findViewById(R.id.button);
        mprogress = (ProgressBar)findViewById(R.id.progress);
    }
    private void updateQuestion()
    {
        try {
            question.setText(q.getQuestion(arr.get(qno)));
            option1.setText(q.getOption1(arr.get(qno)));
            option2.setText(q.getOption2(arr.get(qno)));
            option3.setText(q.getOption3(arr.get(qno)));
            option4.setText(q.getOption4(arr.get(qno)));
            ans = q.getCorrectOption(arr.get(qno));
            qno++;
        }
        catch(Exception e)
        {
            Toast.makeText(QuizActivity.this,"Quiz Over\nYou scored " + Integer.toString(sc) + " points", Toast.LENGTH_LONG).show();
            reset.setVisibility(View.VISIBLE);
            menu.setVisibility(View.VISIBLE);
            question.setText("Game Over");
            option1.setVisibility(View.GONE);
            option2.setVisibility(View.GONE);
            option3.setVisibility(View.GONE);
            option4.setVisibility(View.GONE);
            mprogress.setVisibility(View.GONE);
            qno = 0;
            sc = 0;
            status = 0;
            count = 1;
        }
    }
    private void update()
    {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(status < 100)
                {
                    status++;
                    android.os.SystemClock.sleep(80);
                    h.post(new Runnable() {
                        @Override
                        public void run() {
                            mprogress.setProgress(status);
                        }
                    });
                }
                h.post(new Runnable() {
                    @Override
                    public void run() {
                        status = 0;
                        mprogress.setProgress(status);

                        if(count == 0)
                        {
                            updateQuestion();
                            update();
                        }
                    }
                });
            }
        }).start();
    }
    private void updateScore(int a)
    {
        score.setText(Integer.toString(a));
    }

}