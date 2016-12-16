package iloveyouboss.test.scratch;

import org.fest.assertions.data.Offset;
import org.junit.Test;

import static org.fest.assertions.api.Assertions.assertThat;


public class FestAssertTests {
    @Test
    public void floatAsserts() {
//        assertThat(2.32 * 3).isEqualTo(6.96);
//
//        org.junit.ComparisonFailure:
//        Expected :6.96
//        Actual   :6.959999999999999
        assertThat(2.32 * 3).isEqualTo(6.96, Offset.offset(0.00005));
    }
}
