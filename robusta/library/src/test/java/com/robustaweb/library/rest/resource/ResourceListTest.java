package com.robustaweb.library.rest.resource;


import com.robustaweb.library.rest.resource.ResourceList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author robusta web
 */
public class ResourceListTest {

    ResourceList testList = new ResourceList();
    ResourceImpl r1 = new ResourceImpl(1L);
    ResourceImpl r2 = new ResourceImpl(2L);
    ResourceImpl r3 = new ResourceImpl(3L);
    ResourceImpl r4 = new ResourceImpl(4L);
    ResourceImpl r5 = new ResourceImpl(5L);
    ResourceImpl r6 = new ResourceImpl(6L);
    ResourceImpl r7 = new ResourceImpl(7L);

    public ResourceListTest() {

        testList.add(r1);
        testList.add(r2);
        testList.add(r3);
        testList.add(r4);
        testList.add(r5);
        testList.add(r6);
        testList.add(r7);

    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testGet() {
    }

    @Test
    public void testGetById() {
    }

    @Test
    public void testContainsId() {
    }

    @Test
    public void testReplace() {
        ResourceImpl r8 = new ResourceImpl(8L);
        assertTrue(testList.indexOf(r4) == 3);
        testList.replace(4, r8);
        assertTrue(testList.indexOf(r8) == 3);
        assertFalse(testList.contains(r4));
    }

    @Test
    public void testGetXml() {
    }

    @Test
    public void testGetXmlIds() {
    }

    @Test
    public void testSetIndexes() throws Exception {
    }

    @Test
    public void testArrayConstructor() {

        ResourceList<ResourceImpl, Long> list = new ResourceList<ResourceImpl, Long>(new ResourceImpl[]{r1, r2, r7});
        assertTrue(list.containsId(7L));

    }

  
}

class SuperResource extends ResourceImpl {

    public SuperResource(long id) {
        super(id);
    }
}
