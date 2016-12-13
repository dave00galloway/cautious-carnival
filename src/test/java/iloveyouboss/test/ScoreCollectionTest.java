package iloveyouboss.test;

import iloveyouboss.ScoreCollection;
import org.junit.Test;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;


public class ScoreCollectionTest {
    @Test
    public void answersArithmeticMeanOfTwoNumbers() throws Exception {
        //Arrange
        ScoreCollection scoreCollection = new ScoreCollection();
        scoreCollection.add(() -> 5);
        scoreCollection.add(() -> 7);

        //Act
        int actualResult = scoreCollection.arithmeticMean();

        //Assert
        assertThat(actualResult, equalTo(6));
    }

}