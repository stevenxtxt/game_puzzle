package com.xute.game_puzzle;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.widget.TextView;

import com.xute.game_puzzle.view.GamePuzzleLayout;
import com.xute.game_puzzle.view.GamePuzzleLayout.GamePuzzleListener;

public class MainActivity extends Activity {

    private GamePuzzleLayout mPuzzleLayout;
    private TextView mLevel;
    private TextView mTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mLevel = (TextView) findViewById(R.id.id_level);
        mTime = (TextView) findViewById(R.id.id_time);

        mPuzzleLayout = (GamePuzzleLayout) findViewById(R.id.id_puzzle);
        mPuzzleLayout.setTimeEnabled(true);
        mPuzzleLayout.setGamePuzzleListener(new GamePuzzleListener() {

            @Override
            public void timechanged(int currentTime) {
                // TODO Auto-generated method stub
                mTime.setText("" + currentTime);
            }

            @Override
            public void nextlevel(final int nextLevel) {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(MainActivity.this).setTitle("Game Info").setMessage("Level Up!!!")
                        .setPositiveButton("NEXT LEVEL", new OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                mPuzzleLayout.nextLevel();
                                mLevel.setText("" + nextLevel);
                            }
                        }).show();
            }

            @Override
            public void gameover() {
                // TODO Auto-generated method stub
                new AlertDialog.Builder(MainActivity.this).setTitle("Game Info").setMessage("Game Over!!!")
                .setPositiveButton("RESTART", new OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        mPuzzleLayout.nextLevel();
                    }
                }).setNegativeButton("QUIT", new OnClickListener() {
                    
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        finish();
                    }
                }).show();
            }
        });
    }

}
