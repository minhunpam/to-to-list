const titleInput = document.getElementById("titleInput");
const descriptionInput = document.getElementById("descriptionInput");
const doneButton = document.getElementById("doneButton");

const titleError = document.getElementById("titleError");
const descriptionError = document.getElementById("descriptionError");

const FIELD_CANNOT_EMPTY_NOTI = "Field cannot be empty!";

doneButton.addEventListener("click", async () => {
    const data = {
        title: titleInput.value,
        description: descriptionInput.value,
    };
    
    const jsonData = JSON.stringify(data, null, 2);
    console.log(jsonData);
    
    try {
        const response = await fetch("http://localhost:8080/todos", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: jsonData
        });

        if (!response.ok) {
            throw new Error("failed to create todo");
        }

        console.log("Todo created successfully!");

    } catch (error) {
        console.log("Error: ", error);
    }
    
});

titleInput.addEventListener("blur", validateTitle);
descriptionInput.addEventListener("blur", validateDescription);

titleInput.addEventListener("input", updateButtonState);
descriptionInput.addEventListener("input", updateButtonState);


function validateTitle() {
    const value = titleInput.value.trim();

    if (value.length == 0) {
        setInvalid(titleInput, titleError, FIELD_CANNOT_EMPTY_NOTI);
        return false;
    }

    if (value.length > 100) {
        setInvalid(titleInput, titleError, "Title field must be less than 100 characters");
        return false;
    }

    setValid(titleInput, titleError);

    return true;
}

function validateDescription() {
    const value = descriptionInput.value.trim();

    if (value.length == 0) {
        setInvalid(descriptionInput, descriptionError, FIELD_CANNOT_EMPTY_NOTI);
        return false;
    }

    if (value.length > 250) {
        setInvalid(descriptionInput, descriptionError, "Description field must be less than 250 characters");
        return false;
    }

    setValid(descriptionInput, descriptionError);

    return true;

}

function updateButtonState() {
    const isValid = validateTitle() && validateDescription();

    doneButton.disabled = !isValid;
}

function setInvalid(inputField, errorField, errorMessage) {
    inputField.classList.add("invalid");
    errorField.textContent = errorMessage;
}

function setValid(inputField, errorField) {
    if (inputField.classList.contains("invalid")) {
        inputField.classList.remove("invalid");
    }
    errorField.textContent = "";
}

