# Store-Driven Reactive UI Flow (`app-store.js`)

This document explains the technical approach used in `app-store.js`, where incoming SSE data is first committed to a Redux-like store and only then reflected in the UI.

---

## Why this approach

Instead of mutating the DOM directly inside network callbacks, this approach introduces a state container in the middle:

1. Receive event from backend stream.
2. Dispatch an action to store.
3. Reducer computes next immutable state.
4. Subscribers react to state change and render UI.

This creates a predictable, traceable data flow and keeps fetching logic separated from rendering logic.

---

## Core architecture

`app-store.js` is split into these units:

- DOM helper: `getRequiredElement(id)` for fail-fast element lookup.
- Store engine: `createStore(reducer, preloadedState)`.
- State model: `initialState` with `users`, `status`, `isStreaming`.
- State transitions: `streamReducer(state, action)`.
- Stream side effects: `startStream()`, `stopExistingStream()`, `EventSource` handlers.
- Renderer: `render()` subscribed via `store.subscribe(render)`.

Data flow direction is always:

`EventSource -> dispatch(action) -> reducer -> new state -> render() -> DOM update`

---

## Store internals

`createStore` provides:

- `getState()`: returns current state snapshot.
- `dispatch(action)`: applies reducer and notifies listeners.
- `subscribe(listener)`: registers listener and returns `unsubscribe()`.

Listeners are maintained in a `Set`, preventing duplicate listener registrations and allowing clean removal.

---

## State model

Initial state:

- `users: []` - accumulated stream items.
- `status: "Idle"` - text shown in status label.
- `isStreaming: false` - stream status flag.

State is treated immutably in reducer updates:

- Arrays are cloned (`[...state.users, action.payload]`).
- Objects are copied with spread (`{ ...state, ...changes }`).

---

## Action contract

### `STREAM_START`
- Triggered when user clicks **Fetch User Data**.
- Clears old users.
- Sets status to `Connecting...`.
- Marks stream active.

### `USER_RECEIVED`
- Triggered for each valid SSE message.
- Appends one user to `users`.
- Updates status to `Receiving user stream...`.

### `STREAM_ERROR`
- Triggered on SSE error or invalid JSON payload.
- Sets a readable error/unavailable status.
- Marks stream inactive.

### `CLEAR`
- Triggered when user clicks **Clear User Data**.
- Stops stream, empties users, sets status to `Cleared`.

---

## Rendering strategy (reactive UI)

`render()` always reads the latest store state and updates UI from state only.

### Status rendering
- `statusLabel.textContent` is derived from `state.status`.

### Table rendering optimization
- `renderedUsersCount` tracks how many rows are already in DOM.
- On each render, only new users (`state.users[renderedUsersCount ... end]`) are appended.
- If user list shrinks (e.g., `STREAM_START` or `CLEAR`), table is reset and count is rewound.

This avoids full table re-render on every event and still preserves state-driven rendering.

---

## Stream lifecycle

### Start flow
1. `startStream()` is called.
2. Existing stream is closed (`stopExistingStream()`).
3. `STREAM_START` action dispatched.
4. New `EventSource` opens `http://localhost:8080/api/users/stream`.

### On each message
1. Parse JSON payload.
2. Dispatch `USER_RECEIVED`.
3. Store notifies subscribers.
4. `render()` appends next row.

### On parse or stream error
1. Dispatch `STREAM_ERROR` with message.
2. Close active stream.
3. UI reflects non-streaming status.

### Clear flow
1. Close stream.
2. Dispatch `CLEAR`.
3. Renderer clears table and updates status.

---

## Safety and edge behavior

- Missing required DOM elements throw early with explicit message.
- Multiple fetch clicks do not create multiple open connections (existing stream is closed first).
- Invalid event payload is handled gracefully through `STREAM_ERROR`.
- UI is initialized once at startup via:
  - `store.subscribe(render)`
  - `render()`

---

## Practical benefits

- Predictable updates: all UI changes are derived from state.
- Better debugging: inspect actions and resulting state transitions.
- Easier extension: add filters/sorting/pagination by expanding reducer + render logic.
- Separation of concerns:
  - stream handling = side effects,
  - reducer = state transitions,
  - render = DOM projection.

---

## Possible next improvements

- Add action creators to avoid hardcoded action objects.
- Add runtime validation for user payload shape (`id`, `name`, `email`).
- Migrate file to TypeScript (`app-store.ts`) with typed state/actions.
- Add batching/debouncing if stream rate grows high.
- Add unsubscribe hooks for teardown in larger app lifecycles.
