type StatusText = "Idle" | "Connecting..." | "Receiving user stream..." | "Cleared" | "Stream closed or unavailable" | "Invalid user event payload";

interface User {
    id: number | string;
    name: string;
    email: string;
}

interface StreamState {
    users: User[];
    status: StatusText;
    isStreaming: boolean;
}

type StreamAction =
    | { type: "STREAM_START" }
    | { type: "USER_RECEIVED"; payload: User }
    | { type: "STREAM_ERROR"; payload?: string }
    | { type: "CLEAR" };

type Reducer<S, A> = (state: S, action: A) => S;
type Listener = () => void;

interface Store<S, A> {
    getState: () => S;
    dispatch: (action: A) => void;
    subscribe: (listener: Listener) => () => void;
}

function getRequiredElement<T extends HTMLElement>(id: string): T {
    const element = document.getElementById(id);

    if (!element) {
        throw new Error(`Missing required element: ${id}`);
    }

    return element as T;
}

function createStore<S, A>(reducer: Reducer<S, A>, preloadedState: S): Store<S, A> {
    let state = preloadedState;
    const listeners = new Set<Listener>();

    return {
        getState() {
            return state;
        },
        dispatch(action: A) {
            state = reducer(state, action);
            listeners.forEach((listener) => listener());
        },
        subscribe(listener: Listener) {
            listeners.add(listener);

            return () => {
                listeners.delete(listener);
            };
        }
    };
}

const initialState: StreamState = {
    users: [],
    status: "Idle",
    isStreaming: false
};

function streamReducer(state: StreamState = initialState, action: StreamAction): StreamState {
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
                status: (action.payload as StatusText) || "Stream closed or unavailable",
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

const fetchBtn = getRequiredElement<HTMLButtonElement>("fetchBtn");
const clearBtn = getRequiredElement<HTMLButtonElement>("clearBtn");
const userTableBody = getRequiredElement<HTMLTableSectionElement>("userTableBody");
const statusLabel = getRequiredElement<HTMLDivElement>("status");

let eventSource: EventSource | null = null;
let renderedUsersCount = 0;

function addUserRow(user: User): void {
    const row = document.createElement("tr");
    row.innerHTML = `
        <td>${user.id}</td>
        <td>${user.name}</td>
        <td>${user.email}</td>
    `;
    userTableBody.appendChild(row);
}

function render(): void {
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

function stopExistingStream(): void {
    if (eventSource) {
        eventSource.close();
        eventSource = null;
    }
}

function startStream(): void {
    stopExistingStream();
    store.dispatch({ type: "STREAM_START" });

    eventSource = new EventSource("http://localhost:8080/api/users/stream");

    eventSource.onmessage = (event: MessageEvent<string>) => {
        try {
            const user = JSON.parse(event.data) as User;
            store.dispatch({ type: "USER_RECEIVED", payload: user });
        } catch (_error) {
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
