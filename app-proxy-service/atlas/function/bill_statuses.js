// http web client
exports = function ({query, headers, body}, response) {
    // 1. parse json body
    const jsonBody = JSON.parse(body.text());
    // 2. extract url
    const url = jsonBody["url"]
    console.log("url:", url);
    // 3. send data to webservice
    return context.http.get({url: url});
};