# java-playground

## CompletableFuture

### RunAsync
`.runAsync(() -> System.out.println("")`

### RunAsync
`.supplyAsync(() -> "")`

### RunAsync
`.thenApply(x -> x.toString())`

### RunAsync
`.thenAccept(x -> System.out.println("x: " + x));`

### RunAsync
`.thenRun(() -> System.out.println(""))`

### RunAsync
`getUser("").thenCompose(user -> getCredit(user))`

### RunAsync
`getWeight().thenCombine(getHeight(), (w, h) -> w/(h*h))`

### RunAsync
`CompletableFuture.allOf(getWeight(), getHeight())`

### RunAsync
`CompletableFuture.anyOf(getTimeout(1), getTimeout(5), getTimeout(15))`

### RunAsync
`.exceptionally(ex -> "result"`

### RunAsync
`.handle((res, ex) -> {ex != null? return ex.getMessage() : res;}`
