<?php
	
	error_reporting(0);

	$con = mysqli_connect("localhost","root", "123123" ,"db");
	mysqli_set_charset($con,"utf8");	

	$userID = $_POST["userID"];
	$userPassword = $_POST["userPassword"];

	$statement = mysqli_prepare($con,"SELECT * FROM USERSS WHERE userID=? AND userPassword =?");
	mysqli_stmt_bind_param($statement, "ss", $userID, $userPassword);
	mysqli_stmt_execute($statement);

	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statemnet, $userID);

	$response = array();
	$response["success"] = false;

	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["userID"] = $userID;
	}

	echo json_encode($response);
?>

