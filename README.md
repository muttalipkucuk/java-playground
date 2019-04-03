# java-playground

## CompletableFuture

### runAsync
`.runAsync(() -> System.out.println("")`

### supplyAsync
`.supplyAsync(() -> "")`

### thenApply
`.thenApply(x -> x.toString())`

### thenAccept
`.thenAccept(x -> System.out.println("x: " + x));`

### thenRun
`.thenRun(() -> System.out.println(""))`

### thenCompose
`getUser("").thenCompose(user -> getCredit(user))`

### thenCombine
`getWeight().thenCombine(getHeight(), (w, h) -> w/(h*h))`

### allOf
`CompletableFuture.allOf(getWeight(), getHeight())`

### anyOf
`CompletableFuture.anyOf(getTimeout(1), getTimeout(5), getTimeout(15))`

### exceptionally
`.exceptionally(ex -> "result"`

### handle
`.handle((res, ex) -> {ex != null? return ex.getMessage() : res;}`
