package com.oreilly.demo.android.pa.uidemo.model.cell;

import com.oreilly.demo.android.pa.uidemo.model.monster.Actor;

/**
 * This class represents a world consisting of a rectangular grid
 * of cells.
 * @see World
 * @see Cell
 */
public class CellWorld implements World {

  private Cell[][] grid;

  /**
   * This constructor builds a <code>CellWorld</code> from a rectangular
   * grid of cells.  It automatically makes horizontally, vertically,
   * and diagonally adjacent cells neighbors of each other.
   */
  public CellWorld(final Cell[][] grid) {

    this.grid = grid;

    int rows = grid.length;
    int cols = grid[0].length;

    for (int i = 0; i < rows; i ++) {
      for (int j = 0; j < cols; j ++) {
        if (i > 0) {
          if (j > 0) grid[i][j].addNeighbor(grid[i-1][j-1]);
          grid[i][j].addNeighbor(grid[i-1][j]);
          if (j < cols - 1) grid[i][j].addNeighbor(grid[i-1][j+1]);
        }
        if (j > 0) grid[i][j].addNeighbor(grid[i][j-1]);
        if (j < cols - 1) grid[i][j].addNeighbor(grid[i][j+1]);
        if (i < rows - 1) {
          if (j > 0) grid[i][j].addNeighbor(grid[i+1][j-1]);
          grid[i][j].addNeighbor(grid[i+1][j]);
          if (j < cols - 1) grid[i][j].addNeighbor(grid[i+1][j+1]);
        }
      }
    }
  }

  /**
   * This method adds an actor to the cell at the given position.
   * It is the responsibility of the caller of this method to
   * make sure that there is space for the actor at the given
   * position.
   * @throws java.lang.InternalError If the thread invoking this method
   * is interrupted
   */
  public void addActor(Actor actor, int xpos, int ypos) {
    try {
      grid[ypos][xpos].enter(actor);
    } catch (InterruptedException e) {
      throw new InternalError();
    }
  }

  public Cell[][] getGrid(){
    return grid;
  }

}