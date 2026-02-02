- Which metadata do I want to include in the html head?

## Layout:
- When users access to this website, the page will split into 2 sides:
    1. The left side is where user can give data in input fields, namely "to-do-list name" and "description"
        - After that, users hit the "Done" button right below these fields to submit a complete to-do-lis
            - By clicking the "Done" button, I will trigger the `POST todos/` 
            - During this phase, data validation must be performed
            - All the collected data will transformed to JSON-format so that it could be deserialized to Java objects

## Data validation:
- Use client-side validation that validates the input fields instantly on the frontend

### Data validation before user clicks the button
- If one of the fields are empty, then:
    - the button is frozen until the data in all fields are valid
    - Character limits for:
        1. "to-do-list name" field is: [0, 100]
        2. "description field is: [0, 250]
    - When user unfocus from the fields, the data validation should be triggered immdiately and around the fields it glows red and notify the rule for each fields
