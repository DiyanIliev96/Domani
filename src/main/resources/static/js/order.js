const payWithCashButton = document.getElementById("pay-cash");
payWithCashButton.addEventListener('click',() => {
    const cardCheckoutForm = document.querySelector("iframe");
    cardCheckoutForm.remove();
    document.forms["cash-form"].submit();
})