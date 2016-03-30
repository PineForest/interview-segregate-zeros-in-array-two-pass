/*
 * Copyright © 2016  David Williams
 *
 * This file is part of the interview-segregate-zeros-in-array-two-pass project.
 *
 * interview-segregate-zeros-in-array-two-pass is free software: you can redistribute it and/or modify it under the terms of the
 * Lesser GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * interview-segregate-zeros-in-array-two-pass is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the Lesser GNU General Public
 * License for more details.
 *
 * You should have received a copy of the Lesser GNU General Public License along with interview-segregate-zeros-in-array-two-pass.
 * If not, see <a href="http://www.gnu.org/licenses/">www.gnu.org/licenses/</a>.
 */

package interview.practice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * Technical interview exercise. A two pass approach to moving all zeros found in an array of n integers to the front of
 * the array while maintaining the sort order of the non-zero integers.
 * <p/>
 * I wrote the original solution using one pass. The interviewer suggested a two pass approach as being a bit cleaner
 * and no different in order of complexity.
 * <p/>
 * Rules:
 * <ol>
 *     <li>Array contains integers.</li>
 *     <li>Move all of the zeros to the front of the array without changing the order of the non-zero elements.</li>
 *     <li>Do not create a second data structure to hold all of the elements.</li>
 *     <li>Expect the array size to be quite large (n > 1 million entries)</li>
 * </ol>
 * <p/>
 * Algorithm example:
 * <ol>
 *     <li>Starting array: [0, 1, 2, 0, 3, 0, 0, 4], Ending array: [0, 0, 0, 0, 1, 2, 3, 4]</li>
 *     <li>Loop #1: Find the count of zeros in the array - c = 4 and the last 0 in the array, AKA fill point - p = 6</li>
 *     <li>Loop #2: While c > 0, i = p - 1 to 0 = 5 to 0</li>
 *     <li>f(i) -> array[5] == 0 then no swap [0, 1, 2, 0, 3, 0, 0, 4], no change c = 4 and p = 6, set --i = 4</li>
 *     <li>f(i) -> array[4] != 0 then swap array[i] and array[p] [0, 1, 2, 0, 0, 0, 3, 4], set --c = 3, --p = 5</li>
 *     <li>f(i) -> array[3] == 0 then no swap [0, 1, 2, 0, 0, 0, 3, 4], no change c = 3 and p = 5, set --i = 2</li>
 *     <li>f(i) -> array[2] != 0 then swap array[i] and array[p] [0, 1, 0, 0, 0, 2, 3, 4], set --c = 2, --p = 4, set --i = 1</li>
 *     <li>f(i) -> array[1] != 0 then swap array[i] and array[p] [0, 0, 0, 0, 1, 2, 3, 4], set --c = 0, --p = 3, resulting array: [0, 1, 0, 0, 0, 2, 3, 4]</li>
 *     <li>f(i) -> c == 0 so done with resulting array [0, 0, 0, 0, 1, 2, 3, 4]</li>
 * </ol>
 * Loop #1 is O(n) and loop #2 O(n) for performance, resulting in O(n) + O(n) = <b>O(n) combined</b>.
 */
public class SegregateArrayZeros {
    private static void segregate(int[] array) {
        int count = 0;
        int lastZero = -1;
        // Get a count of the zeros in the array and the position of the last zero element
        for (int i = 0; i < array.length; ++i) {
            if (array[i] == 0) {
                ++count;
                lastZero = i;
            }
        }
        // Exit if the array has no zeros or is all zeros
        if (count == 0 || count == array.length) {
            return;
        }
        // Edge case where last zero is the only zero and it is the first element in the array
        if (lastZero == 0) {
            return;
        }
        int current = lastZero - 1;
        // Move the zeros to the start of the array while maintaining ordering for the non-zero elements
        while (current >= 0 && lastZero > count - 1) {
            if (array[current] != 0) {
                array[lastZero] = array[current];
                array[current] = 0;
                --lastZero;
            }
            --current;
        }
    }

    public static void main(String[] args) throws IOException {
        File file = new File(args[0]);
        BufferedReader buffer = new BufferedReader(new FileReader(file));
        String line;
        while ((line = buffer.readLine()) != null) {
            line = line.trim();
            String[] stringArray = line.split(",");
            int[] array = convert(stringArray);
            segregate(array);
            System.out.println(Arrays.toString(array));
        }
    }

    private static int[] convert(String[] stringArray) {
        int[] result = new int[stringArray.length];
        for (int i = 0; i < stringArray.length; ++i) {
            result[i] = Integer.parseInt(stringArray[i]);
        }
        return result;
    }
}
