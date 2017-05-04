package com.oreilly.demo.android.pa.uidemo.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.oreilly.demo.android.pa.uidemo.MonsterModel;
import com.oreilly.demo.android.pa.uidemo.R;
import com.oreilly.demo.android.pa.uidemo.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.monster.Monster;

import static com.oreilly.demo.android.pa.uidemo.common.Constants.CELL_SIZE;
import static com.oreilly.demo.android.pa.uidemo.common.Constants.VIEW_HEIGHT_MINUS;

/**
 * Created by Sarah on 5/3/2017.
 */

/*
* This is the default view for the monsters, accesses the protected state image and the
* vulnerable state image. Draws image accordingly.
* */
public class DefaultMonsterView extends View {

    Context context;
    MonsterModel monstermodel;
    Paint paint;
    Bitmap bitmap;
    int cellsize;
    int displayheight;
    int displaywidth;
    int gridwidth;
    int gridheight;


    public DefaultMonsterView(Context context, AttributeSet attributes) {
        super(context, attributes);
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setStyle(Paint.Style.STROKE);
        displayheight = context.getResources().getDisplayMetrics().heightPixels;
        displaywidth = context.getResources().getDisplayMetrics().widthPixels;
        gridwidth = displaywidth/ CELL_SIZE;
        gridheight = (displayheight- VIEW_HEIGHT_MINUS)/CELL_SIZE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Bitmap bitmapp = BitmapFactory.decodeResource(getResources(), R.drawable.green);
        Bitmap bitmapv = BitmapFactory.decodeResource(getResources(), R.drawable.yellow);
        Cell[][] grid = monstermodel.getWorld().getGrid();
        for (int i = 0; i < gridwidth; i++) {
            for (int j = 0; j < gridheight; j++) {
                Cell cell = grid[i][j];
                if (cell.getOccupants().size() > 0) {
                    if (cell.getOccupants().size() <= 0) return;

                    Monster m = (Monster) cell.getOccupants().get(0);
                    if (m.isProtected()) {
                        //if monster is in protected state access protected image
                        bitmap = bitmapp;
                    } else {
                        //otherwise access vulnerable picture
                        bitmap = bitmapv;
                    }
                    canvas.drawBitmap(bitmap, i*cellsize, j*cellsize, paint);
                }

                //Making grid visible here using drawRect()
                canvas.drawRect(i * cellsize,
                        j * cellsize,
                        i * cellsize + cellsize,
                        j * cellsize + cellsize,
                        paint);
            }
        }
    }

    public void setModel(MonsterModel model) { this.monstermodel = model; }

    public void inIt(Cell[][] cells, int cellSize) {
        gridwidth = cells.length;
        gridheight = cells[0].length;
        this.cellsize = cellSize;
    }


}
