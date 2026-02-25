## Layout:
- 2 sides
1. Left-hand side:
   - User gives inputs there
     - When user unfocus from the fields, the data validation should be triggered immediately and around the fields it 
         glows red and notify the rule for each fields
   - After every input fulfill the requirements, users are able to click the "Done" to submit the to-do
2. Right-hand side:
   - Displays list of to-dos, displaying max. 4 to-dos at a time
      - Even though there are only 4 to-dos showing up at a time from user persepective, it still loads all available to-dos --> eager loading
      ---> PROBLEM: need somehow to enable lazy loading, which means we only load 4 consecutive to-dos + 2 head and tail todos in case the scrolling is half-here-half-there
         - With that, the query needs to be fast enough to reduce the latency
   - Scrollable

## 1. Left-hand side: Input section
## 2. Right-hand side: Display section
### To-do-card:
- Display to-do's title
- Has a delete button
   - Behavior of the delete-button
      - On hover, change background color
      - When users click delete, there will be an emerging alert modal using the built-in tag `<dialog>`
         - "Are you sure to delete this to-do?"
         - Has 2 buttons (Yes | No)
- When the modal is open, everything behind is blurred and scrollbar disappears (`overflow: hidden` - applied on the body)

- When users click to a to-do card, the title and description of the clicked to-do will be written to the `titleInput` and `descriptionInput` and the `doneButton` can only be enabled when the values of `titleInput` and `descriptionInput` are modified
   - IDEA: Each to-do card now has 2 attributes:
      1. `data-current-title`
      2. `data-current-description`
   - On interaction with `titleInput` and `descriptionInput`, it would continuously compare the modified inputs with the original ones