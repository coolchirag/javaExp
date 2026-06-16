# Frontend - User Stream UI

This frontend is plain HTML and JavaScript.

## What it does

- Shows **Fetch User Data** button to start streaming users from backend
- Renders each user in table rows as events arrive
- Shows **Clear User Data** button to clear table and stop active stream

## Run

Open `index.html` in a browser after backend is running.

Backend URL used by frontend:

- `http://localhost:8080/api/users/stream`

## Files

- `index.html` - UI layout
- `app.js` - SSE stream handling and table rendering
