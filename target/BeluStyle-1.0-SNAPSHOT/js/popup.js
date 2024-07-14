// Get modal elements
const modal = document.getElementById("myModal");
const btn = document.getElementById("openModal");
const closeBtn = document.getElementsByClassName("close-button")[0];
const closeModalBtn = document.getElementById("closeModal");
const submitBtn = document.getElementById("submitBtn");

// Open the modal
btn.onclick = function () {
    modal.style.display = "block";
    setTimeout(() => modal.classList.add('active'), 10); // Slight delay for smooth fade-in
}

// Close the modal when the close button or close modal button are clicked
closeBtn.onclick = closeModal;
closeModalBtn.onclick = closeModal;

function closeModal() {
    modal.classList.remove('active'); // Start fade-out
    setTimeout(() => modal.style.display = "none", 300); // Hide after transition
}

// Close the modal when clicking outside of the modal content
window.onclick = function (event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}

// Form validation
function validateForm() {
    const inputField = document.getElementById("inputField");
    if (inputField.value.trim() === "") {
        alert("Input field cannot be empty");
        return false;
    }
    return true;
}

// Submit form to the modal if validation passes
submitBtn.onclick = function () {
    if (validateForm()) {
        // Perform the submission logic
        alert("Form submitted successfully");
        closeModal(); // Close the modal after submission
    }
}