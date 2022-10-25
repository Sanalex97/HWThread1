import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        String[] texts = new String[25];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("aab", 30_000);
        }

        long startTs = System.currentTimeMillis(); // start time

        List<FutureTask<Integer>> threads = new ArrayList<>();

        for (String text : texts) {
            MyCallable callable1 = new MyCallable(text);
            FutureTask<Integer> integerFutureTask = new FutureTask<>(callable1);
            new Thread(integerFutureTask).start();
            threads.add(integerFutureTask);
        }
        for (FutureTask<Integer> futureTask : threads) {
            for (String text : texts) {
                System.out.println(text.substring(0, 100) + " -> " + futureTask.get());
            }
        }

        long endTs = System.currentTimeMillis(); // end time

        System.out.println("Time: " + (endTs - startTs) + "ms");
    }

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }
}