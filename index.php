<!DOCTYPE html>
<html>
<head>
    <title>College Store Login</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { font-family: 'Segoe UI', sans-serif; background-color: #2b2b2b; height: 100vh; display: flex; align-items: center; justify-content: center; margin: 0; }
        .login-card { width: 400px; background: #3a3a3a; padding: 40px; border-radius: 10px; border-top: 5px solid #800020; box-shadow: 0 10px 25px rgba(0,0,0,0.5); color: white; }
        h3 { color: #800020; text-align: center; font-weight: bold; margin-bottom: 30px; letter-spacing: 1px; }
        label { color: #cccccc; font-size: 14px; }
        input { background: #222; border: 1px solid #555; color: white; padding: 10px; width: 100%; margin-bottom: 20px; border-radius: 5px; box-sizing: border-box; }
        input:focus { border-color: #800020; outline: none; }
        button { background-color: #800020; color: white; font-weight: bold; width: 100%; padding: 12px; border: none; border-radius: 5px; font-size: 16px; cursor: pointer; }
        button:hover { background-color: #5c0017; }
    </style>
</head>
<body>
    <div class="login-card">
        <h3>STORE LOGIN</h3>
        <form action="dashboard.php">
            <div class="mb-3">
                <label>Username</label>
                <input type="text" name="username" required>
            </div>
            <div class="mb-3">
                <label>Password</label>
                <input type="password" name="password" required>
            </div>
            <button type="submit">LOGIN</button>
        </form>
    </div>
</body>
</html>