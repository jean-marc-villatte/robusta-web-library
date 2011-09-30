/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.robustaweb.library.commons.util;

import junit.framework.TestCase;

/**
 *
 * @author Moi
 */
public class CoupleListTest extends TestCase {
    
    CoupleList<Integer, String> myList;
       Couple c1,c2,c3,c4,c5;
    Couple c6,c7,c8;
    String mySuperString,myOtherSuperString;
    
    
    public CoupleListTest(String testName) {
        super(testName);
    }            

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myList=new CoupleList<Integer, String>();
        
         mySuperString="2";
        myOtherSuperString="3";
        
        c1=new Couple<Integer, String>(1, "1");
        c2=new Couple <Integer, String> (2, mySuperString);
        c3=new Couple<Integer, String>(3, myOtherSuperString);
        c4=new Couple<Integer, String>(4, "4");
        c5=new Couple<Integer, String>(5, "5");
        c6=new Couple("six", new Float(6.0));
        c7=new Couple(c6, c7);
        c8=new Couple(8, "8");
        
        myList.add(c1);myList.add(c2);myList.add(c3);myList.add(c4);myList.add(c5);
      
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of addCouple method, of class CoupleList.
     */
    public void testAddCouple() {
        assertTrue(myList.contains(c1));
    }

    /**
     * Test of getLeftElement method, of class CoupleList.
     */
    public void testGetLeftElement() {
        assertTrue(myList.getLeftElement(0)==1);
    }

    /**
     * Test of getRightElement method, of class CoupleList.
     */
    public void testGetRightElement() {
        
        assertTrue(myList.getRightElement(0).equals("1"));
    }

    /**
     * Test of getAllLeftElements method, of class CoupleList.
     */
    public void testGetAllLeftElements() {
        assertTrue(myList.getAllLeftElements().size()==5);
        assertTrue(myList.getAllLeftElements().get(1)==2);
    }

    /**
     * Test of getAllRightElements method, of class CoupleList.
     */
    public void testGetAllRightElements() {
    }

    

    /**
     * Test of findMyLovers method, of class CoupleList.
     */
    public void testFindMyLovers() {
        String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.findMyLovers(six).size()==2);
    }

    /**
     * Test of findMyFirstLoveInLeft method, of class CoupleList.
     */
    public void testFindMyFirstLoveInLeft() {
        String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.findMyFirstLoveInLeft(six)==6);
        System.out.println(myList);
    }

    /**
     * Test of findMyFirstLoveInRight method, of class CoupleList.
     */
    public void testFindMyFirstLoveInRight() {
              String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.findMyFirstLoveInRight(seven).equals(six));
    }

    /**
     * Test of findMyFirstLove method, of class CoupleList.
     */
    public void testFindMyFirstLove() {
              String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.findMyFirstLove(six).equals(6));
    }

    /**
     * Test of getHashMap method, of class CoupleList.
     */
    public void testGetHashMap() {
    }

    /**
     * Test of getInvertedCouple method, of class CoupleList.
     */
    public void testGetInvertedCouple() {
        CoupleList<String, Integer> cp=CoupleList.<String, Integer>build("cream",3,"coffee",5,"size",7,"cream",9);
        CoupleList<Integer, String> inverse=CoupleList.<String, Integer>build(3,"cream",5, "coffee",7,"size",9,"cream");

        assertTrue(cp.reverse().toString().equalsIgnoreCase(inverse.toString()) );
        assertTrue(cp.reverse().equals(inverse) );

    }

    /**
     * Test of findWhere method, of class CoupleList.
     */
    public void testFindWhere() {
              String six="6";
        Integer seven=7;
        myList.addCouple(6, six);
        myList.addCouple(seven, six);
        myList.addCouple(seven, "666");
        
        assertTrue(myList.size()==8);
        assertTrue(myList.findWhere(six).get(0).getLeft()==6);
    }

    /**
     * Test of removeWhere method, of class CoupleList.
     */
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

    
    public void testBuilder(){

        CoupleList<String, String> cp=CoupleList.<String, String>build("cream","lavande","coffee","java","size","big","cream","none");
        assertTrue(cp.size()==4 );
        assertTrue(cp.findMyLovers("cream").size()==2 );
        //System.out.println(cp);
    }

}
