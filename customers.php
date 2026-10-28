<!DOCTYPE html>
<html>
<head>
    <title>Store Customers</title>
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
        th { background-color: #800020 !important; color: white !important; }
    </style>
</head>
<body>

<div class="sidebar">
    <h4>STORE ADMIN</h4>
    <a href="dashboard.php">Dashboard</a>
    <a href="products.php">Products</a>
    <a href="sales.php">Sales</a>
    <a href="customers.php" class="active">Customers</a>
    <a href="#">Reports</a>
    <a href="index.php" style="color: #ff4d4d; margin-top: 50px;">Logout</a>
</div>

<div class="main-content">
    <div class="top-navbar">
        <h5 class="mb-0">Manage Customers</h5>
        <div class="d-flex align-items-center">
            <span class="me-3 text-muted">Admin</span>
            <div style="width: 40px; height: 40px; background: #800020; color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold;">A</div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4">
            <div class="card p-4 mb-4">
                <h5 class="mb-3">Add Customer</h5>
                <form onsubmit="addCustomer(event)">
                    <div class="mb-3">
                        <label>Customer Name</label>
                        <input type="text" class="form-control" id="cName" required>
                    </div>
                    <div class="mb-3">
                        <label>Phone Number</label>
                        <input type="text" class="form-control" id="cPhone" required>
                    </div>
                    <button type="submit" class="btn btn-burgundy w-100">Add Customer</button>
                </form>
            </div>
        </div>

        <div class="col-md-8">
            <div class="card p-4">
                <h5 class="mb-3">Customer List</h5>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Phone</th>
                        </tr>
                    </thead>
                    <tbody id="customerTable">
                        <tr><td>1</td><td>Arya</td><td>9534277866</td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<script>
    function addCustomer(event) {
        event.preventDefault();
        const name = document.getElementById('cName').value;
        const phone = document.getElementById('cPhone').value;
        
        const table = document.getElementById('customerTable');
        const row = table.insertRow();
        row.innerHTML = `<td>-</td><td>${name}</td><td>${phone}</td>`;
        
        document.getElementById('cName').value = '';
        document.getElementById('cPhone').value = '';
    }
</script>

</body>
</html>