## Important logging info that I should pay attention to when running the Spring application
1. Did the application start successfully?
    - `Started BackendApplication in 2.963 seconds`
2. Which port is server running on?
   - `Tomcat started on port 8080 (http)`
3. Did the database connect successfully?
4. Database information
5. Did JPA repositories load?
   - `Finished Spring Data repository scanning in 30 ms. Found 1 JPA repository interface.`

## Problems with `async` and `DOMContentLoaded`
- When using `async` with `<script>`, browser loads script in parallel -> Executes as soon as it's ready -> Doesn't wait for DOM
---> This leads to Race condition between the HTML and the script

### Problem:
- "When the page is ready, load my todos."
- But the script was not there yet to hear it. Later the script arrived and signaled for ready page, the event had already finished --> Todos didn't load

### Solution:
- Using `defer` --> “Load my script in the background, but don’t run it until the page is fully built.”
- The order becomes safe:
   1. Browser builds page.
   2. Script runs.
   3. Script waits for “page ready”.
   4. Browser announces “page ready”.
   5. Todos load.

