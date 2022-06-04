function redirectProduct(areaClick) {
    const url = productPath + areaClick.attr("data-alias");
    window.location.href = url;
}

function calculateCartSize(isIncrease) {
    let cartSizeSpan = $("#cart-size");
    let size = parseInt(cartSizeSpan.text());
    if (isIncrease)
        cartSizeSpan.text(size + 1);
    else
        cartSizeSpan.text(size - 1);
}