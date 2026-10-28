<!DOCTYPE html>
<html>
<head>
    <title>Record Sale</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f6f9; font-family: 'Segoe UI', sans-serif; margin: 0; }
        .sidebar { width: 250px; height: 100vh; position: fixed; background-color: #2b2b2b; padding-top: 20px; }
        .sidebar h4 { color: #800020; text-align: center; font-weight: bold; margin-bottom: 30px; }
        .sidebar a { display: block; color: #ccc; padding: 15px 20px; text-decoration: none; border-left: 3px solid transparent; }
        .sidebar a:hover, .sidebar a.active { background-color: #3a3a3a; color: white; border-left: 3px solid #800020; }
        .main-content { margin-left: 250px; padding: 20px; }
        .top-navbar { background: white; padding: 15px 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .card { border: none; border-radius: 10px; box-shadow: 0 4px 6px rgba(0,0,0,0.05); }
        .btn-burgundy { background-color: #800020; color: white; }
        .btn-burgundy:hover { background-color: #5c0017; color: white; }
    </style>
</head>
<body>

<!-- Sidebar -->
<div class="sidebar">
    <h4>STORE ADMIN</h4>
    <a href="dashboard.php">Dashboard</a>
    <a href="products.php">Products</a>
    <a href="sales.php" class="active">Sales</a>
    <a href="#">Customers</a>
    <a href="#">Reports</a>
    <a href="index.php" style="color: #ff4d4d; margin-top: 50px;">Logout</a>
</div>

<!-- Main Content -->
<div class="main-content">
    <div class="top-navbar">
        <h5 class="mb-0">Record a Sale</h5>
        <div class="d-flex align-items-center">
            <span class="me-3 text-muted">Admin</span>
            <div style="width: 40px; height: 40px; background: #800020; color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold;">A</div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-6">
            <div class="card p-4">
                <h5 class="mb-3">New Sale</h5>
                <form onsubmit="calculateBill(event)">
                    <div class="mb-3">
                        <label>Select Product</label>
                        <select class="form-control" id="saleProduct" onchange="updatePrice()">
                            <option value="Pen" data-price="10">Pen (₹10)</option>
                            <option value="Notebook" data-price="50">Notebook (₹50)</option>
                            <option value="Eraser" data-price="5">Eraser (₹5)</option>
                        </select>
                    </div>
                    <div class="mb-3">
                        <label>Quantity</label>
                        <input type="number" class="form-control" id="saleQty" min="1" value="1" oninput="updatePrice()">
                    </div>
                    <div class="mb-3">
                        <label>Total Bill (₹)</label>
                        <input type="text" class="form-control" id="saleTotal" readonly style="font-weight: bold; font-size: 18px; color: #800020;">
                    </div>
                    <button type="submit" class="btn btn-burgundy w-100">Record Sale</button>
                </form>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript for automatic bill calculation -->
<script>
    function updatePrice() {
        const select = document.getElementById('saleProduct');
        const price = select.options[select.selectedIndex].getAttribute('data-price');
        const qty = document.getElementById('saleQty').value;
        const total = price * qty;
        document.getElementById('saleTotal').value = total.toFixed(2);
    }

    function calculateBill(event) {
        event.preventDefault();
        const product = document.getElementById('saleProduct').value;
        const qty = document.getElementById('saleQty').value;
        const total = document.getElementById('saleTotal').value;
        
        alert(`Sale Recorded!\n\nProduct: ${product}\nQuantity: ${qty}\nTotal Bill: ₹${total}`);
        
        // Reset form
        document.getElementById('saleQty').value = 1;
        updatePrice();
    }

    // Run on page load to show initial price
    updatePrice();
</script>

</body>
</html>