package day1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Day1 {

    String inputPath;

    public Day1(String inputPath) {
        this.inputPath = inputPath;
    }

    public void solveA() {
        try (var stream = Files.lines(Paths.get(inputPath))) {
            AtomicInteger highest = new AtomicInteger();
            AtomicInteger sum = new AtomicInteger();

            stream.forEach(s -> {
                // double line shift detected
                if (s.length() == 0) {
                    // compare and find highest
                    int currSum = sum.intValue();
                    int currHighest = highest.intValue();

                    System.out.println("cs: " + currSum + " - ch: " + currHighest);

                    if (currSum > currHighest) {
                        highest.set(currSum);
                    }
                    sum.set(0);
                } else {
                    // sum up the calories of the individual elf
                    sum.addAndGet(Integer.parseInt(s));
                }
            });

            System.out.println("Highest: " + highest.intValue());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void solveB() {
        var top3 = new PriorityQueue<Integer>();

        try (var stream = Files.lines(Paths.get(inputPath))) {
            AtomicInteger sum = new AtomicInteger();

            stream.forEach(s -> {
                // double line shift detected
                if (s.length() == 0) {
                    int currSum = sum.intValue();
                    System.out.println("cs - " + currSum);
                    System.out.println("peeked: " + top3.peek());

                    // compare the current sum to all elements in the top3
                    boolean shouldAdd = false;
                    for(int i : top3) {
                        if(currSum >= i) {
                            shouldAdd = true;
                        }
                    }

                    if(shouldAdd || top3.size() == 0) {
                        top3.add(currSum);
                    }

                    if(top3.size() > 3) {
                        top3.remove();
                    }

                    sum.set(0);
                } else {
                    // sum up the calories of the individual elf
                    sum.addAndGet(Integer.parseInt(s));
                }
            });
            int top3Total = top3.stream().mapToInt(Integer::intValue).sum();
            System.out.println("Total of highest 3: " + top3Total);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
