package ar.fiuba.tdd.template.tp0;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
Without 0 to 31 (new line, bell, Carriage Return, etc) and 127 to 159 (java handles Unicode like
Start of String, Set Transmit State, etc)
 */
class RandomInRanges {
    static final int[] firstRange = {32, 127};
    static final int[] secondRange = {160, 256};
    static final int [][] ranges = {firstRange,secondRange};
    private final List<Integer> range = new ArrayList<>();

    RandomInRanges(){
        for(int j = 0 ; j <= 1 ; j++) {
            for (int num = ranges[j][0]; num < ranges[j][1]; num++) {
                this.range.add(num);
            }
        }
    }

    int getRandom() {
        return this.range.get(new Random().nextInt(this.range.size()));
    }
}
