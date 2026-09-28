interface User {
    id: number;
    name: string;
    email: string;
}

function getRequiredElement<T extends HTMLElement>(id: string): T {
    const element = document.getElementById(id);

    if (!element) {
        throw new Error(`Missing required element: ${id}`);
    }

    return element as T;
}

const fetchBtn = getRequiredElement<HTMLButtonElement>("fetchBtn");
const clearBtn = getRequiredElement<HTMLButtonElement>("clearBtn");
const userTableBody = getRequiredElement<HTMLTableSectionElement>("userTableBody");
const statusLabel = getRequiredElement<HTMLDivElement>("status");

let eventSource: EventSource | null = null;

function setStatus(text: string): void {
    statusLabel.textContent = `Status: ${text}`;
}

function clearTable(): void {
    userTableBody.innerHTML = "";
}

function addUserRow(user: User): void {
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
    `;
    userTableBody.appendChild(row);
}

function stopExistingStream(): void {
    if (eventSource) {
        eventSource.close();
        eventSource = null;
    }
}

function startStream(): void {
    stopExistingStream();
    clearTable();
    setStatus("Connecting...");

    eventSource = new EventSource("http://localhost:8080/api/users/stream");

    eventSource.onmessage = (event: MessageEvent<string>) => {
        const user: User = JSON.parse(event.data);
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
