package com.lab3.lines.elements;

import androidx.annotation.NonNull;

import java.util.Objects;

public class Ball {
    public int color;
    public Tile tile;

    public Ball(int color, Tile tile) {
        this.color = color;
        this.tile = tile;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ball ball = (Ball) o;
        return color == ball.color && tile.equals(ball.tile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(color, tile);
    }

    @NonNull
    @Override
    public String toString() {
        return "Ball{" +
                "color=" + color +
                ", p=" + tile +
                '}';
    }
}
