package iloveyouboss.test.scratch;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.number.IsCloseTo.closeTo;
//// https://mvnrepository.com/artifact/org.hamcrest/hamcrest-all
//testCompile group: 'org.hamcrest', name: 'hamcrest-all', version: '1.3'


public class HamcrestAssertTests {
    @Test
    public void floatAsserts() {
        //assertThat(2.32 * 3, equalTo(6.96));
//        java.lang.AssertionError:
//        Expected: <6.96>
//                but: was <6.959999999999999>
//                Expected :<6.96>
//
//                Actual   :<6.959999999999999>
        assertThat(2.32 * 3, closeTo(6.96, 0.00005));
    }
}
