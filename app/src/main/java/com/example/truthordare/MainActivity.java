package com.example.truthordare;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

// TODO::::::: a "#" means this task was completed!

// Making a theme selector, could use a global variable for which content pane to set
//  # Make the background a gradient color scheme instead of just blue, then change buttons
//  # Refactor filling the arrays code. Going to need a method for "one and done" storage for filling the questions
// Will probably need a string builder to delete questions too.
//  # Improve the layout of the "view questions" pages to make it easier for the user to know what they are looking at
//  # Use internal storage to save user input for "Add Question". This will likely require redesigning how the files are read in the first place
// Delete bad questions, edit others
// Maybe should only be writing the new questions to internal storage? No, i think I need to use stringbuilder for delete somehow
// Fix delete outofbounds indexing bug
// Restore all option in case user deletes all questions or something


public class MainActivity extends AppCompatActivity {

    private Button initTruthBtn, initDareBtn, truthBtn, dareBtn, addQuestionBtn, addTruthBtn, addDareBtn, viewDaresHomePageBtn, viewTruthsHomePageBtn, viewTruthsBtn, viewDaresBtn;
    private ImageButton backBtn, addQuestionBackBtn, viewDaresBackBtn, viewTruthsBackBtn;
    private TextView chosenMode, randomPhrase;
    private EditText userQuestion;
    private GameMaker gm;
    private ListView listViewDares, listViewTruths;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gm = new GameMaker(this);
        initWidgets();
    }


    // Initialize the home page buttons.
    private void initWidgets() {
        initTruthBtn = findViewById(R.id.initTruthButton);
        initTruthBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openTruth(0);
            }
        });

        initDareBtn = findViewById(R.id.initDareButton);
        initDareBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                openDare(0);
            }
        });

        addQuestionBtn = findViewById(R.id.goToAddQuestionBtn);
        addQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAddQuestion();
            }
        });
        viewDaresHomePageBtn = findViewById(R.id.viewDareQuestionHomeBtn);
        viewDaresHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewDareQuestions(0);
            }
        });
        viewTruthsHomePageBtn = findViewById(R.id.viewTruthQuestionHomeBtn);
        viewTruthsHomePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewTruthQuestions(0);
            }
        });
    }


    // Initialize the "game page" widgets.
    private void initGamePageButtons() {
        truthBtn = findViewById(R.id.truthButton2);
        truthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTruth(1);
            }
        });
        dareBtn = findViewById(R.id.dareButton2);
        dareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDare(1);
            }
        });
        backBtn = findViewById(R.id.backBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
    }


    // Open the addQuestion screen.
    private void openAddQuestion() {
        setContentView(R.layout.activity_new_question);
        userQuestion = findViewById(R.id.userQuestionEntry);
        addQuestionBackBtn = findViewById(R.id.addQuestionBackBtn);
        addTruthBtn = findViewById(R.id.addToTruthBtn);
        addDareBtn = findViewById(R.id.addToDareBtn);
        addTruthBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userQuestion.getText().toString().length() == 0) {
                    return;
                }
                gm.updateTruthsDB(userQuestion.getText().toString());
                userQuestion.getText().clear();
            }
        });
        addDareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userQuestion.getText().toString().length() == 0) {
                    return;
                }
                gm.updateDaresDB(userQuestion.getText().toString());
                userQuestion.getText().clear();
            }
        });

        addQuestionBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
    }


    // Return to the home screen and re-fill both ArrayLists.
    private void backToHome() {
        setContentView(R.layout.activity_main);
        initWidgets();
        gm.reFillQuestions(this);
        //gm.test();
    }


    // Mode: 0 = home page, 1 = game page
    // Retrieve a truth question and set the new content view
    public void openTruth(int mode) {
        if (mode == 0) {
            setContentView(R.layout.activity_secondary);
        }
        chosenMode = findViewById(R.id.t_or_f);
        randomPhrase = findViewById(R.id.rndmPhrase);
        chosenMode.setText(gm.switchTruth());
        randomPhrase.setText(gm.getTruth());
        initGamePageButtons();
     //   gm.test();
    }


    // Mode: 0 = home page, 1 = game page
    // Retrieve a dare question and set the new content view
    public void openDare(int mode) {
        if (mode == 0) {
            setContentView(R.layout.activity_secondary);
        }
        chosenMode = findViewById(R.id.t_or_f);
        randomPhrase = findViewById(R.id.rndmPhrase);
        chosenMode.setText(gm.switchDare());
        randomPhrase.setText(gm.getDare());
        initGamePageButtons();
        gm.test();
    }


    // Will probably want a different class for the layout maker
    // Mode = 0 means the content view must be changed, 1 means the content view is already the current view.
    private void openViewDareQuestions(int mode) {
        gm.reFillQuestions(this); // ensures previously added questions show up
        if (mode == 0){
            setContentView(R.layout.activity_view_dare_questions);
        }
        viewTruthsBtn = findViewById(R.id.viewTruthQuestions);
        viewTruthsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewTruthQuestions(0);
            }
        });
        viewDaresBackBtn = findViewById(R.id.viewDareQuestionBackBtn);
        viewDaresBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
        listViewDares = findViewById(R.id.dareQuestionsListView);
        DareListViewAdapter dareAdapter = new DareListViewAdapter(gm,this);
        listViewDares.setAdapter(dareAdapter);
    }

    // Mode = 0 means the content view must be changed, 1 means the content view is already the current view.
    private void openViewTruthQuestions(int mode) {
        gm.reFillQuestions(this); // ensures previously added questions show up
        if (mode == 0){
            setContentView(R.layout.activity_view_truth_questions);
        }
        viewDaresBtn = findViewById(R.id.viewDareQuestions);
        viewDaresBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openViewDareQuestions(0);
            }
        });
        viewTruthsBackBtn = findViewById(R.id.viewTruthQuestionBackBtn);
        viewTruthsBackBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });
        listViewTruths = findViewById(R.id.truthQuestionsListView);
        TruthListViewAdapter truthAdapter = new TruthListViewAdapter(gm,this);
        listViewTruths.setAdapter(truthAdapter);
    }


}
