package ch.bbw.m411.connect4;

public enum Stone {
    RED, BLUE;

    public Stone opponent() {
        return this == RED ? BLUE : RED;
    }
}
