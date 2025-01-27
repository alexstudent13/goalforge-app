/* Reference used in creating this: https://developers.google.com/calendar/api/quickstart/js */
/* exported gapiLoaded */
/* exported gisLoaded */
/* exported handleAuthClick */
/* exported handleSignoutClick */

// TODO(developer): Set to client ID and API key from the Developer Console
const CLIENT_ID = '844459821044-ju672n5r7fr8r5fveoe1jvur1e3p8cem.apps.googleusercontent.com';
const API_KEY = 'AIzaSyCoIcSiNBPY4Nj3l0DkRJjeM6ppZ561GC0';

// Discovery doc URL for APIs used by the quickstart
const DISCOVERY_DOC = 'https://www.googleapis.com/discovery/v1/apis/calendar/v3/rest';

// Authorization scopes required by the API; multiple scopes can be
// included, separated by spaces.
const SCOPES = 'https://www.googleapis.com/auth/calendar';

let tokenClient;
let gapiInited = false;
let gisInited = false;

document.getElementById("authorize_button").style.visibility = "hidden";
document.getElementById("signout_button").style.visibility = "hidden";
document.getElementById("event_form").style.visibility = "hidden";

/**
 * Callback after api.js is loaded.
 */
function gapiLoaded() {
    gapi.load("client", initializeGapiClient);
}

/**
 * Callback after the API client is loaded. Loads the
 * discovery doc to initialize the API.
 */
async function initializeGapiClient() {
    await gapi.client.init({
        apiKey: API_KEY,
        discoveryDocs: [DISCOVERY_DOC]
    });
    gapiInited = true;
    maybeEnableButtons();
}

/**
 * Callback after Google Identity Services are loaded.
 */
function gisLoaded() {
    tokenClient = google.accounts.oauth2.initTokenClient({
        client_id: CLIENT_ID,
        scope: SCOPES,
        callback: "" // defined later
    });
    gisInited = true;
    maybeEnableButtons();
}

/**
 * Enables user interaction after all libraries are loaded.
 */
function maybeEnableButtons() {
    if (gapiInited && gisInited) {
        document.getElementById("authorize_button").style.visibility = "visible";
    }
}

/**
 *  Sign in the user upon button click.
 */
function handleAuthClick() {
    tokenClient.callback = async resp => {
        if (resp.error !== undefined) {
            throw resp;
        }
        document.getElementById("signout_button").style.visibility = "visible";
        document.getElementById("event_form").style.visibility = "visible";
        document.getElementById("authorize_button").innerText = "Refresh";
        await listUpcomingEvents();
    };

    if (gapi.client.getToken() === null) {
        // Prompt the user to select a Google Account and ask for consent to share their data
        // when establishing a new session.
        tokenClient.requestAccessToken({ prompt: "consent" });
    } else {
        // Skip display of account chooser and consent dialog for an existing session.
        tokenClient.requestAccessToken({ prompt: "" });
    }
}

/**
 *  Sign out the user upon button click.
 */
function handleSignoutClick() {
    const token = gapi.client.getToken();
    if (token !== null) {
        google.accounts.oauth2.revoke(token.access_token);
        gapi.client.setToken("");
        document.getElementById("content").innerText = "";
        document.getElementById("authorize_button").innerText = "Authorize";
        document.getElementById("signout_button").style.visibility = "hidden";
        document.getElementById("event_form").style.visibility = "hidden";
        // document.getElementById("calendarLink").style.display = "inline"; // Show the calendar link
    }
}

/**
 * Print the summary and start datetime/date of the next ten events in
 * the authorized user's calendar. If no events are found an
 * appropriate message is printed.
 */
async function listUpcomingEvents() {
    let response;
    try {
        const request = {
            'calendarId': 'primary',
            'timeMin': (new Date()).toISOString(),
            'showDeleted': false,
            'singleEvents': true,
            'maxResults': 10,
            'orderBy': 'startTime',
        };
        response = await gapi.client.calendar.events.list(request);
    } catch (err) {
        document.getElementById('content').innerText = err.message;
        return;
    }

    const events = response.result.items;
    if (!events || events.length == 0) {
        document.getElementById('content').innerText = 'No events found.';
        return;
    }
    // Flatten to string to display
    const output = events.reduce(
        (str, event) => `${str}${event.summary} (${event.start.dateTime || event.start.date})\n`,
        'Events:\n');
    document.getElementById('content').innerText = output;
    document.getElementById("calendarLink").style.display = "inline"; // Show the calendar link
}

const addEvent = () => {
    const title = document.getElementById("title").value;
    const desc = document.getElementById("desc").value;
    const date = document.getElementById("date").value;
    const start = document.getElementById("st").value;
    const end = document.getElementById("et").value;

    // Calculate start and end Date objects
    const startDate = new Date(`${date}T${start}`);
    const endDate = new Date(`${date}T${end}`);

    // If the end time is less than the start time, adjust the end date to the next day using this if statement
    if (endDate <= startDate) {
        endDate.setDate(endDate.getDate() + 1);
    }

    const event = {
        summary: title,
        location: "Google Meet",
        description: desc,
        start: {
            dateTime: startDate.toISOString(),
            timeZone: "America/Los_Angeles"
        },
        end: {
            dateTime: endDate.toISOString(),
            timeZone: "America/Los_Angeles"
        },
        recurrence: ["RRULE:FREQ=DAILY;COUNT=1"],
        reminders: {
            useDefault: false,
            overrides: [
                { method: "email", minutes: 24 * 60 },
                { method: "popup", minutes: 5 * 60 }
            ]
        }
    };

    console.log(event);
    var request = gapi.client.calendar.events.insert({
        calendarId: "primary",
        resource: event
    });

    request.execute(function(resp) {
        console.log('Event created: ', resp.htmlLink);
        if (!resp.error) {
            document.getElementById('content').innerText = `Event created: ${resp.htmlLink}`;
        } else {
            console.log('Error creating event: ', resp.error);
            document.getElementById('content').innerText = 'Error creating event.';
        }
    });
};