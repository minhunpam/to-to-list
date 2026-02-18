export function setInvalid(inputField, errorField, errorMessage) {
    inputField.classList.add("invalid");
    errorField.textContent = errorMessage;
}

export function setValid(inputField, errorField) {
    if (inputField.classList.contains("invalid")) {
        inputField.classList.remove("invalid");
    }
    errorField.textContent = "";
}