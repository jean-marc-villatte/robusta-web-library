/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.commons.util;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

/**
 *
 * @author Moi
 */
public class CoupleListTest {
    
    CoupleList<Integer, String> myList;
       Couple c1,c2,c3,c4,c5;
    Couple c6,c7,c8;
    String twoString, threeString;
    



    @Before
    protected void setUp() throws Exception {

        myList=new CoupleList<Integer, String>();
        
         twoString ="2";
        threeString ="3";
        
        c1=new Couple<Integer, String>(1, "1");
        c2=new Couple <Integer, String> (2, twoString);
        c3=new Couple<Integer, String>(3, threeString);
        c4=new Couple<Integer, String>(4, "4");
        c5=new Couple<Integer, String>(5, "5");
        c6=new Couple("six", new Float(6.0));
        c7=new Couple(c6, c7);
        c8=new Couple(8, "8");
        
        myList.add(c1);myList.add(c2);myList.add(c3);myList.add(c4);myList.add(c5);
      
    }


    @Test
    public void testAddCouple() {
        assertTrue(myList.contains(c1));
    }

    @Test
    public void testGetLeftElement() {
        assertTrue(myList.getLeftElement(0)==1);
    }

    @Test
    public void testGetRightElement() {
        
        assertTrue(myList.getRightElement(0).equals("1"));
    }

    @Test
    public void testGetAllLeftElements() {
        assertTrue(myList.getAllLeftElements().size()==5);
        assertTrue(myList.getAllLeftElements().get(1)==2);
    }

    @Test
    public void testGetAllRightElements() {
        assertTrue(myList.getAllRightElements().size()==5);
        assertTrue(myList.getAllRightElements().get(1).equals(threeString));
    }


    @Test
    public void testMatchers() {
        String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.matchers(six).size()==2);
    }

    @Test
    public void testLeftMatcher() {
        String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.leftMatcher(six)==6);
        System.out.println(myList);
    }

    @Test
    public void testRightMatcher() {
              String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.rightMatcher(seven).equals(six));
    }

    @Test
    public void testFirstMatcher() {
              String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.firstMatcher(six).equals(6));
    }

    @Test
    public void testGetHashMap() {
    }

    @Test
    public void testGetInvertedCouple() {
        CoupleList<String, Integer> cp=CoupleList.<String, Integer>build("cream",3,"coffee",5,"size",7,"cream",9);
        CoupleList<Integer, String> inverse=CoupleList.<String, Integer>build(3,"cream",5, "coffee",7,"size",9,"cream");

        assertTrue(cp.reverse().toString().equalsIgnoreCase(inverse.toString()) );
        assertTrue(cp.reverse().equals(inverse) );

    }

    @Test
    public void testFindWhere() {
              String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.findWhere(six).get(0).getLeft()==6);
    }

    @Test
    public void testRemoveWhere() {
              String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        myList.removeWhere(six);
        assertTrue(myList.size()==6);
        myList.removeWhere(seven);
        assertTrue(myList.size()==5);
        
        
    }

    @Test
    public void testBuilder(){

        CoupleList<String, String> cp=CoupleList.<String, String>build("cream","lavande","coffee","java","size","big","cream","none");
        assertTrue(cp.size()==4 );
        assertTrue(cp.matchers("cream").size()==2 );
    }

    @Test
    public void testJoin(){
        String result = CoupleList.build("jo", "two", 2, 4, 5, "two").join("=", "&&&");
        assertEquals("jo=two&&&2=4&&&5=two", result);
    }

}
