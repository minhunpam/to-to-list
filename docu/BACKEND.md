- Which metadata do I want to include in the html head?

## Layout:
- When users access to this website, the page will split into 2 sides:
    1. The left side includes:
        1. Input fields, namely "to-do-list name" and "description" 
        2. A Button to submit the to-do-list
    2. The right side is the list of to-dos
        - Order chronologically based on the creation OR update time ---> implement later!

## Business Logic
- When users hit the "Done" button right below these fields to submit a complete to-do-list
    - By clicking the "Done" button, I will trigger the `POST todos/` endpoint in `TodoController`
    - During this phase, data validation must be performed
    - All the collected data will be transformed to JSON-format so that it could be deserialized to Java objects
    - More specifically:
      - When the "Done" button is clicked, a Post HTTP request is made, which triggers the corresponding function in 
        the controller
        - Jackson converts JSON object into Java DTO (`TodoDTO`) via `@RequestBody`. During this phase validation 
      - The controller then triggers the function from the service
      - The service then triggers the built-in `save()` function of the repository inherited from `JpaRespository`
      - The created to-do will then be saved

## Data validation:
- Use client-side validation that validates the input fields instantly on the frontend
- Use server-side validation that validates request body on the backend (controller)

### Data validation before user clicks the button
- If one of the fields are empty, then:
    - the button is frozen until the data in all fields are valid
    - Character limits for:
        1. "to-do-list name" field is: [1, 100]
        2. "description field" is: [1, 250]
- Data validation should be implemented in both frontend and backend, if it is only implemented in frontend, it can 
  easily be bypassed

