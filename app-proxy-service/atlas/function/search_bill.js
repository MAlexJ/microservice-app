// http web client
exports = function ({query, headers, body}, response) {
    // 1. parse json body
    const jsonBody = JSON.parse(body.text());
    // 2. extract url
    const url = jsonBody["url"]
    console.log("url:", url);
    // 3. extract formUrlencodedData payload
    const data = jsonBody["formUrlencodedData"]
    let formUrlencodedData = data.map(function (val) {
        return val.key + '=' + val.value;
    }).join('&');
    console.log("formUrlencodedData:", formUrlencodedData);
    // 4. send data to webservice
    return context.http.post({
        url: url, body: formUrlencodedData, headers: {"Content-Type": ["application/x-www-form-urlencoded"]},
    }).then(response => {
        console.log("Response:", response['status']);
        return response;
    })
};