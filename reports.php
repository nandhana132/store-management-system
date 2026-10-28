<!DOCTYPE html>
<html>
<head>
    <title>Store Reports</title>
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
        .bg-burgundy { background-color: #800020; color: white; }
    </style>
</head>
<body>

<div class="sidebar">
    <h4>STORE ADMIN</h4>
    <a href="dashboard.php">Dashboard</a>
    <a href="products.php">Products</a>
    <a href="sales.php">Sales</a>
    <a href="customers.php">Customers</a>
    <a href="reports.php" class="active">Reports</a>
    <a href="index.php" style="color: #ff4d4d; margin-top: 50px;">Logout</a>
</div>

<div class="main-content">
    <div class="top-navbar">
        <h5 class="mb-0">Store Reports</h5>
        <div class="d-flex align-items-center">
            <span class="me-3 text-muted">Admin</span>
            <div style="width: 40px; height: 40px; background: #800020; color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold;">A</div>
        </div>
    </div>

    <div class="row mb-4">
        <div class="col-md-4">
            <div class="card p-4 bg-burgundy">
                <h6>Total Revenue</h6>
                <h3>₹1,45,000</h3>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-4 bg-dark text-white">
                <h6>Total Sales</h6>
                <h3>342 Transactions</h3>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card p-4 bg-secondary text-white">
                <h6>Low Stock Items</h6>
                <h3>3 Items</h3>
            </div>
        </div>
    </div>

    <div class="card p-4">
        <h5 class="mb-3">Sales Summary</h5>
        <table class="table">
            <thead>
                <tr>
                    <th>Date</th>
                    <th>Total Sales (₹)</th>
                    <th>Transactions</th>
                </tr>
            </thead>
            <tbody>
                <tr><td>Today</td><td>₹4,500</td><td>12</td></tr>
                <tr><td>Yesterday</td><td>₹6,200</td><td>18</td></tr>
                <tr><td>This Week</td><td>₹25,000</td><td>85</td></tr>
            </tbody>
        </table>
    </div>
</div>

</body>
</html>