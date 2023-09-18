import org.apache.commons.collections.list.SynchronizedList;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

public class HippodromeTest {

    @Test
    public void exThrowsWhenNull() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(null));
        assertEquals("Horses cannot be null.", e.getMessage());
    }

    @Test
    public void listEmpty() {
        List<Horse> horses = new ArrayList<>();
        //   horses.add(new Horse("test" , 2 ,2));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Hippodrome(horses));
        assertEquals("Horses cannot be empty.", exception.getMessage());
    }

    @Test
    public void getHorsesTesting() {
        List<Horse> horses = new ArrayList<>();

        for (int i = 1  ; i < 30; i++) {
            horses.add(new Horse(" " + i ,i,i));
        }

        Hippodrome hippodrome = new Hippodrome(horses);

        assertEquals(horses, hippodrome.getHorses());
    }

    @Test
    public  void moveFromAllHorsesTesting (){
        List<Horse> horses = new ArrayList<>();

        for (int i = 1; i < 50 ; i++) {
            horses.add(Mockito.mock(Horse.class));
        }

        Hippodrome hippodrome = new Hippodrome(horses);
        hippodrome.move();

        for (Horse horse: horses){
            Mockito.verify(horse).move();
        }
    }


    @Test
    public void getWinnerTesting(){

        List<Horse> horses = new ArrayList<>();

        horses = List.of(new Horse("test1",3,100), new Horse("test3" , 2, 2),
        new Horse("test4" , 6,200) ,new Horse("test7", 8,1000));


       // Horse horse = horses.stream().max(Comparator.comparing(Horse::getDistance)).get();

        Hippodrome hippodrome = new Hippodrome(horses);

        hippodrome.getWinner();



        assertEquals(horses.get(3), hippodrome.getWinner());

    }
}
