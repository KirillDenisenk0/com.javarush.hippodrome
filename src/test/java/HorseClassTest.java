import net.bytebuddy.implementation.bytecode.Throw;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;

import java.lang.reflect.Field;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class HorseClassTest {

    @Test
    public void constructorThrowEx() {
        assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 1));
    }

    @Test
    public void trueMessageFromEx() {
        /*IllegalArgumentException illegalArgumentException = assertThrows(IllegalArgumentException.class, () -> new Horse(null, 1, 2));
        assertEquals("Name cannot be null.", illegalArgumentException.getMessage());
        */

        try {
            new Horse( null,1,1);

        } catch ( IllegalArgumentException e){
            assertEquals("Name cannot be null." , e.getMessage());
        }
    }

    @ParameterizedTest
   // @MethodSource("argsForTestMethod")
    @ValueSource(strings = { "" , " ", "    " , " \t\t " , "\n\n\n\n\n\n"})
    public  void varParamToConstructor(String s){

        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse(s, 1, 1));
        assertEquals("Name cannot be blank.", e.getMessage());
    }

    static Stream<String> argsForTestMethod (){
        return Stream.of(""," ", "    ");
    }

    @Test
    public void negNumberInSecondParam(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("Mahadi", -1, 2));
        assertEquals("Speed cannot be negative.", e.getMessage());
    }

    @Test
    public void negNumberInThirdParam(){
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Horse("Mahadi", 2, -2));

        assertEquals("Distance cannot be negative.", e.getMessage());
    }
    @Test
    public void getNameTesting(){
        String name = new String("Mahadi");

            Horse horse = new Horse(name, 2, 2);

        assertEquals(name , horse.getName());
    }

    @Test
    public  void speedMethodTesting(){
        double d = 2;

        Horse horse = new Horse("test", d, 3);
        assertEquals(d, horse.getSpeed());
    }

    @Test
    public void getDistanceTesting(){

        double d = 2 ;
        Horse horse = new Horse("test", 2, d);
        assertEquals(d,horse.getDistance());
        Horse horse1 = new Horse("еуые", 2);
        assertEquals(0, horse1.getDistance());
    }


}

@ExtendWith(MockitoExtension.class)
class mockitoTest{

    @Test
    public void methodMoveTesting(){


        try(MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)){
         Horse   horse = new Horse("test" , 200 ,20);
            horse.move();
            mockedStatic.verify(() -> horse.getRandomDouble(Mockito.anyDouble(), Mockito.anyDouble()));
        }
    }

    @Test
    public void methodGetDistance_GiveTrueValue(){
       Horse horse = new Horse("test" , 2 , 2) ;

        double expected = 2;

        OngoingStubbing<Double> doubleOngoingStubbing = Mockito.when(horse.getDistance()).thenReturn(0.5);

        assertEquals(2, doubleOngoingStubbing);
    }

   @ParameterizedTest
    @ValueSource(doubles = {0.1 , 0.2 , 0.5 ,0.9,})
    public void trueValueFromGetRandom(Double random){

        try(MockedStatic<Horse> mockedStatic = Mockito.mockStatic(Horse.class)){
            Horse horse = new Horse("test" , 2,2);

            mockedStatic.when(() -> Horse.getRandomDouble(0.2 , 0.9)).thenReturn(random) ;

            horse.move();

            assertEquals( 2 + 2 * random , horse.getDistance() );
        }
   }

}
