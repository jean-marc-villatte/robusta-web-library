/*
 * Copyright 2007-2011 Nicolas Zozol
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.robustaweb.library.commons.util;

/**
 * This class is designed to simplify Numbers representation in HTML views. <p>Be aware that it's not time efficiency, not designed for complex Math</p>.
 * @author Nicolas Zozol for Robusta Web ToolKit & http://www.edupassion.com - nzozol@robustaweb.com
 */
public class MathUtils {

    /**
     *  Numbers will be equals if enough decimals are equals, without thinking about approximations. This is absolutely not a pure mathematic Equality.
     * <p>
     * approximativelyEquals(1.2352, 1.2357, 3) returns true because there is no rounding at the 3th decimal
     * </p>
     *
     * @param n1 first numbre
     * @param n2 second number
     * @param decimalPrecision
     * @return true if n1 is approximatively equal to n2
     */
    public static boolean approximativelyEquals(Number n1, Number n2, int decimalPrecision) {


        if (n1.toString().equals(n2.toString())) {
            return true;
        }

        String s1 = n1.toString();
        String s2 = n2.toString();

        s1 = StringUtils.removeTrailingCharacters(s1, '0');
        s2 = StringUtils.removeTrailingCharacters(s2, '0');



        if (!s1.contains(".") && !s2.contains(".")) {
            return s1.equals(s2);
        }

        int seriousNumber;
        if (s1.contains(".")) {
            seriousNumber = s1.indexOf('.');
        } else {//s2.contains(".")
            seriousNumber = s2.indexOf('.');
        }

        /* checking before */
        if (s1.substring(0, seriousNumber).equals(s2.substring(0, seriousNumber))) {

            s1 = s1.substring(seriousNumber);
            s1 = StringUtils.removeCharacter(s1, '.');
            s2 = s2.substring(seriousNumber);
            s2 = StringUtils.removeCharacter(s2, '.');

            /* truncation */
            String t1 = StringUtils.truncate(s1, decimalPrecision);
            String t2 = StringUtils.truncate(s2, decimalPrecision);
            t1 = StringUtils.removeTrailingCharacters(t1, '0');
            t2 = StringUtils.removeTrailingCharacters(t2, '0');
            return t1.equals(t2);


        } else {
            return false;
        }
    }

    /**
     * <p>
     * Returns a visual approximation of a number, keeping only a specified decimals. <br/>
     * For exemple : <br/>
     *    &nbsp; approximate(12.2578887 , 2) will return 12.25 <br/>
     *    &nbsp; approximate(12.25 , 0) will return 12 <br/>
     *    &nbsp; approximate(12.00 , 3) will return 12 <br/>
     *    &nbsp; approximate(19.5 , 0) will return 20 <br/>
     *</p>
     * <p>The last exemple emphasis the fact that it's made for showing convenient numbers for no mathematical public. If the number was</p>
     * <p>Note that Math.round(19.5) returns 20, not 19. This function will act the same way.</p>
     * @param n
     * @param precision
     * @return
     */
    public static String approximate(Number n, int precision) {


        if (n instanceof Integer) {
            return n.toString();
        }



        String s = n.toString();

        if (!s.contains(".")) {
            return s;
        }

        String serious = s.substring(0, s.indexOf('.'));

        s = s.substring(s.indexOf('.') + 1);
        s = StringUtils.removeTrailingCharacters(s, '0');

        if (s.length() == 0) {
            return serious;
        }


        // We'll comments results based on approximate(12.63645, 3) and approximate(12.63545, 0)
        String decimals = "";

        if (s.length() > precision) {


            decimals = StringUtils.truncate(s, precision + 1);
            //decimal is now "636" or "6"

            Float after = new Float(decimals);
            //after is 636 or 6, as Float objects
            after = after / 10;
            //after is 63.6 or .6, as Float objects
            Integer round = Math.round(after);
            //round is 64 or 1
            decimals = round.toString();
            decimals = StringUtils.removeTrailingCharacters(decimals, '0');

        } else {

            decimals = StringUtils.truncate(s, precision);
            decimals = StringUtils.removeTrailingCharacters(decimals, '0');

        }



        if (decimals.length() > 0 && precision > 0) {
            return serious + "." + decimals;
        } else {
            //if we must round the serious string
            if (decimals.length() > 0 && precision == 0) {
                assert decimals.length() == 1 : "problem here !";
                if (decimals.length() != 1) {
                    throw new IllegalStateException("Error in the algorithm for MathUtilisties.approximate(" + n + ", " + precision + ")");
                }
                Integer finalValue = new Integer(decimals);
                if (finalValue > 0) {
                    Integer si = new Integer(serious);
                    si += 1;
                    return si.toString();
                } else {
                    return serious;
                }
            } else {
                return serious;
            }
        }
    }

    /**
     * Convert a String in Double, Float, Integer, Long, Short or Byte, depending on the T exemple
     * For exemple convert("2", 0f) will return 2.0 as a float
     * @param <T> type returned
     * @param str String to convert
     * @param exemple exemple of the type
     * @return a number representation of the String
     * @throws IllegalArgumentException if the T type is not Double, Float, Integer, Long, Short or Byte
     * @throws NumberFormatException if the conversion fails
     */
    public static <T extends Number> T convert(String str, T exemple) throws NumberFormatException, IllegalArgumentException{

        T result = null;
        if (exemple instanceof Double) {
            result = (T) new Double(str);
        } else if (exemple instanceof Float) {
            result = (T) new Float(str);
        } else if (exemple instanceof Integer) {
            result = (T) new Integer(str);
        } else if (exemple instanceof Long) {
            result = (T) new Long(str);
        } else if (exemple instanceof Short) {
            result = (T) new Short(str);
        } else if (exemple instanceof Byte) {
            result = (T) new Byte(str);
        }else{
            throw new IllegalArgumentException("Conversion is not possible with class "+exemple.getClass()
                    +"; only allowing Double, Float, integer, Long, Short & Byte");
        }

        return result;

    }
  
}
