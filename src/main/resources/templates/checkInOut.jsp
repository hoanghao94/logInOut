<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Check In/Out</title>
    <style>
        * {
            box-sizing: border-box;
        }
        body {
            font-family: Arial, sans-serif;
        }
        table {
            border-collapse: collapse;
            width: 100%;
            margin: auto;
        }
        th, td {
            padding: 8px;
            text-align: center;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f2f2f2;
        }
        tr:hover {
            background-color: #ddd;
        }
        .button {
            background-color: #4CAF50;
            color: white;
            border: none;
            padding: 8px 16px;
            text-align: center;
            text-decoration: none;
            display: inline-block;
            font-size: 14px;
            margin: 4px 2px;
            cursor: pointer;
            border-radius: 8px;
        }
        .button:hover {
            opacity: 0.8;
        }
        .form-group {
            display: flex;
            align-items: center;
            margin: 8px;
        }
        .form-group label {
            flex-basis: 120px;
            font-weight: bold;
        }
        .form-group input[type="text"] {
            flex-grow: 1;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
    </style>
</head>
<body>
<h1>TimeSheet</h1>
<form>
    <table>
        <tr>
            <th>Check In</th>
            <th>Check Out</th>
        </tr>
        <tr>
            <td>7:58:15</td>
            <td></td>
        </tr>
    </table>
    <div class="form-group">
        <label for="checkin">Check In</label>
        <input type="text" id="checkin" name="checkin" value="7:58:15" readonly>
        <button class="button" type="button">Check In</button>
    </div>
    <div class="form-group">
        <label for="checkout">Check Out</label>
        <input type="text" id="checkout" name="checkout">
        <button class="button" type="button">Check Out</button>
    </div>
</form>
<div class="log">
    <h2>LOG</h2>
    <table>
        <tr>
            <th>Date</th>
            <th>Time</th>
            <th>User</th>
            <th>Action</th>
        </tr>
        <tr>
            <td>5/4/2023</td>
            <td>8:00:00 AM</td>
            <td>TEN-</td>
            <td>Action</td>
        <tr>
        </table>
</div>
