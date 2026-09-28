function getRequiredElement(id) {
    const element = document.getElementById(id);

    if (!element) {
        throw new Error(`Missing required element: ${id}`);
    }

    return element;
}

function createStore(reducer, preloadedState) {
    let state = preloadedState;
    const listeners = new Set();

    return {
        getState() {
            return state;
        },
        dispatch(action) {
            state = reducer(state, action);
            listeners.forEach((listener) => listener());
        },
        subscribe(listener) {
            listeners.add(listener);

            return () => {
                listeners.delete(listener);
            };
        }
    };
}

const initialState = {
    users: [],
    status: "Idle",
    isStreaming: false
};

function streamReducer(state = initialState, action) {
    switch (action.type) {
        case "STREAM_START":
            return {
                ...state,
                users: [],
                status: "Connecting...",
                isStreaming: true
            };
        case "USER_RECEIVED":
            return {
                ...state,
                users: [...state.users, action.payload],
                status: "Receiving user stream...",
                isStreaming: true
            };
        case "STREAM_ERROR":
            return {
                ...state,
                status: action.payload || "Stream closed or unavailable",
                isStreaming: false
            };
        case "CLEAR":
            return {
                ...state,
                users: [],
                status: "Cleared",
                isStreaming: false
            };
        default:
            return state;
    }
}

const store = createStore(streamReducer, initialState);

const fetchBtn = getRequiredElement("fetchBtn");
const clearBtn = getRequiredElement("clearBtn");
const userTableBody = getRequiredElement("userTableBody");
const statusLabel = getRequiredElement("status");

let eventSource = null;
let renderedUsersCount = 0;

function addUserRow(user) {
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
    `;
    userTableBody.appendChild(row);
}

function render() {
    const state = store.getState();
    statusLabel.textContent = `Status: ${state.status}`;

    if (state.users.length < renderedUsersCount) {
        userTableBody.innerHTML = "";
        renderedUsersCount = 0;
    }

    for (let i = renderedUsersCount; i < state.users.length; i += 1) {
        addUserRow(state.users[i]);
    }

    renderedUsersCount = state.users.length;
}

function stopExistingStream() {
    if (eventSource) {
        eventSource.close();
        eventSource = null;
    }
}

function startStream() {
    stopExistingStream();
    store.dispatch({ type: "STREAM_START" });

    eventSource = new EventSource("http://localhost:8080/api/users/stream");

    eventSource.onmessage = (event) => {
        try {
            const user = JSON.parse(event.data);
            store.dispatch({ type: "USER_RECEIVED", payload: user });
        } catch (error) {
            store.dispatch({ type: "STREAM_ERROR", payload: "Invalid user event payload" });
            stopExistingStream();
        }
    };

    eventSource.onerror = () => {
        store.dispatch({ type: "STREAM_ERROR", payload: "Stream closed or unavailable" });
        stopExistingStream();
    };
}

store.subscribe(render);
render();

fetchBtn.addEventListener("click", startStream);
clearBtn.addEventListener("click", () => {
    stopExistingStream();
    store.dispatch({ type: "CLEAR" });
});
