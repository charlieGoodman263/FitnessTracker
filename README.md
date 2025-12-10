# Intro

Console-based fitness tracker with client + admin flows. Clients log session templates with weight and RPE; admins manage templates and review users.
# Run

**Requires Java 8+; no external libraries.**
- Main app: run src/com/fitness/app/Main.java.
- Tests: run src/com/fitness/tests/TestSuite.java.
# Accounts & data

- Admin login: admin / password.
- userLoginInfo.txt holds credentials; signing up appends a new client.
- User data is stored under userSessions/<id>; shared templates live in userSessions/templates.
# What you can do

- Client: view logged sessions, log a template with weight + RPE (0 - 10 integer), view personal bests.
- Admin: list users, view a client’s sessions/PBs, add/delete shared templates.
# Notes

- RPE = Rate of Perceived Exertion (0–10, 0 = rest, 10 = max effort).
