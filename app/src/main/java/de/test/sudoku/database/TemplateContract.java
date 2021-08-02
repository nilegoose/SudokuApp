package de.test.sudoku.database;

import android.provider.BaseColumns;

public final class TemplateContract {
    private TemplateContract(){}

    public static class FeedEntry implements BaseColumns{
        public static final String TABLE_NAME = "sudoku_template";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_LIST = "gameState";
        public static final String COLUMN_NAME_INI = "initial";
    }
}
