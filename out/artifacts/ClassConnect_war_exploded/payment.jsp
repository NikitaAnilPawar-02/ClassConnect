<%
    Integer studentId = (Integer) session.getAttribute("studentId");
    Integer studentStandard = (Integer) session.getAttribute("studentStandard");
    double studentFee = 0;

    if (studentStandard == 8) studentFee = 8000;
    else if (studentStandard == 9) studentFee = 9000;
    else if (studentStandard == 10) studentFee = 10000;
%>

<script src="https://checkout.razorpay.com/v1/checkout.js"></script>

<button id="pay-btn" class="btn btn-primary">Pay Now</button>

<script>
    document.getElementById("pay-btn").onclick = function () {
        fetch("CreateOrderServlet", {
            method: "POST",
            headers: { "Content-Type": "application/x-www-form-urlencoded" },
            body: "studentId=<%=studentId%>&amount=<%=studentFee%>"
        })
        .then(response => response.json())
        .then(order => {
            var options = {
                key: "rzp_test_bGve6FnJr1tdg7",
                amount: order.amount,
                currency: "INR",
                name: "Gurukrupa Classes",
                description: "Fee Payment",
                order_id: order.id,
                handler: function (response) {
                    fetch("PaymentSuccessServlet", {
                        method: "POST",
                        headers: { "Content-Type": "application/x-www-form-urlencoded" },
                        body: "studentId=<%=studentId%>&amountPaid=<%=studentFee%>&razorpay_payment_id=" + response.razorpay_payment_id
                    }).then(() => window.location.href = "paymentConfirmation.jsp");
                }
            };
            var rzp = new Razorpay(options);
            rzp.open();
        })
        .catch(error => console.error("Error:", error));
    };
</script>
