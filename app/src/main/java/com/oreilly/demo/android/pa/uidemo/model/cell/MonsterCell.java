package com.oreilly.demo.android.pa.uidemo.model.cell;

import com.oreilly.demo.android.pa.uidemo.CellListener;
import com.oreilly.demo.android.pa.uidemo.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.monster.Actor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Semaphore;

import static com.oreilly.demo.android.pa.uidemo.BuildConfig.DEBUG;

/**
 * Created by Sarah on 5/3/2017.
 */
//renamed AbstractCell from ecosystem to provide methods for a monster in a cell.
    //modified to fit our purposes

public class MonsterCell implements Cell {
    /**
     * This class implements a cell with a fixed capacity.
     */

    private static Random random = new Random(System.currentTimeMillis());

    /**
     * The neighboring cells of this cell.
     */
    protected ArrayList<Cell> neighbors = new ArrayList<Cell>();

    /**
     * The current occupants of this cell.
     */
    protected ArrayList<Actor> occupants = new ArrayList<Actor>();

    /**
     * A semaphore to control entry into this limited-capacity cell.
     */
    protected Semaphore sema;
    protected CellListener listener;

    /**
     * Constructs a cell with the given capacity.
     */
    public MonsterCell(int capacity, CellListener listener) {
        sema = new Semaphore(capacity);
        this.listener = listener;
    }

    /**
     * This method adds a neighbor to this cell in a thread-safe manner.
     */
    public synchronized void addNeighbor(Cell cell) {
        neighbors.add(cell);
    }

    /**
     * This method adds an occupant to this cell in a thread-safe manner.
     */
    protected synchronized void addOccupant(Actor actor) {
        occupants.add(actor);
    }

    /**
     * This method removes an occupant from this cell in a thread-safe manner.
     */
    protected synchronized void removeOccupant(Actor actor) {
        occupants.remove(actor);
    }

    /**
     * This method waits for space to open in this cell, if necessary, and then
     * places the actor inside the cell.
     */
    public void enter(Actor actor) throws InterruptedException {
        Cell previous = actor.getCell();
        if (this != previous) {
            // if necessary, wait for space in this cell
            sema.acquire();
            // if the actor was somewhere else before, take them out of there
            if (previous != null) {
                previous.leave(actor);
            }
            // put the actor into this cell
            actor.setCell(this);
            addOccupant(actor);
            // fire event to tell all occupants of this cell about the new arrival
            enterCell(new CellEvent(this, actor));
            //getView().update();
        }
    }

    public void leave(Actor actor) {
        removeOccupant(actor);
        // fire event to tell all occupants of this cell about the departure
        leaveCell(new CellEvent(this, actor));
        // release the space that was occupied by the actor who just left
        sema.release();
        //getView().update();
    }

    /**
     * This method fires an <code>CellEvent.enterCell</code> event
     * to all occupants of this cell.
     */
    @SuppressWarnings("unchecked")
    public void enterCell(final CellEvent event) {
        this.listener.enterCell(event);
        List<Actor> currentOccupants;
        synchronized (this) {
            currentOccupants = (List<Actor>) this.occupants.clone();
        }
        for (Actor a : currentOccupants) {
            a.enterCell(event);
        }
    }

    /**
     * This method fires an <code>CellEvent.leaveCell</code> event
     * to all occupants of this cell.
     */
    @SuppressWarnings("unchecked")
    public void leaveCell(final CellEvent event) {
        this.listener.leaveCell(event);
        List<Actor> currentOccupants;
        synchronized (this) {
            currentOccupants = (List<Actor>) this.occupants.clone();
        }
        for (Actor a : currentOccupants) {
            a.enterCell(event);
        }
    }

    /**
     * This method returns a clone of this cell's neighbors.
     */
    @SuppressWarnings("unchecked")
    public synchronized List<Cell> getNeighbors() {
        return (List<Cell>) neighbors.clone();
    }

    /**
     * This method returns a clone of this cell's occupants.
     */
    @SuppressWarnings("unchecked")
    public synchronized List<Actor> getOccupants() {
        return (List<Actor>) occupants.clone();
    }

    public synchronized Cell randomNeighbor() {
        int size = neighbors.size();
        return size == 0 ? null : (Cell) neighbors.get(random.nextInt(size));
    }
}