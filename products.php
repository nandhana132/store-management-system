<!DOCTYPE html>
<html>
<head>
    <title>Store Products</title>
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

<!-- Sidebar -->
<div class="sidebar">
    <h4>STORE ADMIN</h4>
    <a href="dashboard.php">Dashboard</a>
    <a href="products.php" class="active">Products</a>
    <a href="#">Sales</a>
    <a href="#">Customers</a>
    <a href="#">Reports</a>
    <a href="index.php" style="color: #ff4d4d; margin-top: 50px;">Logout</a>
</div>

<!-- Main Content -->
<div class="main-content">
    <div class="top-navbar">
        <h5 class="mb-0">Manage Inventory</h5>
        <div class="d-flex align-items-center">
            <span class="me-3 text-muted">Admin</span>
            <div style="width: 40px; height: 40px; background: #800020; color: white; border-radius: 50%; display: flex; align-items: center; justify-content: center; font-weight: bold;">A</div>
        </div>
    </div>

    <div class="row">
        <!-- Add Product Form -->
        <div class="col-md-4">
            <div class="card p-4 mb-4">
                <h5 class="mb-3">Add New Product</h5>
                <form onsubmit="addProduct(event)">
                    <div class="mb-3">
                        <label>Product Name</label>
                        <input type="text" class="form-control" id="pName" required>
                    </div>
                    <div class="mb-3">
                        <label>Price (₹)</label>
                        <input type="number" class="form-control" id="pPrice" required>
                    </div>
                    <div class="mb-3">
                        <label>Stock Quantity</label>
                        <input type="number" class="form-control" id="pStock" required>
                    </div>
                    <button type="submit" class="btn btn-burgundy w-100">Add Product</button>
                </form>
            </div>
        </div>

        <!-- Product List -->
        <div class="col-md-8">
            <div class="card p-4">
                <h5 class="mb-3">Product List</h5>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>ID</th>
                            <th>Name</th>
                            <th>Price</th>
                            <th>Stock</th>
                            <th>Status</th>
                        </tr>
                    </thead>
                    <tbody id="productTable">
                        <tr><td>1</td><td>Pen</td><td>₹10</td><td>50</td><td><span class="badge bg-success">In Stock</span></td></tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>

<!-- JavaScript to make the form work for the demo -->
<script>
    function addProduct(event) {
        event.preventDefault(); // Prevents page reload
        const name = document.getElementById('pName').value;
        const price = document.getElementById('pPrice').value;
        const stock = document.getElementById('pStock').value;
        
        const table = document.getElementById('productTable');
        const row = table.insertRow();
        row.innerHTML = `<td>-</td><td>${name}</td><td>₹${price}</td><td>${stock}</td><td><span class="badge bg-success">In Stock</span></td>`;
        
        // Clear form
        document.getElementById('pName').value = '';
        document.getElementById('pPrice').value = '';
        document.getElementById('pStock').value = '';
    }
</script>

</body>
</html>