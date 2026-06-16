const fetchBtn = document.getElementById("fetchBtn");
const clearBtn = document.getElementById("clearBtn");
const userTableBody = document.getElementById("userTableBody");
const statusLabel = document.getElementById("status");

let eventSource = null;

function setStatus(text) {
    statusLabel.textContent = `Status: ${text}`;
}

function clearTable() {
    userTableBody.innerHTML = "";
}

function addUserRow(user) {
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
    `;
    userTableBody.appendChild(row);
}

function stopExistingStream() {
    if (eventSource) {
        eventSource.close();
        eventSource = null;
    }
}

function startStream() {
    stopExistingStream();
    clearTable();
    setStatus("Connecting...");

    eventSource = new EventSource("http://localhost:8080/api/users/stream");

    eventSource.onmessage = (event) => {
        const user = JSON.parse(event.data);
        addUserRow(user);
        setStatus("Receiving user stream...");
    };

    eventSource.onerror = () => {
        setStatus("Stream closed or unavailable");
        stopExistingStream();
    };
}

fetchBtn.addEventListener("click", startStream);

clearBtn.addEventListener("click", () => {
    stopExistingStream();
    clearTable();
    setStatus("Cleared");
});
