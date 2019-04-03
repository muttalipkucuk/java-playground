package completablefuture;

import lombok.SneakyThrows;
import org.junit.Test;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class Example {

    @Test
    @SneakyThrows
    public void test1() {
        CompletableFuture<String> cf = new CompletableFuture<>();
        cf.complete("test");
        String result = cf.get();    // complete(..) must before get()

        System.out.println("result = " + result);
    }

    @Test
    @SneakyThrows
    public void test2() {
        // runAsync
        // in: ()
        // out: CompletableFuture<Void>
        // example: .runAsync(() -> System.out.println(""))

        CompletableFuture.runAsync(() -> {
            System.out.println("Printed...");
        });
    }

    @Test
    @SneakyThrows
    public void test3() {
        // runAsync
        // in: ()
        // out: CompletableFuture<U>
        // example: .runAsync(() -> "")
        CompletableFuture<String> cf = CompletableFuture.supplyAsync(() -> "result");
        String result = cf.get();

        System.out.println("result = " + result);
    }

    @Test
    @SneakyThrows
    public void test4() {
        // thenApply
        // in: T
        // out: CompletableFuture<U>
        // example: .thenApply(x -> x.toString())

        String result = CompletableFuture
                .supplyAsync(() -> "Muttalip")
                .thenApply(name -> "Hello " + name)
                .thenApply(helloName -> helloName + ", how are you?")
                .get();

        System.out.println("result = " + result);
    }

    @Test
    @SneakyThrows
    public void test5() {
        // thenAccept
        // in: T
        // out: CompletableFuture<Void>
        // example: .thenAccept(x -> System.out.println("x: " + x));

        CompletableFuture
                .supplyAsync(() -> "productName")
                .thenAccept(productName -> System.out.println("Product name: " + productName));
    }

    @Test
    @SneakyThrows
    public void test6() {
        // thenRun()
        // in: ()
        // out: CompletableFuture<Void>
        // example: .thenRun(() -> System.out.println(""))

        CompletableFuture
                .supplyAsync(() -> "productName")
                .thenRun(() -> System.out.println("Product name: "));
    }

    @Test
    @SneakyThrows
    public void test7() {
        // thenCompose
        // in: () -> CompletionStage<U>
        // out: CompletableFuture<U>
        // example: getUser("").thenCompose(user -> getCredit(user))

        Double credit = getUser("1")
                .thenCompose(this::getCredit)
                .get();

        System.out.println("Credit: " + credit);
    }

    private CompletableFuture<String> getUser(String userId) {
        return CompletableFuture.supplyAsync(() -> "User " + userId);
    }

    private CompletableFuture<Double> getCredit(String user) {
        return CompletableFuture.supplyAsync(() -> 50.);
    }

    @Test
    @SneakyThrows
    public void test8() {
        // thenCombine
        // in: CompletionStage<U>, (x, y) -> V
        // out: CompletableFuture<V>
        // example: getWeight().thenCombine(getHeight(), (w, h) -> w/(h*h))

        Double bmi = getWeight()
                .thenCombine(getHeight(), (weight, height) ->
                        weight / (height * height))
                .get();

        System.out.println("BMI: " + bmi);
    }

    private CompletableFuture<Double> getWeight() {
        return CompletableFuture.supplyAsync(() -> 80.);
    }

    private CompletableFuture<Double> getHeight() {
        return CompletableFuture.supplyAsync(() -> 1.85);
    }

    @Test
    @SneakyThrows
    public void test9() {
        // allOf (run parallel, after all of them are completed)
        // in: CompletableFuture<?> ...
        // out: CompletableFuture<Void>

        CompletableFuture.allOf(getWeight(), getHeight())
            .thenRun(() -> System.out.println("Retrieved weight and height!"));
    }

    @Test
    @SneakyThrows
    public void test10() {
        // anyOf (run parallel, after any of them are completed)
        // in: CompletableFuture<?> ...
        // out: CompletableFuture<Object>

        Object lowestTimeout = CompletableFuture.anyOf(
                getTimeout(1),
                getTimeout(5),
                getTimeout(15))
                .get();

        System.out.println("Lowest timeout: " + lowestTimeout);
    }

    private CompletableFuture<Integer> getTimeout(int timeout) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(timeout);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return timeout;
        });
    }

    @Test
    @SneakyThrows
    public void test11() {
        CompletableFuture<String> stringForAge = getStringForAge(-1);
        System.out.println("String for age (with exceptionally): " + stringForAge.get());

        stringForAge = getStringForAge(10);
        System.out.println("String for age (with exceptionally): " + stringForAge.get());

        stringForAge = getStringForAge(50);
        System.out.println("String for age (with exceptionally): " + stringForAge.get());
    }

    private CompletableFuture<String> getStringForAge(Integer age) {
        return CompletableFuture.supplyAsync(() -> {
            if (age < 0)
                throw new IllegalArgumentException("Age can not be negative");

            if(age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        })
        .exceptionally(ex -> {
            System.out.println("Oops! We have an exception - " + ex.getMessage());
            return "Unknown!";
        });
    }

    @Test
    @SneakyThrows
    public void test12() {
        CompletableFuture<String> stringForAge = getStringForAgeWithHandle(-1);
        System.out.println("String for age (with handle): " + stringForAge.get());

        stringForAge = getStringForAgeWithHandle(10);
        System.out.println("String for age (with handle): " + stringForAge.get());

        stringForAge = getStringForAgeWithHandle(50);
        System.out.println("String for age (with handle): " + stringForAge.get());
    }

    private CompletableFuture<String> getStringForAgeWithHandle(Integer age) {
        return CompletableFuture.supplyAsync(() -> {
            if (age < 0)
                throw new IllegalArgumentException("Age can not be negative");

            if(age > 18) {
                return "Adult";
            } else {
                return "Child";
            }
        })
        .handle((res, ex) -> {
            if (ex != null) {
                System.out.println("Oops! We have an exception - " + ex.getMessage());
                return "Unknown!";
            }
            return res;
        });
    }
}
