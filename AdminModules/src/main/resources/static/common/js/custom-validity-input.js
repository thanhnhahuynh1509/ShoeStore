
function requiredInput(input, message) {
    if(input.value === null || input.value.length <= 0) {
        input.setCustomValidity(message)
    } else {
        input.setCustomValidity("");
    }
}

function passwordMatch(rePassword, message) {
    let passwordVal = $("#password").val();
    let rePasswordVal = rePassword.value;
    if(rePasswordVal !== passwordVal) {
        rePassword.setCustomValidity(message);
    } else {
        rePassword.setCustomValidity("");
    }
}