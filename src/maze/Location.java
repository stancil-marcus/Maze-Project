/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package maze;

/**
 *
 * @author stanc
 */
public class Location {
        private int row;
        private int col;
        /**
         * Default constructor
         */
        public Location()
        {
            row = 0;
            col = 0;
        }
        /**
         * Alternate constructor
         * @param i row
         * @param j column
         */
        public Location(int i, int j)
        {
            row = i;
            col = j;
        }
        /**
         * Returns row
         * @return row
         */
        int getRow()
        {
            return row;
        }
        /**
         * Returns column
         * @return col
         */
        int getCol()
        {
            return col;
        }
        /**
         * Sets the row.
         * @param row1 row
         */
        void setRow(int row1)
        {
            row = row1;
        }
        /*Sets the column
         * 
         * @param col1 col
         */
        void setCol(int col1)
        {
            col = col1;
        } 
}
