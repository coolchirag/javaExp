# Frontend - User Stream UI

This frontend is plain HTML with a TypeScript source file and browser-ready JavaScript.

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
- `app.ts` - TypeScript source for SSE stream handling and table rendering
- `app.js` - Browser-ready JavaScript loaded by `index.html`
