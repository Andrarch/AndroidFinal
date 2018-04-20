package com.example.andrew.androidfinal;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


import java.util.ArrayList;


public class RobinQuiz extends Activity {
    ListView listViewObject;

    ArrayList<String> quizLines = new ArrayList<String>();

    SearchDatabaseHelper searchDatabase;
    EditText javaEditText;
    Button multipleR, trueFalseR, numericR, infoButtonR, showList;
    SQLiteDatabase databaseDb;
    Cursor cursor;

    Button button;
    protected static final String ACTIVITY_NAME = "QuizActivity";
    private static final int REQUEST_CODE_QUIZ = 1;
    public static final String SHARED_PREFS = "sharedPrefs";
    public static final String KEY_HIGHSCORE = "keyHighScore";

    private TextView textViewHighScore;

    //    LinearLayout addQuestLL;
//    LinearLayout quizLL;
//    LinearLayout resultLL;
//    LinearLayout questListLL;
//    ImageView addQuestIV;
//    ImageView quizIV;
//    ImageView resultIV;
//    ImageView questListIV;
//    TextView addQuest;
//    TextView quiz;
    TextView result;
    TextView questList;
    Context ctx;
//    int highscore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_robin_quiz);
        infoButtonR = findViewById(R.id.otherR);
        multipleR = findViewById(R.id.multipleR);
        trueFalseR = findViewById(R.id.trueFalseR);
        numericR = findViewById(R.id.numericR);
        Toolbar tbar = (Toolbar) findViewById(R.id.quiz_toolbar);
        showList = findViewById(R.id.questionList);
        setActionBar(tbar);


        result = findViewById(R.id.resultsR);
        questList = findViewById(R.id.questionList);

//        addQuestLL = findViewById(R.id.addQuestionLL);
//        quizLL = findViewById(R.id.quizLL);
//        resultLL = findViewById(R.id.resultsLL);
//        questListLL = findViewById(R.id.questionListLL);
//        addQuestIV = findViewById(R.id.addQuestionIV);
//        quizIV = findViewById(R.id.quizIV);
//        resultIV = findViewById(R.id.resultsIV);
//        questListIV = findViewById(R.id.questionListIV);


        ctx = this;

        infoButtonR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar snackbar = Snackbar.make(multipleR, "google v zebra", Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        multipleR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, RobinMultipleChoice.class);
                startActivity(intent);

            }
        });
        numericR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, RobinNumeric.class);
                startActivity(intent);
                // startActivityForResult(intent,50);
            }
        });
        trueFalseR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent = new Intent(RobinQuiz.this, trueFalseRobin.class);
                startActivity(intent);
                // startActivityForResult(intent,50);
            }
        });
        showList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(ctx);
                dialog.setContentView(R.layout.activity_robin_list_view);

                final Intent intent = new Intent(RobinQuiz.this, RobinListView.class);
                startActivity(intent);
                Log.i(ACTIVITY_NAME, "User clicked True False");
                //callQuestList();
                // startActivityForResult(intent,50);
            }
        });
    }
}

//        private void callTakeQuiz() {
//            Snackbar snackbar = Snackbar.make(quiz, "Multiple choice", Snackbar.LENGTH_LONG);
//            snackbar.show();
//
//            Intent intent = new Intent(QuizActivity.this, AnnaTakeQuizActivity.class);
//            startActivityForResult(intent, REQUEST_CODE_QUIZ);
//
//            Log.i(ACTIVITY_NAME, "User clicked Multiple Choice");
//        }

//        @Override
//        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//            super.onActivityResult(requestCode, resultCode, data);
//
//            if (requestCode == REQUEST_CODE_QUIZ) {
//                if (resultCode == RESULT_OK) {
//                    int score = data.getIntExtra(AnnaTakeQuizActivity.EXTRA_SCORE, 0);
//                    if (score > highscore) {
//                        updateHighScore(score);
//                    }
//                }
//            }
//        }

//        private void callResults() {
//
//            Intent intent = new Intent(QuizActivity.this, AnnaResultActivity.class);
//            intent.putExtra("Score", highscore);
//            startActivity(intent);
//
//            Log.i(ACTIVITY_NAME, "User clicked Results");
//        }

//        private void loadHighScore() {
//            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//            highscore = prefs.getInt(KEY_HIGHSCORE, 0);
//            textViewHighScore.setText("Highscore: " + highscore);
//        }
//
//        private void updateHighScore(int highscoreNew) {
//            highscore = highscoreNew;
//            textViewHighScore.setText("Highscore: " + highscore);
//            SharedPreferences prefs = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//            SharedPreferences.Editor editor = prefs.edit();
//            editor.putInt(KEY_HIGHSCORE, highscore);
//            editor.apply();
//        }

//        private void callQuestList() {
//            final Dialog dialog = new Dialog(ctx);
//            dialog.setContentView(R.layout.activity_robin_list_view);
//            TextView txt = (TextView) dialog.findViewById(R.id.txt);
//            txt.setText("Notification will be here");
//            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButton);
//            dialogButton.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    dialog.dismiss();
//                }
//            });
//            dialog.show();
//
//            Intent intent = new Intent(RobinQuiz.this, RobinListView.class);
//            startActivity(intent);
//
//            Log.i(ACTIVITY_NAME, "User clicked True False");
//        }


//        public boolean onCreateOptionsMenu (Menu m){
//            getMenuInflater().inflate(R.menu.menu_quiz_toolbar, m );
//            return true;
//        }
//
//        public boolean onOptionsItemSelected(MenuItem mi){
//            int id = mi.getItemId();
//
//            switch(id){
//                case R.id.import_xml:
//
////                SharedPreferences sp = getSharedPreferences("ImportPrefs", MODE_PRIVATE);
//                    MultChoicDBHelper helper = new MultChoicDBHelper(QuizActivity.this);
////                boolean imported = sp.getBoolean("AlreadyImported", false);
////                if(!imported)
//                    helper.importXML();
////                else
////                    Toast.makeText(this, "Already imported", Toast.LENGTH_LONG).show();
////
////                sp.edit().putBoolean("AlreadyImported", true).commit();
//                    break;
//
//            }
//
//            return true;
//        }


        //ctx = this;

        // TextView message = (TextView) result.findViewById(R.id.textView);

//        Snackbar snackbar = Snackbar.make(findViewById(R.id.textView), "Quiz data", Snackbar.LENGTH_LONG);

        //      snackbar.show();
//        snackbar.setAction(R.string.AndrewStopSearchSnackGo, (t) -> {
//            Intent intent = new Intent(RobinQuiz.this, RobinQuiz.class);
//            intent.putExtra("StopNumber", message.getText());
//            startActivity(intent);
//
//        });

//        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getContext());
//        alertBuilder.setMessage("Stop Number: "+message.getText())
//                .setPositiveButton(R.string.Continue, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Intent intent = new Intent(RobinQuiz.this, RobinQuiz.class);
//                        intent.putExtra("StopNumber", message.getText());
//                        startActivity(intent);
//                    }
//                })
//                .setNegativeButton(R.string.Delete, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        Toast toast = Toast.makeText(RobinQuiz.this, "Placeholder for Delete", Toast.LENGTH_LONG);
//                        toast.show();
//                    }
//                });
//
//
//        AlertDialog alert = alertBuilder.create();
//        alert.show();

//    });
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(RobinQuiz.this, "I was clicked", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }

//    }
//}

