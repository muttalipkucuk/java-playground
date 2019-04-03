# java-playground

## CompletableFuture
`.runAsync(() -> System.out.println("")`
`.supplyAsync(() -> "")`
`.thenApply(x -> x.toString())`
`.thenAccept(x -> System.out.println("x: " + x));`
`.thenRun(() -> System.out.println(""))`
`getUser("").thenCompose(user -> getCredit(user))`
`getWeight().thenCombine(getHeight(), (w, h) -> w/(h*h))`
`CompletableFuture.allOf(getWeight(), getHeight())`
`CompletableFuture.anyOf(getTimeout(1), getTimeout(5), getTimeout(15))`
`.exceptionally(ex -> "result"`
`.handle((res, ex) -> {ex != null? return ex.getMessage() : res;}`
