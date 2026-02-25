import { 
    setInvalid,
    setValid, 
} from "./helper.js"

const FIELD_CANNOT_EMPTY_NOTIFICATION = "Field cannot be empty!";
const MAX_TITLE_LENGTH = 100;
const MAX_DESCIPTION_LENGTH = 250;

const titleInput = document.getElementById("titleInput");
const descriptionInput = document.getElementById("descriptionInput");
const doneButton = document.getElementById("doneButton");
const todoList = document.getElementById("todoList");

const titleError = document.getElementById("titleError");
const descriptionError = document.getElementById("descriptionError");

const modal = document.querySelector("#delete-modal");
const confirmButton = document.querySelector("#confirm-button");
const cancelButton = document.querySelector("#cancel-button");

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
        await loadTodos();

    } catch (error) {
        console.log("Error: ", error);
    }
    
});

titleInput.addEventListener("blur", validateTitle);
descriptionInput.addEventListener("blur", validateDescription);

titleInput.addEventListener("input", updateButtonState);
descriptionInput.addEventListener("input", updateButtonState);

document.addEventListener("DOMContentLoaded", () => {
    loadTodos();
});

async function loadTodos() {
    try {
        const response = await fetch("http://localhost:8080/todos", {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("[FAILED] cannot fetch todos");
        }

        const todos = await response.json();
        renderTodos(todos);
    } catch (error) {
        console.log("Error: ", error);
        todoList.innerHTML = "<p>Failed to load todos.</p>";
    }
}

function renderTodos(todos) {
    todoList.innerHTML = "";

    if (!Array.isArray(todos) || todos.length === 0) {
        todoList.classList.add("empty");
        todoList.innerHTML = "<p>No todos yet.</p>";
        return;
    }
    
    if (todoList.classList.contains("empty")) {
        todoList.classList.remove("empty")
    }
    
    todoList.classList.add("not-empty");
    todos.reverse().forEach((todo) => {
        const card = document.createElement("article");
        card.className = "todo-card";
        card.dataset.id = todo.id;

        const title = document.createElement("h2");
        title.className = "todo-card-title";
        title.textContent = todo.title ?? "(untitled)";

        const deleteButton = document.createElement("button");
        deleteButton.className = "todo-delete-button";
        deleteButton.textContent = "Delete";
        deleteButton.dataset.id = todo.id;
        

        card.appendChild(title);
        card.appendChild(deleteButton);
        todoList.appendChild(card);
    });
}

let selectedTodoID = null;
let selectedTodoCard = null;
let originalTitle = null;
let originalDescription = null;

todoList.addEventListener("click", async (event) => {
    // Click the to-do card
    const todoCard = event.target.closest(".todo-card");
    if (!todoCard) return;

    if (selectedTodoCard && selectedTodoCard !== todoCard) {
        selectedTodoCard.classList.remove("selected");
    }

    todoCard.classList.add("selected");
    selectedTodoCard = todoCard;    
    selectedTodoID = todoCard.dataset.id;

    try {
        const response = await fetch("http://localhost:8080/todos/" + selectedTodoID, {
            method: "GET"
        });
        if (!response.ok) {
            throw new Error("[FAILED] cannot fetch todo!");
        }

        const todo = await response.json();
        
        titleInput.value = todo.title ?? "";
        descriptionInput.value = todo.description ?? "";
        originalTitle = titleInput.value;
        originalDescription = descriptionInput.value;

    } catch (error) {
        console.log("Error: ", error);
        alert("Error: ", error);
    }

    doneButton.setAttribute("disable", "true");
    validateTitle();
    validateDescription();
    
    // Click delete button of a to-do
    const button = event.target.closest(".todo-delete-button");
    if (!button) return;

    selectedTodoID = button.dataset.id;

    modal.showModal();
    document.body.classList.add("modal-open");
});

confirmButton.addEventListener("click", async () => {
    await fetch("http://localhost:8080/todos/" + selectedTodoID, {
        method: "DELETE"
    });
    await loadTodos();
    modal.close();
    document.body.classList.remove("modal-open");
});

cancelButton.addEventListener("click", () => {
   modal.close();
   document.body.classList.remove("modal-open");
});


function validateTitle() {
    const value = titleInput.value.trim();

    if (value.length == 0) {
        setInvalid(titleInput, titleError, FIELD_CANNOT_EMPTY_NOTIFICATION);
        return false;
    }

    if (value.length > MAX_TITLE_LENGTH) {
        setInvalid(titleInput, titleError, "Title field must be less than 100 characters");
        return false;
    }

    setValid(titleInput, titleError);

    return true;
}

function validateDescription() {
    const value = descriptionInput.value.trim();

    if (value.length == 0) {
        setInvalid(descriptionInput, descriptionError, FIELD_CANNOT_EMPTY_NOTIFICATION);
        return false;
    }

    if (value.length > MAX_DESCIPTION_LENGTH) {
        setInvalid(descriptionInput, descriptionError, "Description field must be less than 250 characters");
        return false;
    }

    setValid(descriptionInput, descriptionError);

    return true;
}

function updateButtonState() {
    const isValid = validateTitle() && validateDescription();

    if (originalTitle && originalDescription) {
        if ((titleInput.value.trim() !== originalTitle || descriptionInput.value.trim() !== originalDescription) && isValid) {            
            doneButton.disabled = false;
            doneButton.textContent = "Save";
        }
        else {
            doneButton.disabled = true;
            doneButton.textContent = "Done";
        }
    }
    else {
        console.log("debug");
        doneButton.disabled = !isValid;
    }

}
