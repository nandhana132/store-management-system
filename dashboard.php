<!DOCTYPE html>
<html>
<head>
    <title>Store Dashboard</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f6f9; font-family: 'Segoe UI', sans-serif; margin: 0; }
        .sidebar { width: 250px; height: 100vh; position: fixed; background-color: #2b2b2b; padding-top: 20px; }
        .sidebar h4 { color: #800020; text-align: center; font-weight: bold; margin-bottom: 30px; }
        .sidebar a { display: block; color: #ccc; padding: 15px 20px; text-decoration: none; border-left: 3px solid transparent; }
        .sidebar a:hover, .sidebar a.active { background-color: #3a3a3a; color: white; border-left: 3px solid #800020; }
        .main-content { margin-left: 250px; padding: 20px; }
        .top-navbar { background: white; padding: 15px 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.05); display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px; }
        .stat-card { color: white; padding: 20px; border-radius: 10px; }
        .bg-burgundy { background-color: #800020; }
        .bg-dark-grey { background-color: #4a4a4a; }
        .bg-orange { background-color: #e67e22; }
        .bg-green { background-color: #27ae60; }
    </style>
</head>
<body>

<div class="sidebar">
    <h4>STORE ADMIN</h4>
    <a href="dashboard.php" class="active">Dashboard</a>
    <a href="products.php">Products</a>
    <a href="sales.php">Sales</a>
    <a href="customers.php">Customers</a>
    <a href="reports.php">Reports</a>
    <a href="index.php" style="color: #ff4d4d; margin-top: 50px;">Logout</a>
</div>

<div class="main-content">
    <div class="top-navbar">
        <h5 class="mb-0">Welcome, Admin! 👋</h5>
        <div class="d-flex align-items-center">
            <span class="me-3 text-muted">Today</span>
            <div style="width: 40px; height: 40px; background: #800020; color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold;">A</div>
        </div>
    </div>

    <h4 class="mb-3">Overview</h4>

    <div class="row g-3 mb-4">
        <div class="col-md-3">
            <div class="stat-card bg-burgundy">
                <h6>Total Sales</h6>
                <h2>₹45,000</h2>
            </div>
        </div>
        <div class="col-md-3">
            <div class="stat-card bg-dark-grey">
                <h6>Total Products</h6>
                <h2>120 Items</h2>
            </div>
        </div>
        <div class="col-md-3">
            <div class="stat-card bg-orange">
                <h6>Total Customers</h6>
                <h2>85 Students</h2>
            </div>
        </div>
        <div class="col-md-3">
            <div class="stat-card bg-green">
                <h6>System Status</h6>
                <h2>Online</h2>
            </div>
        </div>
    </div>

    <div class="card p-4 shadow-sm">
        <h5>Sales Overview</h5>
        <p class="text-muted">Welcome to the College Store Management System.</p>
        <div style="height: 250px; background: #f8f9fa; border: 1px dashed #ccc; border-radius: 8px; display: flex; align-items: center; justify-content: center; color: #888;">
            [ Chart Graph Placeholder ]
        </div>
    </div>
</div>

</body>
</html>