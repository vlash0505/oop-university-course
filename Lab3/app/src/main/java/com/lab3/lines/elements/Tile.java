package com.lab3.lines.elements;

import android.os.Build;
import androidx.annotation.NonNull;

import java.util.Objects;

public class Tile {
    public int row;
    public int col;

    public Tile(int row, int col) {
        this.col = col;
        this.row = row;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return row == tile.row && col == tile.col;
    }

    @Override
    public int hashCode() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return Objects.hash(row, col);
        }
        return -1;
    }

    @NonNull
    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
