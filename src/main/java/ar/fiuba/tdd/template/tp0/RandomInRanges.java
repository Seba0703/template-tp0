package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Without line feed 10 13 or 133
 */
class RandomInRanges{

    private final List<Integer> range = new ArrayList<>();

    private boolean isNotLineFeed(int num) {
        return ( num != 10 && num != 13 && num  != 133); //new line, Carriage Return, Next Line
    }

    RandomInRanges() {
        for (int j = 0 ; j <= 255 ; j++) {
            if ( isNotLineFeed(j) ) {
                this.range.add(j);
            }
        }
    }

    public int getRandom() {
        return this.range.get(new Random().nextInt(this.range.size()));
    }
}
