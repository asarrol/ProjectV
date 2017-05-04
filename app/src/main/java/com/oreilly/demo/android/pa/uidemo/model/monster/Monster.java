package com.oreilly.demo.android.pa.uidemo.model.monster;

import com.oreilly.demo.android.pa.uidemo.MonsterStateListener;
import com.oreilly.demo.android.pa.uidemo.common.Constants;
import com.oreilly.demo.android.pa.uidemo.model.cell.Cell;
import com.oreilly.demo.android.pa.uidemo.model.cell.CellEvent;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static com.oreilly.demo.android.pa.uidemo.BuildConfig.DEBUG;

/**
 * Created by Sarah on 5/2/2017.
 */

//This class is derived from LiveActor in ecosystem

public class Monster implements Runnable, Actor, MonsterStateTransitions {

    private MonsterStateListener monsterstatelistener;
    private protectedState PROTECTED = new protectedState(this);
    private vulnerableState VULNERABLE = new vulnerableState(this);

    MonsterState monsterstate;

    /**
     * The cell this actor is currently occupying.
     */
    private Cell currentCell;

    /**
     * The live thread of this actor.  This thread runs the <code>run()</code>
     * method implemented by concrete subclasses of this class.
     */
    private ExecutorService liveThread;

    /**
     * The work thread of this actor.  This thread handles requests, usually
     * coming from the live thread, to do some work, such as moving to another cell.
     * It is necessary to use a separate thread for this because an attempt to
     * enter another cell might block.  For example, without using a separate
     * work thread, this can cause deadlock
     * if two actors are trying to move into each other's cell of capacity one.
     * A separate work thread also allows an actor to change their mind
     * if another task of higher priority should take precedence over
     * a task on which the work thread is currently working.
     */
    private ExecutorService workThread;

    /**
     * The destination of the most recent attempt to move.
     */
    private Future task = null;

    /**
     * This method indicates whether this actor is still alive.
     */
    protected synchronized boolean isAlive() { return liveThread != null; }

    /**
     * This method brings this actor to life by starting its internal threads.
     */
    public synchronized void start() {
        if (! isAlive()) {
            liveThread = Executors.newFixedThreadPool(1);
            workThread = Executors.newFixedThreadPool(1);
        }
        liveThread.execute(this);
    }

    /**
     * This method kills this actor by stopping its internal threads.
     */
    public synchronized void kill() {
        if (isAlive()) {
            //liveThread.shutdown();
            //workThread.shutdown();
            //liveThread = null;
            //workThread = null;
            die();
        }
    }

    public synchronized void setCell(Cell cell) { currentCell = cell; }

    public synchronized Cell getCell() { return currentCell; }

    /**
     * This method is used to schedule the runnable for execution by this
     * actor.  If the actor is still waiting for a previously scheduled
     * runnable to execute, then this invocation preempts the previous one.
     */
    protected synchronized void execute(Runnable runnable) {
        if (task != null && !task.isDone()) {
            task.cancel(true);
        }
        task = workThread.submit(runnable);
    }

    /**
     * This method removes this dead actor from the cell it occupied.
     */
    protected synchronized void die() {
        Cell cell = getCell();
        setCell(null);
        cell.leave(this);
    }

    //constructor for Monster
    public Monster () { this.monsterstate = PROTECTED; }

    /*
    * Need a runnable to change state*/
    private Runnable changeState = new Runnable() {
        public void run() {
            changeState();
        }
    };

    /*
    * Need a runnable to move around*/
    private Runnable move = new Runnable() {
        public void run() {
            try {

                getCell().randomNeighbor().enter(Monster.this);

            } catch (InterruptedException e) {
                // if interrupted before entering the cell, then set interrupted flag
                // so that the worker thread can detect this
                Thread.currentThread().interrupt();
            }
        }
    };

    /*
    private Runnable newMonster = new Runnable() {
        public void run() {
            try {

            } catch (InterruptedException e) {
                // if interrupted before entering the cell, then set interrupted flag
                // so that the worker thread can detect this
                Thread.currentThread().interrupt();
            }
        }
    };
    */

    private static Random random = new Random();

    void changeState(){
        random = new Random();
        int k = random.nextInt(100);
        if(k%2 == 0){
            return;
        }
        else {
            if (monsterstate == PROTECTED){
                toVulnerableState();
            }
            else{
                toProtectedState();
            }
        }
    }

    public void run() {
        while (! Thread.interrupted()) {
            try {
                //schedule a change of monster state for execution
                // schedule a move for execution
                execute(changeState);
                Thread.sleep(2000);
                execute(move);
                Thread.sleep(600);

            } catch (InterruptedException exc) {
                Thread.currentThread().interrupt();
            }
        }
    }

    public void setMonsterStateChangeListener(MonsterStateListener listener){
        this.monsterstatelistener = listener;
    }

    public void setstate(MonsterState state){
        this.monsterstate = state;
    }

    public boolean isProtected(){
        if (monsterstate.getid()== Constants.STATE_PROTECTED ) {
            return true;
        }
        return false;
    }

    @Override
    public void init() { toProtectedState(); }

    @Override
    public void toVulnerableState(){
        setstate(VULNERABLE);
    }

    @Override
    public void toProtectedState(){
        setstate(PROTECTED);
    }

    @Override
    public void enterCell(CellEvent event){
        monsterstatelistener.onMSChange(monsterstate.getid());
    }

    @Override
    public void leaveCell(CellEvent event){
        monsterstatelistener.onMSChange(monsterstate.getid());
    }
}
