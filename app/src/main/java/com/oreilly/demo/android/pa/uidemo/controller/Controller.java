package com.oreilly.demo.android.pa.uidemo.controller;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.ModelListener;
import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.DefaultMonsterModel;
import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.cell.CellEvent;
import com.oreilly.demo.android.pa.uidemo.model.cell.MonsterCell;
import com.oreilly.demo.android.pa.uidemo.model.monster.Actor;
import com.oreilly.demo.android.pa.uidemo.model.monster.Monster;
import com.oreilly.demo.android.pa.uidemo.view.DefaultMonsterView;

import java.util.Random;
import android.widget.Toast;


/**
 * Created by Sarah on 5/3/2017.
 */

/*
* This is the controller. Controller will see events from the model and view and relay them correspondingly
* to the model and the view by extension. Controller also will be responsible for lifecycle methods.
* */

public class Controller extends Activity implements ModelListener, ViewListener {

    private int width;
    private int height;
    private DefaultMonsterView monsterview;
    private DefaultMonsterModel monstermodel;
    UIUpdateThread UIupdater;

    /* onCreate
    1. Get display dimensions and derive grid width&height
	2. Initialize the model
	3. create actors and populate world in the model
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        monsterview = (DefaultMonsterView) findViewById(R.id.Monster);

        //making grid based on display dimensions
        final Point displayDimensions = new Point();
        getWindowManager().getDefaultDisplay().getSize(displayDimensions);
        width = displayDimensions.x / Constants.CELL_SIZE;
        height = (displayDimensions.y - Constants.VIEW_HEIGHT_MINUS) / Constants.CELL_SIZE;
        Cell[][] cells = new MonsterCell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                cells[i][j] = new MonsterCell(1, this);
            }
        }

        // Initialize the model
        monstermodel = new DefaultMonsterModel(cells, this);

        // create actors and populate world in the model
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < Constants.MONSTER_COUNT; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Actor monster = new Monster();
            ((Monster) monster).setMonsterStateChangeListener(this);
            monstermodel.addLiveActor(monster);
            monstermodel.addActor(monster, y, x);
        }

        // Dependency injection on model
        monstermodel.setModelListener(this);

        // Dependency injection on view
        monsterview.setModel(monstermodel);
        monsterview.setOnTouchListener(this);

        monsterview.inIt(cells, Constants.CELL_SIZE);

        // start the initial actors
        for (Actor a : monstermodel.getLiveActors()) {
            a.start();
        }

        UIupdater = new UIUpdateThread(monsterview);
        (new Thread(UIupdater)).start();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        Cell[][] grid = monstermodel.getWorld().getGrid();

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();
            int y = (int) event.getY();
            int gridX = x / Constants.CELL_SIZE;
            int gridY = y / Constants.CELL_SIZE;
            if (gridX >= width || gridY >= height) {
                return false;
            }
            Cell cell = grid[gridX][gridY];
            int count = cell.getOccupants().size();
            if (count > 0) {
                Monster monster = (Monster) cell.getOccupants().get(0);
                if (!monster.isProtected()) {
                    monstermodel.removeLiveActor(monster);
                    monster.kill();
                    return true;
                }
            }

        }

        return false;
}

    @Override
    public void onMSChange(int stateId) {

    }

    @Override
    public void onChangeModel() {

    }

    @Override
    public void enterCell(CellEvent event) {

    }

    @Override
    public void leaveCell(CellEvent event) {

    }

}
