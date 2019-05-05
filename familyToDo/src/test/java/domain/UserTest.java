package domain;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;



public class UserTest {
    User testCase, anotherUser, sameButDifferent;
    UserPreferences pref;
    Todo baseCase;
    
    
    
    @Before
    public void setUp() {
        testCase = new User(1, "testPerson", "doWork", "pass123");     
        anotherUser = new User(2, "testPerson", "doWork2", "pass123");  
        sameButDifferent = new User(3, "testPerson", "doWork", "pass123");  
        pref = new UserPreferences(testCase.getId());
        baseCase = new Todo("task", testCase.getId(), pref);
    }
 
    @Test
    public void testSameUserEquality() {
        assertThat(testCase.equals(testCase), is(true));
    }
    
    @Test
    public void testSameUsernameEquality() {
        assertThat(sameButDifferent.equals(testCase), is(true));
    }
    
    @Test
    public void testUserWithTask() {
        assertThat(testCase.equals(baseCase), is(false));
    }
    
    
    @Test
    public void testDifferentUserInequality() {
        assertThat(testCase.equals(anotherUser), is(false));
    }
    
    
    @Test
    public void testHashCode() {
        assertThat(testCase.hashCode(), is(-1358942586));
    }
}