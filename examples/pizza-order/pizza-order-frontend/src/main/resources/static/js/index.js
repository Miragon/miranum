$("#form").submit(async function (event) {
    event.preventDefault();

    const data = {
        "name": $("#name").val(),
        "email": $("#email").val(),
        "drinks": $("#order-drinks").val().split(","),
        "food": $("#order-food").val().split(",")
    };

    const response = await fetch("http://localhost:8080/api/place-order",
        {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        }
    );

    response.text().then(response => {
        const text = `Your order was ${response}, you will be notified soon!`;
        $('.response').append('<br><p style="border:2px solid green; text-align: center">' + text + '</p>');
    }).catch(err => {
        console.error(err);
        $('.response').append('<br><p style="border:2px solid red; text-align: center">' + err + '</p>');
    })
});